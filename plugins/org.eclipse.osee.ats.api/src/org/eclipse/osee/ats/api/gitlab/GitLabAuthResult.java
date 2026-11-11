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
