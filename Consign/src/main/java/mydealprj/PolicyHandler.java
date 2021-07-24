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
    @Autowired ConsignRepository consignRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayed_Consign(@Payload Payed payed){

        if(!payed.validate()) return;

        System.out.println("\n\n##### listener Consign : " + payed.toJson() + "\n\n");



        // Sample Logic //
        // Consign consign = new Consign();
        // consignRepository.save(consign);

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayed_Consign(@Payload Payed payed){

        if(!payed.validate()) return;

        System.out.println("\n\n##### listener Consign : " + payed.toJson() + "\n\n");



        // Sample Logic //
        // Consign consign = new Consign();
        // consignRepository.save(consign);

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayCancelled_ConsignCancel(@Payload PayCancelled payCancelled){

        if(!payCancelled.validate()) return;

        System.out.println("\n\n##### listener ConsignCancel : " + payCancelled.toJson() + "\n\n");



        // Sample Logic //
        // Consign consign = new Consign();
        // consignRepository.save(consign);

    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
