package com.aiw.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aiw.entity.Page;
import com.aiw.entity.Privilege;
import com.aiw.entity.SysResult;
import com.aiw.mapper.PrivilegeMapper;

@RestController(value="ApiPrivilegeController")
@RequestMapping(value="/api/privilege")
public class PrivilegeController extends BaseController<PrivilegeMapper, Privilege>{
   
	
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public SysResult list(@ModelAttribute Page page,@ModelAttribute Privilege t){
    	return  list_p(page, t);
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public SysResult save(@RequestBody Privilege t){
		return  save_p(t);
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public SysResult update(@RequestBody Privilege t) {
		return update_p(t);
	}
    
	@Override
    @RequestMapping(value = "/delete/{id}")
	@ResponseBody
    public SysResult delete(@PathVariable("id") Integer id){
		return delete_p(id);
    }
    
	@Override
    @RequestMapping(value = "/{id}")
    public SysResult get(@PathVariable("id") Integer id){
		return get_p(id);
    }
        
	@Override
    @RequestMapping(value = "")
    public SysResult get(){
    	return get_p();
    }
	
	
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
    public SysResult tree(){
    	
		 List<Privilege>  list = mapper.select();
		 
		 Map<Integer,Privilege> all = new HashMap<>();
		 Privilege root = new Privilege();
		 root.setName("根");
		 root.setChildren(new ArrayList<Privilege>());
		 root.setDepth(0);
		 
		 all.put(0, root);
		 
		 int dept = 1;
		 for(int i = 0;i < dept;i++) {
			 for (Privilege Privilege : list) {
				 //按照深度遍历
				 if(i == 0 && Privilege.getDepth() > dept) {
					 dept = Privilege.getDepth();
				 }
				 if((Privilege.getDepth() - 1) == i) {
					 all.put(Privilege.getId(), Privilege);
					 
					 if(Privilege.getPid()== null) {
						 Privilege.setPid(0);
					 }
					 if(all.get(Privilege.getPid()).getChildren() == null) {
						 all.get(Privilege.getPid()).setChildren(new ArrayList<Privilege>());
					 }
					 all.get(Privilege.getPid()).getChildren().add(Privilege);
				 }
			}
		 }
		 SysResult SysResult = new SysResult();
	     return SysResult;  
    }
	
	@Override
	@RequestMapping(value = "/badd")
	public SysResult badd() {
		return get_p();
	}

	@Override
	@RequestMapping(value = "/bedit/{id}")
	public SysResult bupdate(@PathVariable("id") Integer id) {
		return get_p();
	}
    
}
	
