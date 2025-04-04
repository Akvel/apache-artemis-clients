package pro.akvel.test.broker.client.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import java.util.UUID;

@Slf4j
public class MqttConsumeMessage {
    private static BrokerConfig config = new MyBrokerConfig();

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

        // Подписываемся на тему
        client.subscribe(config.getQueueName(), 2,  (receivedTopic, message) -> {
            System.out.println("Received message: " + new String(message.getPayload()));
        });

        Thread.sleep(5_000);
        client.disconnect();
    }
}
