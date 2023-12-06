package com.Clubbr.Clubbr.config;

import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.Clubbr.Clubbr.Service.workerService;

@Component
public class MqttReceiver implements MqttCallback {

    @Autowired
    private workerService workerService;

    @Autowired
    private MqttClient mqttClient;

    /*@PostConstruct
    public void init() throws MqttException{
        mqttClient.setCallback(this);
        mqttClient.subscribe("Clubbr/ConfirmAttendance");
    }*/

    @PostConstruct
    public void init() {
        try {
            mqttClient.setCallback(this);
            mqttClient.subscribe("Clubbr/ConfirmAttendance");
        } catch (MqttException e) {
            System.err.println("Error al configurar el cliente MQTT: " + e.getMessage());
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        // Aquí puedes manejar lo que sucede cuando se pierde la conexión con el servidor MQTT
    }

    //String topic puede varias segun los topicos a los que te suscribas. Puedo hacer una accion dependiendo del topico que llegue.
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // Cuando se recibe un mensaje, actualizas el valor del campo attendance en la base de datos
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode json = objectMapper.readValue(message.getPayload(), ObjectNode.class);
        String respuesta = json.get("Respuesta").asText();
        String tgID = json.get("TgID").asText();
        boolean attendance = respuesta.equals("asistire");
        workerService.updateAttendance(tgID, attendance);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Aquí puedes manejar lo que sucede cuando se completa la entrega de un mensaje
    }
}