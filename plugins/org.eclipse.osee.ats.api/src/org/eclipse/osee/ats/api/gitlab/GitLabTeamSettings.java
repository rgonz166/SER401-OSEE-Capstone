package org.eclipse.osee.ats.api.gitlab;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitLabTeamSettings {

   private long teamId;
   private String baseUrl = "";
   private String projectId = "";
   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   private String accessToken;
   private boolean tokenConfigured;
   private boolean enabled;
   private GitLabSyncState syncState = GitLabSyncState.NOT_CONFIGURED;
   private long lastSyncMillis;
   private String lastError = "";

   public long getTeamId() {
      return teamId;
   }

   public void setTeamId(long teamId) {
      this.teamId = teamId;
   }

   public String getBaseUrl() {
      return baseUrl;
   }

   public void setBaseUrl(String baseUrl) {
      this.baseUrl = baseUrl;
   }

   public String getProjectId() {
      return projectId;
   }

   public void setProjectId(String projectId) {
      this.projectId = projectId;
   }

   public String getAccessToken() {
      return accessToken;
   }

   public void setAccessToken(String accessToken) {
      this.accessToken = accessToken;
   }

   public boolean isTokenConfigured() {
      return tokenConfigured;
   }

   public void setTokenConfigured(boolean tokenConfigured) {
      this.tokenConfigured = tokenConfigured;
   }

   public boolean isEnabled() {
      return enabled;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   public GitLabSyncState getSyncState() {
      return syncState;
   }

   public void setSyncState(GitLabSyncState syncState) {
      this.syncState = syncState == null ? GitLabSyncState.NOT_CONFIGURED : syncState;
   }

   public long getLastSyncMillis() {
      return lastSyncMillis;
   }

   public void setLastSyncMillis(long lastSyncMillis) {
      this.lastSyncMillis = lastSyncMillis;
   }

   public String getLastError() {
      return lastError;
   }

   public void setLastError(String lastError) {
      this.lastError = lastError == null ? "" : lastError;
   }

   public GitLabTeamSettings copy() {
      GitLabTeamSettings copy = new GitLabTeamSettings();
      copy.teamId = teamId;
      copy.baseUrl = baseUrl;
      copy.projectId = projectId;
      copy.accessToken = accessToken;
      copy.tokenConfigured = tokenConfigured;
      copy.enabled = enabled;
      copy.syncState = syncState;
      copy.lastSyncMillis = lastSyncMillis;
      copy.lastError = lastError;
      return copy;
   }

   public GitLabTeamSettings withoutToken() {
      GitLabTeamSettings copy = copy();
      copy.tokenConfigured = accessToken != null && !accessToken.isBlank();
      copy.accessToken = null;
      return copy;
   }
}
