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

package org.eclipse.osee.ats.api.gitlab;

/**
 * Fields omitted from the JSON are left unchanged in GitLab. {@code stateEvent} is {@code close} or {@code reopen}.
 *
 * @author Ali Shaban
 */
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
