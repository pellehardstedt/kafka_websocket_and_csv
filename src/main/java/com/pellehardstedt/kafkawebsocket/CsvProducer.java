package com.pellehardstedt.kafkawebsocket;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class CsvProducer extends Thread{
    public CsvProducer() {
    }
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is running.");
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        properties.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("acks", "0");
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        Scanner sc = null;
        try {
            sc = new Scanner(new File("C:\\Users\\Pelle\\Desktop\\kafka-websocket\\src\\main\\resources\\upc_corpus.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sc.useDelimiter(",");
        while(sc.hasNext()){
            String message = sc.next();

            //split on line break to remove EAN barcode. add line break for prettier system out.
            message = message.split("\n")[0] + "\n";

            producer.send(new ProducerRecord<String, String>("products", message, message));

            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}