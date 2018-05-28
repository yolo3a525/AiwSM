package com.aiw.bdzb.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aiw.bdzb.entity.Label;
import com.aiw.bdzb.mapper.LabelMapper;
import com.aiw.controller.base.BaseController;
import com.aiw.entity.BaseJsonBean;
import com.aiw.entity.KeyValue;
import com.aiw.entity.Page;
import com.aiw.util.DDData;


/** 
 * 说明：bdzb.label
 * 创建人：aiw
 * 创建时间：2017-10-23
 */

@Controller
@Scope("prototype")
@RequestMapping(value="/bdzb/label")
public class LabelController extends BaseController<LabelMapper, Label>{
   
    @Autowired
    DDData dddata;
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list2(){
    	 ModelAndView modelAndView = new ModelAndView(); 
    	 Map<String,String> labelTypeList = DDData.dd.get("label");
    	 Map<KeyValue,List<Label>> map = new LinkedHashMap<KeyValue, List<Label>>();
    	 KeyValue keyvalue = null;
    	 Label label = new Label();
    	 for (String labelType : labelTypeList.keySet()) {
    		 label.setType(Integer.parseInt(labelType));
    		 keyvalue = new KeyValue();
    		 keyvalue.setKey(labelType);
    		 keyvalue.setValue(labelTypeList.get(labelType));
    		 map.put(keyvalue, mapper.select(label));
		 }
    	 modelAndView.addObject("map", map);
		 setJspListPath(modelAndView);
	     return modelAndView;  
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public Label save(@ModelAttribute Label t){
	    Label label = save_p(t);
	    dddata.init();
		return  label;
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(Label t) {
	    BaseJsonBean bj = update_p(t);
	    dddata.init();
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
		return get_p();
	}

	@Override
	@RequestMapping(value = "/bedit/{id}")
	public ModelAndView bupdate(@PathVariable("id") Integer id) {
		return get_p();
	}

	@Override
	public ModelAndView list(Page page, Label t) {
		// TODO Auto-generated method stub
		return null;
	}
	
}