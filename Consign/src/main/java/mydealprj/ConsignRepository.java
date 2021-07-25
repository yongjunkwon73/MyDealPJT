package mydealprj;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="consigns", path="consigns")
public interface ConsignRepository extends PagingAndSortingRepository<Consign, Long>{


}
