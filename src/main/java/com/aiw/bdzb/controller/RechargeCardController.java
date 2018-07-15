package com.aiw.bdzb.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aiw.base.controller.BaseController;
import com.aiw.base.entity.BaseJsonBean;
import com.aiw.base.entity.Page;
import com.aiw.bdzb.entity.RechargeCard;
import com.aiw.bdzb.entity.Tenant;
import com.aiw.bdzb.mapper.RechargeCardMapper;
import com.aiw.bdzb.mapper.TenantMapper;


/** 
 * 说明：bdzb.rechargecard
 * 创建人：aiw
 * 创建时间：2017-10-30
 */

@Controller
@Scope("prototype")
@RequestMapping(value="/bdzb/rechargecard")
public class RechargeCardController extends BaseController<RechargeCardMapper, RechargeCard>{
   
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute RechargeCard t){
    	return  list_p(page, t);
    }
    
	@Override
	@RequestMapping(value = "/batchCreate")
	@ResponseBody
    public RechargeCard save(@ModelAttribute RechargeCard t){
		RechargeCard tt = null;
		Random rm = new Random(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String no = sdf.format(new Date(System.currentTimeMillis()));
		List<RechargeCard> list = new ArrayList<RechargeCard>();
		for(int i = 0; i < t.getCount();i++) {
			tt = new RechargeCard();
			tt.setDays(t.getDays());
			tt.setNo("BD"+no+rm.nextInt(1000));
			tt.setExpires(t.getExpires());
			tt.setType(t.getType());
			tt.setLastUpdateUser(getUser().getUsername());
			tt.setCreateUser(getUser().getUsername());
			list.add(tt);
		}
    	mapper.batchCreate(list);
    	
    	return t;
    }
	
	
	@Autowired
	TenantMapper tenantMapper;
	
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/use")
	@ResponseBody
    public BaseJsonBean use(@ModelAttribute RechargeCard t){
		
    	RechargeCard dbT = mapper.get(t.getId());
    	if(dbT == null ||
    	   dbT.getStatus() != 0 ||
    	   dbT.getExpires().getTime() < new java.sql.Date(System.currentTimeMillis()).getTime())
    	{
    		bj.setMsg("充值失败，过期或已经充值过。");
    		return bj;
    	}
    	
    	Tenant tenant  = tenantMapper.getByPhone(t.getPhone());
    	t.setTenantid(t.getId());
    	t.setStatus(1);
    	mapper.update(t);
    	
    	Map<String, Comparable> map = new HashMap<String, Comparable>();
    	map.put("id", tenant.getId());
    	map.put("days", dbT.getDays());
    	map.put("vip", dbT.getType());
    	if(tenant.getVipDeadLine() != null) {
    		map.put("vipDeadLine", "1");
    	}
    	tenantMapper.updateRecharge(map);
    	
    	return bj;
    }
	
	@ResponseBody
    public RechargeCard batchCreate(@ModelAttribute RechargeCard t){
		return  save_p(t);
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(RechargeCard t) {
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