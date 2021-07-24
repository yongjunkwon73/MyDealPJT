package mydealprj;

public class Puchased extends AbstractEvent {

    private Long puId;
    private Long cuId;
    private String puStatus;
    private String puDate;
    private Long carId;

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
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
}