package mydealprj;

public class PayCancelled extends AbstractEvent {

    private Long id;
    private Long puId;
    private Long salePrice;
    private String payStatus;
    private String payCalncelDate;

    public PayCancelled(){
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
    public String getPayCalncelDate() {
        return payCalncelDate;
    }

    public void setPayCalncelDate(String payCalncelDate) {
        this.payCalncelDate = payCalncelDate;
    }
}
