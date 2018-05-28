package com.aiw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aiw.entity.DG;

public interface DGMapper extends BaseMapper<DG> {

	DG get(@Param("code")String code);

	Integer delete(String code);
	
	List<DG> findDG();
}
