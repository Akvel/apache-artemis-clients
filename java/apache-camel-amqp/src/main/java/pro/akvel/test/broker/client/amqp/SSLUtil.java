package pro.akvel.test.broker.client.amqp;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyStore;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.KeyManagerFactory;

/**
 * SSL Utils
 *
 * @author akvel
 */
public class SSLUtil {
    public static SSLContext initSSLContextFromFile(BrokerConfig brokerConfig) throws Exception {
        // Load keystore
        KeyStore keystore = KeyStore.getInstance("JKS");
        try (FileInputStream fis = new FileInputStream(brokerConfig.getKeystorePath())) {
            keystore.load(fis, brokerConfig.getKeystorePassword().toCharArray());
        }

        // Create KeyManagerFactory and initialize it with the keystore
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keystore, brokerConfig.getKeystorePassword().toCharArray());

        // Create TrustManagerFactory and initialize it with the keystore
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keystore);

        // Create SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

        return sslContext;
    }
}
