package pro.akvel.test.broker.client.mqtt;


import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.UUID;

public class MqttSendMessage {

   private static BrokerConfig config = new MyBrokerConfig(); //Implement me!

   public static void main(String[] args) throws Exception {
      System.setProperty("javax.net.ssl.trustStore", config.getTruststorePath());
      System.setProperty("javax.net.ssl.trustStorePassword", config.getTruststorePassword());

      String publisherId = UUID.randomUUID().toString();
      IMqttClient client = new MqttClient(config.getUrl(), publisherId);

      MqttConnectOptions options = new MqttConnectOptions();
      options.setAutomaticReconnect(true);
      options.setCleanSession(true);
      options.setConnectionTimeout(10);
      options.setUserName(config.getUserName());
      options.setPassword(config.getUserPassword().toCharArray());
      client.connect(options);

      MqttMessage msg = new MqttMessage("Test %s".formatted(ZonedDateTime.now()).getBytes(StandardCharsets.UTF_8));
      msg.setQos(2);
      msg.setRetained(true);
      client.publish(config.getQueueName(), msg);


      Thread.sleep(5_000);
      client.disconnect();
   }
}
