import axios from 'axios';
import instance from './base'


let role = 'role';
export const getRole = params => { return instance.get(`${role}` + '/get/' + params.id); };
export const getRolePrivileges = params => { return instance.get(`${role}` + '/privilegeTree/' + params.id); };
export const getRoleList = params => { return instance.get(`${role}`+ '/list', { params: params }); };
export const getRoleListPage = params => { return instance.get(`${role}`+ '/list', { params: params }); };

export const removeRole = params => { return instance.post(`${role}` + '/delete/' + params.id); };
export const editRole = params => { return instance.post(`${role}`+ '/update', params); };
export const updateRolePrivilege = params => { return instance.post(`${role}` + '/savePrivilege', params); };
export const addRole = params => { return instance.post(`${role}`+ '/save', params); };
