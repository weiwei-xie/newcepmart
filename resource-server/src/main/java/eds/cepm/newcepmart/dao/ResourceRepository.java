package eds.cepm.newcepmart.dao;


import eds.cepm.newcepmart.po.ResourcePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<ResourcePO, Long> {

//    @Query(value = "select r.* from resource_view r", nativeQuery = true)
//    Page<Resource> findAllResource(Resource resource, Pageable pageable);

    @Query(value = "select r.* from resource_view r", nativeQuery = true)
    public List<ResourcePO> findAllResource();
}