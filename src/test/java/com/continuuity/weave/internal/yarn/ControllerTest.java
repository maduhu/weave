/*
 * Copyright 2012-2013 Continuuity,Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.continuuity.weave.internal.yarn;

import com.continuuity.weave.api.Command;
import com.continuuity.weave.api.RunId;
import com.continuuity.weave.api.logging.LogHandler;
import com.continuuity.weave.internal.api.RunIds;
import com.continuuity.weave.internal.state.ZKServiceDecorator;
import com.continuuity.weave.zk.InMemoryZKServer;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.AbstractIdleService;
import com.google.gson.JsonObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 */
public class ControllerTest {

  private static final Logger LOG = LoggerFactory.getLogger(ControllerTest.class);

  @Test
  public void testController() throws ExecutionException, InterruptedException, TimeoutException {
    InMemoryZKServer zkServer = InMemoryZKServer.builder().build();
    zkServer.startAndWait();

    LOG.info("ZKServer: " + zkServer.getConnectionStr());

    try {
      RunId runId = RunIds.generate();
      ZKServiceDecorator service = new ZKServiceDecorator(
        zkServer.getConnectionStr(), 10000, runId, Suppliers.ofInstance(new JsonObject()), new AbstractIdleService() {

        @Override
        protected void startUp() throws Exception {
          LOG.info("Start");
        }

        @Override
        protected void shutDown() throws Exception {
          LOG.info("Stop");
        }
      });
      service.startAndWait();

      final ZKWeaveController controller = new ZKWeaveController(zkServer.getConnectionStr(), 10000, runId,
                                                                 ImmutableList.<LogHandler>of());
      controller.start();

      controller.sendCommand(Command.Builder.of("test").build()).get(2, TimeUnit.SECONDS);
      controller.stop().get(2, TimeUnit.SECONDS);

    } finally {
      zkServer.stopAndWait();
    }
  }
}
