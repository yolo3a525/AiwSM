import Login from './views/Login.vue'
import NotFound from './views/404.vue'


let routes = [
    {
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },
    {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    //{ path: '/main', component: Main },
    // {    
    //     path: '/',
    //     component: Home,
    //     name: '人员管理',
    //     iconCls: 'fa fa-bar-chart',
    //     children: [
    //         { path: '/user', component: user3, name: '用户管理' },
    //         { path: '/role', component: role, name: '角色管理' },
    //         { path: '/privilege', component: privilege, name: '权限管理' },
    //         { path: '/menu', component: menu, name: '菜单管理' },
    //     ]
    // },
    // {
    //     path: '/',
    //     component: Home,
    //     name: 'Charts',
    //     iconCls: 'fa fa-bar-chart',
    //     children: [
    //         { path: '/echarts', component: echarts, name: 'echarts' }
    //     ]
    // },
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;
