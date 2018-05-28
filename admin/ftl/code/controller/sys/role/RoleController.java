package com.fh.controller.sys.role;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.sys.role.RoleManager;
import com.fh.service.sys.rolemx.RoleMxManager;

/** 
 * 说明：绝色
 * 创建人：FH Q313596790
 * 创建时间：2017-04-29
 */
@Controller
@RequestMapping(value="/role")
public class RoleController extends BaseController {
	
	String menuUrl = "role/list.do"; //菜单地址(权限用)
	@Resource(name="roleService")
	private RoleManager roleService;
	
	@Resource(name="rolemxService")
	private RoleMxManager rolemxService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Role");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ROLE_ID", this.get32UUID());	//主键
		pd.put("id", "
Expression var[4] is undefined on line 62, column 40 in controllerTemplate.ftl.
The problematic instruction:
----------
==> ${var[4]?replace("无",0)} [on line 62, column 38 in controllerTemplate.ftl]
----------

Java backtrace for programmers:
----------
freemarker.core.InvalidReferenceException: Expression var[4] is undefined on line 62, column 40 in controllerTemplate.ftl.
	at freemarker.core.TemplateObject.assertNonNull(TemplateObject.java:124)
	at freemarker.core.TemplateObject.invalidTypeException(TemplateObject.java:134)
	at freemarker.core.RegexBuiltins$replace_reBI._getAsTemplateModel(RegexBuiltins.java:149)
	at freemarker.core.Expression.getAsTemplateModel(Expression.java:89)
	at freemarker.core.MethodCall._getAsTemplateModel(MethodCall.java:86)
	at freemarker.core.Expression.getAsTemplateModel(Expression.java:89)
	at freemarker.core.Expression.getStringValue(Expression.java:93)
	at freemarker.core.DollarVariable.accept(DollarVariable.java:76)
	at freemarker.core.Environment.visit(Environment.java:196)
	at freemarker.core.MixedContent.accept(MixedContent.java:92)
	at freemarker.core.Environment.visit(Environment.java:196)
	at freemarker.core.IfBlock.accept(IfBlock.java:82)
	at freemarker.core.Environment.visit(Environment.java:196)
	at freemarker.core.ConditionalBlock.accept(ConditionalBlock.java:79)
	at freemarker.core.Environment.visit(Environment.java:196)
	at freemarker.core.IteratorBlock$Context.runLoop(IteratorBlock.java:172)
	at freemarker.core.Environment.visit(Environment.java:351)
	at freemarker.core.IteratorBlock.accept(IteratorBlock.java:95)
	at freemarker.core.Environment.visit(Environment.java:196)
	at freemarker.core.MixedContent.accept(MixedContent.java:92)
	at freemarker.core.Environment.visit(Environment.java:196)
	at freemarker.core.Environment.process(Environment.java:176)
	at freemarker.template.Template.process(Template.java:232)
	at com.aiw.util.Freemarker.printFile(Freemarker.java:55)
	at com.aiw.util.CodeUtil.main(CodeUtil.java:75)
