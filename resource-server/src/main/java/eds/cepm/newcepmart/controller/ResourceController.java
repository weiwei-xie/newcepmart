package eds.cepm.newcepmart.controller;

import eds.cepm.newcepmart.dao.ResourceRepository;
import eds.cepm.newcepmart.dao.ResourceViewRepository;
import eds.cepm.newcepmart.po.ResourcePO;
import eds.cepm.newcepmart.po.ResourceViewPO;
import eds.cepm.newcepmart.vo.ResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RefreshScope
public class ResourceController {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceViewRepository resourceViewRepository;

    @GetMapping(value = "resources")
    public Object all(){
        List<ResourceViewPO> resourceViewPOS = resourceViewRepository.findAll();
        List<ResourceVO> resourceVOS = new ArrayList<>();
        for(ResourceViewPO resourceViewPO : resourceViewPOS){
            resourceVOS.add(resourceViewPO.convertToVO());
        }

        return resourceVOS;
    }

    @GetMapping(value = "resource/{id}")
    public Object get(@PathVariable long id){
        ResourceViewPO resourceViewPO = null;
        try {
            resourceViewPO = resourceViewRepository.getOne(id);
        } catch (EntityNotFoundException e) {}

        if(resourceViewPO != null){
            return resourceViewPO.convertToVO();
        }
        return null;
    }

    @RequestMapping(value = "resource/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id){
         resourceRepository.deleteById(id);
    }

    @RequestMapping(value = "resource", method = RequestMethod.POST)
    public void save(@RequestBody ResourcePO resource){
        resourceRepository.save(resource);
    }
}