package com.aiw.mapper;

import java.util.List;

import com.aiw.entity.Menu;

public interface MenuMapper extends BaseMapper<Menu> {

	List<Menu> selectByUserId(Integer id);

	
	
	

}
