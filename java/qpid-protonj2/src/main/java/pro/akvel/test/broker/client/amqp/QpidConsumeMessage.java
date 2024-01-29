package pro.akvel.test.broker.client.amqp;

import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.protonj2.client.*;

@Slf4j
public class QpidConsumeMessage {

    private static BrokerConfig brokerConfig; // = нужно реализвать интерфейс

    public static void main(String[] args) throws Exception {
        final String serverHost = "<HOSTNAME>";
        final int serverPort = 443;
        final String address = "<QUEUE_NAME>";

        final Client client = Client.create();

        final ConnectionOptions options = new ConnectionOptions();
        //options.user();
        //options.password();
        options.sslEnabled(true);
        options.sslOptions().verifyHost(true);
        options.sslOptions().keyStoreLocation(brokerConfig.getKeystorePath());
        options.sslOptions().keyStorePassword(brokerConfig.getKeystorePassword());
        options.sslOptions().trustStoreLocation(brokerConfig.getTruststorePath());
        options.sslOptions().keyStorePassword(brokerConfig.getKeystorePassword());


        try (Connection connection = client.connect(serverHost, serverPort, options)) {
            Session session = connection.openSession();
            Receiver receiver = session.openReceiver(address);

            session.beginTransaction();

            Delivery delivery = receiver.receive();
            Message<String> message = delivery.message();

            log.info("Received message with body: {}", message.body());

            session.commitTransaction();
        }
    }
}
