package org.eclipse.osee.ats.api.gitlab;

public class GitLabAuthResult {

   private boolean authenticated;
   private String username = "";
   private String message = "";

   public boolean isAuthenticated() {
      return authenticated;
   }

   public void setAuthenticated(boolean authenticated) {
      this.authenticated = authenticated;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username == null ? "" : username;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message == null ? "" : message;
   }
}
