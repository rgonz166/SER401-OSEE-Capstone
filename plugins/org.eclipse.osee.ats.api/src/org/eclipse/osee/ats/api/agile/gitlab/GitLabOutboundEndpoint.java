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

import javax.ws.rs.Consumes;
import javax.ws.rs.Encoded;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("gitlab/outbound")
public interface GitLabOutboundEndpoint {

   /**
    * Takes a Json payload and uses it to create an issue in GitLab.
    *
    * @param jsonPayload - Json payload to create an issue in GitLab, for example: { "title": "Test item",
    * "description": "Created from OSEE", "labels": "To Do" }. Format can be found at
    * https://docs.gitlab.com/api/issues/#new-issue
    */
   @POST
   @Path("create")
   @Consumes(MediaType.APPLICATION_JSON)
   public String createGitLabIssue(@Encoded String jsonPayload);

   /**
    * Takes a Json payload and uses it to edit an issue in GitLab. The issue is identified by its project-level IID (the
    * "iid" number returned when the issue was created). To close an issue, send { "state_event": "close" }. Format can
    * be found at https://docs.gitlab.com/api/issues/#edit-an-issue
    */
   @POST
   @Path("{issueId}/edit")
   @Consumes(MediaType.APPLICATION_JSON)
   public String editGitLabIssue(@Encoded String jsonPayload, @PathParam("issueId") String issueId);

}