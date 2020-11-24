package eds.cepm.newcepmart.dao;

import eds.cepm.newcepmart.po.ResourceViewPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceViewRepository extends JpaRepository<ResourceViewPO, Long> {

}