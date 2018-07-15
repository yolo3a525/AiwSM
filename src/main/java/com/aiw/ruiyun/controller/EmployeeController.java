package com.aiw.ruiyun.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.aiw.base.controller.BaseController;
import com.aiw.base.entity.BaseJsonBean;
import com.aiw.base.entity.Page;
import com.aiw.base.util.Const;
import com.aiw.base.util.FileDownload;
import com.aiw.base.util.FileUpload;
import com.aiw.base.util.ObjectExcelRead;
import com.aiw.base.util.PageData;
import com.aiw.base.util.PathUtil;
import com.aiw.ruiyun.entity.Employee;
import com.aiw.ruiyun.mapper.EmployeeMapper;



/** 
 * 说明：ruiyun.employee
 * 创建人：aiw
 * 创建时间：2017-05-19
 */

@Controller
@RequestMapping(value="/employee")
public class EmployeeController extends BaseController<EmployeeMapper, Employee>{
   
	
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
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute Employee t){
    	return  list_p(page, t);
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public Employee save(@ModelAttribute Employee t){
		return  save_p(t);
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(Employee t) {
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
	
	
	
	
	
	
	/**下载模版
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		FileDownload.fileDownload(response, PathUtil.getClasspath() + "Users.xls", "Users.xls");
	}
	
	/**从EXCEL导入到数据库
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/readExcel")
	@ResponseBody
	public BaseJsonBean readExcel(
			@RequestParam(value="excel",required=false) MultipartFile file
			) throws Exception{
		
		
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "userexcel");							//执行上传
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			
			
			Employee employee = null;
			List<Employee> listEmployee = new ArrayList<Employee>();
			for (PageData pageData : listPd) {
				employee = new Employee();
				employee.setName(pageData.getString("var0"));
				employee.setPhone(pageData.getString("var1"));
				employee.setEmail(pageData.getString("var2"));
				listEmployee.add(employee);
			}
			mapper.batchSave(listEmployee);
		}
		BaseJsonBean bjb = new BaseJsonBean();
		bjb.setCode(0);
		bjb.setMsg("");
		return bjb;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}