package com.aiw.bdzb.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.RestTest;
import com.aiw.bdzb.entity.BalanceRecord;
import com.aiw.bdzb.entity.CardSet;
import com.aiw.bdzb.entity.Jewelry;
import com.aiw.bdzb.entity.Order;
import com.aiw.bdzb.entity.OrderDetail;
import com.aiw.bdzb.entity.Tenant;
import com.aiw.bdzb.mapper.BalanceRecordMapper;
import com.aiw.bdzb.mapper.CardSetMapper;
import com.aiw.bdzb.mapper.JewelryMapper;
import com.aiw.bdzb.mapper.OrderMapper;
import com.aiw.bdzb.mapper.TenantMapper;
import com.aiw.bdzb.util.BDZBConstants;
import com.aiw.controller.base.BaseController;
import com.aiw.entity.BaseJsonBean;
import com.aiw.entity.Chart;
import com.aiw.entity.Page;


/** 
 * 说明：bdzb.order
 * 创建人：aiw
 * 创建时间：2017-05-25
 */

@Controller
@Scope("prototype")
@RequestMapping(value= {"/bdzb/order","/bdzb/appointmentorder"})
public class OrderController extends BaseController<OrderMapper, Order>{
	
	
	 @InitBinder
	 public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat2.setLenient(false);
		
		binder.registerCustomEditor(java.sql.Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
		binder.registerCustomEditor(java.sql.Timestamp.class, new CustomDateEditor(dateFormat2, true));   //true:允许输入空值，false:不能为空值
	}
	
   
	@Autowired
	JewelryMapper jewelryMapper;
	
	
	@Autowired
    BalanceRecordMapper balanceRecordMapper;
	
	@Autowired
	TenantMapper tenantMapper;
	
	@Autowired
    CardSetMapper cardSetMapper;

    
    //预租订单 与 其他状态的订单
	@Override
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@ModelAttribute Page page,@ModelAttribute Order t){
		
		
		
		
		
		ModelAndView m = list_p(page, t);
		if(list == null || list.size() < 1) {//没有订单直接返回
			return m;
		}
		
		
		
	/*	List<Integer> ids = new ArrayList<Integer>();
		for (Order order : list) {
			ids.add(order.getId());
		}
		List<OrderDetail> orderDetailList = mapper.selectJewelry(ids);
		Map<Integer,List<OrderDetail>> orderDetailMap = new HashMap<Integer,List<OrderDetail>>();
		
		for (OrderDetail orderDetail : orderDetailList) {
			if(orderDetailMap.get(orderDetail.getOrderid()) == null) {
				orderDetailMap.put(orderDetail.getOrderid(), new ArrayList<OrderDetail>());
			}
			orderDetailMap.get(orderDetail.getOrderid()).add(orderDetail);
		}
		
		for (Order order : list) {
			order.setOrderDetailList(orderDetailMap.get(order.getId()));
		}*/
		m.addObject("status", t.getStatus());
    	return  m;
    }
	
	
	
	 
    //订单详情列表
    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    public ModelAndView orderDetail(@PathVariable("id") Integer id){
		
    	ModelAndView modelAndView = new ModelAndView(); 
    	
    	Order order = mapper.get(id);
    	
    	
    		
		Tenant tenant = tenantMapper.get(order.getTenantid());
		
		List<OrderDetail> orderDetailList = mapper.selectJewelry(id);
		modelAndView.addObject("list", orderDetailList);//查询结果
		modelAndView.addObject("order", order);//查询结果
		modelAndView.addObject("tenant", tenant);//查询结果
		modelAndView.addObject("orderStatus", order.getStatus());//查询结果
		modelAndView.setViewName("/" + getModule() + "/detail");
    		
    	return  modelAndView;
    }
    
    
    //从订单中移除不想要的珠宝
   	@RequestMapping(value = "/detail/remove/{id}/{jewelryid}")
   	@ResponseBody
    public Integer detailRemove(@PathVariable("id") Integer id,@PathVariable("jewelryid") Integer jewelryid){
   		
   		Map<Object, Object> map = new HashMap<>();
   		map.put("id", id);
   		map.put("jewelryid", jewelryid);
   		mapper.removeJewelryFromOrderList(map);
   		return 1;
   }
	
    //后台直接创建订单初始化页面
	@RequestMapping(value = "/bcreate")
	public ModelAndView bcreate() {
	    
        List<Integer> slist = (List<Integer>)request.getSession().getAttribute("cart");
        int totalMoney = 0;
        if(slist != null && slist.size() > 0 ) {
            List<Jewelry> jewelryList = jewelryMapper.selectCart(slist);
            for (Jewelry jewelry : jewelryList) {
                totalMoney += jewelry.getPrice();
            }
        }
	    
		ModelAndView m = get_p();
		m.addObject("totalMoney", totalMoney);
		m.setViewName("/" + getModule() + "/create");
		return m;
	}
   	
	//shiji = 1 借  shiji = 2 还的时候
	public Integer pay(Integer jewelryAmount,Integer days,Integer mayWearTotalMoney,Integer shiji) {
	    
	    Integer pay2 = 0;
        Integer pay1 = 0;
        Integer pay3 = 0;
        
        Integer chaDays = 0;
	    
	    if(shiji == 1) {
	        if(jewelryAmount > mayWearTotalMoney) {
	            pay2 =  (100 +  
	                    ((jewelryAmount - mayWearTotalMoney) % 5 == 0 ? (jewelryAmount - mayWearTotalMoney) / 5 : (jewelryAmount - mayWearTotalMoney) / 5 + 1)//没5w一个台阶
	                    * 50) * BDZBConstants.getFreeday();
	        }
	        chaDays = days - BDZBConstants.getFreeday();
	    }
	    
	    if(shiji == 2){
	        chaDays = days;
	    }
	    
        if(chaDays > 0 ) {
            pay1 =  chaDays * mayWearTotalMoney * 10000 / 1000;
            if(jewelryAmount > mayWearTotalMoney) {
                pay3 =  (100 +  
                        ((jewelryAmount - mayWearTotalMoney) % 5 == 0 ? (jewelryAmount - mayWearTotalMoney) / 5 : (jewelryAmount - mayWearTotalMoney) / 5 + 1)//没5w一个台阶
                        * 50) * chaDays;
            }
        }
	       
        return pay1 + pay2 + pay3;
	    
	}
	
	
	//后台直接创建订单.
    @RequestMapping(value = "/create")
	@ResponseBody
    public BaseJsonBean create(@ModelAttribute Order t){
    	
    	BaseJsonBean baseJsonBean = new BaseJsonBean();
    	List<Integer> list = (List<Integer>)request.getSession().getAttribute("cart");
    	if(list == null || list.size() < 1) {
    		baseJsonBean.setMsg("出租车没有珠宝");
    		return baseJsonBean;
    	}
    	int totalMoney = 0;
    	List<Jewelry> jewelryList = jewelryMapper.selectCart(list);
    	for (Jewelry jewelry : jewelryList) {
    		totalMoney += jewelry.getPrice();
			if(jewelry.getOrderStatus() != BDZBConstants.JEWELRY_STATUS_WAITING) {
				baseJsonBean.setMsg("请移除出租车中不能租用的珠宝.");
				return baseJsonBean;
			}
		}
    	
    	
    	//判断用户是否真的存在
    	String phone = t.getPhone();
    	Tenant tenant = tenantMapper.getByPhone(phone);
    	if(tenant == null) {
    		baseJsonBean.setMsg("你输入手机号码的用户不存在。");
			return baseJsonBean;
    	}else {
    		if(tenant.getVip() == 0 || tenant.getVip() == 1) {
    			baseJsonBean.setMsg("非会员，请先办理会员手续。");
    			return baseJsonBean;
    		}
    	}
    	
    	CardSet carSet = cardSetMapper.getCardSetByVipLevel(tenant.getVip());
    	long chaTime =  t.getEndTime().getTime() - t.getStartTime().getTime();
    	Integer days =  (int) (chaTime % 86400000 == 0 ? chaTime / 86400000 : chaTime / 86400000 + 1);
    	Integer pay = pay(totalMoney / 10000, days, carSet.getWearTotalMoney(),1);
    	
    	//是否超过本人承受能力
    	if(pay > 0 && t.getExcessAmount() == null) {
    		baseJsonBean.setCode(BaseJsonBean.E1001);
    		baseJsonBean.setMsg(pay+"");
    		return baseJsonBean;
    	}
    	
    	//下单
    	t.setTenantid(tenant.getId());
    	t.settPhone(t.getPhone());//写入联系人手机号码，同会员自己。
    	t.setStatus(1);//直接跳过预约一步.如果是app.需要先进入预约状态.
    	Order order =   save_p(t);
    	OrderDetail od = null;
    	for (Integer string : list) {
    		od = new OrderDetail();
        	od.setJewelryid(string);
        	od.setOrderid(order.getId());
    		mapper.insertOrderDetail(od);
		}
    	//清空购物车
    	request.getSession().removeAttribute("cart");
    	
    	
    	
    	return baseJsonBean;
    }
    
	@Override
    @RequestMapping(value = "/save")
	@ResponseBody
    public Order save(@ModelAttribute Order t){
		return  save_p(t);
    }
	
	@Override
    @RequestMapping(value = "/update")
	@ResponseBody
	public BaseJsonBean update(Order t) {
		if(t.getStatus() != null) {
			if(t.getStatus() == 2 || t.getStatus() == 3) {//完成与取消
				
				//判断归还时候要不要交钱
				if(t.getStatus() == 2) {
				    Order dbOrder = mapper.get(t.getId());
				    Tenant tenant = tenantMapper.get(dbOrder.getTenantid());
					Map<?, ?> rsMap = mapper.sumPriceByOrderId(t.getId());
                    int totalPrice = 0;
                    if(rsMap != null ) {
                        totalPrice = ((Double) rsMap.get("totalPrice")).intValue();
                    }
                    CardSet carSet = cardSetMapper.getCardSetByVipLevel(tenant.getVip());
					long chaTime =  System.currentTimeMillis() - dbOrder.getEndTime().getTime();
					Integer days =  (int) (chaTime % 86400000 == 0 ? chaTime / 86400000 : chaTime / 86400000 + 1);
					Integer pay = pay(totalPrice / 10000, days, carSet.getWearTotalMoney(),2);
					if(pay > 0 && t.getOverdueAmount() == null) {
						bj.setMsg(pay+"");
						bj.setCode(BaseJsonBean.E1002);
						return bj;
					}
					t.setReturnTime(new Timestamp(System.currentTimeMillis()));
					
					//如果是余额转押金用户，需要把金额归还
					if(dbOrder.getDepositMethod() == 2) {
					    
					    //数据库操作 1.插入记录，2.更新余额
                        BalanceRecord br = new BalanceRecord();
                        br.setTenantId(tenant.getId());
                        br.setMethod("订单押金返还" + dbOrder.getId());
                        br.setAmount(dbOrder.getDeposit());
                        br.setBalance(tenant.getBalance() + dbOrder.getDeposit());
                        balanceRecordMapper.insert(br);
                        
                        Tenant tenantBR = new Tenant();
                        tenantBR.setId(tenant.getId());
                        tenantBR.setBalance(br.getBalance());
                        tenantMapper.update(tenantBR);
					}
					
				}else {
				    //取消订单
				    Order dbOrder = mapper.get(t.getId());
				    RestTest.cancelOrder(dbOrder.gettPhone()
                            , dbOrder.getId()+"");
				}
				
			}else if(t.getStatus() == 1) {//变成真正的借戴中.
				//先检查客户是否满足vip身份.
				Order dbOrder = mapper.get(t.getId());
				
				Tenant tenant = tenantMapper.get(dbOrder.getTenantid());
				if(tenant == null) {
				    bj.setMsg("客户不存在,请先让客户自己登记");
                    return bj;
				}else if(tenant.getVip() == 0) {
                    bj.setMsg("客户不是vip,请先办理");
                    return bj;
                }else {
                    
                    //0.查询订单总金额
                    Map<?, ?> rsMap = mapper.sumPriceByOrderId(t.getId());
                    
                    int totalPrice = 0;
                    if(rsMap != null ) {
                        totalPrice = ((Double) rsMap.get("totalPrice")).intValue();
                    }
                    //1.判断是否要多收钱
                    CardSet carSet = cardSetMapper.getCardSetByVipLevel(tenant.getVip());
                    long chaTime =  t.getEndTime().getTime() - t.getStartTime().getTime();
                    Integer days =  (int) (chaTime % 86400000 == 0 ? chaTime / 86400000 : chaTime / 86400000 + 1);
                    Integer pay = pay(totalPrice / 10000, days, carSet.getWearTotalMoney(),1);
                    
                    
                    //2是否超过本人承受能力
                    if(pay > 0 && t.getExcessAmount() == null) {
                        bj.setCode(BaseJsonBean.E1001);
                        bj.setMsg(pay+"");
                        return bj;
                    }
                    
                   //3 判断如果是余额转押金，是不是够
                    if(t.getDepositMethod() != null && t.getDepositMethod() == 2) {
                        if(tenant.getBalance() < t.getDeposit()) {
                            bj.setMsg("你的余额不够抵扣押金!");
                            return bj;
                        }
                        //数据库操作 1.插入记录，2.更新余额
                        BalanceRecord br = new BalanceRecord();
                        br.setTenantId(tenant.getId());
                        br.setMethod("订单押金" + t.getId());
                        br.setAmount(-t.getDeposit());
                        br.setBalance(tenant.getBalance() - t.getDeposit());
                        balanceRecordMapper.insert(br);
                        
                        Tenant tenantBR = new Tenant();
                        tenantBR.setId(tenant.getId());
                        tenantBR.setBalance(br.getBalance());
                        tenantMapper.update(tenantBR);
                    }
                    
					//t.setStartTime(new java.sql.Timestamp(System.currentTimeMillis()));不是自动生成是设置的
					RestTest.orderSuccess(t.gettPhone()
							, dbOrder.getId(),t.getEndTime());
				}
			}
		}
		t.setLocked(0);//解锁
		return update_p(t);
	}
    
	@Override
    @RequestMapping(value = "/delete/{id}")
	@ResponseBody
    public Integer delete(@PathVariable("id") Integer id){
		
	    delete_p(id);
		
		//删除detail中的数据
		mapper.deleteDetail(id);
		
		return 1;
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
	
	public static void main(String[] args) {
		
		
		Integer a = 1;
		System.out.println(a == 1);
		
	}
	@Override
	@RequestMapping(value = "/badd")
	public ModelAndView badd() {
		return get_p();
	}

	@Override
	@RequestMapping(value = "/bedit/{id}")
	public ModelAndView bupdate(@PathVariable("id") Integer id) {
	    ModelAndView modelAndView = get_p(id);
        
        Order order = mapper.get(id);
        Tenant tenant = tenantMapper.get(order.getTenantid());
        List<OrderDetail> orderDetailList = mapper.selectJewelry(id);
        int totalPrice = 0;
        for (OrderDetail orderDetail : orderDetailList) {
            totalPrice = totalPrice + orderDetail.getPrice();
        }
        ((Order)modelAndView.getModel().get("edit")).setDeposit(totalPrice);
        modelAndView.addObject("list", orderDetailList);//查询结果
        modelAndView.addObject("order", order);//查询结果
        modelAndView.addObject("tenant", tenant);//查询结果
	    
		return modelAndView;
	}
	
	@RequestMapping(value = "/chart1")
	@ResponseBody
    public Chart chart1(@RequestParam("yearNumber") Integer yearNumber){
    	
    	Map<String, Integer> map = new HashMap<String, Integer>();
        
    	map.put("yearNumber", yearNumber);
    	
    	
    	List<Map> list =  mapper.selectChart1(map);
    	
    	Chart chart = new Chart();
    	List<Object> categories = new ArrayList<Object>();
    	List<Object> data = new ArrayList<Object>();
    	
    	
    	
    	int[] yuyueArray = new int[12];
    	int[] jiedaiArray = new int[12];
    	
    	
    	for(int i =0 ;i < 12;i++) {
    	    for (Map<?, ?> object : list) {
                if((Integer)object.get("monthNumber") == i+1) {
                    yuyueArray[i] = yuyueArray[i] + ((Long)object.get("statuscount")).intValue();
                    if((Integer)object.get("status") == 1 || (Integer)object.get("status") == 2) {
                        jiedaiArray[i] = jiedaiArray[i] + ((Long)object.get("statuscount")).intValue();
                    }
                }
            }
    	}
    	
    	for(int i = 1 ;i < 12;i++) {
    	    yuyueArray[i] = yuyueArray[i] + yuyueArray[i-1];
    	    jiedaiArray[i] = jiedaiArray[i] + jiedaiArray[i-1];
        }

    	data.add(yuyueArray);
    	data.add(jiedaiArray);
    	
    	chart.setData(data);
    	chart.setCategories(categories);
    	
    	return chart;
    }
	
}