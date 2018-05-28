package com.aiw.bdzb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aiw.bdzb.entity.Jewelry;
import com.aiw.bdzb.mapper.JewelryMapper;
import com.aiw.controller.base.BaseController;
import com.aiw.entity.BaseJsonBean;
import com.aiw.entity.Page;


/** 
 * 说明：bdzb.jewelry
 * 创建人：aiw
 * 创建时间：2017-05-25
 */

@Controller
@Scope("prototype")
@RequestMapping(value="/bdzb/rentcart")
public class RentCartController extends BaseController<JewelryMapper, Jewelry>{
   
	
	@Autowired
	JewelryMapper jewelryMapper;
	
	@SuppressWarnings("unchecked")
    @Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute Jewelry t){
		 List<Integer> list = (List<Integer>)request.getSession().getAttribute("cart");
		 List<Jewelry> jewelryList = null;
		 if(list != null && list.size() > 0) {
			 jewelryList = jewelryMapper.selectCart(list);
		 }
		 
		 ModelAndView modelAndView = new ModelAndView(); 
		 modelAndView.addObject("list", jewelryList);//查询结果
		 modelAndView.addObject("query", t);//保留查询条件
		 modelAndView.addObject("module",t.getClass().getSimpleName().toLowerCase());//动态告诉页面什么模块
		 setJspListPath(modelAndView);
	     return modelAndView;  
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public Jewelry save(@ModelAttribute Jewelry t){
		return  save_p(t);
    }
	
	
	@SuppressWarnings("unchecked")
    @RequestMapping(value = "/add/{id}")
	@ResponseBody
    public List<Integer> addCart(@PathVariable("id") Integer id){
		List<Integer> list = (List<Integer>)request.getSession().getAttribute("cart");
		if(list == null) {
			list = new ArrayList<Integer>();
			request.getSession().setAttribute("cart", list);
		}
		list.add(id);
		return  list;
    }
	
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(Jewelry t) {
		return update_p(t);
	}
	
	
    
    
	@SuppressWarnings("unchecked")
    @Override
    @RequestMapping(value = "/delete/{id}")
	@ResponseBody
    public Integer delete(@PathVariable("id") Integer id){
		List<Integer> list = (List<Integer>)request.getSession().getAttribute("cart");
		list.remove(id);
		return 1;
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
		return get_p(id);
	}

}