package com.aiw.api.base.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aiw.entity.DG;
import com.aiw.entity.Page;
import com.aiw.entity.SysResult;
import com.aiw.mapper.DGMapper;


@RestController(value="ApiDGController")
@RequestMapping(value="/api/dg")
public class DGController extends BaseController<DGMapper, DG>{
    
    
    
    @Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public SysResult list(@ModelAttribute Page page,@ModelAttribute DG t){
        return  list_p(page, t);
    }
    
    
    @Override
    @RequestMapping(value = "/save")
    @ResponseBody
    public SysResult save(@RequestBody DG t){
        SysResult d = save_p(t);
        return  d;
    }
    
    @Override
    @RequestMapping(value = "/update")
    @ResponseBody
    public SysResult update(@RequestBody DG t) {
        SysResult bj = update_p(t);
        return bj;
    }
    
    @Override
    @ResponseBody
    public SysResult delete(@PathVariable("id") Integer id){
        return delete_p(id);
    }
    
    
    @RequestMapping(value = "/delete/{dgCode}")
    @ResponseBody
    public SysResult delete(@PathVariable("dgCode") String dgCode){
        int i = mapper.delete(dgCode);
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

    @RequestMapping(value = "/bedit/{code}")
    public SysResult bupdate(@PathVariable("code") String code) {
        SysResult SysResult = new SysResult(); 
        return SysResult; 
    }


    @Override
    public SysResult bupdate(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }
}
    
