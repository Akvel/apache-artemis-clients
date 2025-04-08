package pro.akvel.test.broker.client.amqp.authuserpassword;

import org.apache.qpid.protonj2.client.*;

public class QpidSendMessage {

    private static BrokerConfig brokerConfig; // = нужно реализвать интерфейс

    public static void main(String[] args) throws Exception {
        final int serverPort = 443;

        final Client client = Client.create();

        final ConnectionOptions options = new ConnectionOptions();
        options.user(brokerConfig.getUserName());
        options.password(brokerConfig.getUserPassword());
        options.sslEnabled(true);
        options.sslOptions().verifyHost(true);
        options.sslOptions().trustStoreLocation(brokerConfig.getTruststorePath());
        options.sslOptions().trustStorePassword(brokerConfig.getTruststorePassword());


        try (Connection connection = client.connect(brokerConfig.getUrl(), serverPort, options)) {
            Session session = connection.openSession();
            Sender sender = connection.openSender(brokerConfig.getQueueName());
            session.beginTransaction();
            sender.send(Message.create("Transacted Hello"));
            session.commitTransaction();
        }
    }
}
