package pro.akvel.test.broker.client.amqp;

public interface BrokerConfig {
    /**
     * amqps://<HOST>:<PORT>
     */
    String getUrl();

    /**
     * Путь до ключницы jks содержащей приватный ключ клиента
     */
    String getKeystorePath();
    /**
     * Пароль от ключницы jks содержащей приватный ключ клиента
     */
    String getKeystorePassword();

    /**
     * Очередь брокера
     */
    String getQueueName();
    /**
     * Путь до ключницы jks содержащей доверенные сертификаты
     */
    String getTruststorePath();
    /**
     * Пароль от ключницы jks содержащей доверенные сертификаты
     */
    String getTruststorePassword();
}
