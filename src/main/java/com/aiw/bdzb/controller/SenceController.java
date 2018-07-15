package com.aiw.bdzb.controller;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.aiw.base.util.DDData;
import com.aiw.base.util.FileUpload;
import com.aiw.base.util.FileUtil;
import com.aiw.base.util.PathUtil;
import com.aiw.bdzb.entity.Sence;
import com.aiw.bdzb.mapper.SenceMapper;
import com.aiw.bdzb.util.BDZBConstants;


/** 
 * 说明：bdzb.scene
 * 创建人：aiw
 * 创建时间：2017-10-23
 */

@Controller
@Scope("prototype")
@RequestMapping(value="/bdzb/sence")
public class SenceController extends BaseController<SenceMapper, Sence>{
   
    
    @Autowired
    DDData dddata;
    
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute Sence t){
    	return  list_p(page, t);
    }
	
	
	
	
	
	
	
	
    @RequestMapping(value = "/graph", method = RequestMethod.GET)
    public ModelAndView list2(){
    	ModelAndView modelAndView = new ModelAndView();
    	Sence sence = new Sence();
    	sence.setIsShow(1);
		modelAndView.addObject("list", mapper.select(sence));
		return modelAndView;
    }
    
    @RequestMapping(value = "/save")
	@ResponseBody
    public Sence save(@ModelAttribute Sence t,@RequestParam("tempid") String tempid){
		String tempfilePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_TEMP;
    	String imagefilePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_SCENE_IMG;
    	
    	File imagetempfile = new File(tempfilePath + tempid + BDZBConstants.IMAGE_EXT );
    	
    	if(imagetempfile.exists()) {
    		t.setImageFlag(1);
    	}
    	
    	File imagetempTitlefile = new File(tempfilePath + tempid +"_title" + BDZBConstants.IMAGE_EXT );
    	
    	Sence j = save_p(t);
    	
    	try {
			FileUtils.moveFile(imagetempfile, new File(imagefilePath+t.getId() + BDZBConstants.IMAGE_EXT));
			FileUtils.moveFile(imagetempTitlefile, new File(imagefilePath+t.getId() +"_title" + BDZBConstants.IMAGE_EXT));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	dddata.init();
		return  j;
    }
	
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(@ModelAttribute Sence t,@RequestParam("tempid") String tempid){
		String tempfilePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_TEMP;
    	String imagefilePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_SCENE_IMG;
    	
    	File imagetempfile = new File(tempfilePath + tempid + BDZBConstants.IMAGE_EXT );
    	File imagetempTitlefile = new File(tempfilePath + tempid +"_title" + BDZBConstants.IMAGE_EXT );
    	
    	try {
	    	if(imagetempfile.exists()) {
	    		t.setImageFlag(1);
	    		FileUtil.delFile(imagefilePath+t.getId() + BDZBConstants.IMAGE_EXT);
				FileUtils.moveFile(imagetempfile, new File(imagefilePath+t.getId() + BDZBConstants.IMAGE_EXT));
	    	}
	    	if(imagetempTitlefile.exists()) {
	    		t.setImageFlag(1);
	    		FileUtil.delFile(imagefilePath+t.getId() +"_title" + BDZBConstants.IMAGE_EXT);
				FileUtils.moveFile(imagetempTitlefile, new File(imagefilePath+t.getId() +"_title" + BDZBConstants.IMAGE_EXT));
	    	}
	    	
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	  BaseJsonBean bj = update_p(t);
          dddata.init();
          return bj;
	}
    
    @RequestMapping(value = "/paixu")
    @ResponseBody
    public BaseJsonBean paixu(@RequestParam("idAndIndexs") String idAndIndexs){
       
        String [] idAndIndexArray = idAndIndexs.split("#");
        Sence sence = new Sence();
        for (String string : idAndIndexArray) {
            String[] idIndexArry = string.split("&");
            sence.setId(Integer.parseInt(idIndexArry[0]));
            sence.setOrder(Integer.parseInt(idIndexArry[1]));
            mapper.update(sence);
            
        }
        
        return bj;
        
    }
    
	@Override
    @RequestMapping(value = "/delete/{id}")
	@ResponseBody
    public Integer delete(@PathVariable("id") Integer id){
	    Integer i = delete_p(id);
        dddata.init();
        return i ;
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
	
	 @RequestMapping(value = "/upload/{tempid}")
	   	@ResponseBody
   	public BaseJsonBean uploadTemp(
   			@RequestParam(value="image",required=false) MultipartFile image,
   			@RequestParam(value="imageTitle",required=false) MultipartFile imageTitle,
   			@PathVariable("tempid") String tempid
   			) throws Exception{
   		if (null != image && !image.isEmpty()) {
   			String filePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_TEMP;
   			FileUtil.delFile(filePath + tempid + FileUtil.extName(image));
   			FileUpload.fileUp(image, filePath, tempid);						
   			return bj;
   		}
   		
   		if (null != imageTitle && !imageTitle.isEmpty()) {
   			String filePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_TEMP;
   			FileUtil.delFile(filePath + tempid + "_title" + FileUtil.extName(imageTitle));
   			FileUpload.fileUp(imageTitle, filePath, tempid+"_title");						
   			return bj;
   		}
   		
   		bj.setMsg("error");
   		return bj;
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

	@Override
	public Sence save(Sence t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public BaseJsonBean update(Sence t) {
		// TODO Auto-generated method stub
		return null;
	}
}