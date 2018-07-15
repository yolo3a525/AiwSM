package com.aiw.bdzb.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aiw.base.mapper.BaseMapper;
import com.aiw.bdzb.entity.Jewelry;

/** 
 * 说明：bdzb.jewelry
 * 创建人：aiw
 * 创建时间：2017-05-25
 */

public interface JewelryMapper extends BaseMapper<Jewelry> {

	public void saveMovie(Jewelry jewelry);
	public List<Jewelry> selectCart(List<?> list);
	
	
	public Integer updateImageFlag(String sid);
	public Integer updateMovieFlag(String sid);
	
	public List<Jewelry> mycart(String phone);
	
	public int mycartUpdate(@Param("map")Map<String,Object> map);
	
	public int addToCart(Map<?, ?> map);
	
	/**
	 * 清空租戴车
	 * @param phone
	 */
	public void clearCart(@Param("phone")String phone,@Param("ids")List<?> list);
	
	
	public void deleteJewelryFromCart(Map<?, ?> map);
	
	public void batchSave(List<Jewelry> list);
	
	//批量上架
	public void batchup(Map<?, ?> map);
	
	
	
	public Integer selectCount(Map<?, ?> map);
	public Integer selectCountGys();
	//统计待上架
	public Integer jewelryselectCountShowStatus0(Map<?, ?> map);
	//可借戴
	public Integer jewelryselectCountStatusF1(Map<?, ?> map);
	//预约中
	public Integer selectCountStatus20(Map<?, ?> map);
	//租赁中
	public Integer selectCountStatus21(Map<?, ?> map);
	
	public List<Jewelry> dayNew3();
	public List<Jewelry> shouyetuijian();
	public List<Jewelry> dapeituijian(List<?> list);
	
	
	
}
