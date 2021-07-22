package mydealprj;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DashBordRepository extends CrudRepository<DashBord, Long> {

    List<DashBord> findByPuId(Long puId);
    List<DashBord> findByCuId(Long cuId);
    List<DashBord> findByPuId(Long puId);
    List<DashBord> findByPuId(Long puId);
    List<DashBord> findByPuId(Long puId);

}