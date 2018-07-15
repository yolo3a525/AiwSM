package com.aiw.api.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aiw.bdzb.util.BDZBConstants;
import com.aiw.entity.Page;
import com.aiw.entity.SysResult;
import com.aiw.entity.User;
import com.aiw.mapper.UserMapper;
import com.aiw.util.FileUpload;
import com.aiw.util.PathUtil;

@Controller(value="ApiSysController")
@RequestMapping(value="/api/sys")
public class SysController extends BaseController<UserMapper, User>{
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public SysResult list(@ModelAttribute Page page,@ModelAttribute User t){
    	return  list_p(page, t);
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public SysResult save(@ModelAttribute User t){
		return  save_p(t);
    }
	
	@RequestMapping(value = "/logo")
    @ResponseBody
    public SysResult logouploadinput() throws Exception{
	    SysResult SysResult = new SysResult(); 
	    //SysResult.setViewName("/" + getModule() + "/logo");
        return SysResult; 
    }
	
	
    @RequestMapping(value = "/logoupload")
    @ResponseBody
    public SysResult logoupload(
            @RequestParam(value="image",required=false) MultipartFile image
            ) throws Exception{
        if (null != image && !image.isEmpty()) {
            //String uuid = UUID.randomUUID().toString();
            String filePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_SYS;
            FileUpload.fileUp(image, filePath, "logo");                      
        }
        return SysResult.oK();
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public SysResult update(User t) {
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
	
	@RequestMapping(value = "/info")
    public SysResult bsave(){
    	SysResult SysResult = new SysResult(); 
    	//SysResult.addObject("sys", System.getProperties());
    	//SysResult.addObject("env", System.getenv());
    	//SysResult.setViewName("/sys/info");  
	    return SysResult; 
    }
	
	@RequestMapping(value = "/pageSize/{pageSize}")
	@ResponseBody
    public Integer pageSize(@PathVariable("pageSize") Integer pageSize){
		request.getSession().setAttribute("pageSize", pageSize);
    	return pageSize;
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
	
