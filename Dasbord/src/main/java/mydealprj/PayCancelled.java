package mydealprj;

public class PayCancelled extends AbstractEvent {

    private Long payId;
    private Long puID;
    private Long salePrice;
    private String payStatus;
    private String payCalncelDate;

    public Long getId() {
        return payId;
    }

    public void setId(Long payId) {
        this.payId = payId;
    }
    public Long getPuId() {
        return puID;
    }

    public void setPuId(Long puID) {
        this.puID = puID;
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