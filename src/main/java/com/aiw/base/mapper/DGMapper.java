package com.aiw.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aiw.base.entity.DG;

public interface DGMapper extends BaseMapper<DG> {

	DG get(@Param("code")String code);

	Integer delete(String dgCode);
	
	List<DG> findDG();
}
