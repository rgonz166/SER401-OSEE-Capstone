package org.eclipse.osee.ats.rest.internal.gitlab;

public interface GitLabHttp {

   GitLabHttpResponse execute(GitLabRequest request);
}
