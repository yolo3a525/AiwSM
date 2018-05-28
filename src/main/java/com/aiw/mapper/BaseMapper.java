package com.aiw.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aiw.entity.BaseEntity;
import com.aiw.entity.Page;

public interface BaseMapper<T extends BaseEntity> {
	
	//新增
	int insert(T t);
	
	
	//删
	int delete(T t);
	int delete(int id);
	
	
	//修改
	int update(T t);
	
	
	//查
	List<T> select(Map<?, ?> m);
	List<T> select(T t);
	T get(@Param("id")Integer id);
	List<T> select();
	
	
	//page,form对象 ,m参数
	List<T> selectPage(@Param("page")Page p,@Param("t")T t,@Param("m")Map<?, ?> m);
	List<T> selectPage(@Param("page")Page p,@Param("t")T t);
	List<T> selectPage(@Param("page")Page p);
	//翻页查
	//List<T> selectpage(@Param("page")Page page,@Param("t") T t,@Param("q")Map map);
	
	
	Integer selectCount(@Param("t") Map<?, ?> t);
}
