package eds.cepm.newcepmart.resource.controller;

import eds.cepm.newcepmart.resource.dao.ResourceRepository;
import eds.cepm.newcepmart.resource.dao.ResourceViewRepository;
import eds.cepm.newcepmart.resource.po.ResourcePO;
import eds.cepm.newcepmart.resource.po.ResourceViewPO;
import eds.cepm.newcepmart.resource.vo.ResourceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RefreshScope
public class ResourceController {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);

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
        LOG.info("********************* "+ resourceVOS);
        return resourceVOS;
    }

    @GetMapping(value = "resource/{id}")
    public Object get(@PathVariable long id){
        ResourceViewPO resourceViewPO = resourceViewRepository.findById(id).get();
        ResourceVO resourceVO = null;
        if(resourceViewPO != null){
            resourceVO = resourceViewPO.convertToVO();
        }
        LOG.info("********************* "+ resourceVO);
        return resourceVO;
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