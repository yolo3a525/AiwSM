package com.aiw.bdzb.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.aiw.bdzb.entity.Jewelry;
import com.aiw.bdzb.mapper.JewelryMapper;
import com.aiw.bdzb.mapper.LabelMapper;
import com.aiw.bdzb.mapper.SenceMapper;
import com.aiw.bdzb.util.BDZBConstants;
import com.aiw.controller.base.BaseController;
import com.aiw.entity.BaseJsonBean;
import com.aiw.entity.Page;
import com.aiw.util.DDData;
import com.aiw.util.FileUpload;
import com.aiw.util.FileUtil;
import com.aiw.util.ObjectExcelRead;
import com.aiw.util.PathUtil;


/** 
 * 说明：bdzb.jewelry
 * 创建人：aiw
 * 创建时间：2017-05-25
 */

@Controller
@Scope("prototype")
@RequestMapping(value= {"/bdzb/jewelry","/bdzb/gysjewelry","/bdzb/share"})
public class JewelryController extends BaseController<JewelryMapper, Jewelry>{
   
    
    
	
    
    
	@Override
    @RequestMapping(value = {"/list","/listxiajia"}, method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute Jewelry t){
	    
	    //如果是供应商，那么查询的时候一定有这个条件。
	    if(isGys()) {
	        t.setGysid(getId());
	    }
	    
	  //设置pageSize
        if(request.getSession().getAttribute("pageSize") != null) {
            page.setPageSize((Integer)(request.getSession().getAttribute("pageSize")));
        }
         Map rm = new HashMap<>();
         list = mapper.selectPage(page,t,rm);
         ModelAndView modelAndView = new ModelAndView(); 
         modelAndView.addObject("list", list);//查询结果
         modelAndView.addObject("query", t);//保留查询条件
         modelAndView.addObject("module",t.getClass().getSimpleName().toLowerCase());//动态告诉页面什么模块
         setJspListPath(modelAndView);
         
		initLabel(modelAndView);
//		Map<String,String> jewelryMap = DDData.dd.get("珠宝");
//		for (Jewelry jewelry : list) {
//			jewelry.setType(jewelryMap.get(jewelry.getTypeid()+""));
//		}
    	return  modelAndView;
    }
    
    @RequestMapping(value = "/save")
	@ResponseBody
    public Jewelry save(@ModelAttribute Jewelry t,@RequestParam("tempid") String tempid){
    	
    	String tempfilePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_TEMP;
    	String imagefilePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_IMG;
    	String moviefilePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_MOIVE;
    	
    	File imagetempfile = new File(tempfilePath + tempid + BDZBConstants.IMAGE_EXT );
    	File movietempfile = new File(tempfilePath + tempid + BDZBConstants.MOVIE_EXT );
    	
    	if(imagetempfile.exists()) {
    		t.setImageflag(1);
    	}
    	if(movietempfile.exists()) {
    		t.setMovieflag(1);
    	}
    	//t.setStatus(1);
    	//如果是供应商，那么查询的时候一定有这个条件。
        if(isGys()) {
            t.setGysid(getId());
        }
    	Jewelry j = save_p(t);
    	
    	try {
			FileUtils.moveFile(imagetempfile, new File(imagefilePath+t.getSid() + BDZBConstants.IMAGE_EXT));
			FileUtils.moveFile(movietempfile, new File(moviefilePath+t.getSid() + BDZBConstants.MOVIE_EXT));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  j;
    }
    
    @RequestMapping(value = "/batchup")
    @ResponseBody
    public BaseJsonBean batchup(@RequestParam(name="jewelryids",required=true) String jewelryids
            ,@RequestParam(name="showflag",required=true) Integer showflag){
        
        Map map = new HashMap();
        map.put("jewelryids", jewelryids);
        map.put("showflag", showflag);
        mapper.batchup(map);
        return  bj;
    }
    
	
    @RequestMapping(value = "/update")
	@ResponseBody
    public BaseJsonBean update2(@ModelAttribute Jewelry t,@RequestParam(name="tempid",required=false) String tempid){
		
		String tempfilePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_TEMP;
    	String imagefilePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_IMG;
    	String moviefilePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_MOIVE;
    	
    	File imagetempfile = new File(tempfilePath + tempid + BDZBConstants.IMAGE_EXT );
    	File movietempfile = new File(tempfilePath + tempid + BDZBConstants.MOVIE_EXT );
    	
    	try {

        	if(imagetempfile.exists()) {
        		t.setImageflag(1);
        		//删除之前存在的
        		FileUtil.delFile(imagefilePath+t.getSid() + BDZBConstants.IMAGE_EXT);
        		FileUtils.moveFile(imagetempfile, new File(imagefilePath+t.getSid() + BDZBConstants.IMAGE_EXT));
        	}
        	if(movietempfile.exists()) {
        		t.setMovieflag(1);
        		FileUtil.delFile(moviefilePath+t.getSid() + BDZBConstants.MOVIE_EXT);
        		FileUtils.moveFile(movietempfile, new File(moviefilePath+t.getSid() + BDZBConstants.MOVIE_EXT));
        	}
        	bj = update_p(t);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bj;
	}
	
    @RequestMapping(value = "/hot")
	@ResponseBody
	public BaseJsonBean hot(Jewelry t) {
		return update_p(t);
	}
    
    @RequestMapping(value = "/upload/{tempid}")
   	@ResponseBody
   	public BaseJsonBean uploadTemp(
   			@RequestParam(value="movie",required=false) MultipartFile movie,
   			@RequestParam(value="image",required=false) MultipartFile image,@PathVariable("tempid") String tempid
   			) throws Exception{
   		if (null != movie && !movie.isEmpty()) {
   			String filePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_TEMP;
   			FileUtil.delFile(filePath + tempid + FileUtil.extName(movie));
   			FileUpload.fileUp(movie, filePath, tempid);						
   			return bj;
   		}
   		if (null != image && !image.isEmpty()) {
   			String filePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_TEMP;
   			FileUtil.delFile(filePath + tempid + FileUtil.extName(image));
   			FileUpload.fileUp(image, filePath, tempid);						
   			return bj;
   		}
   		bj.setMsg("error");
   		return bj;
   	}
    
	
    
    
    
	/**
	 * 
	 * @param file 导入的数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/import")
	@ResponseBody
	public BaseJsonBean uploadFileAndSendMail(
			@RequestParam(value="excel",required=false) MultipartFile file
			) throws Exception{
		int fail = 0;
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_TEMP;								//文件上传路径
			//先删除原始的文件
			FileUtil.delFile(filePath + file.getName()); 
			String newFileName =  FileUpload.fileUp(file, filePath, file.getName());							//执行上传
			List<Map> listPd = (List)ObjectExcelRead.readExcel(filePath, newFileName, 1, 0, 0);
			
			try {
				List<Jewelry> list = new ArrayList<Jewelry>();
				Jewelry jweelry = null;
				String sql = null;
				
				Integer id = null;
				String sid = null;
				String name = null;
				String price = null;
				String pinlei = null;
				String fengge = null;
				String faxing = null;
				String fuse = null;
				String lianxing = null;
				String changjing = null;
				String shejishishuo = null;
				String hotflag = null;
				String showflag = null;
				for (Map map : listPd) {
					jweelry = new Jewelry();
					sid = (String) map.get("var0");
					if(sid == null || sid.trim().equals("")) {
						continue;
					}
					name = (String)  map.get("var1");
					price = (String) map.get("var2");
					pinlei = (String) map.get("var3");
					fengge = (String) map.get("var4");
					faxing = (String) map.get("var5");
					fuse = (String) map.get("var6");
					lianxing = (String) map.get("var7");
					changjing = (String) map.get("var8");
					shejishishuo = (String) map.get("var9");
					
					hotflag = (String) map.get("var10");
					showflag = (String) map.get("var11");
					
					jweelry.setSid(sid);
					jweelry.setName(name);
					jweelry.setPrice((int)(Double.parseDouble(price)));
					
					String pinleiNumber = DDData.ddLabelValueToKey.get("pinlei").get(pinlei);
					jweelry.setLabel_pinlei(pinleiNumber == null ? 0 :
					        Integer.parseInt(pinleiNumber));
					
					String fenggeNumber = DDData.ddLabelValueToKey.get("fengge").get(fengge);
                    jweelry.setLabel_fengge(fenggeNumber == null ? 0 :
                            Integer.parseInt(fenggeNumber));
					
					String fuseNumber = DDData.ddLabelValueToKey.get("fuse").get(fuse);
                    jweelry.setLabel_fuse(fuseNumber == null ? 0 :
                            Integer.parseInt(fuseNumber));
                    String lianxingNumber = DDData.ddLabelValueToKey.get("lianxing").get(lianxing);
                    jweelry.setLabel_lianxing(lianxingNumber == null ? 0 :
                            Integer.parseInt(lianxingNumber));
                    String changjingNumber = DDData.ddLabelValueToKey.get("changjing").get(changjing);
                    jweelry.setLabel_changjing(changjingNumber == null ? 0 :
                            Integer.parseInt(changjingNumber));
					
					
					jweelry.setDesignerWords(shejishishuo);
					jweelry.setHotflag(hotflag.trim().equals("是") ? 1 : 0);
					jweelry.setShowflag(showflag.trim().equals("是") ? 1 : 0);
					//如果是供应商，那么查询的时候一定有这个条件。
			        if(isGys()) {
			            jweelry.setGysid(getId());
			        }
					//jweelry.setStatus(1);
					jweelry.setCreateUser(getUser().getUsername());
					jweelry.setLastUpdateUser(getUser().getUsername());
					list.add(jweelry);
				}
				mapper.batchSave(list);
				logger.info("成功导入" + list.size() + "条珠宝基本数据.");
				bj.setMsg("成功导入" + list.size() + "条珠宝基本数据.");
			}catch(Exception e) {
				logger.error(e,e);
				bj.setMsg("数据格式错误...请严格按照模板数据来处理");
			}
		}
		return bj;
	}
	
	
    //@RequestMapping(value = "/upload/{id}")
	@ResponseBody
	public BaseJsonBean upload(
			@RequestParam(value="movie",required=false) MultipartFile movie,
			@RequestParam(value="image",required=false) MultipartFile image,@PathVariable("id") Integer id
			) throws Exception{

		if (null != movie && !movie.isEmpty()) {
			//String uuid = UUID.randomUUID().toString();
			String filePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_MOIVE;
			FileUpload.fileUp(movie, filePath, id+"");						
			Jewelry j = new Jewelry();
			j.setMovieflag(1);
			j.setId(id);
			update_p(j);
			return bj;
		}
		if (null != image && !image.isEmpty()) {
			//String uuid = UUID.randomUUID().toString();
			String filePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_IMG;
			FileUpload.fileUp(image, filePath, id+"");						
			Jewelry j = new Jewelry();
			j.setImageflag(1);
			j.setId(id);
			update_p(j);
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
    
    @RequestMapping(value = "/{id}")
    public ModelAndView shareDetail(@PathVariable("id") Integer id){
        return get_p(id);
    }
	
	@Override
    @RequestMapping(value = "/jewelrydetail/{id}")
    public ModelAndView get(@PathVariable("id") Integer id){
        
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id", id);
        List<Jewelry> list = mapper.select(map);
        ModelAndView modelAndView = new ModelAndView(); 
        if(list != null && list.size() > 0) {
            modelAndView.setViewName("/" + getModule() + "/share");
            
            
            Jewelry dbj = list.get(0);
            
            if(dbj.getLabel_faxing() != null && dbj.getLabel_faxing() != 0) {
                dbj.setLabel_faxing_name(DDData.ddLabel.get("faxing").get(dbj.getLabel_faxing()+""));
            }
            if(dbj.getLabel_fuse() != null && dbj.getLabel_fuse() != 0) {
                dbj.setLabel_fuse_name(DDData.ddLabel.get("fuse").get(dbj.getLabel_fuse()+""));
            }
            if(dbj.getLabel_lianxing() != null && dbj.getLabel_lianxing() != 0) {
                dbj.setLabel_lianxing_name(DDData.ddLabel.get("lianxing").get(dbj.getLabel_lianxing()+""));
            }
            if(dbj.getLabel_changjing() != null && dbj.getLabel_changjing() != 0) {
                dbj.setLabel_changjing_name(DDData.ddLabel.get("changjing").get(dbj.getLabel_changjing()+""));
            }
            if(dbj.getLabel_fengge() != null && dbj.getLabel_fengge() != 0) {
                dbj.setLabel_fengge_name(DDData.ddLabel.get("fengge").get(dbj.getLabel_fengge()+""));
            }
            if(dbj.getLabel_pinlei() != null && dbj.getLabel_pinlei() != 0) {
                dbj.setLabel_pinlei_name(DDData.ddLabel.get("pinlei").get(dbj.getLabel_pinlei()+""));
            }
            
            modelAndView.addObject("edit", dbj);
            
        }else {
            modelAndView.setViewName("/error");
        }
        return modelAndView; 
    }
        
	@Override
    @RequestMapping(value = "")
    public ModelAndView get(){
    	return get_p();
    }

	@Autowired
	LabelMapper labelMapper;
	
	@Autowired
	SenceMapper sceneMapper;
	
	@Override
	@RequestMapping(value = "/badd")
	public ModelAndView badd() {
		
		ModelAndView modelAndView = get_p();
		//根据当前时间生成一个随机的临时id
		modelAndView.addObject("tempid", System.currentTimeMillis());
		
		initLabel(modelAndView);
		 
		return modelAndView;
	}

	@Override
	@RequestMapping(value = "/bedit/{id}")
	public ModelAndView bupdate(@PathVariable("id") Integer id) {

		ModelAndView modelAndView =  get_p(id);
		//根据当前时间生成一个随机的临时id
		modelAndView.addObject("tempid", System.currentTimeMillis());
		
		initLabel(modelAndView);
		
		modelAndView.setViewName("/" + getModule() + "/edit");
		return modelAndView;
	}
	
	private void initLabel(ModelAndView modelAndView) {
		
		 modelAndView.addObject("pinlei", DDData.ddLabel.get("pinlei"));
		 modelAndView.addObject("fengge", DDData.ddLabel.get("fengge"));
		 modelAndView.addObject("faxing", DDData.ddLabel.get("faxing"));
		 modelAndView.addObject("fuse", DDData.ddLabel.get("fuse"));
		 modelAndView.addObject("lianxing", DDData.ddLabel.get("lianxing"));
		 modelAndView.addObject("changjing",DDData.ddLabel.get("changjing"));
		 
		 
	}

	
	
	//被废弃.有新的方法替代.
	@Override
	public Jewelry save(Jewelry t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseJsonBean update(Jewelry t) {
		// TODO Auto-generated method stub
		return null;
	}

}