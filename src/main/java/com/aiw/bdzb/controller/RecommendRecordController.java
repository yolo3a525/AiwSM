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

import com.aiw.base.controller.BaseController;
import com.aiw.base.entity.BaseJsonBean;
import com.aiw.base.entity.Page;
import com.aiw.bdzb.entity.RecommendRecord;
import com.aiw.bdzb.entity.Tenant;
import com.aiw.bdzb.mapper.RecommendRecordMapper;
import com.aiw.bdzb.mapper.TenantMapper;


/** 
 * 说明：bdzb.recommendrecord
 * 创建人：aiw
 * 创建时间：2017-10-24
 */

@Controller
@Scope("prototype")
@RequestMapping(value="/bdzb/recommendrecord")
public class RecommendRecordController extends BaseController<RecommendRecordMapper, RecommendRecord>{
   
	@Autowired
	private TenantMapper tenantMapper;
	
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute RecommendRecord t){
    	return  list_p(page, t);
    }
    
    @RequestMapping(value = "/save")
	@ResponseBody
    public BaseJsonBean save2(@ModelAttribute RecommendRecord t){
		BaseJsonBean baseJsonBean = new BaseJsonBean();
		Tenant tenant = tenantMapper.getByPhone(t.getRecommended());
		if(tenant != null && (tenant.getReferer() == null || tenant.getReferer().trim().equals(""))) {
			Tenant refer = tenantMapper.getByPhone(t.getReferrer());
			if(refer == null) {
				baseJsonBean.setMsg("推荐人不存在");
			}else {
				save_p(t);
				
				//更新推荐人
				Tenant refer2 = new Tenant();
				refer2.setId(refer.getId());
				refer2.setJifen(refer.getJifen() + t.getJifen());
				refer2.setVipDeadLine(t.getNewVipDeadLine());
				tenantMapper.update(refer2);
				
				//更新被推荐人
				Tenant tenant2 = new Tenant();
				tenant2.setReferer(t.getReferrer());
				tenant2.setId(tenant.getId());
				tenantMapper.update(tenant2);
			}
			
		}else {
			baseJsonBean.setMsg("只能有一个推荐人.");
		}
		return baseJsonBean;
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(RecommendRecord t) {
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
	public RecommendRecord save(RecommendRecord t) {
		// TODO Auto-generated method stub
		return null;
	}
}