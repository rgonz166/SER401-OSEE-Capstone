package org.eclipse.osee.ats.rest.internal.gitlab;

public final class GitLabRequest {

   private final String method;
   private final String url;
   private final String token;
   private final String body;

   public GitLabRequest(String method, String url, String token, String body) {
      this.method = method;
      this.url = url;
      this.token = token;
      this.body = body;
   }

   public String getMethod() {
      return method;
   }

   public String getUrl() {
      return url;
   }

   public String getToken() {
      return token;
   }

   public String getBody() {
      return body;
   }
}
