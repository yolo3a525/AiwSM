import babelpolyfill from 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
//import './assets/theme/theme-green/index.css'

import VueRouter from 'vue-router'
import store from './vuex/store'
import Vuex from 'vuex'
//import NProgress from 'nprogress'
//import 'nprogress/nprogress.css'

import routes from './routes'

import 'font-awesome/css/font-awesome.min.css'

Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.use(Vuex)

//NProgress.configure({ showSpinner: false });

const router = new VueRouter({
  routes
})
router.options.routes = [];
if(localStorage.getItem('menu') != null && localStorage.getItem('menu') != "undefined"){
  let menu = JSON.parse(localStorage.getItem('menu'))
  console.log(menu)
  if (menu) {
    var menu2 = null;
    var node = null;
    for(var i =0;i < menu.length;i++){
       node = {
        path: '/',
        component: require('./views/Home.vue'),
        name: menu[i].name,
        iconCls: 'fa fa-bar-chart',
        children: []
      }
      for(var k = 0; k < menu[i].children.length;k++){
        menu2 = menu[i].children[k];
        //node.children.push({ path: '/'+ menu2.url, component:  require('./views/user/'+menu2.url+'.vue'), name: menu2.name })
        //if(k == 0 && i == 0){
          node.children.push({ path: '/'+menu2.url, component:  require('./views/'+menu2.url+'.vue'), name: menu2.name })
        //}else{
          //node.children.push({ path: '/'+ menu2.url, component:  require('./views/user/'+menu2.url+'.vue'), name: menu2.name })
        //}
        
      }
      router.options.routes.push(node);
    }
    router.addRoutes(router.options.routes);
  }
}


router.beforeEach((to, from, next) => {
  //debugger;
  //NProgress.start();
  if (to.path == '/login') {
    localStorage.removeItem('user');
    localStorage.removeItem('menu');
  }
  let user = JSON.parse(localStorage.getItem('user'));
  if (!user && to.path != '/login') {
    next({ path: '/login' })
  } else {
    next()
  }
})

new Vue({
  //el: '#app',
  //template: '<App/>',
  router,
  store,
  //components: { App }
  render: h => h(App)
}).$mount('#app')








//router.afterEach(transition => {
//NProgress.done();
//});
