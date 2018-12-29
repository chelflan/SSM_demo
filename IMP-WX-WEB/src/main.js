// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

Vue.config.productionTip = false
// 解决浏览器兼容性问题
import 'babel-polyfill'

// 引入iview
import iView from 'iview'  //引入ivew库
import 'iview/dist/styles/iview.css'  // 使用 iview CSS
Vue.config.productionTip = false
Vue.use(iView)

//引入axios
import axios from 'axios';

//设置为true时生产模式，若没有设置则为开发环境
axios.defaults.withCredentials = true;
axios.defaults.headers.common['Authorization'] = '';
axios.defaults.headers.common['Cache-Control'] = 'no-cache';
axios.defaults.headers.common['Cache-Control'] = 'no-store';
axios.defaults.headers.common['Cache-Control'] = 'must-revalidate';

axios.interceptors.request.use(
  (config) => {
    console.log(config,"config----")
    return config
  }
)

axios.interceptors.response.use((res) => {
  let data = res.data;
  //未登录
  if(data.code == 20001) {
    router.replace({ path: '/'})
  } else {
    return Promise.resolve(res);
  }
}, (error) => {
  console.log(error,"axios-response-err");
  return Promise.reject(error);
})

Vue.prototype.$http = axios;

let startApp = function () {
  axios.get('./static/config/config.json', {
    headers: {
      'Cache-Control': 'no-cache'
    }
  }).then((res) => {
    console.log(res,"res-----");
    axios.defaults.baseURL = res.data.BASE_URL;
    axios.defaults.headers.common['Cache-Control'] = "no-cache";
    /* eslint-disable no-new */
    new Vue({
      el: '#app',
      router,
      components: { App },
      template: '<App/>'
    })
  }).catch((error) => {
    console.error(error,'请求本地数据错误')
  })
}

startApp();
