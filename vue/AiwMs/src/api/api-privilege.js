import axios from 'axios';
import instance from './base'


let privilege = 'privilege';
export const getPrivilege = params => { return instance.get(`${privilege}` + '/get/' + params.id); };
export const getPrivilegeList = params => { return instance.get(`${privilege}`+ '/list', { params: params }); };
export const getPrivilegeListPage = params => { return instance.get(`${privilege}`+ '/list', { params: params }); };
export const removePrivilege = params => { return instance.post(`${privilege}` + '/delete/' + params.id); };
export const editPrivilege = params => { return instance.post(`${privilege}`+ '/update', params); };
export const addPrivilege = params => { return instance.post(`${privilege}`+ '/save', params); };
