package com.aiw.bdzb.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aiw.base.mapper.BaseMapper;
import com.aiw.bdzb.entity.Order;
import com.aiw.bdzb.entity.OrderDetail;

/** 
 * 说明：bdzb.order
 * 创建人：aiw
 * 创建时间：2017-05-25
 */

public interface OrderMapper extends BaseMapper<Order> {

	public List<OrderDetail> selectJewelry(Integer id);
	
	public int insertOrderDetail(OrderDetail orderDetail);
	
	public int deleteDetail(Integer id);

	public void removeJewelryFromOrderList(Map<?, ?> m);
	
	List<Map> selectChart1(@Param("t") Map<?, ?> t);

	List<Order> selectAppointmentRemind();
	List<Order> selectorderAppointment2HourBefore();
	List<Order> selectorderYuqi();
	
	
	
	List<Map> orderSummary(@Param("t") Map<?, ?> t);
	
	Map<?, ?> sumPriceByOrderId(@Param("id")Integer id);
	
}
