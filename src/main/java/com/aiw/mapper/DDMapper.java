package com.aiw.mapper;

import org.apache.ibatis.annotations.Param;

import com.aiw.entity.DD;

public interface DDMapper extends BaseMapper<DD> {

	DD get(@Param("code")String code);
	Integer delete(@Param("dgCode") String dgCode,@Param("ddItem") String ddItem);

}
