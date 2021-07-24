package mydealprj;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="DashBoard_table")
public class DashBoard {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long dashId;
        private Long puId;
        private Long cuId;
        private String puDate;
        private String puCancelDate;
        private String puStatus;
        private Long payId;
        private String payDate;
        private String payCancelDate;
        private String payStatus;
        private Long conId;
        private String conDate;
        private String conCancelDate;
        private String conStatus;
        private Long stockId;
        private String stockType;
        private String stockDate;
        private Long stockTotal;
        private Long salePrice;


        public Long getDashId() {
            return dashId;
        }

        public void setDashId(Long dashId) {
            this.dashId = dashId;
        }
        public Long getPuId() {
            return puId;
        }

        public void setPuId(Long puId) {
            this.puId = puId;
        }
        public Long getCuId() {
            return cuId;
        }

        public void setCuId(Long cuId) {
            this.cuId = cuId;
        }
        public String getPuDate() {
            return puDate;
        }

        public void setPuDate(String puDate) {
            this.puDate = puDate;
        }
        public String getPuCancelDate() {
            return puCancelDate;
        }

        public void setPuCancelDate(String puCancelDate) {
            this.puCancelDate = puCancelDate;
        }
        public String getPuStatus() {
            return puStatus;
        }

        public void setPuStatus(String puStatus) {
            this.puStatus = puStatus;
        }
        public Long getPayId() {
            return payId;
        }

        public void setPayId(Long payId) {
            this.payId = payId;
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
        public String getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(String payStatus) {
            this.payStatus = payStatus;
        }
        public Long getConId() {
            return conId;
        }

        public void setConId(Long conId) {
            this.conId = conId;
        }
        public String getConDate() {
            return conDate;
        }

        public void setConDate(String conDate) {
            this.conDate = conDate;
        }
        public String getConCancelDate() {
            return conCancelDate;
        }

        public void setConCancelDate(String conCancelDate) {
            this.conCancelDate = conCancelDate;
        }
        public String getConStatus() {
            return conStatus;
        }

        public void setConStatus(String conStatus) {
            this.conStatus = conStatus;
        }
        public Long getStockId() {
            return stockId;
        }

        public void setStockId(Long stockId) {
            this.stockId = stockId;
        }
        public String getStockType() {
            return stockType;
        }

        public void setStockType(String stockType) {
            this.stockType = stockType;
        }
        public String getStockDate() {
            return stockDate;
        }

        public void setStockDate(String stockDate) {
            this.stockDate = stockDate;
        }
        public Long getStockTotal() {
            return stockTotal;
        }

        public void setStockTotal(Long stockTotal) {
            this.stockTotal = stockTotal;
        }
        public Long getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(Long salePrice) {
            this.salePrice = salePrice;
        }

}
