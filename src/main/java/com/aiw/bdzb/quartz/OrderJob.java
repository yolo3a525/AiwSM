package com.aiw.bdzb.quartz;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.RestTest;
import com.aiw.bdzb.entity.Order;
import com.aiw.bdzb.mapper.OrderMapper;

public class OrderJob {
    
    Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	OrderMapper orderMapper;
	
	public void execute() {
		
		//1.获取预约要到期的用户提醒他们
		List<Order> list  = orderMapper.selectAppointmentRemind();
		Order order = null;
		if(list != null && list.size() > 0) {
		    for (Order order2 : list) {
		          //短信通知
	            
	            
	             //修改成锁定状态
	             order = new Order();
	             order.setId(order2.getId());
	             order.setLocked(1);
	             orderMapper.update(order);
            }
		     
		}else {
		    logger.info("没有预约快到的用户");
		}
		
		//2
        list  = orderMapper.selectorderAppointment2HourBefore();
        if(list != null && list.size() > 0) {
            for (Order order2 : list) {
                 RestTest.orderAppointment2HourBefore(order2.gettPhone(), order2.getId()+"");
            }
        }else {
            logger.info("没有预约快到的用户预约2小时前提醒");
        }
		
	}
}
