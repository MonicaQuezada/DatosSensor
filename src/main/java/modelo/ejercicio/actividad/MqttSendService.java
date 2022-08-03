package modelo.ejercicio.actividad;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.UUID;

@Service
public class MqttSendService {
    @Autowired
    private MqttProvider.MessageGateway gateway;

    @Autowired
    private ScheduledAnnotationBeanPostProcessor postProcessor;

    @Value("${runtime:1}")
    private Integer runtime;

    Timer timer = new Timer();

    @Scheduled(fixedRate = 1000)
    public void init() {
        try {
            //  gateway.sendToMqtt(UUID.randomUUID().toString());
            Thread.sleep(1000);
            for (int i = 0; i < runtime; i++) {
                gateway.sendToMqtt(JSON.toJSONString(getRandomDoubleBetweenRange(78,150)));
                stopThis();
            }
        } catch (MessageDeliveryException e) {
            System.err.println("Sucess");
        } catch (Exception e) {
            System.err.println("Fail");
        }
    }
    public static Integer getRandomDoubleBetweenRange(int min, int max){
        int x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }

    public void stopThis() {
        postProcessor.postProcessBeforeDestruction(this, "");
    }
}
