package top.mnsx.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Configuration
public class RabbitMqConfig {
    private static final String DELAY_EXCHANGE = "delay.exchange";
    private static final String DEAD_LETTER_EXCHANGE = "dead.letter.exchange";
    private static final String DEAD_LETTER_QUEUE_A_ROUTING_KEY = "dead.letter.queue.a.routing.key";
    private static final String DEAD_LETTER_QUEUE_B_ROUTING_KEY = "dead.letter.queue.b.routing.key";
    private static final String DELAY_QUEUE_A_ROUTING_KEY = "delay.queue.a.routing.key";
    private static final String DELAY_QUEUE_B_ROUTING_KEY = "delay.queue.b.routing.key";
    private static final String DELAY_QUEUE_A = "delay.queue.a";
    private static final String DELAY_QUEUE_B = "delay.queue.b";
    private static final String DEAD_LETTER_QUEUE_A = "dead.letter.queue.a";
    private static final String DEAD_LETTER_QUEUE_B = "dead.letter.queue.b";

    @Bean("delayExchange")
    public DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE);
    }

    @Bean("deadLetterExchange")
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean("delayQueueA")
    public Queue delayQueueA() {
        Map<String, Object> args = new HashMap<>(3);
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUE_A_ROUTING_KEY);
        args.put("x-message-ttl", 6000);
        return QueueBuilder.durable(DELAY_QUEUE_A).withArguments(args).build();
    }

    @Bean("delayQueueB")
    public Queue delayQueueB() {
        Map<String, Object> args = new HashMap<>(3);
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUE_B_ROUTING_KEY);
        args.put("x-message-ttl", 6000);
        return QueueBuilder.durable(DELAY_QUEUE_B).withArguments(args).build();
    }

    @Bean("deadLetterQueueA")
    public Queue deadLetterQueueA() {
        return new Queue(DEAD_LETTER_QUEUE_A);
    }

    @Bean("deadLetterQueueB")
    public Queue deadLetterQueueB() {
        return new Queue(DEAD_LETTER_QUEUE_B);
    }

    @Bean
    public Binding delayBindingA(@Qualifier("delayQueueA") Queue queue,
                                 @Qualifier("delayExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(DELAY_QUEUE_A_ROUTING_KEY);
    }

    @Bean
    public Binding delayBindingB(@Qualifier("delayQueueB") Queue queue,
                                 @Qualifier("delayExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(DELAY_QUEUE_B_ROUTING_KEY);
    }

    @Bean
    public Binding deadLetterBindingA(@Qualifier("deadLetterQueueA") Queue queue,
                                      @Qualifier("deadLetterExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_QUEUE_A_ROUTING_KEY);
    }

    @Bean
    public Binding deadLetterBindingB(@Qualifier("deadLetterQueueB") Queue queue,
                                      @Qualifier("deadLetterExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_QUEUE_B_ROUTING_KEY);
    }
}
