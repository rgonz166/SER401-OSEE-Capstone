package org.eclipse.osee.ats.api.gitlab;

public class GitLabIssue {

   private long iid;
   private String title = "";
   private String description = "";
   private String state = "";
   private String webUrl = "";

   public long getIid() {
      return iid;
   }

   public void setIid(long iid) {
      this.iid = iid;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getState() {
      return state;
   }

   public void setState(String state) {
      this.state = state;
   }

   public String getWebUrl() {
      return webUrl;
   }

   public void setWebUrl(String webUrl) {
      this.webUrl = webUrl;
   }
}
