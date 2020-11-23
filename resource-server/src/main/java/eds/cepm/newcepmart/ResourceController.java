package eds.cepm.newcepmart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RestController
@RefreshScope
public class ResourceController {

    @Autowired
    private ResourceRepository resourceRepository;

    @GetMapping(value = "resource/all")
    public Object all(){
        return resourceRepository.findAll();
    }

    @GetMapping(value = "resource/{id}")
    public Object get(@PathVariable long id){
        return resourceRepository.findById(id).get();
    }

    @RequestMapping(value = "resource/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id){
         resourceRepository.deleteById(id);
    }

    @RequestMapping(value = "resource", method = RequestMethod.POST)
    public void save(@RequestBody Resource resource){
        resourceRepository.save(resource);
    }
}