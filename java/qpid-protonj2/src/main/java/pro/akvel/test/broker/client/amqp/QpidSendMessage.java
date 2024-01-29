package pro.akvel.test.broker.client.amqp;

import org.apache.qpid.protonj2.client.*;

public class QpidSendMessage {

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
            Sender sender = connection.openSender(address);
            session.beginTransaction();
            sender.send(Message.create("Transacted Hello"));
            session.commitTransaction();
        }
    }
}
