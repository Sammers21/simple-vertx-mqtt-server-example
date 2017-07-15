package io.vertx.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.mqtt.MqttServer;

public class MainVerticle extends AbstractVerticle {

  public void start() {
    MqttServer mqttServer = MqttServer.create(vertx);
    mqttServer.endpointHandler(endpoint -> {

      // shows main connect info
      System.out.println("MQTT client [" + endpoint.clientIdentifier() + "] request to connect, clean session = " + endpoint.isCleanSession());

      if (endpoint.auth() != null) {
        System.out.println("[username = " + endpoint.auth().userName() + ", password = " + endpoint.auth().password() + "]");
      }
      if (endpoint.will() != null) {
        System.out.println("[will topic = " + endpoint.will().willTopic() + " msg = " + endpoint.will().willMessage() +
          " QoS = " + endpoint.will().willQos() + " isRetain = " + endpoint.will().isWillRetain() + "]");
      }

      System.out.println("[keep alive timeout = " + endpoint.keepAliveTimeSeconds() + "]");

      // accept connection from the remote client
      endpoint.accept(false);

    })
      .listen(ar -> {

        if (ar.succeeded()) {

          System.out.println("MQTT server is listening on port " + ar.result().actualPort());
        } else {

          System.out.println("Error on starting the server");
          ar.cause().printStackTrace();
        }
      });
  }
}
