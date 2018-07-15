package com.aiw.zycp.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aiw.api.base.controller.BaseController;
import com.aiw.entity.Page;
import com.aiw.entity.SysResult;
import com.aiw.zycp.entity.Answer;
import com.aiw.zycp.mapper.AnswerMapper;

@RestController(value="ApiAnswerController")
@RequestMapping(value="/zycp/api/answer")
public class AnswerController extends BaseController<AnswerMapper, Answer>{
   
	
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public SysResult list(@ModelAttribute Page page,@ModelAttribute Answer t){
    	return  list_p(page, t);
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public SysResult save(@ModelAttribute Answer t){
        int i = mapper.insert(t);
        logger.debug("update " + i);
        
        //发送邮件
        
        return SysResult.oK(t);
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
    public SysResult badd() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SysResult bupdate(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SysResult update(Answer t) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SysResult delete(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }
	
		

    
}
	
