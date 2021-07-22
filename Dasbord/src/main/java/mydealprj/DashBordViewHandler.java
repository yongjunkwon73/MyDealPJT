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
public class DashBordViewHandler {


    @Autowired
    private DashBordRepository dashBordRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPuchased_then_CREATE_1 (@Payload Puchased puchased) {
        try {

            if (!puchased.validate()) return;

            // view 객체 생성
            DashBord dashBord = new DashBord();
            // view 객체에 이벤트의 Value 를 set 함
            dashBord.setId(+1);
            dashBord.setPuId(puchased.getId());
            dashBord.setCuId(puchased.getCuId());
            dashBord.setPuStatus(puchased.getPuStatus());
            dashBord.setPuDate(puchased.getPuDate());
            // view 레파지 토리에 save
            dashBordRepository.save(dashBord);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenPuchaseCancelled_then_UPDATE_1(@Payload PuchaseCancelled puchaseCancelled) {
        try {
            if (!puchaseCancelled.validate()) return;
                // view 객체 조회

                    List<DashBord> dashBordList = dashBordRepository.findByPuId(puchaseCancelled.getId());
                    for(DashBord dashBord : dashBordList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dashBord.setPuStatus(puchaseCancelled.getPuStatus());
                    dashBord.setPuCancelDate(puchaseCancelled.getPuCanceldate());
                // view 레파지 토리에 save
                dashBordRepository.save(dashBord);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPayed_then_UPDATE_2(@Payload Payed payed) {
        try {
            if (!payed.validate()) return;
                // view 객체 조회

                    List<DashBord> dashBordList = dashBordRepository.findByCuId(payed.getPuId());
                    for(DashBord dashBord : dashBordList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dashBord.setPayId(payed.getPayId());
                    dashBord.setPayDate(payed.getPayDate());
                    dashBord.setPayStatus(payed.getPayStatus());
                    dashBord.setSalePrice(payed.getSalePrice());
                // view 레파지 토리에 save
                dashBordRepository.save(dashBord);
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

                    List<DashBord> dashBordList = dashBordRepository.findByPuId(consigned.getPuId());
                    for(DashBord dashBord : dashBordList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dashBord.setConId(consigned.getId());
                    dashBord.setPayStatus(consigned.getConStatus());
                    dashBord.setConStatus(consigned.getConStatus());
                // view 레파지 토리에 save
                dashBordRepository.save(dashBord);
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

                    List<DashBord> dashBordList = dashBordRepository.findByPuId(consignCancelled.getPuId());
                    for(DashBord dashBord : dashBordList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dashBord.setConId(consignCancelled.getId());
                    dashBord.setConStatus(consignCancelled.getPayStatus());
                // view 레파지 토리에 save
                dashBordRepository.save(dashBord);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPayCancelled_then_UPDATE_5(@Payload PayCancelled payCancelled) {
        try {
            if (!payCancelled.validate()) return;
                // view 객체 조회

                    List<DashBord> dashBordList = dashBordRepository.findByPuId(payCancelled.getPuId());
                    for(DashBord dashBord : dashBordList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dashBord.setPayId(payCancelled.getId());
                    dashBord.setPayStatus(payCancelled.getPayStatus());
                    dashBord.setPuCancelDate(payCancelled.getPayCalncelDate());
                // view 레파지 토리에 save
                dashBordRepository.save(dashBord);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

