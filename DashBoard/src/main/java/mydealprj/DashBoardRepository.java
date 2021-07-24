package mydealprj;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DashBoardRepository extends CrudRepository<DashBoard, Long> {

    List<DashBoard> findByPuId(Long puId);
    List<DashBoard> findByPayId(Long payId);
    List<DashBoard> findByPayId(Long payId);
    List<DashBoard> findByConId(Long conId);
    List<DashBoard> findByConId(Long conId);
    List<DashBoard> findByStockId(Long stockId);

}