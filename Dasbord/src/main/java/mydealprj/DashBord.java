package mydealprj;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="DashBord_table")
public class DashBord {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long dashId;
        private Long puId;
        private Long cuId;
        private String puStatus;
        private String puDate;
        private String puCancelDate;
        private Long payId;
        private String payStatus;
        private String payDate;
        private Long conId;
        private String conStatus;
        private String conDate;
        private String conCancelDate;
        private Long carId;
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
        public String getPuCancelDate() {
            return puCancelDate;
        }

        public void setPuCancelDate(String puCancelDate) {
            this.puCancelDate = puCancelDate;
        }
        public Long getPayId() {
            return payId;
        }

        public void setPayId(Long payId) {
            this.payId = payId;
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
        public Long getConId() {
            return conId;
        }

        public void setConId(Long conId) {
            this.conId = conId;
        }
        public String getConStatus() {
            return conStatus;
        }

        public void setConStatus(String conStatus) {
            this.conStatus = conStatus;
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
        public Long getCarId() {
            return carId;
        }

        public void setCarId(Long carId) {
            this.carId = carId;
        }
        public Long getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(Long salePrice) {
            this.salePrice = salePrice;
        }

}
