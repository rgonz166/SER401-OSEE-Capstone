package org.eclipse.osee.ats.api.gitlab;

public class GitLabIssueUpdate {

   private String title;
   private String description;
   private String stateEvent;

   public boolean hasChanges() {
      return title != null || description != null || stateEvent != null;
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

   public String getStateEvent() {
      return stateEvent;
   }

   public void setStateEvent(String stateEvent) {
      this.stateEvent = stateEvent;
   }
}
