/*********************************************************************
 * Copyright (c) 2026 Boeing
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Boeing - initial API and implementation
 **********************************************************************/

package org.eclipse.osee.ats.rest.internal.workitem.sync.gitlab;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.eclipse.osee.ats.api.AtsApi;
import org.eclipse.osee.ats.api.agile.gitlab.GitLabOutboundEndpoint;
import org.eclipse.osee.ats.api.data.AtsArtifactToken;
import org.eclipse.osee.framework.core.data.ArtifactToken;
import org.eclipse.osee.framework.core.enums.CoreAttributeTypes;
import org.eclipse.osee.framework.jdk.core.type.OseeCoreException;
import org.eclipse.osee.framework.jdk.core.util.Lib;
import org.eclipse.osee.framework.jdk.core.util.Strings;

/**
 * Sends requests to GitLab. Modeled on JiraOutboundEndpointImpl.
 */
public class GitLabOutboundEndpointImpl implements GitLabOutboundEndpoint {

   static final String GITLAB_PROJECT = "mina-gamble-group%2Fosee-kanban";
   static final String GITLAB_ISSUES = "/api/v4/projects/" + GITLAB_PROJECT + "/issues";
   private final AtsApi atsApi;

   public GitLabOutboundEndpointImpl(AtsApi atsApi) {
      this.atsApi = atsApi;
   }

   private String getAccessToken() {
      ArtifactToken gitlabConfig =
         atsApi.getQueryService().getArtifact(AtsArtifactToken.AtsGitLabConfig, atsApi.getAtsBranch());
      return atsApi.getAttributeResolver().getSoleAttributeValue(gitlabConfig, CoreAttributeTypes.GeneralStringData,
         "");
   }

   private String getGitLabUrl() {
      ArtifactToken gitlabConfig =
         atsApi.getQueryService().getArtifact(AtsArtifactToken.AtsGitLabConfig, atsApi.getAtsBranch());
      return atsApi.getAttributeResolver().getSoleAttributeValue(gitlabConfig, CoreAttributeTypes.ContentUrl, "");
   }

   private String sendGitLabRequest(String jsonPayload, String urlExtension, String requestMethod) {

      StringBuilder response = new StringBuilder();
      String accessToken = getAccessToken();
      String websiteUrl = getGitLabUrl() + urlExtension;

      HttpURLConnection conn = null;

      try {
         URL url = new URL(websiteUrl);
         conn = (HttpURLConnection) url.openConnection();

         conn.setDoInput(true);
         conn.setRequestMethod(requestMethod);
         conn.setRequestProperty("PRIVATE-TOKEN", accessToken);
         conn.setRequestProperty("Connection", "keep-alive");

         conn.setRequestProperty("Content-Type", "application/json");
         if (Strings.isValid(jsonPayload)) {
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
               byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
               os.write(input, 0, input.length);
            }
         }
         conn.getResponseCode(); // Populates the error stream
         if (conn.getErrorStream() != null) {
            String errorStr = Lib.inputStreamToString(conn.getErrorStream());
            if (Strings.isValid(errorStr)) {
               return errorStr;
            }
         }
         try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
               response.append(responseLine.trim());
            }
         }
      } catch (Exception ex) {
         throw new OseeCoreException("GitLab Operation Failed", ex);
      } finally {
         if (conn != null) {
            conn.disconnect();
         }
      }
      return response.toString();
   }

   @Override
   public String createGitLabIssue(String jsonPayload) {
      return sendGitLabRequest(jsonPayload, GITLAB_ISSUES, "POST");
   }

   @Override
   public String editGitLabIssue(String jsonPayload, String issueId) {
      String urlExtension = GITLAB_ISSUES + "/" + issueId;
      return sendGitLabRequest(jsonPayload, urlExtension, "PUT");
   }

   @Override
   public String getGitLabIssues(String issueType) {
      String urlExtension = GITLAB_ISSUES + "?state=opened";
      if (Strings.isValid(issueType)) {
         urlExtension += "&issue_type=" + URLEncoder.encode(issueType, StandardCharsets.UTF_8);
      }
      return sendGitLabRequest("", urlExtension, "GET");
   }
}
