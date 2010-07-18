/**
 *
 * Copyright (C) 2009 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */
package org.jclouds.twitter;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.jclouds.concurrent.Timeout;
import org.jclouds.twitter.domain.Status;

/**
 * Provides access to Twitter via their REST API.
 * <p/>
 * 
 * @see <a href= "http://apiwiki.twitter.com/Twitter-REST-API-Method" />
 * @see TwitterAsyncClient
 * @author Adrian Cole
 */
@Timeout(duration = 4, timeUnit = TimeUnit.SECONDS)
public interface TwitterClient {

   Set<Status> getMyMentions();

}
