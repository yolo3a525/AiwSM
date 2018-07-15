package com.aiw.api.${module}.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aiw.entity.${component};
import com.aiw.entity.Page;
import com.aiw.entity.SysResult;
import com.aiw.mapper.${component}Mapper;


/** 
 * 说明：${module}.${componentLower}
 * 创建人：aiw
 * 创建时间：${nowDate?string("yyyy-MM-dd")}
 */

@RestController(value="Api${component}Controller")
@RequestMapping(value="/api/${componentLower}")
public class ${component}Controller extends BaseController<${component}Mapper, ${component}>{
   
   
   
   
   @Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public SysResult list(@ModelAttribute Page page,@ModelAttribute {component} t){
        return  list_p(page, t);
    }
    
    
    @Override
    @RequestMapping(value = "/save")
    @ResponseBody
    public SysResult save(@RequestBody {component} t){
        SysResult d = save_p(t);
        return  d;
    }
    
    @Override
    @RequestMapping(value = "/update")
    @ResponseBody
    public SysResult update(@RequestBody {component} t) {
        SysResult bj = update_p(t);
        return bj;
    }
    
    @Override
    @ResponseBody
    public SysResult delete(@PathVariable("id") Integer id){
        return delete_p(id);
    }
    
    
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public SysResult delete(@PathVariable("id") String id){
        int i = mapper.delete(id);
        return SysResult.oK();
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
    @Override
    @RequestMapping(value = "/badd")
    public SysResult badd() {
        return get_p();
    }

    @RequestMapping(value = "/bedit/{id}")
    public SysResult bupdate(@PathVariable("id") String id) {
        SysResult SysResult = new SysResult(); 
        return SysResult; 
    }


    @Override
    public SysResult bupdate(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }
   
	
}