package com.aiw.ruiyun.controller;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.aiw.controller.base.BaseController;
import com.aiw.entity.BaseJsonBean;
import com.aiw.entity.Page;
import com.aiw.ruiyun.entity.Salary;
import com.aiw.ruiyun.mapper.SalaryMapper;
import com.aiw.util.Const;
import com.aiw.util.FileUpload;
import com.aiw.util.FileUtil;
import com.aiw.util.Freemarker;
import com.aiw.util.ObjectExcelRead;
import com.aiw.util.PageData;
import com.aiw.util.PathUtil;
import com.aiw.util.mail.MailSenderInfo;
import com.aiw.util.mail.SimpleMailSender;


/** 
 * 说明：ruiyun.salary
 * 创建人：aiw
 * 创建时间：2017-05-19
 */

@Controller
@RequestMapping(value="/salary")
public class SalaryController extends BaseController<SalaryMapper, Salary>{
   
	
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
	
    @RequestMapping(value = "/sendmailInit", method = RequestMethod.GET)
    public ModelAndView sendmailInit(){
    	
    	 ModelAndView modelAndView = new ModelAndView(); 
    	 modelAndView.setViewName("/" + getModule() + "/sendmail");
	     return modelAndView;  
    	
    }
    
    
    @RequestMapping(value = "/emailLogin")
    public ModelAndView emailLogin(@RequestParam("email")String email,@RequestParam("password")String password){
    	
    	request.getSession().setAttribute("email", email);
    	request.getSession().setAttribute("password", password);
    	
    	ModelAndView modelAndView = new ModelAndView(); 
    	modelAndView.setViewName("/" + getModule() + "/sendmail");
	    return modelAndView;  
    	
    }
    
	
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute Salary t){
    	return  list_p(page, t);
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public Salary save(@ModelAttribute Salary t){
		return  save_p(t);
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(Salary t) {
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
			
			Salary salary = null;
			List<Salary> listEmployee = new ArrayList<Salary>();
			for (PageData pageData : listPd) {
				salary = new Salary();
				salary.setName(pageData.getString("var0"));
				salary.setSalary(new BigDecimal(pageData.getString("var1")));
				listEmployee.add(salary);
			}
			mapper.batchSave(listEmployee);
		}
		BaseJsonBean bjb = new BaseJsonBean();
		bjb.setCode(0);
		bjb.setMsg("");
		return bjb;
		
		
	}
	
	/**从EXCEL导入到数据库
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sendMail")
	@ResponseBody
	public BaseJsonBean uploadFileAndSendMail(
			@RequestParam(value="excel",required=false) MultipartFile file,
			@RequestParam(value="email",required=false)String email,
			@RequestParam(value="password",required=false)String password
			) throws Exception{
		int fail = 0;
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			//先删除原始的
			FileUtil.delFile(filePath + file.getName()); 
			String newFileName =  FileUpload.fileUp(file, filePath, file.getName());							//执行上传
			String fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
			List<Map> listPd = (List)ObjectExcelRead.readExcel(filePath, newFileName, 2, 0, 0);
			
			for (Map pageData : listPd) {
				
				Map map = new HashMap<>();
				map.put("data",pageData);
				map.put("date", fileName);
				
				//排除邮件这一列.
				String toEmail = (String) pageData.get("var2");
				pageData.remove("var2");
				
				
				String src = "code/";
				Freemarker.printFile("salary.ftl", map, 
						"salary.html",
						src,
						"");
				
				byte[] bb = Files.readAllBytes(new File(PathUtil.getClasspath()+"code/salary.html").toPath());
				
				MailSenderInfo m = new MailSenderInfo();
				if(email.contains("126")) {
					m.setMailServerHost("smtp.126.com");
				}else {
					m.setMailServerHost("smtp.exmail.qq.com");
				}
				m.setMailServerPort("25");
				m.setFromAddress(email);
				m.setUserName(email);
				m.setPassword(password);
				
				m.setSubject(fileName + "薪资明细");
				
				
				m.setContent(new String(bb));
				m.setToAddress(toEmail);
				//m.setToAddress("3684170@qq.com");
				m.setValidate(true);
				SimpleMailSender mi = new  SimpleMailSender();
				try {
					mi.sendHtmlMail(m);
					logger.info(toEmail + "发送成功");
				}catch(Exception e) {
					e.printStackTrace();
					logger.error(toEmail + "发送失败");
					fail++;
				}
			}
			bj.setMsg("发送完成,发送失败"+fail + "人.");
		}
		return bj;
	}
}