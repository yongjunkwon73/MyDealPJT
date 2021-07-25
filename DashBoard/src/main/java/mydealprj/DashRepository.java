package mydealprj;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DashRepository extends CrudRepository<Dash, Long> {

    List<Dash> findByPayId(Long payId);
   // List<Dash> findByPayId(Long payId);
    List<Dash> findByConId(Long conId);
   // List<Dash> findByConId(Long conId);
    List<Dash> findByStockId(Long stockId);

}