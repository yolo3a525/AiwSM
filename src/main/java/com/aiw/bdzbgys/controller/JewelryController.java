package com.aiw.bdzbgys.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aiw.base.entity.Page;
import com.aiw.bdzb.entity.Jewelry;


/** 
 * 说明：bdzb.jewelry
 * 创建人：aiw
 * 创建时间：2017-05-25
 */

@Controller("gys")
@Scope("prototype")
@RequestMapping(value="/bdzbgys/jewelry")
public class JewelryController extends com.aiw.bdzb.controller.JewelryController{
   
	
	
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute Jewelry t){
    	t.setGysid(getId());
    	return super.list(page, t);
    }
    
    @RequestMapping(value = "/save")
	@ResponseBody
    public Jewelry save(@ModelAttribute Jewelry t,@RequestParam("tempid") String tempid){
    	t.setGysid(getId());
    	return super.save(t, tempid);
    }
//	
//    @RequestMapping(value = "/update")
//	@ResponseBody
//    public BaseJsonBean update2GYS(@ModelAttribute Jewelry t,@RequestParam(name="tempid",required=false) String tempid){
//		
//		return update2(t, tempid);
//	}
//	
//    @RequestMapping(value = "/upload/{tempid}")
//   	@ResponseBody
//   	public BaseJsonBean uploadTempGYS(
//   			@RequestParam(value="movie",required=false) MultipartFile movie,
//   			@RequestParam(value="image",required=false) MultipartFile image,@PathVariable("tempid") String tempid
//   			) throws Exception{
//   		return uploadTemp(movie, image, tempid);
//   	}
//    
//	
//    
//    
//    
//	/**
//	 * 
//	 * @param file 导入的数据
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/import")
//	@ResponseBody
//	public BaseJsonBean uploadFileAndSendMailGYS(
//			@RequestParam(value="excel",required=false) MultipartFile file
//			) throws Exception{
//		return uploadFileAndSendMail(file);
//	}
//	
//	
//    
//    @RequestMapping(value = "/delete/{id}")
//	@ResponseBody
//    public Integer deleteGYS(@PathVariable("id") Integer id){
//		return delete(id);
//    }
//    
//    @RequestMapping(value = "/{id}")
//    public ModelAndView getGYS(@PathVariable("id") Integer id){
//		return get(id);
//    }
//        
//	@Override
//    @RequestMapping(value = "")
//    public ModelAndView get(){
//    	return get();
//    }
//
//	
//	@RequestMapping(value = "/badd")
//	public ModelAndView baddGYS() {
//		return badd();
//	}
//
//	@RequestMapping(value = "/bedit/{id}")
//	public ModelAndView bupdateGYS(@PathVariable("id") Integer id) {
//
//		return bupdate(id);
//	}
//
//	
//	


}