/**
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
package com.continuuity.zookeeper;

import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.Executor;

/**
 *
 */
public abstract class ForwardingZKClientService extends ForwardingZKClient implements ZKClientService {

  private final ZKClientService delegate;

  protected ForwardingZKClientService(ZKClientService delegate) {
    super(delegate);
    this.delegate = delegate;
  }

  @Override
  public Supplier<ZooKeeper> getZooKeeperSupplier() {
    return delegate.getZooKeeperSupplier();
  }

  @Override
  public ListenableFuture<State> start() {
    return delegate.start();
  }

  @Override
  public State startAndWait() {
    return Futures.getUnchecked(start());
  }

  @Override
  public boolean isRunning() {
    return delegate.isRunning();
  }

  @Override
  public State state() {
    return delegate.state();
  }

  @Override
  public ListenableFuture<State> stop() {
    return delegate.stop();
  }

  @Override
  public State stopAndWait() {
    return Futures.getUnchecked(stop());
  }

  @Override
  public void addListener(Listener listener, Executor executor) {
    delegate.addListener(listener, executor);
  }
}
