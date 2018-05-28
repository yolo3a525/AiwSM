package com.aiw.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aiw.entity.Role;
import com.aiw.entity.User;

public interface UserMapper extends BaseMapper<User> {

	
	//查询角色,返回所有角色列表，主要看是否匹配上了。
	List<Role> queryRoleByUserId(Integer id);
	
	List<Role> queryRoleByUserIds(@Param("ids")List<Integer> ids);
	
	//仅仅返回真正存在的角色。
	List<Role> queryCheckedRoleByUserId(Integer id);
	
	//删除角色关系表
	void deleteUserPrivilegeByUserId(Integer id);
	
	void addUserRole(Map<?, ?> map);
	
	

}
