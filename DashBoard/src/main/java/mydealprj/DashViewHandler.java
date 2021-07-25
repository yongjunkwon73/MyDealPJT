package mydealprj;

import mydealprj.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DashViewHandler {


    @Autowired
    private DashRepository dashRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPuchased_then_CREATE_1 (@Payload Puchased puchased) {
        try {

            if (!puchased.validate()) return;

            // view 객체 생성
            Dash dash = new Dash();
            // view 객체에 이벤트의 Value 를 set 함
            dash.setPuId(puchased.getId());
            dash.setPuDate(puchased.getPuDate());
            dash.setPuStatus(puchased.getPuStatus());
            // view 레파지 토리에 save
            dashRepository.save(dash);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenPayed_then_UPDATE_1(@Payload Payed payed) {
        try {
            if (!payed.validate()) return;
                // view 객체 조회

                    List<Dash> dashList = dashRepository.findByPayId(payed.getPayId());
                    for(Dash dash : dashList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dash.setPayStatus(payed.getPayStatus());
                    dash.setPayDate(payed.getPayDate());
                    dash.setPuId(payed.getPuId());
                // view 레파지 토리에 save
                dashRepository.save(dash);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPayCancelled_then_UPDATE_2(@Payload PayCancelled payCancelled) {
        try {
            if (!payCancelled.validate()) return;
                // view 객체 조회

                    List<Dash> dashList = dashRepository.findByPayId(payCancelled.getId());
                    for(Dash dash : dashList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dash.setPayStatus(payCancelled.getPayStatus());
                    dash.setPuCancelDate(payCancelled.getPayCalncelDate());
                    dash.setPuId(payCancelled.getPuId());
                // view 레파지 토리에 save
                dashRepository.save(dash);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenConsigned_then_UPDATE_3(@Payload Consigned consigned) {
        try {
            if (!consigned.validate()) return;
                // view 객체 조회

                    List<Dash> dashList = dashRepository.findByConId(consigned.getId());
                    for(Dash dash : dashList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dash.setConDate(consigned.getConDate());
                    dash.setPuId(consigned.getPuId());
                    dash.setConStatus(consigned.getConStatus());
                // view 레파지 토리에 save
                dashRepository.save(dash);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenConsignCancelled_then_UPDATE_4(@Payload ConsignCancelled consignCancelled) {
        try {
            if (!consignCancelled.validate()) return;
                // view 객체 조회

                    List<Dash> dashList = dashRepository.findByConId(consignCancelled.getId());
                    for(Dash dash : dashList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dash.setPuId(consignCancelled.getPuId());
                    dash.setConCancelDate(consignCancelled.getConCancelDate());
                    dash.setConStatus(consignCancelled.getConStatus());
                // view 레파지 토리에 save
                dashRepository.save(dash);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenStockChanged_then_UPDATE_5(@Payload StockChanged stockChanged) {
        try {
            if (!stockChanged.validate()) return;
                // view 객체 조회

                    List<Dash> dashList = dashRepository.findByStockId(stockChanged.getId());
                    for(Dash dash : dashList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dash.setStockDate(stockChanged.getStockDate());
                    dash.setStockTotal(stockChanged.getStockTotal());
                    dash.setPuId(stockChanged.getPuId());
                // view 레파지 토리에 save
                dashRepository.save(dash);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

