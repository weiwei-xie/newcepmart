package eds.cepm.newcepmart.po;

import eds.cepm.newcepmart.vo.ResourceVO;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="resource_view")
public class ResourceViewPO {
    @Id
    @Column(name = "res_id")
    private Long res_id;

    @Column(name = "res_parent_id")
    private Long res_parent_id;

    @Column(name = "res_des")
    private String res_des;

    @Column(name = "res_name")
    private String res_name;

    @Column(name = "res_type")
    private String res_type;

    @Column(name = "sox_flag")
    private Integer sox_flag;

    @Column(name = "requestable")
    private Integer requestable;

    @Column(name = "create_time")
    private Timestamp create_time;

    @Column(name = "update_time")
    private Timestamp update_time;

    @Column(name = "res_fqn")
    private String res_fqn;

    public Long getRes_id() {
        return res_id;
    }

    public void setRes_id(Long res_id) {
        this.res_id = res_id;
    }

    public Long getRes_parent_id() {
        return res_parent_id;
    }

    public void setRes_parent_id(Long res_parent_id) {
        this.res_parent_id = res_parent_id;
    }

    public String getRes_des() {
        return res_des;
    }

    public void setRes_des(String res_des) {
        this.res_des = res_des;
    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public Integer getSox_flag() {
        return sox_flag;
    }

    public void setSox_flag(Integer sox_flag) {
        this.sox_flag = sox_flag;
    }

    public Integer getRequestable() {
        return requestable;
    }

    public void setRequestable(Integer requestable) {
        this.requestable = requestable;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }

    public String getRes_type() {
        return res_type;
    }

    public void setRes_type(String res_type) {
        this.res_type = res_type;
    }

    public String getRes_fqn() {
        return res_fqn;
    }

    public void setRes_fqn(String res_fqn) {
        this.res_fqn = res_fqn;
    }

    public ResourceVO convertToVO(){
        ResourceVO resourceVO = new ResourceVO();
        BeanUtils.copyProperties(this, resourceVO);
        return resourceVO;
    }
}
