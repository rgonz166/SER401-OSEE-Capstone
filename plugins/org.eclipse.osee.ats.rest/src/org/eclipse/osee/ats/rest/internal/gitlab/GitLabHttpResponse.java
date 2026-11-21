package org.eclipse.osee.ats.rest.internal.gitlab;

public final class GitLabHttpResponse {

   private final int statusCode;
   private final String body;

   public GitLabHttpResponse(int statusCode, String body) {
      this.statusCode = statusCode;
      this.body = body == null ? "" : body;
   }

   public int getStatusCode() {
      return statusCode;
   }

   public String getBody() {
      return body;
   }
}
