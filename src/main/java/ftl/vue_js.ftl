import axios from 'axios';
import instance from './base'

let component = '${componentLower}';
export const get${component} = params => { return instance.get(`${component}` + '/get/' + params.id); };
export const get${component}List = params => { return instance.get(`${component}`+ '/list', { params: params }); };
export const get${component}ListPage = params => { return instance.get(`${component}`+ '/list', { params: params }); };

export const remove${component} = params => { return instance.post(`${component}` + '/delete/' + params.id); };
export const batchRemove${component} = params => { return instance.post(`${component}/delete/batch`, params); };
export const edit${component} = params => { return instance.post(`${component}`+ '/update', params); };
export const add${component} = params => { return instance.post(`${component}`+ '/save', params); };