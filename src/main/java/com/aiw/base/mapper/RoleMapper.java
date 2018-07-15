package com.aiw.base.mapper;

import java.util.List;
import java.util.Map;

import com.aiw.base.entity.Privilege;
import com.aiw.base.entity.Role;

public interface RoleMapper extends BaseMapper<Role> {

	
	//查询权限
	List<Privilege> queryPrivilegeByRoleId(Integer id);
	
	//删除权限关系表
	void deleteRolePrivilegeByRoleId(Integer id);
	
	void addRolePrivilege(Map<?, ?> map);
	

}
