package mydealprj;

public class PuchaseCancelled extends AbstractEvent {

    private Long id;
    private Long cuId;
    private Long carId;
    private String puStatus;
    private String puCanceldate;

    public PuchaseCancelled(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return puCanceldate;
    }

    public void setPuCanceldate(String puCanceldate) {
        this.puCanceldate = puCanceldate;
    }
}
