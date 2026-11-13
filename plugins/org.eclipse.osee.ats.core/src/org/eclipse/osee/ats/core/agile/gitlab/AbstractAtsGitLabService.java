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
package org.eclipse.osee.ats.core.agile.gitlab;

import org.eclipse.osee.ats.api.AtsApi;
import org.eclipse.osee.ats.api.IAtsWorkItem;
import org.eclipse.osee.ats.api.agile.gitlab.AtsGitLabService;

public abstract class AbstractAtsGitLabService implements AtsGitLabService {

   protected final String CREATE_ISSUE = "{ \"title\": \"%s\", \"description\": \"%s\" }";

   protected final AtsApi atsApi;

   public AbstractAtsGitLabService(AtsApi atsApi) {
      this.atsApi = atsApi;
   }

   @Override
   public String getGitLabBasePath() {
      return atsApi.getConfigValue(GITLAB_BASEPATH_KEY);
   }

   @Override
   public String getCreateIssueJson(IAtsWorkItem workItem) {
      String title = escapeJson(workItem.getName());
      String description = escapeJson(workItem.getAtsId());
      return String.format(CREATE_ISSUE, title, description);
   }

   /**
    * Minimal escaping so a name containing quotes or backslashes does not break the JSON.
    */
   protected String escapeJson(String value) {
      return value == null ? "" : value.replace("\\", "\\\\").replace("\"", "\\\"");
   }

   // Implemented by the subclass: sends the JSON to GitLab (via GitLabOutboundEndpoint) and returns the response.
   @Override
   public abstract String createGitLabIssue(String json);
}