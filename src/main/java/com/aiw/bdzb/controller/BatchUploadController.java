package com.aiw.bdzb.controller;

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

import com.aiw.base.controller.BaseController;
import com.aiw.base.entity.BaseJsonBean;
import com.aiw.base.entity.Page;
import com.aiw.base.util.FileUpload;
import com.aiw.base.util.FileUtil;
import com.aiw.base.util.PathUtil;
import com.aiw.bdzb.entity.Jewelry;
import com.aiw.bdzb.mapper.JewelryMapper;
import com.aiw.bdzb.util.BDZBConstants;


/** 
 * 说明：bdzb.cardset
 * 创建人：aiw
 * 创建时间：2017-10-19
 */

@Controller
@Scope("prototype")
@RequestMapping(value= {"/bdzb/batchupload","/bdzb/gysbatchupload"})
public class BatchUploadController extends BaseController<JewelryMapper, Jewelry>{
   
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute Jewelry t){
    	return  list_p(page, t);
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public Jewelry save(@ModelAttribute Jewelry t){
		return  save_p(t);
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(Jewelry t) {
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
		ModelAndView modelAndView = new ModelAndView(); 
    	setJspItemPath(modelAndView);
	    return modelAndView; 
    }
	
    @RequestMapping(value = "/upload")
	@ResponseBody
    public BaseJsonBean upload(@RequestParam(value="file",required=false) MultipartFile file){
    	
    	String filePath = "";
    	
    	if (null != file) {
    	    String filename = null;
    		if(file.getOriginalFilename().endsWith(BDZBConstants.MOVIE_EXT)) {
    		    filename = file.getOriginalFilename().replace(BDZBConstants.MOVIE_EXT, "");
    			filePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_MOIVE;
    			FileUtil.delFile(filePath + file.getOriginalFilename());
    			FileUpload.fileUp(file, filePath,filename);	
    			mapper.updateMovieFlag(filename);
    			System.out.println(filePath);
    			
    		}else if(file.getOriginalFilename().endsWith(BDZBConstants.IMAGE_EXT)) {
    		    filename = file.getOriginalFilename().replace(BDZBConstants.IMAGE_EXT, "");
    			filePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_IMG;
    			FileUtil.delFile(filePath + file.getOriginalFilename());
    			FileUpload.fileUp(file, filePath, filename);	
    			mapper.updateImageFlag(filename);
    			System.out.println(filePath);
    		}
    	}
		return bj;
    }
    
    
	@Override
	@RequestMapping(value = "/badd")
	public ModelAndView badd() {
		return get_p();
	}

	@Override
	@RequestMapping(value = "/bedit/{id}")
	public ModelAndView bupdate(@PathVariable("id") Integer id) {
		ModelAndView modelAndView =  get_p(id);
		modelAndView.setViewName("/" + getModule() + "/edit");
		return modelAndView;
	}
}