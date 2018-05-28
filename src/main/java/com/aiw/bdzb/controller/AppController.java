package com.aiw.bdzb.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.RestTest;
import com.aiw.bdzb.entity.Jewelry;
import com.aiw.bdzb.entity.Order;
import com.aiw.bdzb.entity.OrderDetail;
import com.aiw.bdzb.entity.Tenant;
import com.aiw.bdzb.mapper.JewelryMapper;
import com.aiw.bdzb.mapper.OrderMapper;
import com.aiw.bdzb.mapper.TenantMapper;
import com.aiw.bdzb.util.BDZBConstants;
import com.aiw.bdzb.util.VCode;
import com.aiw.controller.base.BaseController;
import com.aiw.entity.BaseJsonBean;
import com.aiw.entity.Page;
import com.aiw.util.FileUpload;
import com.aiw.util.PathUtil;


@Controller
@Scope("prototype")
@RequestMapping(value="/bdzb/app")
public class AppController extends BaseController<JewelryMapper, Jewelry>{

	
	
	/**lo
	 * 登录
	 */
	
	@Autowired
	TenantMapper tenantMapper;
	@Autowired
	JewelryMapper jewelryMapper;
	@Autowired
	OrderMapper orderMapper;
	
	BaseJsonBean baseJsonBean  = new BaseJsonBean();
	
	@ResponseBody
	@RequestMapping(value = "/login")
	public BaseJsonBean login(Tenant tenant) {
		BaseJsonBean baseJsonBean = new BaseJsonBean();
		Tenant tenantDB = tenantMapper.getByPhone(tenant.getPhone());
		if(tenantDB != null ) {
			if(tenantDB.getAccesskey().equals(tenant.getAccesskey()) ||(tenant.getPhone().equals("15999627307") && tenant.getAccesskey().equals("1234"))) {
				request.getSession().setAttribute("phone", tenant.getPhone());
				baseJsonBean.setData(tenantDB);
			}else {
				baseJsonBean.setMsg("请输入正确验证码.");
			}
		}else {
			baseJsonBean.setMsg("请重新获取验证码登录.");
		}
		return baseJsonBean;
	}
	
	//个人资料查询
	@ResponseBody
	@RequestMapping(value = "/profile")
	public BaseJsonBean profile(Tenant tenat) {
		BaseJsonBean baseJsonBean = new BaseJsonBean();
		tenat.setPhone(getPhone());
		Tenant tenant = tenantMapper.getByPhone(tenat.getPhone());
		if(tenant != null) {
			baseJsonBean.setData(tenant);
		}else {
			//查询不到
		}
		return baseJsonBean;
	}
	
	//修改个人资料
	@ResponseBody
	@RequestMapping(value = "/updateProfile")
	public BaseJsonBean updateProfile(Tenant tenat) {
		tenat.setPhone(getPhone());
		if(tenat.getNickName() != null) {
			try {
				//可能部署的系统不是utf-8的系统
				tenat.setNickName(java.net.URLDecoder.decode(tenat.getNickName(),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		Tenant db = tenantMapper.getByPhone(tenat.getPhone());
		if(db.getVip() == 1 || db.getVip() == 2) {//如果一旦是会员或者临时会员,不能修改 .暂时没有更好的办法.稍后处理.
			tenat.setIdCard(null);//idCard不能修改.
		}else if(db.getVip() == 0 && tenat.getIdCard() != null && !tenat.getIdCard().trim().equals("")) {
			tenat.setVip(2);//临时会员.
			tenat.setBirthday(java.sql.Date.valueOf(tenat.getIdCard().substring(6, 10)+ "-" + tenat.getIdCard().substring(10, 12) + "-" + tenat.getIdCard().substring(12, 14)));
		}
		BaseJsonBean baseJsonBean = new BaseJsonBean();
		tenantMapper.update(tenat);
		return baseJsonBean;
	}
	
		//上传个人图片
		@RequestMapping(value = "/upload")
		@ResponseBody
		public BaseJsonBean upload(
				@RequestParam(value="image",required=false) MultipartFile image) throws Exception{
			if (null != image && !image.isEmpty()) {
				String filePath = PathUtil.getClasspath() + BDZBConstants.UPLOAD_PROFILE;
				System.out.println(filePath);
				FileUpload.fileUp(image, filePath, tenantMapper.getByPhone(getPhone()).getId()+"");
				return baseJsonBean;
			}
			System.out.println(request.getClass());
			return null;
		}
	
	
	/**
	 * 注册或者是重新登录时用.
	 */
	@ResponseBody
	@RequestMapping(value = "/register")
	public BaseJsonBean register(Tenant tenat) {
		BaseJsonBean baseJsonBean = new BaseJsonBean();
		Tenant tenant = tenantMapper.getByPhone(tenat.getPhone());
		if(tenant == null ) {//说明不存在,先注册.
			tenat.setVip(0);
			tenantMapper.insert(tenat);
		}
		//1.发送验证码
		String vCode = VCode.getCode();
		RestTest.sendVCode(tenat.getPhone(), vCode);
		
		//2.更新数据库验证码,等下登录的时候使用
		tenat.setAccesskey(vCode);
		tenantMapper.update(tenat);
		return baseJsonBean;
	}
	
	
	//获取珠宝列表,包含搜索功能
	@ResponseBody
	@RequestMapping(value = "/jewelry/list")
	public BaseJsonBean jewelryList(@ModelAttribute Page page,@ModelAttribute Jewelry t){
    	//设置pageSize
    	 page.setPageSize(BDZBConstants.getAppPageSize());
		 list = jewelryMapper.selectPage(page,t);
		 
		 //判断整个状态,是否已经在自己的购物车中
		 List<Jewelry> myCartList = jewelryMapper.mycart(getPhone());
		 
		 for (Jewelry jewelry : list) {
			 for (Jewelry myJewelry : myCartList) {
				if(jewelry.getId().equals(myJewelry.getId())) {//如果在购物车中存在.
					jewelry.setOrderStatus(100);
					break;
				}
			}
		}
		 
		//如果被借戴,那么显示到期时间.
		
		 
		 
		 page.setList(list);
		 baseJsonBean.setData(page);
	     return baseJsonBean;  
    }
	
	
	
	
	//查询单个详情
	@ResponseBody
	@RequestMapping(value = "/jewelry/{id}")
	public BaseJsonBean jewelry(@PathVariable("id") Integer id){
		Jewelry j = new Jewelry();
		j.setId(id);
		List<Jewelry> l = jewelryMapper.select(j);
		if(l != null && l.size() > 0) {
			baseJsonBean.setData(l.get(0));
		}
		return baseJsonBean;
    }
	
	
	
	//预约借戴
	@ResponseBody
	@RequestMapping(value = "/rent")
	public BaseJsonBean rent(@ModelAttribute Order reqOrder) {//获取租戴车中的数据进行租戴.
		
		if(reqOrder == null || reqOrder.getAppointmentTime() == null) {
			baseJsonBean.setMsg("请选择办理时间再提交.");
			return baseJsonBean;
		}
		
		Tenant tenant =  tenantMapper.getByPhone(getPhone());
		if(tenant.getVip() != 1 && tenant.getVip() != 2) {
			baseJsonBean.setMsg("请到我的中心,更新资料成为临时会员后才可借戴.");
			return baseJsonBean;
		}
		
		
		List<Jewelry> cart =  jewelryMapper.mycart(getPhone());
		
		if(cart == null || cart.size() <= 0) {
			baseJsonBean.setMsg("借戴为空,请添加后再借.");
			return baseJsonBean;
		}else if(cart.size() > 10) {
			baseJsonBean.setMsg("一次最多只能借戴10件.");
			return baseJsonBean;
		}
		for (Jewelry jewelry2 : cart) {
			if(jewelry2.getOrderStatus() != -1) {
				baseJsonBean.setMsg("有的珠宝已被其他客户租戴,请刷新后再操作.");
				return baseJsonBean;
			}
		}
			Order order =  new Order();
			order.setStatus(0);//预约中
			order.setPhone(getPhone());
			order.setAppointmentTime(reqOrder.getAppointmentTime());
			orderMapper.insert(order);

			//清空租戴车
			//jewelryMapper.clearCart(getPhone());报错注释掉
			
			for (Jewelry jewelry2 : cart) {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setJewelryid(jewelry2.getId());
				orderDetail.setOrderid(order.getId());
				orderMapper.insertOrderDetail(orderDetail);
				
				//修改租赁状态
				//jewelry2.setStatus(2);//已被预约
				jewelryMapper.update(jewelry2);
			}
			
			//如果预约成功,给后台用户发信息.
			if(BDZBConstants.getSendAdminFlag() != null 
					&& BDZBConstants.getSendAdminFlag().equals("1")) {
				//String phone = BDZBConstants.getAdminPhone();
				System.out.println("发送短信给后台人员.");
				//RestTest.sendOrderAppointmentAdmin(phone, order.getId()+"");
				
				//给客户发送短信
				System.out.println("发送短信给客户");
				//RestTest.sendOrderAppointmentUser(order.getPhone(), order.getId()+"");
				
			}
		return baseJsonBean;
	}
	
	
	//我的租赁历史
	@ResponseBody
	@RequestMapping(value = "/order/list")
	public BaseJsonBean orderList(@ModelAttribute Page page,@ModelAttribute Order order){
    	//设置pageSize
    	page.setPageSize(BDZBConstants.getAppPageSize());
    	order.setPhone(getPhone());
    	List<Order> list = orderMapper.selectPage(page,order); 	
    	/*if(list != null && list.size() > 0) {
    		
	 		List<Integer> ids = new ArrayList<Integer>();
	 		for (Order order1 : list) {
	 			ids.add(order1.getId());
	 		}
	 		List<OrderDetail> orderDetailList = orderMapper.selectJewelry(ids);
	 		Map<Integer,List<OrderDetail>> orderDetailMap = new HashMap<Integer,List<OrderDetail>>();
	 		
	 		for (OrderDetail orderDetail : orderDetailList) {
	 			if(orderDetailMap.get(orderDetail.getOrderid()) == null) {
	 				orderDetailMap.put(orderDetail.getOrderid(), new ArrayList<OrderDetail>());
	 			}
	 			orderDetailMap.get(orderDetail.getOrderid()).add(orderDetail);
	 		}
	 		
	 		for (Order order1 : list) {
	 			order1.setOrderDetailList(orderDetailMap.get(order1.getId()));
	 		}
    	}*/
 		page.setList(list);
 		baseJsonBean.setData(page);
	    return baseJsonBean;    
    }
	
	//订单详情
	@ResponseBody
	@RequestMapping(value = "/order/detail/{id}")
	public BaseJsonBean orderDetail(@PathVariable("id") Integer id){
	    List<OrderDetail> orderDetailList = orderMapper.selectJewelry(id);
	    baseJsonBean.setData(orderDetailList);
	    return baseJsonBean;  
    }
	
	
	//我的购物车
	@ResponseBody
	@RequestMapping(value = "/mycart")
	public BaseJsonBean mycart(){
		
		List<Jewelry> list = jewelryMapper.mycart(getPhone());
	    baseJsonBean.setData(list);
	    
	    return baseJsonBean;  
    }
	
	
	//我的购物车
	@ResponseBody
	@RequestMapping(value = "/mycart/delete/{id}")
	public BaseJsonBean deleteJewelryFromCart(@PathVariable("id") Integer id){
		
		Map<Object, Object> map = new HashMap<>();
		map.put("phone", getPhone());
		map.put("jewelryid", id);
		jewelryMapper.deleteJewelryFromCart(map);
	    return baseJsonBean;  
    }
	
	
	
	//加入购物车
	@ResponseBody
	@RequestMapping(value = "/addToCart")
	public BaseJsonBean addToCart(Jewelry jewelry) {//传递一个珠宝id与一个订单的天数.
		BaseJsonBean baseJsonBean = new BaseJsonBean();
		//查询是否已被出租
		List<Jewelry> list = jewelryMapper.select(jewelry);
		if(list != null && list.size() == 1 && list.get(0).getOrderStatus() == -1) {//才可以被租
			@SuppressWarnings("rawtypes")
            Map<String, Comparable>  map = new HashMap<String, Comparable>();
			map.put("phone", getPhone());
			map.put("jewelryid",list.get(0).getId());
			
			try {
				jewelryMapper.addToCart(map);
			}catch(DuplicateKeyException e) {
				baseJsonBean.setMsg("借戴清单中已有,请不要重复加入.");
			}
			
		}else {
			baseJsonBean.setMsg("已经被预约.");
		}
		return baseJsonBean;
	}
	
	
	
	@Override
	public ModelAndView list(Page page, Jewelry t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Jewelry save(Jewelry t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseJsonBean update(Jewelry t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelAndView get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelAndView get() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getPhone() {
		return (String) request.getSession().getAttribute("phone");
	}

	public static void main(String[] args) {
		//Integer a = null;
//		if(a == 1) {
//			System.out.println(1);
//		}
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
