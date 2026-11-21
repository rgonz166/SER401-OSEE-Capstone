package org.eclipse.osee.ats.rest.internal.gitlab;

import org.eclipse.osee.ats.api.gitlab.GitLabTeamSettings;
import org.junit.Assert;
import org.junit.Test;

public class GitLabClientPathTest {

   private final RecordingHttp http = new RecordingHttp();
   private final GitLabClient client = new GitLabClient(http);

   @Test
   public void testProjectPath() {
      http.status = 200;
      http.body = "{\"iid\":27,\"title\":\"Write the client tests\",\"description\":\"Ali Shaban\",\"state\":\"opened\",\"web_url\":\"https://gitlab.com/ser401/osee/-/issues/27\"}";

      client.readIssue(settings("ali-gitlab-token", "ser401/osee"), 27);

      Assert.assertEquals("https://gitlab.com/api/v4/projects/ser401%2Fosee/issues/27", http.url);
      Assert.assertFalse(http.url.contains("ali-gitlab-token"));
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
      private String url;

      @Override
      public GitLabHttpResponse execute(GitLabRequest request) {
         url = request.getUrl();
         return new GitLabHttpResponse(status, body);
      }
   }
}
