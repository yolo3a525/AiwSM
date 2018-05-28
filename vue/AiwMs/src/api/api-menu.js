import axios from 'axios';
import instance from './base'


let menu = 'menu';
export const getMenu = params => { return instance.get(`${menu}` + '/get/' + params.id); };
export const getMenuList = params => { return instance.get(`${menu}` + '/tree', { params: params }); };
export const getMenuListPage = params => { return instance.get(`${menu}`+ '/tree', { params: params }); };
export const removeMenu = params => { return instance.post(`${menu}` + '/delete/' + params.id); };
export const editMenu = params => { return instance.post(`${menu}` + '/update', params); };
export const addMenu = params => { return instance.post(`${menu}` + '/save', params); };
