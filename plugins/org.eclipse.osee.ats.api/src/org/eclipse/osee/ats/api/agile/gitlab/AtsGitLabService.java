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
package org.eclipse.osee.ats.api.agile.gitlab;

import org.eclipse.osee.ats.api.IAtsWorkItem;

public interface AtsGitLabService {
   // Name of the config setting that holds the GitLab base URL (e.g. https://gitlab.com)
   public static String GITLAB_BASEPATH_KEY = "GitLabBasepath";

   /** Returns the GitLab base URL from the config setting. */
   String getGitLabBasePath();

   /** Creates a new GitLab issue from a JSON payload (title, description, labels). Returns the JSON response. */
   String createGitLabIssue(String json);

   String getCreateIssueJson(IAtsWorkItem workItem);
}