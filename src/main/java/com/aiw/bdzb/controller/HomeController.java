package com.aiw.bdzb.controller;

import java.sql.Date;
import java.util.HashMap;
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

import com.aiw.bdzb.entity.Jifen;
import com.aiw.bdzb.entity.Tenant;
import com.aiw.bdzb.mapper.JewelryMapper;
import com.aiw.bdzb.mapper.JifenMapper;
import com.aiw.bdzb.mapper.OrderMapper;
import com.aiw.bdzb.mapper.TenantMapper;
import com.aiw.controller.base.BaseController;
import com.aiw.entity.BaseJsonBean;
import com.aiw.entity.Page;


/** 
 * 说明：bdzb.jifen
 * 创建人：aiw
 * 创建时间：2017-10-24
 */

@Controller
@Scope("prototype")
@RequestMapping(value="/bdzb/home")
public class HomeController extends BaseController<JifenMapper, Jifen>{
	
	
	@Autowired
	private TenantMapper tenantMapper;
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private JewelryMapper jewelryMapper;
	
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView home(){
    	ModelAndView modelAndeView = new ModelAndView();
    	//查询今天注册了多少
    	Tenant t = new Tenant();
    	t.setStartRegisterDate(new Date(System.currentTimeMillis()));
    	t.setEndRegisterDate(new Date(System.currentTimeMillis()));
    	
    	Map<String, Comparable> map = new HashMap<String, Comparable>();
    	map.put("startDate", new Date(System.currentTimeMillis()));
        map.put("endDate", new Date(System.currentTimeMillis()));
        Map<String, Comparable> map2 = new HashMap<String, Comparable>();
    	int todayRegister = tenantMapper.selectCount(map);    	
    	int totalRegister = tenantMapper.selectCount(map2);
    	
    	int todayyuyueOrder = orderMapper.selectCount(map);
    	int totalyuyueOrder = orderMapper.selectCount(map2);
    	
    	Map<String, Comparable> map3 = new HashMap<String, Comparable>();
    	map3.put("jiedai", 1);
    	map3.putAll(map);
    	Map<String, Comparable> map4 = new HashMap<String, Comparable>();
    	map4.put("jiedai", 1);
    	int todayjiedaiOrder = orderMapper.selectCount(map3);
    	int totaljiedaiOrder = orderMapper.selectCount(map4);
    	
    	modelAndeView.addObject("todayRegister", todayRegister);
    	modelAndeView.addObject("totalRegister", totalRegister);
    	modelAndeView.addObject("todayyuyueOrder", todayyuyueOrder);
    	modelAndeView.addObject("totalyuyueOrder", totalyuyueOrder);
    	modelAndeView.addObject("todayjiedaiOrder", todayjiedaiOrder);
    	modelAndeView.addObject("totaljiedaiOrder", totaljiedaiOrder);
    	
    	Map<Object, Object> mapc = new HashMap<>();
    	if(isGys()) {
    	    mapc.put("gysid", getId());
    	    modelAndeView.setViewName("/bdzb/gyshome");
    	}else {
    	    modelAndeView.setViewName("/bdzb/home");
    	}
    	
    	//总数
    	modelAndeView.addObject("jewelryselectCount", jewelryMapper.selectCount(mapc));
    	
    	
    	//供应商数量
    	modelAndeView.addObject("jewelryselectCountGys", jewelryMapper.selectCountGys());
    	
    	//下架中
    	modelAndeView.addObject("jewelryselectCountShowStatus0", jewelryMapper.jewelryselectCountShowStatus0(mapc));
    	//可借戴
    	modelAndeView.addObject("jewelryselectCountStatusF1", jewelryMapper.jewelryselectCountStatusF1(mapc));
    	//预约中
    	modelAndeView.addObject("jewelryselectCountStatus20", jewelryMapper.selectCountStatus20(mapc));
    	//借戴中
    	modelAndeView.addObject("jewelryselectCountStatus21", jewelryMapper.selectCountStatus21(mapc));
    	
    	
    	return modelAndeView;
    	
    }
	
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute Jifen t){
    	return  list_p(page, t);
    }
	
    @RequestMapping(value = "/list2", method = RequestMethod.GET)
    public ModelAndView list2(@ModelAttribute Jifen t){
		 list = mapper.select(t);
		 ModelAndView modelAndView = new ModelAndView(); 
		 modelAndView.addObject("list", list);
		 modelAndView.addObject("module",t.getClass().getSimpleName().toLowerCase());//动态告诉页面什么模块
		 modelAndView.setViewName("/" + getModule() + "/list2");
	     return modelAndView;  
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public Jifen save(@ModelAttribute Jifen t){
		
		if(t.getConsume() != null && t.getConsume() !=0 ) {//线下消费类型
			t.setJifen(t.getConsume() / 10);
		}
		Tenant tenant = tenantMapper.get(t.getTenantId());
		
		Tenant tenant2 = new Tenant();
		tenant2.setJifen(tenant.getJifen() + t.getJifen());
		if(t.getConsume() != null && t.getConsume() !=0 ) {//线下消费类型
			tenant2.setConsumeAmount(tenant.getConsumeAmount() + t.getConsume());
		}
		tenant2.setPhone(tenant.getPhone());
		tenant2.setId(tenant.getId());
		tenantMapper.update(tenant2);
		
		t.setAfterJifen(tenant2.getJifen());
		return  save_p(t);
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(Jifen t) {
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