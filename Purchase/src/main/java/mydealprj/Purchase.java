package mydealprj;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Purchase_table")
public class Purchase {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long puId;
    private Long cuId;
    private String puStatus;
    private String puDate;
    private String puCancelDate;
    private Long carId;

    @PostPersist
    public void onPostPersist(){
        Puchased puchased = new Puchased();
        BeanUtils.copyProperties(this, puchased);
        puchased.publishAfterCommit();

                
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        mydealprj.external.Billing billing = new mydealprj.external.Billing();
        // mappings goes here
        // 일단 오류 Marking  후 수정 
        // 원 Application.applicationContext.getBean(mydealprj.external.BillingService.class).pay(billing);
         PurchaseApplication.applicationContext.getBean(mydealprj.external.BillingService.class).pay(billing);
        
    }
    @PostUpdate
    public void onPostUpdate(){
        PuchaseCancelled puchaseCancelled = new PuchaseCancelled();
        BeanUtils.copyProperties(this, puchaseCancelled);
        puchaseCancelled.publishAfterCommit();

    }

    public Long getPuId() {
        return puId;
    }

    public void setPuId(Long puId) {
        this.puId = puId;
    }
    public Long getCuId() {
        return cuId;
    }

    public void setCuId(Long cuId) {
        this.cuId = cuId;
    }
    public String getPuStatus() {
        return puStatus;
    }

    public void setPuStatus(String puStatus) {
        this.puStatus = puStatus;
    }
    public String getPuDate() {
        return puDate;
    }

    public void setPuDate(String puDate) {
        this.puDate = puDate;
    }
    public String getPuCancelDate() {
        return puCancelDate;
    }

    public void setPuCancelDate(String puCancelDate) {
        this.puCancelDate = puCancelDate;
    }
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }




}
