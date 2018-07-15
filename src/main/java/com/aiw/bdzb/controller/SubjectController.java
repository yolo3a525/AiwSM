package com.aiw.bdzb.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
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
import com.aiw.bdzb.entity.Subject;
import com.aiw.bdzb.mapper.SubjectMapper;
import com.aiw.bdzb.util.BDZBConstants;


/** 
 * 说明：bdzb.subject
 * 创建人：aiw
 * 创建时间：2017-10-30
 */

@Controller
@Scope("prototype")
@RequestMapping(value="/bdzb/subject")
public class SubjectController extends BaseController<SubjectMapper, Subject>{
   
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute Subject t){
    	return  list_p(page, t);
    }
	
	
	
	@RequestMapping(value = "/insertImage")
    @ResponseBody
    public BaseJsonBean insertImage(
            @RequestParam(value="file",required=false) MultipartFile image
            ) throws Exception{
        if (null != image && !image.isEmpty()) {
            String filePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_SUBJECT_CONTENT_IMG;
            String fileName = System.currentTimeMillis()+"";
            String fileNameAndExt = FileUpload.fileUp(image, filePath, fileName); 
            Map<Object, Object> map = new HashMap<>();
            map.put("src", "/"+BDZBConstants.UPLOAD_SUBJECT_CONTENT_IMG + fileNameAndExt);
            bj.setData(map);
            return bj;
        }
        bj.setMsg("error");
        return bj;
    }
	
	
	
	@RequestMapping(value = "/save")
	@ResponseBody
    public Subject save(@ModelAttribute Subject t,@RequestParam("tempid") String tempid){
		String tempfilePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_TEMP;
    	String imagefilePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_SUBJECT_IMG;
    	
    	File imagetempfile = new File(tempfilePath + tempid + BDZBConstants.IMAGE_EXT );
    	
    	if(imagetempfile.exists()) {
    		t.setImgFlag(1);
    	}
    	Subject j = save_p(t);
    	
    	try {
			FileUtils.moveFile(imagetempfile, new File(imagefilePath+t.getId() + BDZBConstants.IMAGE_EXT));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  j;
    }
	
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(@ModelAttribute Subject t,@RequestParam("tempid") String tempid){
		String tempfilePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_TEMP;
    	String imagefilePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_SUBJECT_IMG;
    	
    	File imagetempfile = new File(tempfilePath + tempid + BDZBConstants.IMAGE_EXT );
    	try {
	    	if(imagetempfile.exists()) {
	    		t.setImgFlag(1);
	    		FileUtil.delFile(imagefilePath+t.getId() + BDZBConstants.IMAGE_EXT);
				FileUtils.moveFile(imagetempfile, new File(imagefilePath+t.getId() + BDZBConstants.IMAGE_EXT));
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return update_p(t);
	}
	
    
	@Override
	@ResponseBody
    public Subject save(@ModelAttribute Subject t){
		return  save_p(t);
    }
	
	@Override
	@ResponseBody
	public BaseJsonBean update(Subject t) {
		return update_p(t);
	}
	
	 @RequestMapping(value = "/upload/{tempid}")
	   	@ResponseBody
	public BaseJsonBean uploadTemp(
			@RequestParam(value="image",required=false) MultipartFile image,@PathVariable("tempid") String tempid
			) throws Exception{
		if (null != image && !image.isEmpty()) {
			String filePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_TEMP;
			FileUtil.delFile(filePath + tempid + FileUtil.extName(image));
			FileUpload.fileUp(image, filePath, tempid);						
			return bj;
		}
		bj.setMsg("error");
		return bj;
	}
    
	@Override
    @RequestMapping(value = "/delete/{id}")
	@ResponseBody
    public Integer delete(@PathVariable("id") Integer id){
		return delete_p(id);
    }
	
    @RequestMapping(value = "/preview/{id}")
    public ModelAndView preview(@PathVariable("id") Integer id){
		ModelAndView modelAndView = new ModelAndView(); 
		modelAndView.setViewName("/" + getModule() + "/preview");
		modelAndView.addObject("edit", mapper.get(id));
	    return modelAndView;
    }
    
    @RequestMapping(value = "/previewapp/{id}")
    public ModelAndView previewapp(@PathVariable("id") Integer id){
        ModelAndView modelAndView = new ModelAndView(); 
        modelAndView.setViewName("/" + getModule() + "/previewapp");
        modelAndView.addObject("content", mapper.get(id).getContent());
        return modelAndView;
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
    
    
	@Override
	@RequestMapping(value = "/badd")
	public ModelAndView badd() {
		ModelAndView modelAndView = get_p();
		//根据当前时间生成一个随机的临时id
		modelAndView.addObject("tempid", System.currentTimeMillis());
		
		return modelAndView;
	}

	@Override
	@RequestMapping(value = "/bedit/{id}")
	public ModelAndView bupdate(@PathVariable("id") Integer id) {
		ModelAndView modelAndView =  get_p(id);
		//根据当前时间生成一个随机的临时id
		modelAndView.addObject("tempid", System.currentTimeMillis());
		modelAndView.setViewName("/" + getModule() + "/item");
		return modelAndView;
	}
}