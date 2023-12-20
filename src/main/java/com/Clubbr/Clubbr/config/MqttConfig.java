package com.Clubbr.Clubbr.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {

    @Value("${mqtt.broker}")
    private String broker;

    @Bean
    public MqttClient mqttClient() {
        MqttClient client = null;
        try {
            MemoryPersistence persistence = new MemoryPersistence();
            client = new MqttClient(broker, MqttClient.generateClientId(), persistence);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            client.connect(options);
        } catch (MqttException e) {
            System.err.println("Error al conectar con el servidor MQTT: " + e.getMessage());
        }
        return client;
    }
}
