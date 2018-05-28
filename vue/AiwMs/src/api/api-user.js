import axios from 'axios';
import instance from './base'

let user = 'user';
export const getUser = params => { return instance.get(`${user}` + '/get/' + params.id); };
export const getUserRoles = params => { return instance.get(`${user}` + '/roleTree/' + params.id); };
export const getUserList = params => { return instance.get(`${user}`+ '/list', { params: params }); };
export const getUserListPage = params => { return instance.get(`${user}`+ '/list', { params: params }); };

export const removeUser = params => { return instance.post(`${user}` + '/delete/' + params.id); };
export const batchRemoveUser = params => { return instance.post(`${user}/delete/batch`, params); };
export const editUser = params => { return instance.post(`${user}`+ '/update', params); };
export const updateUserRole = params => { return instance.post(`${user}` + '/saveRole', params); };
export const addUser = params => { return instance.post(`${user}`+ '/save', params); };

export const requestLogin = params => { return instance.post('login', params); };
