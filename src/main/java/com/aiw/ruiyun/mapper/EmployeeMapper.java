package com.aiw.ruiyun.mapper;

import java.util.List;

import com.aiw.mapper.BaseMapper;
import com.aiw.ruiyun.entity.Employee;

/** 
 * 说明：ruiyun.employee
 * 创建人：aiw
 * 创建时间：2017-05-19
 */

public interface EmployeeMapper extends BaseMapper<Employee> {

	public void batchSave(List<Employee> list);
	
}
