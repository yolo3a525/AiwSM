package com.aiw.bdzb.quartz;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.RestTest;
import com.aiw.bdzb.entity.Order;
import com.aiw.bdzb.entity.Tenant;
import com.aiw.bdzb.mapper.OrderMapper;
import com.aiw.bdzb.mapper.TenantMapper;

public class DayJob {
    Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	OrderMapper orderMapper;
	
	
	@Autowired
	TenantMapper tenantMapper;
	
	public void execute() {
	    
	    
		
		//1.生日
		List<Tenant> list  = tenantMapper.shengri();
		if(list != null && list.size() > 0) {
		    for (Tenant tenant : list) {
		        RestTest.shengri(tenant.getPhone());
            }
		}else {
		    logger.info("没有过生日的");
		}
		//1.逾期
        List<Order> orderList  = orderMapper.selectAppointmentRemind();
        if(list != null && list.size() > 0) {
            for (Order order : orderList) {
                  //短信通知
                  if(order.getChaochuDays() == -1) {
                      RestTest.orderYuqiBefore1Day(order.gettPhone(), order.getId()+"");
                  }else if(order.getChaochuDays() == 0){
                      RestTest.orderYuqiDay(order.gettPhone(), order.getId()+"");
                  }else if(order.getChaochuDays() > 0) {
                      RestTest.orderYuqiDayAfter(order.gettPhone(), order.getId()+"",order.getChaochuDays()+"");
                  }
            }
        }else {
            logger.info("没有预约快到的用户");
        }
		
	}
}
