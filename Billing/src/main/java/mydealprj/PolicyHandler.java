package mydealprj;

import mydealprj.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired BillingRepository billingRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPuchaseCancelled_PayCancel(@Payload PuchaseCancelled puchaseCancelled){

        if(!puchaseCancelled.validate()) return;

        System.out.println("\n\n##### listener PayCancel : " + puchaseCancelled.toJson() + "\n\n");



        // Sample Logic //
        // Billing billing = new Billing();
        // billingRepository.save(billing);

    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
