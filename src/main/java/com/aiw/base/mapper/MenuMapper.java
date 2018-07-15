package com.aiw.base.mapper;

import java.util.List;

import com.aiw.base.entity.Menu;

public interface MenuMapper extends BaseMapper<Menu> {

	List<Menu> selectByUserId(Integer id);

	
	
	

}
