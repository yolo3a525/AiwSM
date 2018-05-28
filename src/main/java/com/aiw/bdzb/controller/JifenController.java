package com.aiw.bdzb.controller;

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
import com.aiw.bdzb.mapper.JifenMapper;
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
@RequestMapping(value="/bdzb/jifen")
public class JifenController extends BaseController<JifenMapper, Jifen>{
	
	
	@Autowired
	private TenantMapper tenantMapper;
	
	
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
    
    @SuppressWarnings("unused")
    @RequestMapping(value = "/save")
	@ResponseBody
    public BaseJsonBean save3(@ModelAttribute Jifen t){
		
		if(t.getConsume() != null && t.getConsume() !=0 ) {//线下消费类型
			t.setJifen(t.getConsume() / 10);
		}else {//使用积分。
		    t.setJifen(-t.getJifen());
		}
		Tenant tenant = tenantMapper.get(t.getTenantId());
		
		Tenant tenant2 = new Tenant();
		tenant2.setJifen(tenant.getJifen()== null ? 0 : tenant.getJifen() +  t.getJifen());
		if(tenant2.getJifen() < 0) {
		    bj.setMsg("积分数不够");
		    return bj;
		}
		if(t.getConsume() != null && t.getConsume() !=0 ) {//线下消费类型
			tenant2.setConsumeAmount(tenant.getConsumeAmount() == null ? 0 : tenant.getConsumeAmount() + t.getConsume());
		}
		tenant2.setPhone(tenant.getPhone());
		tenant2.setId(tenant.getId());
		tenantMapper.update(tenant2);
		
		t.setAfterJifen(tenant2.getJifen());
		Jifen jifen = save_p(t);
		return  bj;
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

    @Override
    public Jifen save(Jifen t) {
        // TODO Auto-generated method stub
        return null;
    }
}