package com.aiw.dominos.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aiw.dominos.entity.Info;
import com.aiw.entity.Page;
import com.aiw.mapper.BaseMapper;

/** 
 * 说明：dominos.info
 * 创建人：aiw
 * 创建时间：2017-10-19
 */

public interface InfoMapper extends BaseMapper<Info> {

	
	List<Info> selectJoinPage(@Param("page")Page p,@Param("t")Info t);
}
