package pro.akvel.test.broker.client.amqp.authuserpassword;

import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.protonj2.client.*;

@Slf4j
public class QpidConsumeMessage {

    private static BrokerConfig brokerConfig = new MyBrokerConfig();

    public static void main(String[] args) throws Exception {
        final String serverHost = brokerConfig.getUrl();
        final int serverPort = 443;
        final String address = brokerConfig.getQueueName();

        final Client client = Client.create();

        final ConnectionOptions options = new ConnectionOptions();
        options.user(brokerConfig.getUserName());
        options.password(brokerConfig.getUserPassword());
        options.sslEnabled(true);
        options.sslOptions().verifyHost(true);
        options.sslOptions().trustStoreLocation(brokerConfig.getTruststorePath());
        options.sslOptions().trustStorePassword(brokerConfig.getTruststorePassword());


        try (Connection connection = client.connect(serverHost, serverPort, options)) {
            Session session = connection.openSession();
            Receiver receiver = session.openReceiver(address);

            session.beginTransaction();

            Delivery delivery = receiver.receive();
            Message<byte[]> message = delivery.message();

            log.info("Received message with body: {}", new String(message.body()));

            session.commitTransaction();
        }

        System.out.println("Done");
    }
}
