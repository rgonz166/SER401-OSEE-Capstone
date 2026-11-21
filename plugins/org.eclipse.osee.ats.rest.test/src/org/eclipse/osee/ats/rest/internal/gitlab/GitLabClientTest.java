package org.eclipse.osee.ats.rest.internal.gitlab;

import org.eclipse.osee.ats.api.gitlab.GitLabAuthResult;
import org.eclipse.osee.ats.api.gitlab.GitLabIssue;
import org.eclipse.osee.ats.api.gitlab.GitLabIssueUpdate;
import org.eclipse.osee.ats.api.gitlab.GitLabTeamSettings;
import org.junit.Assert;
import org.junit.Test;

public class GitLabClientTest {

   private final RecordingHttp http = new RecordingHttp();
   private final GitLabClient client = new GitLabClient(http);

   @Test
   public void testAuthenticate() {
      http.status = 200;
      http.body = "{\"id\":7,\"username\":\"ali\"}";

      GitLabAuthResult result = client.authenticate(settings("ali-gitlab-token", "ser401/osee"));

      Assert.assertEquals("ali", result.getUsername());
      Assert.assertEquals("https://gitlab.com/api/v4/user", http.url);
      Assert.assertEquals("ali-gitlab-token", http.token);
   }

   @Test
   public void testReadIssue() {
      http.status = 200;
      http.body = "{\"iid\":36,\"title\":\"Move the team filter\",\"description\":\"Raul Gonzalez\",\"state\":\"opened\",\"web_url\":\"https://gitlab.com/ser401/osee/-/issues/36\"}";

      GitLabIssue issue = client.readIssue(settings("raul-board-token", "ser401/osee"), 36);

      Assert.assertEquals(36L, issue.getIid());
      Assert.assertEquals("Move the team filter", issue.getTitle());
      Assert.assertEquals("opened", issue.getState());
   }

   @Test
   public void testUpdateIssue() {
      http.status = 200;
      http.body = "{\"iid\":34,\"title\":\"Add the workflow filter\",\"description\":\"Liezel Vallar\",\"state\":\"closed\",\"web_url\":\"https://gitlab.com/ser401/osee/-/issues/34\"}";
      GitLabIssueUpdate update = new GitLabIssueUpdate();
      update.setTitle("Add the workflow filter");
      update.setStateEvent("close");

      GitLabIssue issue = client.updateIssue(settings("liezel-filter-token", "ser401/osee"), 34, update);

      Assert.assertEquals("PUT", http.method);
      Assert.assertEquals("closed", issue.getState());
      Assert.assertTrue(http.requestBody.contains("Add the workflow filter"));
   }

   @Test
   public void testBadToken() {
      http.status = 401;
      http.body = "mina-save-token";

      try {
         client.authenticate(settings("mina-save-token", "ser401/osee"));
         Assert.fail("should have failed");
      } catch (GitLabClientException ex) {
         Assert.assertEquals("GitLab rejected the access token.", ex.getMessage());
      }
   }

   @Test
   public void testNoToken() {
      GitLabTeamSettings settings = settings("kai-column-token", "ser401/osee");
      settings.setAccessToken("");

      try {
         client.readIssue(settings, 48);
         Assert.fail("should have failed");
      } catch (GitLabClientException ex) {
         Assert.assertEquals("GitLab base URL, project, and access token are required.", ex.getMessage());
      }
   }

   @Test
   public void testRedirect() {
      http.status = 302;
      http.body = "https://gitlab.com/users/sign_in";

      try {
         client.readIssue(settings("liezel-filter-token", "ser401/osee"), 34);
         Assert.fail("should have failed");
      } catch (GitLabClientException ex) {
         Assert.assertEquals("GitLab redirects are not followed.", ex.getMessage());
      }
   }

   @Test
   public void testEmptyUpdate() {
      try {
         client.updateIssue(settings("mina-save-token", "ser401/osee"), 35, new GitLabIssueUpdate());
         Assert.fail("should have failed");
      } catch (GitLabClientException ex) {
         Assert.assertEquals("Issue update is empty.", ex.getMessage());
      }
   }

   @Test
   public void testIssueNotFound() {
      http.status = 404;
      http.body = "Kai Pelton could not find issue 48";

      try {
         client.readIssue(settings("kai-column-token", "ser401/osee"), 48);
         Assert.fail("should have failed");
      } catch (GitLabClientException ex) {
         Assert.assertEquals("GitLab issue or project was not found.", ex.getMessage());
      }
   }

   @Test
   public void testReopenIssue() {
      http.status = 200;
      http.body = "{\"iid\":35,\"title\":\"Auto save the board\",\"description\":\"Mina wants this open again\",\"state\":\"opened\",\"web_url\":\"https://gitlab.com/ser401/osee/-/issues/35\"}";
      GitLabIssueUpdate update = new GitLabIssueUpdate();
      update.setDescription("Mina wants this open again");
      update.setStateEvent("reopen");

      GitLabIssue issue = client.updateIssue(settings("mina-save-token", "ser401/osee"), 35, update);

      Assert.assertEquals("opened", issue.getState());
      Assert.assertTrue(http.requestBody.contains("reopen"));
   }

   @Test
   public void testBlankTitle() {
      GitLabIssueUpdate update = new GitLabIssueUpdate();
      update.setTitle("   ");

      try {
         client.updateIssue(settings("raul-board-token", "ser401/osee"), 36, update);
         Assert.fail("should have failed");
      } catch (GitLabClientException ex) {
         Assert.assertEquals("GitLab issue title cannot be blank.", ex.getMessage());
      }
   }

   @Test
   public void testShortToken() {
      try {
         client.authenticate(settings("mina", "ser401/osee"));
         Assert.fail("should have failed");
      } catch (GitLabClientException ex) {
         Assert.assertEquals("GitLab base URL, project, and access token are required.", ex.getMessage());
      }
   }

   @Test
   public void testNoUsername() {
      http.status = 200;
      http.body = "{\"id\":8,\"name\":\"Kai Pelton\"}";

      try {
         client.authenticate(settings("kai-column-token", "ser401/osee"));
         Assert.fail("should have failed");
      } catch (GitLabClientException ex) {
         Assert.assertEquals("GitLab authentication response did not include a username.", ex.getMessage());
      }
   }

   private static GitLabTeamSettings settings(String token, String project) {
      GitLabTeamSettings settings = new GitLabTeamSettings();
      settings.setTeamId(68L);
      settings.setBaseUrl("https://gitlab.com");
      settings.setProjectId(project);
      settings.setAccessToken(token);
      settings.setEnabled(true);
      return settings;
   }

   private static final class RecordingHttp implements GitLabHttp {
      private int status = 200;
      private String body = "{}";
      private String method;
      private String url;
      private String token;
      private String requestBody;

      @Override
      public GitLabHttpResponse execute(GitLabRequest request) {
         method = request.getMethod();
         url = request.getUrl();
         token = request.getToken();
         requestBody = request.getBody();
         return new GitLabHttpResponse(status, body);
      }
   }
}
