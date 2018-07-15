package com.aiw.base.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.aiw.base.entity.BaseJsonBean;
import com.aiw.base.entity.Page;
import com.aiw.base.entity.User;
import com.aiw.base.mapper.UserMapper;
import com.aiw.base.util.FileUpload;
import com.aiw.base.util.PathUtil;
import com.aiw.bdzb.util.BDZBConstants;

@Controller
@Scope("prototype")
@RequestMapping(value="/sys")
public class SysController extends BaseController<UserMapper, User>{
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute User t){
    	return  list_p(page, t);
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public User save(@ModelAttribute User t){
		return  save_p(t);
    }
	
	@RequestMapping(value = "/logo")
    @ResponseBody
    public ModelAndView logouploadinput() throws Exception{
	    ModelAndView modelAndView = new ModelAndView(); 
	    modelAndView.setViewName("/" + getModule() + "/logo");
        return modelAndView; 
    }
	
	
    @RequestMapping(value = "/logoupload")
    @ResponseBody
    public BaseJsonBean logoupload(
            @RequestParam(value="image",required=false) MultipartFile image
            ) throws Exception{
        if (null != image && !image.isEmpty()) {
            //String uuid = UUID.randomUUID().toString();
            String filePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_SYS;
            FileUpload.fileUp(image, filePath, "logo");                      
        }
        return bj;
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(User t) {
		return update_p(t);
	}
    
	@Override
    @RequestMapping(value = "/delete/{id}")
	@ResponseBody
    public Integer delete(@PathVariable("id") Integer id){
		return delete_p(id);
    }
    
	@Override
    @RequestMapping(value = "/{id}")
    public ModelAndView get(@PathVariable("id") Integer id){
		return get_p(id);
    }
        
	@Override
    @RequestMapping(value = "")
    public ModelAndView get(){
    	return get_p();
    }
	
	@RequestMapping(value = "/info")
    public ModelAndView bsave(){
    	ModelAndView modelAndView = new ModelAndView(); 
    	modelAndView.addObject("sys", System.getProperties());
    	modelAndView.addObject("env", System.getenv());
    	modelAndView.setViewName("/sys/info");  
	    return modelAndView; 
    }
	
	@RequestMapping(value = "/pageSize/{pageSize}")
	@ResponseBody
    public Integer pageSize(@PathVariable("pageSize") Integer pageSize){
		request.getSession().setAttribute("pageSize", pageSize);
    	return pageSize;
    }
	
	@Override
	@RequestMapping(value = "/badd")
	public ModelAndView badd() {
		return get_p();
	}

	@Override
	@RequestMapping(value = "/bedit/{id}")
	public ModelAndView bupdate(@PathVariable("id") Integer id) {
		return get_p();
	}
	
}
	
