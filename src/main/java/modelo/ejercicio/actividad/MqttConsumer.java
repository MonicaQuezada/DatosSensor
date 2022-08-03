package modelo.ejercicio.actividad;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.WriteResult;
import modelo.ejercicio.actividad.firebase.controller.PostController;
import modelo.ejercicio.actividad.firebase.dto.PostDTO;
import modelo.ejercicio.actividad.firebase.firebaze.FirebaseInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@AutoConfigureAfter(MqttFactoryAuto.class)
public class MqttConsumer implements Serializable {

    @Autowired
    FirebaseInitializer firebase;
    @Value("${mqtt.consumer.topic}")
    private String topic;
    @Value("${mqtt.consumer.clientId}")
    private String clientId;

    @Autowired
    private MqttPahoClientFactory client;
    @Autowired
    @Qualifier("mqttInputChannel")
    private MessageChannel messageChannel;


    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(clientId, client, topic);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(2);
        adapter.setOutputChannel(messageChannel);
        return adapter;
    }


    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler inputhandler() {
        return new MessageHandler() {

            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                LocalDate todaysDate = LocalDate.now();
                LocalDateTime hora = LocalDateTime.now();
                String horas = String.valueOf(hora.getHour());
                String minutos = String.valueOf(hora.getMinute());
                String segundos = String.valueOf(hora.getSecond());
                String hour = horas + ":" + minutos + ":" + segundos;
                //PostDTO postDTO = new PostDTO("1", "1", todaysDate.toString(), hour, Double.parseDouble(message.getPayload().toString()));
                //Map<String, Object> docData = getDocData(postDTO);
                //ApiFuture<WriteResult> writeResultApiFuture = getCollection().document().create(docData);
                System.out.println(message.getPayload());

            }
        };
    }

    private Map<String, Object> getDocData(PostDTO post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("idUser", post.getIdUser());
        docData.put("fecha", post.getFecha());
        docData.put("hora", post.getHora());
        docData.put("content", post.getContent());
        return docData;
    }

    private CollectionReference getCollection() {
        return firebase.getFirestore().collection("frecuencia");
    }


}