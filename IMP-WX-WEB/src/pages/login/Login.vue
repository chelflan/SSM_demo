<template>
  <div class="login-page">
    <img src="../../assets/login_back.png" class="login-back">
    <div class="login-box">
      <h3>微问诊管理平台</h3>
      <h4>(扫码咨询)</h4>
      <!----------登录部分-> 输入用户名 s---------->
      <Form ref="formInline" :model="formInline" :rules="ruleInline" @keyup.enter.native ="handleSubmit('formInline')">

        <FormItem prop="userAcct">
          <Input type="text" v-model="formInline.userAcct" placeholder="请输入用户名" size="large">
          <Icon type="md-person" slot="prepend"></Icon>
          </Input>
        </FormItem>
        <!--为了消除360浏览器兼容模式自动填充表单-->
        <div v-if="isIeBrower">
          <FormItem v-show="false">
            <Input type="text" placeholder="请输入用户名" size="large"/>
          </FormItem>

          <FormItem v-show="false">
            <Input type="password" size="large">
            </Input>
          </FormItem>
        </div>

        <!----------登录部分-> 输入密码 ---------->
        <FormItem prop="password">
          <Input type="password" v-model="formInline.password" placeholder="请输入密码" size="large">
          <Icon type="md-lock" slot="prepend"></Icon>
          </Input>
        </FormItem>

        <FormItem>
          <Button type="primary" :loading="loading"  @click="handleSubmit('formInline')" long>
            <span v-if="!loading">登录</span>
            <span v-else>登录中...</span>
          </Button>
        </FormItem>
      </Form>
    </div>
    <div class="copyright">版权所有：四川骏逸富顿科技有限公司 ICP证：蜀ICP备12018915号-1</div>
  </div>
</template>

<script>
  import * as util from '../../utils/util'
  export default {
    name: "Login",
    data(){
      return {
        isIeBrower: false,
        loading: false,
        isShow: true,
        formInline: {
          userAcct: '',
          password: ''
        },
        ruleInline: {
          userAcct: [
            {required: true, message: '请输入用户名', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {type: 'string', min: 6, message: '密码至少为6位', trigger: 'blur'}
          ]
        }
      }
    },
    created(){
      //判断浏览器的类型
      if(window.navigator.userAgent.indexOf("Trident") != -1){
        this.isIeBrower = true;
        //自动刷新下
        if(!!sessionStorage.getItem("ieControl") && sessionStorage.getItem("ieControl") == "ieControl"){
          sessionStorage.setItem("ieControl",null);
          location.reload();
        }
      }
    },
    methods:{
      handleSubmit(name) {
        this.$refs[name].validate((valid) => {
          if (valid) {
            this.login();
          } else {
            this.$Message.error('请输入正确的用户名和密码！');
          }
        })
      },
      //登录接口
      login(){
        let vm = this;
        this.loading = true;
        this.$http({
          method:'post',
          url:'api/user/login',
          data:{
            userAcct:this.formInline.userAcct,
            pwd:this.formInline.password
          }
        }).then(function(res){
          console.log(res,"res-----")
          if(res.status == '200' && res.data.code == '0'){

            util.global.userInfo = res.data.data;
            util.global.impWxid = res.data.impWxid;
            util.deleteSession()
            util.saveSession(res.data.impWxid)
            // //存在sessionStorage
            sessionStorage.clear();
            for(let item in res.data.data){
              sessionStorage.setItem(item,res.data.data[item]);
            }
            vm.$router.push("/home");

          }else{
            vm.$Message.error(res.data.msg);
          }
          vm.loading = false;
        }).catch(function(err){
          console.log(err,"err")
          vm.$Message.error("网络错误!");
          vm.loading = false;
        })
      }
    }
  }
</script>

<style lang="less" scoped>
  .login-page{
    display: flex;
    justify-content: center;
    align-items: center;
    .login-back{
      width: 100%;
      height: 100%;
      position: absolute;
      top: 0;
      left: 0;
      z-index: -9999;
    }
    .login-box{
      position: fixed;
      top:40%;
      transform: translateY(-50%);
      align-self: center;
      width: 400px;
      height: 270px;
      background-color: #fff;
      border-radius: 5px;
      padding: 20px 25px;
      text-align: center;
      h3{
        display: inline-block;
        letter-spacing: 2px;
        font-size: 34px;
        font-family: "latoregular","Trebuchet MS",Arial,Helvetica,sans-serif;
      }
      h4{
        display: inline-block;
        margin-bottom: 5px;
        font-size: 20px;
      }
    }
    .copyright{
      position: absolute;
      margin-left: 0;
      margin-right: 0;
      bottom: 16px;
      width: 100%;
      height: 20px;
      font-size: 16px;
      text-align: center;
      font-family: "latoregular","Trebuchet MS",Arial,Helvetica,sans-serif;
      color: #000000;
    }
  }

</style>
