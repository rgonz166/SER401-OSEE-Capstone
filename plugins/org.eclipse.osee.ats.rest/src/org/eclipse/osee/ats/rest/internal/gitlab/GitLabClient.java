package org.eclipse.osee.ats.rest.internal.gitlab;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.eclipse.osee.ats.api.gitlab.GitLabAuthResult;
import org.eclipse.osee.ats.api.gitlab.GitLabIssue;
import org.eclipse.osee.ats.api.gitlab.GitLabIssueUpdate;
import org.eclipse.osee.ats.api.gitlab.GitLabTeamSettings;

public final class GitLabClient {

   static final int MIN_TOKEN_LENGTH = 8;

   private final GitLabHttp http;
   private final ObjectMapper mapper = new ObjectMapper();

   public GitLabClient(GitLabHttp http) {
      this.http = http;
   }

   public GitLabAuthResult authenticate(GitLabTeamSettings settings) {
      GitLabTeamSettings ready = connectionSettings(settings);
      String url = ready.getBaseUrl() + "/api/v4/user";
      GitLabHttpResponse response = send("GET", url, ready.getAccessToken(), null);
      JsonNode node = readTree(response.getBody());
      String username = node.path("username").asText("");
      if (username.isBlank()) {
         throw new GitLabClientException("GitLab authentication response did not include a username.");
      }
      GitLabAuthResult result = new GitLabAuthResult();
      result.setAuthenticated(true);
      result.setUsername(username);
      result.setMessage("Authenticated as " + username + ".");
      return result;
   }

   public GitLabIssue readIssue(GitLabTeamSettings settings, long issueIid) {
      GitLabTeamSettings ready = connectionSettings(settings);
      String url = issueUrl(ready, issueIid);
      GitLabHttpResponse response = send("GET", url, ready.getAccessToken(), null);
      return parseIssue(response.getBody());
   }

   public GitLabIssue updateIssue(GitLabTeamSettings settings, long issueIid, GitLabIssueUpdate update) {
      GitLabTeamSettings ready = connectionSettings(settings);
      if (update == null || !update.hasChanges()) {
         throw new GitLabClientException("Issue update is empty.");
      }
      String url = issueUrl(ready, issueIid);
      GitLabHttpResponse response = send("PUT", url, ready.getAccessToken(), updateBody(update));
      return parseIssue(response.getBody());
   }

   public static boolean hasConnectionSettings(GitLabTeamSettings settings) {
      if (settings == null) {
         return false;
      }
      String token = trim(settings.getAccessToken());
      return !trim(settings.getBaseUrl()).isEmpty() && !trim(settings.getProjectId()).isEmpty()
         && token.length() >= MIN_TOKEN_LENGTH;
   }

   public static String normalizeBaseUrl(String baseUrl) {
      String value = trim(baseUrl);
      if (value.isEmpty()) {
         throw new GitLabClientException("GitLab base URL is required.");
      }
      if (value.indexOf('\\') >= 0) {
         throw new GitLabClientException("GitLab base URL is not valid.");
      }
      URI uri;
      try {
         uri = new URI(value);
      } catch (Exception ex) {
         throw new GitLabClientException("GitLab base URL is not valid.");
      }
      String scheme = uri.getScheme();
      if (scheme == null || (!"https".equalsIgnoreCase(scheme) && !"http".equalsIgnoreCase(scheme))) {
         throw new GitLabClientException("GitLab base URL must use http or https.");
      }
      if (uri.getHost() == null || uri.getUserInfo() != null || uri.getRawQuery() != null || uri.getRawFragment() != null) {
         throw new GitLabClientException("GitLab base URL must not include credentials, a query, or a fragment.");
      }
      String path = uri.getRawPath() == null ? "" : uri.getRawPath();
      while (path.endsWith("/")) {
         path = path.substring(0, path.length() - 1);
      }
      String port = uri.getPort() < 0 ? "" : ":" + uri.getPort();
      return scheme.toLowerCase() + "://" + uri.getHost() + port + path;
   }

   private GitLabHttpResponse send(String method, String url, String token, String body) {
      GitLabHttpResponse response = http.execute(new GitLabRequest(method, url, token, body));
      int status = response.getStatusCode();
      if (status >= 300 && status < 400) {
         throw new GitLabClientException("GitLab redirects are not followed.", status);
      }
      if (status < 200 || status >= 300) {
         throw new GitLabClientException(failureMessage(status), status);
      }
      return response;
   }

   private static String failureMessage(int status) {
      if (status == 401 || status == 403) {
         return "GitLab rejected the access token.";
      }
      if (status == 404) {
         return "GitLab issue or project was not found.";
      }
      return "GitLab request failed with HTTP " + status + ".";
   }

   private GitLabTeamSettings connectionSettings(GitLabTeamSettings settings) {
      if (!hasConnectionSettings(settings)) {
         throw new GitLabClientException("GitLab base URL, project, and access token are required.");
      }
      String token = trim(settings.getAccessToken());
      String project = trim(settings.getProjectId());
      String base = normalizeBaseUrl(settings.getBaseUrl());
      if (containsToken(base, token) || containsToken(project, token)) {
         throw new GitLabClientException("The access token must be sent only as a request header.");
      }
      if (project.indexOf(' ') >= 0 || project.indexOf('\n') >= 0 || project.indexOf('\r') >= 0 || project.indexOf('\\') >= 0) {
         throw new GitLabClientException("GitLab project id is not valid.");
      }
      GitLabTeamSettings ready = settings.copy();
      ready.setBaseUrl(base);
      ready.setProjectId(project);
      ready.setAccessToken(token);
      return ready;
   }

   private String issueUrl(GitLabTeamSettings settings, long issueIid) {
      if (issueIid <= 0) {
         throw new GitLabClientException("GitLab issue iid must be a positive number.");
      }
      return settings.getBaseUrl() + "/api/v4/projects/" + encodeProject(settings.getProjectId()) + "/issues/" + issueIid;
   }

   static String encodeProject(String projectId) {
      boolean numeric = !projectId.isEmpty();
      for (int i = 0; i < projectId.length(); i++) {
         if (!Character.isDigit(projectId.charAt(i))) {
            numeric = false;
            break;
         }
      }
      if (numeric) {
         return projectId;
      }
      return URLEncoder.encode(projectId, StandardCharsets.UTF_8).replace("+", "%20");
   }

   private String updateBody(GitLabIssueUpdate update) {
      ObjectNode payload = mapper.createObjectNode();
      if (update.getTitle() != null) {
         if (update.getTitle().isBlank()) {
            throw new GitLabClientException("GitLab issue title cannot be blank.");
         }
         payload.put("title", update.getTitle());
      }
      if (update.getDescription() != null) {
         payload.put("description", update.getDescription());
      }
      if (update.getStateEvent() != null) {
         String stateEvent = update.getStateEvent().trim();
         if (!"close".equals(stateEvent) && !"reopen".equals(stateEvent)) {
            throw new GitLabClientException("GitLab state event must be close or reopen.");
         }
         payload.put("state_event", stateEvent);
      }
      try {
         return mapper.writeValueAsString(payload);
      } catch (Exception ex) {
         throw new GitLabClientException("GitLab issue update could not be written.");
      }
   }

   private GitLabIssue parseIssue(String body) {
      JsonNode node = readTree(body);
      long iid = node.path("iid").asLong(0);
      if (iid <= 0) {
         throw new GitLabClientException("GitLab issue response did not include an iid.");
      }
      GitLabIssue issue = new GitLabIssue();
      issue.setIid(iid);
      issue.setTitle(node.path("title").asText(""));
      issue.setDescription(node.path("description").asText(""));
      issue.setState(node.path("state").asText(""));
      issue.setWebUrl(node.path("web_url").asText(""));
      return issue;
   }

   private JsonNode readTree(String body) {
      try {
         return mapper.readTree(body);
      } catch (Exception ex) {
         throw new GitLabClientException("GitLab returned a response that could not be read.");
      }
   }

   private static boolean containsToken(String value, String token) {
      return value != null && token != null && !token.isEmpty() && value.contains(token);
   }

   private static String trim(String value) {
      return value == null ? "" : value.trim();
   }
}
