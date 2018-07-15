package com.aiw.ruiyun.mapper;

import java.util.List;

import com.aiw.base.mapper.BaseMapper;
import com.aiw.ruiyun.entity.Salary;

/** 
 * 说明：ruiyun.salary
 * 创建人：aiw
 * 创建时间：2017-05-19
 */

public interface SalaryMapper extends BaseMapper<Salary> {

	public void batchSave(List<Salary> list);

}
