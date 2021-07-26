package mydealprj;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;
 

//import mydealprj.external.Billing ;
//import mydealprj.external.BillingService;

@Entity
@Table(name="Consign_table")
public class Consign {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long conId;
    private Long puId;
    private String conStatus;
    private String payStatus;
    private String conDate;
    private String conCancelDate;

    @PostPersist
    public void onPostPersist() throws Exception{
        // 구매 배송 요청시 
       
        if("YES".equals(this.conStatus)) {
            Consigned consigned = new Consigned();
            BeanUtils.copyProperties(this, consigned);
            consigned.publishAfterCommit();
        }else{
            throw new Exception("Consign system down");
        } 


    }
    @PostUpdate
    public void onPostUpdate() throws Exception{
        // 구매 취소시  배송 취소
        if("NO".equals(this.conStatus)) {
            ConsignCancelled consignCancelled = new ConsignCancelled();
            BeanUtils.copyProperties(this, consignCancelled);
            consignCancelled.publishAfterCommit();
        }else{
            throw new Exception("Consign system down");
        }  


    }

    public Long getConId() {
        return conId;
    }

    public void setConId(Long conId) {
        this.conId = conId;
    }
    public Long getPuId() {
        return puId;
    }

    public void setPuId(Long puId) {
        this.puId = puId;
    }
    public String getConStatus() {
        return conStatus;
    }

    public void setConStatus(String conStatus) {
        this.conStatus = conStatus;
    }
    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
    public String getConDate() {
        return conDate;
    }

    public void setConDate(String conDate) {
        this.conDate = conDate;
    }
    public String getConCancelDate() {
        return conCancelDate;
    }

    public void setConCancelDate(String conCancelDate) {
        this.conCancelDate = conCancelDate;
    }




}
