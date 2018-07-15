package com.aiw.bdzb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aiw.base.mapper.BaseMapper;
import com.aiw.bdzb.entity.RechargeCard;

/** 
 * 说明：bdzb.rechargecard
 * 创建人：aiw
 * 创建时间：2017-10-30
 */

public interface RechargeCardMapper extends BaseMapper<RechargeCard> {

	public void batchCreate(List<RechargeCard> list);
	
	public RechargeCard getByCardId(@Param("no")String no);

}
