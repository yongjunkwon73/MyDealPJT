
package mydealprj.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name="Billing", url="http://Billing:8080")
public interface BillingService {
    @RequestMapping(method= RequestMethod.GET, path="/billings")
    public void pay(@RequestBody Billing billing);

}

