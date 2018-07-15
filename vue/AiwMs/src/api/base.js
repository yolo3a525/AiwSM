import axios from 'axios';
import store from '../vuex/store'
import { Loading, Message } from 'element-ui'

// 超时时间
axios.defaults.timeout = 5000
axios.defaults.withCredentials=true
/*
axios.defaults.headers = {
    'Content-type': 'application/x-www-form-urlencoded'
}
*/
//axios.defaults.headers = {"Access-Control-Allow-Origin": "*"}

var instance = axios.create({
  baseURL:'http://localhost:8080/api/',
});


// http请求拦截器
var loadinginstace
instance.interceptors.request.use(config => {
 // element ui Loading方法
 //loadinginstace = Loading.service({ fullscreen: false })
 store.state.loading = true;
 return config
}, error => {
 //loadinginstace.close()
store.state.loading = false;
 Message.error({
 message: '加载超时'
 })
 return Promise.reject(error)
})


// http响应拦截器
instance.interceptors.response.use(data => {// 响应成功关闭loading
  store.state.loading = false;
  //debugger;
 //loadinginstace.close()
 if(data.data.code != 0){
  if(data.data.code == 100){
    location.href = "/#/login";
 } else{
  Message.error({
    message: data.data.msg
  })
  return Promise.reject(data.data.msg)
 }
  
 }else{
   return data
}
}, error => {
store.state.loading = false;
 //loadinginstace.close()
 if(error.response.status == 403 && error.response.data.message == 'Access Denied'){
    location.href = "/#/login";
 }else if(error.response.status == 403 && error.response.data.message == 'Access is denied'){
   Message.error({
     message: '權限'
   })
 }else{
   Message.error({
     message: '加载失败'
   })
 }
 return Promise.reject(error)
})

export default instance;
