
package mydealprj.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
//  원소스 
 /*
@FeignClient(name="Billing", url="http://Billing:8080")
public interface BillingService {
    @RequestMapping(method= RequestMethod.GET, path="/billings")
    public void pay(@RequestBody Billing billing);
}  */
  
 // pu 서비스에서 바라보는 billing 서비스 url 일부분을 ConfigMao 사용하요 구현
 
@FeignClient(name="Billing", url="http://${api.url.Biiling}")
public interface BillingService {
    @RequestMapping(method= RequestMethod.GET, path="/billings")
    public void pay(@RequestBody Billing billing);
   */
} 