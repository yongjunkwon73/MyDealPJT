package mydealprj;

public class ConsignCancelled extends AbstractEvent {

    private Long id;
    private Long puId;
    private String conStatus;
    private String payStatus;
    private String conCancelDate;

    public ConsignCancelled(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public String getConCancelDate() {
        return conCancelDate;
    }

    public void setConCancelDate(String conCancelDate) {
        this.conCancelDate = conCancelDate;
    }
}
