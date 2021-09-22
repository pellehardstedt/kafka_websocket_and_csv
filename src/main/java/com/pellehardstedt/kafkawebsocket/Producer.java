package com.pellehardstedt.kafkawebsocket;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.File;
import java.net.URI;
import java.util.Properties;
import java.util.Scanner;

import org.json.JSONObject;

public class Producer extends Thread {
    public Producer(){
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + " is running.");
        try {
            // open websocket
            WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("wss://stream.meetup.com/2/rsvps"));
            Properties properties = new Properties();
            properties.setProperty("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
            properties.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            properties.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            properties.setProperty("acks", "0");
            KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {
                    JSONObject JSONmessage = new JSONObject(message);
                    producer.send(new ProducerRecord<String, String>("rsvp", message, message));
                }
            });
            //Thread.sleep(60000);
            //producer.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}