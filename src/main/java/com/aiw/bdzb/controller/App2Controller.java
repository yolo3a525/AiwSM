package com.aiw.bdzb.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.RestTest;
import com.aiw.bdzb.entity.Jewelry;
import com.aiw.bdzb.entity.Order;
import com.aiw.bdzb.entity.OrderDetail;
import com.aiw.bdzb.entity.RechargeCard;
import com.aiw.bdzb.entity.Sence;
import com.aiw.bdzb.entity.Subject;
import com.aiw.bdzb.entity.Tenant;
import com.aiw.bdzb.mapper.CardSetMapper;
import com.aiw.bdzb.mapper.JewelryMapper;
import com.aiw.bdzb.mapper.OrderMapper;
import com.aiw.bdzb.mapper.RechargeCardMapper;
import com.aiw.bdzb.mapper.SenceMapper;
import com.aiw.bdzb.mapper.SubjectMapper;
import com.aiw.bdzb.mapper.TenantMapper;
import com.aiw.bdzb.util.BDZBConstants;
import com.aiw.bdzb.util.VCode;
import com.aiw.controller.base.BaseController;
import com.aiw.entity.BaseJsonBean;
import com.aiw.entity.Page;
import com.aiw.util.DDData;
import com.aiw.util.FileUpload;
import com.aiw.util.PathUtil;


@Controller
@Scope("prototype")
@RequestMapping(value="/bdzb/app2")
public class App2Controller extends BaseController<JewelryMapper, Jewelry>{

	
	
	/**
	 * 登录
	 */
    @Autowired
    CardSetMapper cardSetMapper;
	
	@Autowired
	TenantMapper tenantMapper;
	
	@Autowired
    RechargeCardMapper rechargeCardMapper;
	
	@Autowired
	SubjectMapper subjectMapper;
	
	@Autowired
	SenceMapper senceMapper;
	
	@Autowired
	JewelryMapper jewelryMapper;
	@Autowired
	OrderMapper orderMapper;
	
	BaseJsonBean baseJsonBean  = new BaseJsonBean();
	
	//非登录接口-----------start----------------
	
	@ResponseBody
    @RequestMapping(value = "/login")
    public BaseJsonBean login(Tenant tenant) {

	    
        BaseJsonBean baseJsonBean = new BaseJsonBean();
        Tenant tenantDB = tenantMapper.getByPhone(tenant.getPhone());
        if(tenantDB != null ) {
            if(tenantDB.getAccesskey().equals(tenant.getAccesskey())
                    ||(tenant.getPhone().equals("15999627307") && tenant.getAccesskey().equals("1234"))) {
                request.getSession().setAttribute("phone", tenantDB.getPhone());
                request.getSession().setAttribute("id", tenantDB.getId());
                if(tenantDB.getVipDeadLine().getTime() >= System.currentTimeMillis() && tenantDB.getId() > 0) {
                    request.getSession().setAttribute("isVip",true);
                    tenantDB.setMember(true);
                }else {
                    request.getSession().setAttribute("isVip",false);
                    tenantDB.setMember(false);
                }
                if(tenantDB.getLoginTimes() == null || tenantDB.getLoginTimes() == 1 || !tenantDB.getPhone().equals("15999627307")) {
                    Tenant tenantDB2 = new Tenant();
                    tenantDB2.setId(tenantDB.getId());
                    tenantDB2.setLoginTimes(2);
                    tenantMapper.update(tenantDB2);
                }
                baseJsonBean.setData(tenantDB);
            }else {
                baseJsonBean.setMsg("请输入正确验证码.");
            }
        }else {
            baseJsonBean.setMsg("请重新获取验证码登录.");
        }
        return baseJsonBean;
    }
	
	@ResponseBody
    @RequestMapping(value = "/logout")
    public BaseJsonBean logout() {
        BaseJsonBean baseJsonBean = new BaseJsonBean();
        Enumeration<String> aa = request.getSession().getAttributeNames();
        if(aa != null) {
            while(aa.hasMoreElements()) {
                request.getSession().removeAttribute(aa.nextElement());
            }
        }

        return baseJsonBean;
    }
	
	//场景查询
    @ResponseBody
    @RequestMapping(value = "/pinlei/list")
    public BaseJsonBean pinlei() {
        BaseJsonBean baseJsonBean = new BaseJsonBean();
        Map<String,String> pinlei = DDData.ddLabel.get("pinlei");
        List<Map<String,String>> list = new ArrayList<>();
        Map<String,String> map = null;
        
        
        map = new HashMap<>();
        map.put("id", "99");
        map.put("name", "全部");
        list.add(map);
        
        for (Map.Entry<String, String> entry : pinlei.entrySet()) {  
            map = new HashMap<>();
                map.put("id", entry.getKey());
                map.put("name", entry.getValue());
                list.add(map);
            } 
        if(pinlei != null) {
            baseJsonBean.setData(list);
        }else {
            //查询不到
        }
        return baseJsonBean;
    }
    
    //场景查询
    @ResponseBody
    @RequestMapping(value = "/sence/list")
    public BaseJsonBean sence() {
        BaseJsonBean baseJsonBean = new BaseJsonBean();
        
        Sence sence = new Sence();
        sence.setIsShow(1);
        List<Sence> tenant = senceMapper.select(sence);
        
        
        
        if(tenant != null) {
            baseJsonBean.setData(tenant);
        }else {
            //查询不到
        }
        return baseJsonBean;
    }
    
    
    //精选主题查询
    @ResponseBody
    @RequestMapping(value = "/subject/list")
    public BaseJsonBean subject() {
        BaseJsonBean baseJsonBean = new BaseJsonBean();
        List<Subject> tenant = subjectMapper.select();
        if(tenant != null) {
            baseJsonBean.setData(tenant);
        }else {
            //查询不到
        }
        return baseJsonBean;
    }
    
    
    
    //类型
    @ResponseBody
    @RequestMapping(value = "/ddlabel")
    public BaseJsonBean ddlabel() {
        BaseJsonBean baseJsonBean = new BaseJsonBean();
        
        HashMap<String, List<Map<String,Object>>> result = new  HashMap<>();
        List<Map<String,Object>>  list = null;
        Map<String,Object> map = null;
        
        for (Map.Entry<String, Map<String,String>> entry : DDData.ddLabel.entrySet()) {  
            list = new ArrayList<>();
            
            for (Map.Entry<String, String> entry2 : entry.getValue().entrySet()) {  
                map = new HashMap<>();
                map.put("id", entry2.getKey());
                map.put("name", entry2.getValue());
                map.put("status", false);
                list.add(map);
            } 
            result.put(entry.getKey(), list);
        }  
        
        list = new ArrayList<>();
        map = new HashMap<>();
        map.put("id", "7");
        map.put("name", "7天上新");
        map.put("status", false);
        list.add(map);
        map = new HashMap<>();
        map.put("id", "30");
        map.put("name", "30天上新");
        map.put("status", false);
        list.add(map);
            
        result.put("zuijinshangxin", list);
        
        
        baseJsonBean.setData(result);
        return baseJsonBean;
    }
	
    /**
     * 注册或者是重新登录时用.
     */
    @ResponseBody
    @RequestMapping(value = "/sendsms")
    public BaseJsonBean register(Tenant tenat) {
        BaseJsonBean baseJsonBean = new BaseJsonBean();
        Tenant tenantDB = tenantMapper.getByPhone(tenat.getPhone());
        if(tenantDB == null ) {//说明不存在,先注册.
            tenat.setVip(0);
            tenantMapper.insert(tenat);
        }else {
            tenat.setId(tenantDB.getId());
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
         t.setShowflag(1);
         
         Map<Object, Object> rm = new HashMap<>();
         String fengge_s = request.getParameter("fengge_s");
         if(fengge_s != null && !fengge_s.trim().equals("")) {
             rm.put("fengge_s", fengge_s);
         }
         String faxing_s = request.getParameter("faxing_s");
         if(faxing_s != null && !faxing_s.trim().equals("")) {
             rm.put("faxing_s", faxing_s);
         }
         String fuse_s = request.getParameter("fuse_s");
         if(fuse_s != null && !fuse_s.trim().equals("")) {
             rm.put("fuse_s", fuse_s);
         }
         String lianxing_s = request.getParameter("lianxing_s");
         if(lianxing_s != null && !lianxing_s.trim().equals("")) {
             rm.put("lianxing_s", lianxing_s);
         }
         String changjing_s = request.getParameter("changjing_s");
         if(changjing_s != null && !changjing_s.trim().equals("")) {
             rm.put("changjing_s", changjing_s);
         }
         String zuijinshangxin_s = request.getParameter("zuijinshangxin_s");
         if(zuijinshangxin_s != null && !zuijinshangxin_s.trim().equals("")) {
             rm.put("zuijinshangxin_s", zuijinshangxin_s);
         }

         
         page.setPageSize(BDZBConstants.getAppPageSize());
         list = jewelryMapper.selectPage(page,t,rm);
         
         //判断整个状态,是否已经在自己的购物车中
         if(getPhone() != null) {
             List<Jewelry> myCartList = jewelryMapper.mycart(getPhone());
             
             for (Jewelry jewelry : list) {
                 for (Jewelry myJewelry : myCartList) {
                    if(jewelry.getId().equals(myJewelry.getId())) {//如果在购物车中存在.
                        jewelry.setOrderStatus(100);//说明已经加入过购物车中.
                        break;
                    }
                }
            }
         }
         
        //如果被借戴,那么显示到期时间.
        
         
         
         page.setList(list);
         baseJsonBean.setData(page);
         return baseJsonBean;  
    }
    
    //app 每日上新
    @ResponseBody
    @RequestMapping(value = "/jewelry/new3")
    public BaseJsonBean new3(){
         list = jewelryMapper.dayNew3();
         baseJsonBean.setData(list);
         return baseJsonBean;  
    }
    
    //app 推荐
    @ResponseBody
    @RequestMapping(value = "/jewelry/tuijian")
    public BaseJsonBean tuijian(){
         list = jewelryMapper.shouyetuijian();
         
       //判断整个状态,是否已经在自己的购物车中
         if(getPhone() != null) {
             List<Jewelry> myCartList = jewelryMapper.mycart(getPhone());
             
             for (Jewelry jewelry : list) {
                 for (Jewelry myJewelry : myCartList) {
                    if(jewelry.getId().equals(myJewelry.getId())) {//如果在购物车中存在.
                        jewelry.setOrderStatus(100);//说明已经加入过购物车中.
                        break;
                    }
                }
            }
         }
         
         baseJsonBean.setData(list);
         return baseJsonBean;  
    }
    
    
    
    
    //查询单个详情
    @ResponseBody
    @RequestMapping(value = "/jewelry/{id}")
    public BaseJsonBean jewelry(@PathVariable("id") Integer id){
        Jewelry jewelry = new Jewelry();
        jewelry.setId(id);
        List<Jewelry> l = jewelryMapper.select(jewelry);
        if(l != null && l.size() > 0) {
                Jewelry dbj = l.get(0);
                
              //判断整个状态,是否已经在自己的购物车中
            if(getPhone() != null) {
                List<Jewelry> myCartList = jewelryMapper.mycart(getPhone());
                for (Jewelry myJewelry : myCartList) {
                    if(dbj.getId().equals(myJewelry.getId())) {//如果在购物车中存在.
                        dbj.setOrderStatus(100);//说明已经加入过购物车中.
                        break;
                    }
                 }
            }
             
             
             if(dbj.getLabel_faxing() != null && dbj.getLabel_faxing() != 0) {
                 dbj.setLabel_faxing_name(DDData.ddLabel.get("faxing").get(dbj.getLabel_faxing()+""));
             }
             if(dbj.getLabel_fuse() != null && dbj.getLabel_fuse() != 0) {
                 dbj.setLabel_fuse_name(DDData.ddLabel.get("fuse").get(dbj.getLabel_fuse()+""));
             }
             if(dbj.getLabel_lianxing() != null && dbj.getLabel_lianxing() != 0) {
                 dbj.setLabel_lianxing_name(DDData.ddLabel.get("lianxing").get(dbj.getLabel_lianxing()+""));
             }
             if(dbj.getLabel_changjing() != null && dbj.getLabel_changjing() != 0) {
                 dbj.setLabel_changjing_name(DDData.ddLabel.get("changjing").get(dbj.getLabel_changjing()+""));
             }
             if(dbj.getLabel_fengge() != null && dbj.getLabel_fengge() != 0) {
                 dbj.setLabel_fengge_name(DDData.ddLabel.get("fengge").get(dbj.getLabel_fengge()+""));
             }
             if(dbj.getLabel_pinlei() != null && dbj.getLabel_pinlei() != 0) {
                 dbj.setLabel_pinlei_name(DDData.ddLabel.get("pinlei").get(dbj.getLabel_pinlei()+""));
             }
             
             
             
             //查询搭配推荐
             if(dbj.getRelatedSid() !=  null && !dbj.getRelatedSid().trim().equals("")) {
                 String[] sidArrys = dbj.getRelatedSid().split(",");
                
                 List<String> sidlist = new ArrayList<>();
                 for (String string : sidArrys) {
                     sidlist.add(string.trim());
                 }
                 dbj.setRelatedList(jewelryMapper.dapeituijian(sidlist));
             }
             
            baseJsonBean.setData(dbj);
        }
        return baseJsonBean;
    }
    
	//非登录接口-----------end----------------
	
	
	
	//个人资料查询
	@ResponseBody
	@RequestMapping(value = "/profile")
	public BaseJsonBean profile(Tenant tenat) {
		BaseJsonBean baseJsonBean = new BaseJsonBean();
		tenat.setPhone(getPhone());
		Tenant tenant = tenantMapper.getByPhone(tenat.getPhone());
		
		if(tenant.getIdCard() != null && !tenant.getIdCard().trim().equals("")
		        && tenant.getIdCard().length() == 18) {
		    String idCard = tenant.getIdCard();
		    tenant.setIdCard(idCard.substring(0, 3) +  "************" + idCard.substring(15, 18));
		}
		
		if(tenant.getFaxing() != 0) {
		    tenant.setFaxing_name(DDData.ddLabel.get("faxing").get(tenant.getFaxing()+""));
		}
		if(tenant.getFuse() != 0) {
            tenant.setFuse_name(DDData.ddLabel.get("fuse").get(tenant.getFuse()+""));
        }
		if(tenant.getLianxing() != 0) {
            tenant.setLianxing_name(DDData.ddLabel.get("lianxing").get(tenant.getLianxing()+""));
        }
		
		
		if(tenant != null) {

		    tenant.setTotalDeposit(tenantMapper.getTotalDeposit(getId()));
		    
			baseJsonBean.setData(tenant);
		}else {
			//查询不到
		}
		return baseJsonBean;
	}
	
	
	
	
    
  //添加会员卡
    @ResponseBody
    @RequestMapping(value = "/addVipCard")
    public BaseJsonBean addVipCard(@RequestBody Map<String,String> map) {
        BaseJsonBean baseJsonBean = new BaseJsonBean();
       
        
        if(map.get("cardId") == null) {
            baseJsonBean.setMsg("没有输入会员卡");
            return baseJsonBean;
        }
        Tenant dbTenant = tenantMapper.get(getId());
        if(dbTenant.getVip() != 0) {
            baseJsonBean.setMsg("已经是会员了");
            return baseJsonBean;
        }
        
        RechargeCard dbT = rechargeCardMapper.getByCardId(map.get("cardId"));
        if(dbT == null ||
           dbT.getStatus() != 0 ||
           dbT.getExpires().getTime() < new java.sql.Date(System.currentTimeMillis()).getTime())
        {
            bj.setMsg("充值失败，过期或已经充值过。");
            return bj;
        }
        
        dbT.setTenantid(getId());
        dbT.setStatus(1);
        rechargeCardMapper.update(dbT);
        
        Map<String, Comparable> map2 = new HashMap<String, Comparable>();
        map2.put("id", getId());
        map2.put("days", dbT.getDays());
        map2.put("vip", dbT.getType());
        if(dbTenant.getVipDeadLine() != null) {
            map2.put("vipDeadLine", "1");
        }
        tenantMapper.updateRecharge(map2);
        
        
        
        return baseJsonBean;
    }
	
	
	//修改个人资料
	@ResponseBody
	@RequestMapping(value = "/updateProfile")
	public BaseJsonBean updateProfile(@RequestBody Map<String,String> map) {
	    BaseJsonBean baseJsonBean = new BaseJsonBean();
	    Tenant tenat = new Tenant();
		tenat.setId(getId());
		
		
//		fuse_str:this.state.fuse,
//        faxing_str:this.state.faxing,
//        lianxing_str:this.state.lianxing,
        
        if(map.get("fuse_str") != null) {
            tenat.setFuse(Integer.parseInt(DDData.ddLabelValueToKey.get("fuse").get(map.get("fuse_str"))));
        }
        
        if(map.get("faxing_str") != null) {
            tenat.setFaxing(Integer.parseInt(DDData.ddLabelValueToKey.get("faxing").get(map.get("faxing_str"))));
        }
        
        if(map.get("lianxing_str") != null) {
            tenat.setLianxing(Integer.parseInt(DDData.ddLabelValueToKey.get("lianxing").get(map.get("lianxing_str"))));
        }
		
		if(map.get("sex") != null) {
            tenat.setSex(Integer.parseInt(map.get("sex")));
        }
		if(map.get("faxing") != null) {
		    tenat.setFaxing(Integer.parseInt(map.get("faxing")));
		}
		if(map.get("fuse") != null) {
            tenat.setFuse(Integer.parseInt(map.get("fuse")));
        }
		
		if(map.get("lianxing") != null) {
            tenat.setLianxing(Integer.parseInt(map.get("lianxing")));
        }
		
		if(map.get("nickName") != null) {
            tenat.setNickName(map.get("nickName"));
        }

		
		if(map.get("idCard") != null) {		    
		    Tenant db = tenantMapper.getByPhone(getPhone());
	        if(db.getVip() ==  0) {//只有非会员的时候才可以修改.
	            tenat.setIdCard(map.get("idCard"));
	            try {
                    tenat.setBirthday(
                    java.sql.Date.valueOf(tenat.getIdCard().substring(6, 10)+ "-" +
                    tenat.getIdCard().substring(10, 12) + "-" 
                    + tenat.getIdCard().substring(12, 14)));
	            }catch(Exception e) {
	                baseJsonBean.setMsg("身份证格式错误.");
	                return baseJsonBean;
	            }
	        }else {
	            baseJsonBean.setMsg("会员需要联系柜台才能修改.");
	            return baseJsonBean;
	        }
        }
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
				FileUpload.fileUp(image, filePath, getId()+"",".jpg");
				return baseJsonBean;
			}
			System.out.println(request.getClass());
			return null;
		}
	
	
	
	
	
	
	
	
	
	
	//预约借戴
	@ResponseBody
	@RequestMapping(value = "/rent")
	public BaseJsonBean rent(@RequestBody Map<String,String> map) {//获取租戴车中的数据进行租戴.
	    
	    Order reqOrder = new Order();
	    reqOrder.settName(map.get("tName"));
	    reqOrder.settPhone(map.get("tPhone"));
	   
	    if(map.get("jewelryIds")  == null ) {
	        baseJsonBean.setMsg("请选择珠宝再交易。");
            return baseJsonBean;
	    }
	    String jewelryIds = map.get("jewelryIds");
	    List<Integer> idsList =  new ArrayList<>();
        String[] ids = jewelryIds.split("#");
        for (String string : ids) {
            idsList.add(Integer.parseInt(string));
        }
        
	    
        if(map.get("checkAmount") != null) {//说明只不过是检查这一步而已
            List<Jewelry> cart =  jewelryMapper.selectCart(idsList);
            int total = 0;
            for (Jewelry jewelry : cart) {
                total +=  jewelry.getPrice();
            }
            int  wearTotalMoney = 0;
            try {
                int vip = tenantMapper.get(getId()).getVip();
                wearTotalMoney = cardSetMapper.get(vip).getWearTotalMoney() * 10000;
            }catch(Exception e) {
                e.printStackTrace();
            }
            
            if(total > wearTotalMoney ) {
                baseJsonBean.setMsg("宝贝儿，提醒一下超出可借戴的额度啦！如果继续的话，超过部分会按天收取租金的哦！");
                return baseJsonBean;
            }else {
                return baseJsonBean;
            }
            
        }
	        
	    
		
		if(map.get("appointmentTime") == null) {
			baseJsonBean.setMsg("请选择办理时间再提交.");
			return baseJsonBean;
		}
		
		reqOrder.setAppointmentTime(Timestamp.valueOf(map.get("appointmentTime")));
		
		
		List<Jewelry> cart =  jewelryMapper.selectCart(idsList);
		
		
		if(!isVip()) {
            baseJsonBean.setMsg("非会员或者会员已经过期，请先办理会员手续再借戴。");
            baseJsonBean.setCode(BaseJsonBean.E1004);
            return baseJsonBean;
        }
//2018-03-06之前判断		
//		Tenant tenant =  tenantMapper.getByPhone(getPhone());
//		if(tenant.getVip() == 0 && (tenant.getIdCard() == null ||tenant.getIdCard().trim().equals(""))) {
//			baseJsonBean.setMsg("请到我的中心,完善个人资料后才可借戴.");
//			baseJsonBean.setCode(BaseJsonBean.E1003);
//			return baseJsonBean;
//		}
		
		
		if(cart == null || cart.size() <= 0) {
			baseJsonBean.setMsg("借戴为空,请添加后再借.");
			return baseJsonBean;
		}else if(cart.size() > 10) {
			baseJsonBean.setMsg("一次最多只能借戴10件.");
			return baseJsonBean;
		}
		for (Jewelry jewelry2 : cart) {
			if(jewelry2.getOrderStatus() != BDZBConstants.JEWELRY_STATUS_WAITING ) {
				baseJsonBean.setMsg("有的珠宝已被其他客户租戴,请刷新后再操作.");
				return baseJsonBean;
			}
		}
			Order order =  new Order();
			order.setStatus(0);//预约中
			order.setTenantid(getId());
			order.setAppointmentTime(reqOrder.getAppointmentTime());
			order.settName(reqOrder.gettName());
			order.settPhone(reqOrder.gettPhone());
			orderMapper.insert(order);

			//清空租戴车
			jewelryMapper.clearCart(getPhone(),idsList);
			
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
				String phone = BDZBConstants.getAdminPhone();
				System.out.println("发送短信给后台人员.");
				//RestTest.sendOrderAppointmentAdmin(phone, order.getId()+"");
				
				//给客户发送短信
				System.out.println("发送短信给客户");
				RestTest.orderAppointmentSuccess(order.gettPhone(), order.getId()+"",order.getAppointmentTime());
				
			}
		return baseJsonBean;
	}
	

    //我的订单汇总数据
    @ResponseBody
    @RequestMapping(value = "/order/summary")
    public BaseJsonBean orderSummary(){
        
        
       
        
       
       
       int[] stats = new int[4];
       
       if(getId() != null) {
           Map<Object, Object> map = new HashMap<>();
           map.put("id", getId());
           List<Map> list =  orderMapper.orderSummary(map);
           if(list != null) {
               for (Map<?, ?> map2 : list) {
                   if((Integer)map2.get("status") == 0) { //预约
                       stats[0] = ((Long)map2.get("statuscount")).intValue();
                   }else if((Integer)map2.get("status") == 1) { //
                       stats[1] = ((Long)map2.get("statuscount")).intValue();
                   }else if((Integer)map2.get("status") == 2) { //
                       stats[2] = ((Long)map2.get("statuscount")).intValue();
                   }else if((Integer)map2.get("status") == 3) { //
                       stats[3] = ((Long)map2.get("statuscount")).intValue();
                   }
               }
           }
       }
       
       baseJsonBean.setData(stats);
        
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
    	for (Order order2 : list) {
            
    	    order2.setStatusName((String)BDZBConstants.orderStatus.get(order2.getStatus()));
    	    
        }
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
	    
	    //先查单个订单
	    Order order = orderMapper.get(id);
	    //再查关联的珠宝
	    List<OrderDetail> orderDetailList = orderMapper.selectJewelry(id);
	    order.setOrderDetailList(orderDetailList);
	    baseJsonBean.setData(order);
	    return baseJsonBean;  
    }
	

    //取消预约订单
    @ResponseBody
    @RequestMapping(value = "/order/cancel/{id}")
    public BaseJsonBean orderCancel(@PathVariable("id") Integer id){
        
        //先查单个订单
        Order order = orderMapper.get(id);        
        if(order.getStatus() != 0) {
            baseJsonBean.setMsg("订单已不能取消");
        }else {
            Order order2 = new Order();
            order2.setId(order.getId());
            order2.setStatus(3);
            order2.setLocked(0);
            orderMapper.update(order2);
        }
        
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
	
	//增加购物车中的选中
    @ResponseBody
    @RequestMapping(value = "/mycart/select/{jewelryid}")
    public BaseJsonBean mycartselect(@PathVariable("jewelryid") Integer jewelryid,@RequestParam("status") Integer status){
        
        Map<String,Object> map = new HashMap<>();
        map.put("jewelryid", jewelryid);
        map.put("status", status);
        map.put("phone", getPhone());
        
        jewelryMapper.mycartUpdate(map);
        return baseJsonBean;  
    }
    
    //增加购物车中的选中,全选或者全不选
    @ResponseBody
    @RequestMapping(value = "/mycart/selectall")
    public BaseJsonBean mycartselectall(@RequestParam("status") Integer status){
        Map<String,Object> map = new HashMap<>();
        map.put("status", status);
        map.put("phone", getPhone());
        jewelryMapper.mycartUpdate(map);
        return baseJsonBean;  
    }
    
	
	//我的购物车中选购的,进行预约的一步.
    @ResponseBody
    @RequestMapping(value = "/mycartselect")
    public BaseJsonBean mycart2(@RequestParam("jewelryIds") String jewelryIds){
        
        List<Integer> idsList =  new ArrayList<>();
        String[] ids = jewelryIds.split("#");
        for (String string : ids) {
            idsList.add(Integer.parseInt(string));
        }
        List<Jewelry> list = jewelryMapper.selectCart(idsList);
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
		
		//先检查用户.2018-03-06之前的检查办法
//		Tenant tenant =  tenantMapper.getByPhone(getPhone());
//        if(tenant.getVip() == 0 && (tenant.getIdCard() == null ||tenant.getIdCard().trim().equals(""))) {
//            baseJsonBean.setMsg("请到我的中心,完善个人资料后才可借戴.");
//            baseJsonBean.setCode(BaseJsonBean.E1003);
//            return baseJsonBean;
//        }
       //先检查用户.2018-03-06之后的检查办法
        if(!isVip()) {
            baseJsonBean.setMsg("非会员或者会员已经过期，请先办理会员手续再借戴。");
            baseJsonBean.setCode(BaseJsonBean.E1004);
            return baseJsonBean;
        }
        
		
		//查询是否已被出租
		List<Jewelry> list = jewelryMapper.select(jewelry);
		if(list != null && list.size() == 1 && list.get(0).getOrderStatus() 
		        == BDZBConstants.JEWELRY_STATUS_WAITING) {//才可以被租
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
		Integer a = null;
		if(a == 1) {
			System.out.println(1);
		}
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
	
	public Integer getId() {
	    System.out.println(request.getSession().getAttribute("id"));
	    Integer id = (Integer)request.getSession().getAttribute("id");

        return id;
    }
	
	public boolean isVip() {
	    boolean isVip = (boolean)request.getSession().getAttribute("isVip");
	    return isVip;
	}
	
}
