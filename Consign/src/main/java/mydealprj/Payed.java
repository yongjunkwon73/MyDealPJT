
package mydealprj;

public class Payed extends AbstractEvent {

    private Long payId;
    private Long puID;
    private Long salePrice;
    private String payStatus;
    private String payDate;

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
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
    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }
}

