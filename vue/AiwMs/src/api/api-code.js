import axios from 'axios';
import instance from './base'
//import qs from 'qs'; axios默认json方式提交，如果单独使用post提交时考虑转换


let component = 'code';
// let config = {
//     headers: {
//         'Content-type': 'application/x-www-form-urlencoded'
//     }
// }
//export const create = params => { return instance.post(`${component}`+ '/create', qs.stringify(params,{arrayFormat: 'brackets'}),config); };

export const create = params => { return instance.post(`${component}`+ '/create',params);};

