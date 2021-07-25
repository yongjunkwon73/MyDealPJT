package mydealprj;

public class StockChanged extends AbstractEvent {

    private Long stockId;
    private Long carId;
    private String carType;
    private Long stockTotal;
    private String stockDate;
    private String stockType;
    private Long puId;

    public Long getId() {
        return stockId;
    }

    public void setId(Long stockId) {
        this.stockId = stockId;
    }
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }
    public Long getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(Long stockTotal) {
        this.stockTotal = stockTotal;
    }
    public String getStockDate() {
        return stockDate;
    }

    public void setStockDate(String stockDate) {
        this.stockDate = stockDate;
    }
    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }
    public Long getPuId() {
        return puId;
    }

    public void setPuId(Long puId) {
        this.puId = puId;
    }
}