import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)
import * as util from '../utils/util'
import axios from 'axios'

const Login = r => require.ensure([], () => r(require('@/pages/login/Login')), 'login');
const Home = r => require.ensure([], () => r(require('@/pages/home/Home')), 'home');
const BOverStore = r => require.ensure([], () => r(require('@/pages/bOverrunStore/BOverrunStore')), 'bOverStore');


const routes =[
  {
    path: '/',
    name: 'Login',
    component: Login
  }, {
    path: '/home',
    name: 'Home',
    component: Home,
    children: [
      {
        path: 'bOverStore',
        name: 'BOverStore',
        component: BOverStore
      }
    ]
  }]

const router = new Router({
  routes: routes
})
//路由跳转的验证
router.beforeEach((to, from, next) => {
  //验证：避免药店和连锁互相跳转页面、根据权限
  // console.log(to, "to--");
  // console.log(from, "from--");

  //已登录 重定向后用户信息被冲掉
  // let loadUrl = sessionStorage.getItem("prescriptionUrl");
  // let url = sessionStorage.getItem("loadFileUrl");
  // if (!!!url && !!!loadUrl) {
  //   if (to.name === 'Login') {
  //     next();
  //   } else {
  //     router.push({ name: 'Login' })
  //   }
  // } else {
  let session = util.getSession();
  if (session != null) {
    axios({
      methods: "post",
      url: "api/user/userInfo?timestamp="+new Date().getTime(),
      contentType: 'application/json;utf-8'
    }).then(function (res) {
      console.log('routerBefore res----', global.userInfo)
      if (res.status == "200" && res.data.code == "0") {
        //存在sessionStorage
        sessionStorage.clear();
        for (let item in res.data.data) {
          sessionStorage.setItem(item, res.data.data[item]);
        }
        // util.global.userInfo = res.data.data;
        let userInfo = res.data.data;
        if (userInfo.length == 0) {
          //未登录
          console.log("未登录", userInfo)
          if (to.name === 'Login') {
            next();
          } else {
            router.push({ name: 'Login' })
          }
        } else {
          if (from.path === '/' && to.path.length <= 1) {
            router.push({ name: 'Home' })
          } else {
              next();
          }
        }
      }
    }).catch((err) => {
      console.log(err, "router跳转-----err------------");
      next();
    })
  }
  // }
  next();
})



export default router
