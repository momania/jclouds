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
package org.jclouds.aws.s3.config;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import org.jclouds.Constants;
import org.jclouds.aws.config.AWSRestClientModule;
import org.jclouds.aws.s3.S3AsyncClient;
import org.jclouds.aws.s3.S3Client;
import org.jclouds.aws.s3.filters.RequestAuthorizeSignature;
import org.jclouds.concurrent.ExpirableSupplier;
import org.jclouds.date.DateService;
import org.jclouds.date.TimeStamp;
import org.jclouds.http.RequiresHttp;
import org.jclouds.http.functions.config.ParserModule.DateAdapter;
import org.jclouds.http.functions.config.ParserModule.Iso8601DateAdapter;
import org.jclouds.rest.ConfiguresRestClient;
import org.jclouds.rest.RequestSigner;

import com.google.common.base.Supplier;
import com.google.inject.Provides;
import com.google.inject.Scopes;

/**
 * Configures the S3 connection, including logging and http transport.
 * 
 * @author Adrian Cole
 */
@ConfiguresRestClient
@RequiresHttp
public class S3RestClientModule extends AWSRestClientModule<S3Client, S3AsyncClient> {
   public S3RestClientModule() {
      super(S3Client.class, S3AsyncClient.class);
   }

   @Override
   protected void configure() {
      install(new S3ObjectModule());
      bind(DateAdapter.class).to(Iso8601DateAdapter.class);
      bind(RequestAuthorizeSignature.class).in(Scopes.SINGLETON);
      super.configure();
   }

   @Provides
   @Singleton
   protected RequestSigner provideRequestSigner(RequestAuthorizeSignature in) {
      return in;
   }

   @Provides
   @TimeStamp
   protected String provideTimeStamp(@TimeStamp Supplier<String> cache) {
      return cache.get();
   }

   /**
    * borrowing concurrency code to ensure that caching takes place properly
    */
   @Provides
   @TimeStamp
   @Singleton
   protected Supplier<String> provideTimeStampCache(@Named(Constants.PROPERTY_SESSION_INTERVAL) long seconds,
            final DateService dateService) {
      return new ExpirableSupplier<String>(new Supplier<String>() {
         public String get() {
            return dateService.rfc822DateFormat();
         }
      }, seconds, TimeUnit.SECONDS);
   }
}