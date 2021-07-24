
package mydealprj;

public class PuchaseCancelled extends AbstractEvent {

    private Long puId;
    private Long cuId;
    private Long carId;
    private String puStatus;
    private String puCancelDate;

    public Long getId() {
        return puId;
    }

    public void setId(Long puId) {
        this.puId = puId;
    }
    public Long getCuId() {
        return cuId;
    }

    public void setCuId(Long cuId) {
        this.cuId = cuId;
    }
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
    public String getPuStatus() {
        return puStatus;
    }

    public void setPuStatus(String puStatus) {
        this.puStatus = puStatus;
    }
    public String getPuCanceldate() {
        return puCancelDate;
    }

    public void setPuCanceldate(String puCancelDate) {
        this.puCancelDate = puCancelDate;
    }
}

