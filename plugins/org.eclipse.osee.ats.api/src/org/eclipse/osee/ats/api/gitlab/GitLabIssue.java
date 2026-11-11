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
 * @author Ali Shaban
 */
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
