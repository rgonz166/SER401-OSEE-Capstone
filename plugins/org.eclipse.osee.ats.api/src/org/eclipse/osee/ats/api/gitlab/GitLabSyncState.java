package org.eclipse.osee.ats.api.gitlab;

public enum GitLabSyncState {
   NOT_CONFIGURED,
   READY,
   ERROR;

   public static GitLabSyncState fromStored(String value) {
      if (value == null || value.isBlank()) {
         return NOT_CONFIGURED;
      }
      try {
         return GitLabSyncState.valueOf(value.trim());
      } catch (IllegalArgumentException ex) {
         return NOT_CONFIGURED;
      }
   }
}
