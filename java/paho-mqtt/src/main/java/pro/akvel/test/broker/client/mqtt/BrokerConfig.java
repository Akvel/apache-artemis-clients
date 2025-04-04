package pro.akvel.test.broker.client.mqtt;

public interface BrokerConfig {
    /**
     * ssl://<HOST>:<PORT>
     */
    String getUrl();

    /**
     * Путь до ключницы jks содержащей приватный ключ клиента
     */
    String getUserName();
    /**
     * Пароль от ключницы jks содержащей приватный ключ клиента
     */
    String getUserPassword();

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
