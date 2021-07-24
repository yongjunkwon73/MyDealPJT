package mydealprj.external;

public class Billing {

    private Long payId;
    private Long puId;
    private Long salePrice;
    private String payStatus;
    private String payDate;
    private String payCancelDate;

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
