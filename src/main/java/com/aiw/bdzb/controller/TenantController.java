package com.aiw.bdzb.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aiw.bdzb.entity.RecommendRecord;
import com.aiw.bdzb.entity.Tenant;
import com.aiw.bdzb.mapper.RecommendRecordMapper;
import com.aiw.bdzb.mapper.TenantMapper;
import com.aiw.bdzb.util.BDZBConstants;
import com.aiw.controller.base.BaseController;
import com.aiw.entity.BaseJsonBean;
import com.aiw.entity.Chart;
import com.aiw.entity.Page;
import com.aiw.util.DDData;
import com.aiw.util.FileDownload;
import com.aiw.util.ObjectExcelRead;
import com.aiw.util.PathUtil;


/** 
 * 说明：bdzb.tenant
 * 创建人：aiw
 * 创建时间：2017-05-26
 */

@Controller
@Scope("prototype")
@RequestMapping(value="/bdzb/tenant")
public class TenantController extends BaseController<TenantMapper, Tenant>{
	
	
	 @InitBinder
	 public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat2.setLenient(false);
		
		binder.registerCustomEditor(java.sql.Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
		binder.registerCustomEditor(java.sql.Timestamp.class, new CustomDateEditor(dateFormat2, true));   //true:允许输入空值，false:不能为空值
	}
	
   
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute Tenant t){
    	return  list_p(page, t);
    }
	
	
    @RequestMapping(value = "/export", method = RequestMethod.POST)
	@ResponseBody
    public void export(@ModelAttribute Tenant t){
	    
         list = mapper.selectExport(t);
         
         List<String> list2 = new ArrayList<>();
         
         for (Tenant tenant : list) {
             list2.add(tenant.getPhone());
        }
         String filename = System.currentTimeMillis() + ".xlsx";
         ObjectExcelRead.wirteExcel(PathUtil.getClasspath() + BDZBConstants.UPLOAD_TEMP, filename, list2);
         try {
            FileDownload.fileDownload(response, PathUtil.getClasspath() + BDZBConstants.UPLOAD_TEMP + filename, filename);
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public Tenant save(@ModelAttribute Tenant t){
		t.setAccesskey(UUID.randomUUID().toString());
		return  save_p(t);
	}
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(Tenant t) {
		
		if(t.getConsumeAmount() != null && t.getConsumeAmount() != 0) {
			t.setJifen(t.getConsumeAmount() / 100);
		}
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
	
    @RequestMapping(value = "/addreferrer/{referrer}/{recommended}")
    public ModelAndView addreferrer(@PathVariable("referrer") String referrer, @PathVariable("recommended") String recommended){
        ModelAndView modelAndView = new ModelAndView(); 
        Tenant tenant =  mapper.getByPhone(referrer);
        
        modelAndView.addObject("tenant", tenant);
	    modelAndView.addObject("recommended", recommended);
	    
	    modelAndView.setViewName("/" + getModule() + "/addReferrer");
	    
        return modelAndView;
    }
        
	@Override
    @RequestMapping(value = "")
    public ModelAndView get(){
    	return get_p();
    }
	
	@Override
	@RequestMapping(value = "/badd")
	public ModelAndView badd() {
		return get_p();
	}

	@Autowired
	RecommendRecordMapper rrm;
	
	@Override
	@RequestMapping(value = "/bedit/{id}")
	public ModelAndView bupdate(@PathVariable("id") Integer id) {
		
		ModelAndView m = get_p(id);
		RecommendRecord rr = new RecommendRecord();
		rr.setReferrer(((Tenant)m.getModelMap().get("edit")).getPhone());
		m.addObject("recommendList", rrm.select(rr));
		
		return m;
	}
	
    @RequestMapping(value = "/chart1")
	@ResponseBody
    public Chart chart1(@ModelAttribute Tenant t){
    	
    	LinkedHashMap<String,String> all = new LinkedHashMap<String, String>();
    	all.put("0","非会员");
    	all.put("2","临时会员");
    	all.putAll(DDData.dd.get("xiaofeika"));
    	all.putAll(DDData.dd.get("huijika"));
    	
    	List<Map> list =  mapper.selectChart1(t);
    	
    	Chart chart = new Chart();
    	List<Object> categories = new ArrayList<Object>();
    	List<Object> data = new ArrayList<Object>();
    	
    	for (Map<?, ?> object : list) {
    		categories.add(all.get(object.get("vip")+""));
    		data.add(object.get("vipcount"));
		}
    	
    	chart.setCategories(categories);
    	chart.setData(data);
    	return chart;
    }
	
}