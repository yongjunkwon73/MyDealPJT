package mydealprj;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Billing_table")
public class Billing {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long payId;
    private Long puId;
    private Long salePrice;
    private String payStatus;
    private String payDate;
    private String payCancelDate;

    @PostPersist
    public void onPostPersist(){
         // 결재 완료 후 Kafka 전
        if(this.payStatus == "Y") {
          Payed payed = new Payed();
          BeanUtils.copyProperties(this, payed);
          payed.publishAfterCommit();
            
        }      

    }
    @PostUpdate
    public void onPostUpdate(){
         // 결재 취소 전송 
       if(this.payStatus == "M") {
        PayCancelled payCancelled = new PayCancelled();
        BeanUtils.copyProperties(this, payCancelled);
        payCancelled.publishAfterCommit(); 
       }
    }

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }
    public Long getPuId() {
        return puId;
    }

    public void setPuId(Long puId) {
        this.puId = puId;
    }
    public Long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }
    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }
    public String getPayCancelDate() {
        return payCancelDate;
    }

    public void setPayCancelDate(String payCancelDate) {
        this.payCancelDate = payCancelDate;
    }




}
