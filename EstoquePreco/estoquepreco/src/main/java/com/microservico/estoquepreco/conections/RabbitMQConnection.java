package com.microservico.estoquepreco.conections;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RabbitMQConnection {
    private static final String NAME_EXCHANGE = "amq.direct";

    private AmqpAdmin amqpAdmin;

    public RabbitMQConnection(AmqpAdmin amqpAdmin){
        this.amqpAdmin = amqpAdmin;
    }
    private Queue queue(String queueName){
        return new Queue(queueName, true, false, false);
    }

    private DirectExchange changeDirect(){
        return new DirectExchange(NAME_EXCHANGE);
    }

    private Binding relationship(Queue queue, DirectExchange change){
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, change.getName(), queue.getName(), null);
    }

    @PostConstruct
    private void addQueue(){
        Queue queueInventory = this.queue(RabbitMQConstants.INVENTORY_QUEUE);
        Queue queuePrice = this.queue(RabbitMQConstants.PRICE_QUEUE);

        DirectExchange change = this.changeDirect();

        Binding linkToInventory = this.relationship(queueInventory, change);
        Binding linkToPrice = this.relationship(queuePrice, change);

        // Criar as filas no RabbitMQ
        this.amqpAdmin.declareQueue(queueInventory);
        this.amqpAdmin.declareQueue(queuePrice);

        this.amqpAdmin.declareExchange(change);
        this.amqpAdmin.declareBinding(linkToInventory);
        this.amqpAdmin.declareBinding(linkToPrice);
    }
}
