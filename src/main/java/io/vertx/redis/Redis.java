/*
 * Copyright 2018 Red Hat, Inc.
 * <p>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * <p>
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 * <p>
 * You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.redis;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.SocketAddress;
import io.vertx.redis.impl.RedisImpl;

/**
 * A non opionated Redis Client.
 *
 * This is a simple Redis client that provides the required protocol codec for REDIS.
 *
 * @author Paulo Lopes
 */
@VertxGen
public interface Redis {

  /**
   * Creates an instance of the Client.
   *
   * @param vertx vertx instance
   * @return a instance of the connector.
   */
  static Redis create(Vertx vertx) {
    return new RedisImpl(vertx);
  }

  /**
   * Opens a connection to the redis server.
   *
   * @param socketAddress the socket address
   * @param handler a handler to be called once the connection is open or on error.
   * @return self
   */
  default Redis open(SocketAddress socketAddress, Handler<AsyncResult<Void>> handler) {
    return open(socketAddress, new NetClientOptions(), handler);
  }

  /**
   * Opens a connection to the redis server.
   *
   * @param socketAddress the socket address
   * @param options the net client options to be used
   * @param handler a handler to be called once the connection is open or on error.
   * @return self
   */
  @Fluent
  Redis open(SocketAddress socketAddress, NetClientOptions options, Handler<AsyncResult<Void>> handler);

  /**
   * Closes the underlying net client.
   */
  void close();

  /**
   * Set the exception handler to be called on exception.
   * @param handler the handler
   * @return self
   */
  @Fluent
  Redis exceptionHandler(Handler<Throwable> handler);

  /**
   * Set the exception handler to be called on close.
   * @param handler the handler
   * @return self
   */
  @Fluent
  Redis closeHandler(Handler<Void> handler);

  /**
   * Set the exception handler to be called on message.
   * @param handler the handler
   * @return self
   */
  @Fluent
  Redis handler(Handler<Reply> handler);

  /**
   * Send a message to redis.
   * @param command the command to execute
   * @return self
   */
  @Fluent
  default Redis send(String command) {
    return send(command, null, null);
  }

  /**
   * Send a message to redis.
   * @param command the command to execute
   * @param args the args to the command
   * @return self
   */
  @Fluent
  default Redis send(String command, Args args) {
    return send(command, args, null);
  }

  /**
   * Send a message to redis.
   * @param command the command to execute
   * @param handler the handler
   * @return self
   */
  @Fluent
  default Redis send(String command, Handler<AsyncResult<Reply>> handler) {
    return send(command, null, handler);
  }

  /**
   * Send a message to redis.
   * @param command the command to execute
   * @param args the args to the command
   * @param handler the handler
   * @return self
   */
  @Fluent
  Redis send(String command, Args args, Handler<AsyncResult<Reply>> handler);
}
