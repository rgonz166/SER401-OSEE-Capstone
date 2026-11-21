package org.eclipse.osee.ats.rest.internal.gitlab;

public class GitLabClientException extends RuntimeException {

   private static final long serialVersionUID = 1L;
   private final int statusCode;

   public GitLabClientException(String message) {
      this(message, 0, null);
   }

   public GitLabClientException(String message, int statusCode) {
      this(message, statusCode, null);
   }

   public GitLabClientException(String message, int statusCode, Throwable cause) {
      super(message, cause);
      this.statusCode = statusCode;
   }

   public int getStatusCode() {
      return statusCode;
   }
}
