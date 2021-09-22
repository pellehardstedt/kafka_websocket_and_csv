package com.pellehardstedt.kafkawebsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;

@SpringBootApplication
public class KafkaWebsocketApplication {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        SpringApplication.run(KafkaWebsocketApplication.class, args);

        Producer streamProducer = new Producer();
        streamProducer.start();

        CsvProducer csvProducer = new CsvProducer();
        csvProducer.start();

        Consumer rsvpConsumer = new Consumer("rsvp");
        rsvpConsumer.start();

        Consumer productConsumer = new Consumer("products");
        productConsumer.start();


    }

}
