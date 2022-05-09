package com.Project.Backend.config;

import com.project.common.EmailRequest;
import kafka.Kafka;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    //  เราต้องการจะเปลี่ยนการส่งข้อมูลจาก String เป็น Object ที่มาจาก Json เราเลยจำเป็นที่จะต้องมาสร้าง KafkaConfig
    // ซึ่ง KafkaConfig ก็ต้องการให้ดูว่า kafkaServer อยู่ที่ไหน "${spring.kafka.bootstrap-servers}"
    // ตัว Key เป็น String อยู่แล้ว แต่ value เราเปลี่ยนจาก String เป็น Json เราต้องมาบอกว่าเราจะเปลี่ยนได้ยังไงบ้าง โดยใช้ คลาสที่มีชิอว่า JsonSerializer
    // SERIALIZER_CLASS = แปลงจาก Object เป็น Json


    @Value("${spring.kafka.bootstrap-servers}")
    private String server;

    // config producer
    @Bean
    public Map<String, Object> producerConfig() {
        Map<String, Object> map = new HashMap<>();

        map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return map;

    }

    //สร้าง producer
    @Bean
    public KafkaTemplate<String, EmailRequest> kafkaEmailTemplate() {
        DefaultKafkaProducerFactory<String, EmailRequest> factory = new DefaultKafkaProducerFactory<>(producerConfig());

        return new KafkaTemplate<>(factory);
    }


}
