package com.aiw.bdzb.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aiw.base.mapper.BaseMapper;
import com.aiw.bdzb.entity.Tenant;

/** 
 * 说明：bdzb.tenant
 * 创建人：aiw
 * 创建时间：2017-05-26
 */

public interface TenantMapper extends BaseMapper<Tenant> {

	
	Tenant getByPhone(String phone);
	List<Map> selectChart1(@Param("t") Tenant t);
	void updateRecharge(Map<?, ?> m);
	Integer getTotalDeposit(Integer id);
	
	List<Tenant> selectExport(@Param("t")Tenant t);
	List<Tenant> shengri();
	
	
}
