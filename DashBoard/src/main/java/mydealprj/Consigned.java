package mydealprj;

public class Consigned extends AbstractEvent {

    private Long conId;
    private Long puId;
    private String conStatus;
    private String payStatus;
    private String conDate;

    public Long getId() {
        return conId;
    }

    public void setId(Long conId) {
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
}