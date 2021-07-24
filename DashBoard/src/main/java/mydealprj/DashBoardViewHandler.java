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
public class DashBoardViewHandler {


    @Autowired
    private DashBoardRepository dashBoardRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPuchased_then_CREATE_1 (@Payload Puchased puchased) {
        try {

            if (!puchased.validate()) return;

            // view 객체 생성
            DashBoard dashBoard = new DashBoard();
            // view 객체에 이벤트의 Value 를 set 함
            dashBoard.setId(+1);
            dashBoard.setPuId(puchased.getId());
            dashBoard.setCuId(puchased.getCuId());
            dashBoard.setPuDate(puchased.getPuDate());
            dashBoard.setPuStatus(puchased.getPuStatus());
            // view 레파지 토리에 save
            dashBoardRepository.save(dashBoard);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenPuchaseCancelled_then_UPDATE_1(@Payload PuchaseCancelled puchaseCancelled) {
        try {
            if (!puchaseCancelled.validate()) return;
                // view 객체 조회

                    List<DashBoard> dashBoardList = dashBoardRepository.findByPuId(puchaseCancelled.getId());
                    for(DashBoard dashBoard : dashBoardList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dashBoard.setCuId(puchaseCancelled.getCuId());
                    dashBoard.setPuCancelDate(puchaseCancelled.getPuCanceldate());
                    dashBoard.setPuStatus(puchaseCancelled.getPuStatus());
                // view 레파지 토리에 save
                dashBoardRepository.save(dashBoard);
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

                    List<DashBoard> dashBoardList = dashBoardRepository.findByPayId(payed.getPayId());
                    for(DashBoard dashBoard : dashBoardList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dashBoard.setPuId(payed.getPuId());
                    dashBoard.setSalePrice(payed.getSalePrice());
                    dashBoard.setPayStatus(payed.getPayStatus());
                    dashBoard.setPayDate(payed.getPayDate());
                // view 레파지 토리에 save
                dashBoardRepository.save(dashBoard);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPayCancelled_then_UPDATE_3(@Payload PayCancelled payCancelled) {
        try {
            if (!payCancelled.validate()) return;
                // view 객체 조회

                    List<DashBoard> dashBoardList = dashBoardRepository.findByPayId(payCancelled.getId());
                    for(DashBoard dashBoard : dashBoardList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dashBoard.setPuId(payCancelled.getPuId());
                    dashBoard.setPayStatus(payCancelled.getPayStatus());
                    dashBoard.setPayCancelDate(payCancelled.getPayCalncelDate());
                // view 레파지 토리에 save
                dashBoardRepository.save(dashBoard);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenConsigned_then_UPDATE_4(@Payload Consigned consigned) {
        try {
            if (!consigned.validate()) return;
                // view 객체 조회

                    List<DashBoard> dashBoardList = dashBoardRepository.findByConId(consigned.getId());
                    for(DashBoard dashBoard : dashBoardList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dashBoard.setPuId(consigned.getPuId());
                    dashBoard.setConDate(consigned.getConDate());
                    dashBoard.setPuStatus(consigned.getPayStatus());
                    dashBoard.setPuStatus(consigned.getConStatus());
                // view 레파지 토리에 save
                dashBoardRepository.save(dashBoard);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenConsignCancelled_then_UPDATE_5(@Payload ConsignCancelled consignCancelled) {
        try {
            if (!consignCancelled.validate()) return;
                // view 객체 조회

                    List<DashBoard> dashBoardList = dashBoardRepository.findByConId(consignCancelled.getId());
                    for(DashBoard dashBoard : dashBoardList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dashBoard.setPuId(consignCancelled.getPuId());
                    dashBoard.setConCancelDate(consignCancelled.getConCancelDate());
                    dashBoard.setPayStatus(consignCancelled.getPayStatus());
                    dashBoard.setConCancelDate(consignCancelled.getConCancelDate());
                // view 레파지 토리에 save
                dashBoardRepository.save(dashBoard);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenStockChanged_then_UPDATE_6(@Payload StockChanged stockChanged) {
        try {
            if (!stockChanged.validate()) return;
                // view 객체 조회

                    List<DashBoard> dashBoardList = dashBoardRepository.findByStockId(stockChanged.getId());
                    for(DashBoard dashBoard : dashBoardList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dashBoard.setStockDate(stockChanged.getStockDate());
                    dashBoard.setPuId(stockChanged.getPuId());
                    dashBoard.setStockType(stockChanged.getStockType());
                    dashBoard.setStockTotal(stockChanged.getStockTotal());
                // view 레파지 토리에 save
                dashBoardRepository.save(dashBoard);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

