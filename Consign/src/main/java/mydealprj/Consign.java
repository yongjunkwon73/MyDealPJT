package mydealprj;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

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
    public void onPostPersist(){
        Consigned consigned = new Consigned();
        BeanUtils.copyProperties(this, consigned);
        consigned.publishAfterCommit();

    }
    @PostUpdate
    public void onPostUpdate(){
        ConsignCancelled consignCancelled = new ConsignCancelled();
        BeanUtils.copyProperties(this, consignCancelled);
        consignCancelled.publishAfterCommit();

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
