package eds.cepm.newcepmart.resource.dao;

import eds.cepm.newcepmart.resource.po.ResourceViewPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceViewRepository extends JpaRepository<ResourceViewPO, Long> {

}