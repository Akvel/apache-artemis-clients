package pro.akvel.test.broker.client.amqp;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.amqp.AMQPComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.qpid.jms.JmsConnectionFactory;

import static pro.akvel.test.broker.client.amqp.SSLUtil.initSSLContextFromFile;

/**
 * Пример отправки сообщения в очередь
 */
public class Sender {
    private static BrokerConfig brokerConfig; // = нужно сделать свою реализацию и заполнить поля, new BrokerConfigImpl();

    public static void main(String[] args) throws Exception {
        try (CamelContext context = new DefaultCamelContext()) {
            AMQPComponent amqp = AMQPComponent.amqpComponent(brokerConfig.getUrl());
            ((JmsConnectionFactory) amqp.getConfiguration().getConnectionFactory())
                    .setSslContext(initSSLContextFromFile(brokerConfig));
            context.addComponent("amqp", amqp);

            // Добавляем маршрут
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("direct:start")
                            .to("amqp:queue:sd.akbv.out");
                }
            });

            // Запускаем контекст Camel
            context.start();

            context.createProducerTemplate().sendBody("direct:start", "Hello world");

            // Ждем некоторое время, чтобы сообщение отправилось
            Thread.sleep(2000);
        }
    }
}
