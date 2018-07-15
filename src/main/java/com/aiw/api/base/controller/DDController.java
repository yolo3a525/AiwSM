package com.aiw.api.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aiw.entity.DD;
import com.aiw.entity.Page;
import com.aiw.entity.SysResult;
import com.aiw.mapper.DDMapper;
import com.aiw.util.DDData;

@RestController(value="ApiDDController")
@RequestMapping(value="/api/dd")
public class DDController extends BaseController<DDMapper, DD>{
	
	@Autowired
	DDData dddata;

	
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public SysResult list(@ModelAttribute Page page,@ModelAttribute DD t){
    	return  list_p(page, t);
    }
	
	/**
	 * 刷新数据字典
	 */
	private void refresh() {
		dddata.init();
	}
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public SysResult save(@RequestBody DD t){
	    SysResult d = save_p(t);
		refresh();
		return  d;
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public SysResult update(@RequestBody DD t) {
	    SysResult bj = update_p(t);
		refresh();
		return bj;
	}
    
    @RequestMapping(value = "/delete/{dgCode}/{ddItem}")
	@ResponseBody
    public SysResult delete(@PathVariable("dgCode") String dgCode,@PathVariable("ddItem") String ddItem){
    	int i = mapper.delete(dgCode,ddItem);
		refresh();
		return SysResult.oK();
    }
    
	@Override
    @RequestMapping(value = "/{id}")
    public SysResult get(@PathVariable("id") Integer id){
		return get_p(id);
    }
        
	@Override
    @RequestMapping(value = "")
    public SysResult get(){
    	return get_p();
    }
	
	@RequestMapping(value = "/badd/{groupCode}")
	public SysResult badd(@PathVariable("groupCode") String groupCode) {
		SysResult SysResult = new SysResult(); 
		DD dd = new DD();
		dd.setDgCode(groupCode);
		return SysResult; 
	}

	@RequestMapping(value = "/bedit/{code}")
	public SysResult bupdate(@PathVariable("code") String code) {
    	SysResult SysResult = new SysResult(); 
	    return SysResult; 
	}


	@Override
	public SysResult bupdate(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SysResult delete(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SysResult badd() {
		// TODO Auto-generated method stub
		return null;
	}
}
	
