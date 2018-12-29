<template>
  <Layout  style="height: 100%;background-color: #ccc;">

    <Sider ref="side1" hide-trigger collapsible :collapsed-width="78" v-model="isCollapsed">
      <Logo :platName="platName" :isCollapsed = "isCollapsed" :style="{height:'50px'}"></Logo>
      <Menu :active-name="currentActiveName" theme="dark" width="auto" :class="menuitemClasses">
        <div @click="toBOverStore">
          <MenuItem name="1-1" to="/home/bOverStore">
            <Icon type="md-medkit" />
            <span v-show="!isCollapsed">被限制药店配置</span>
          </MenuItem>
        </div>
      </Menu>
    </Sider>

    <Layout :style="{position:'relative',backgroundColor:'#fff'}">

      <Header  class="layout-header-bar">
        <Icon @click.native="collapsedSider" :class="rotateIcon" :style="{margin: '0 20px'}" type="md-menu" size="24"></Icon>
        <navbar-user :userName="userName" class="user-setting"></navbar-user>
      </Header>

      <Content :style="{height:'100%',backgroundColor:'#fff'}">

        <Breadcrumb :style="{height:'50px',lineHeight:'50px',backgroundColor:'#ecf0f5'}">
          <BreadcrumbItem>
            <Icon type="md-home" size="20" style="vertical-align: text-bottom"/>
            主页
          </BreadcrumbItem>
          <!--<BreadcrumbItem>{{currentMenu}}</BreadcrumbItem>-->
          <BreadcrumbItem>{{currentMenuInfo}}</BreadcrumbItem>
        </Breadcrumb>

        <Content :style="{ background: '#fff',padding:'5px',borderTop:'3px solid #d2d6de',minHeight: '83.5%'}">
          <router-view></router-view>
        </Content>

        <!--<Footer :style="{background:'#e3ebed',textAlign:'center',margin:'8px 0 0 0',width:'100%',clear:'both',height:'9%'}">-->
          <!--<div :style="{float:'left'}">-->
            <!--<strong >Copyright &copy; 2014-2015 <a href="http://www.cdfortis.com" :style="{color:'#3c8dbc'}">富顿科技</a>.</strong> All rights reserved.-->
          <!--</div>-->
          <!--<div :style="{float:'right'}"><b>Version</b> 2.2.0</div>-->
        <!--</Footer>-->

      </Content>

    </Layout>
  </Layout>
</template>

<script>
  import Logo from '../../components/Logo'
  // import UserPanel from '../../components/UserPanel'
  import NavbarUser from '../../components/NavbarUser'

  export default {
    name: "home",
    components:{
      Logo:Logo,
      NavbarUser:NavbarUser
    },
    data () {
      return {
        platName:"",
        currentActiveName:'1-1',
        isCollapsed: false,
        userName:'测试名',
        avaLogo:"",
        currentMenu:"药品管理",
        currentMenuInfo:"药品信息"
      }
    },
    created(){

      //在页面刷新时信息保存到sessionStorage里
      // window.addEventListener("beforeunload",()=>{
      //   console.log(document.location.href,"document.location.href=====")
      //   let currentInfo = {
      //     "currentActiveName":this.currentActiveName,
      //     "address":document.location.href.split("#")[1]
      //   }
      //   if(currentInfo.address != '/' && currentInfo.address.indexOf('chainHome') == -1) {
      //     sessionStorage.setItem("currentInfo",JSON.stringify(currentInfo));
      //   }
      // })
      // document.title = '药店后台'
    },
    computed: {
      rotateIcon () {
        return [
          'menu-icon',
          this.isCollapsed ? 'rotate-icon' : ''
        ];
      },
      menuitemClasses () {
        return [
          'menu-item',
          this.isCollapsed ? 'collapsed-menu' : ''
        ]
      }

    },
    mounted(){
      // this.initUser();
      //
      // if(!!sessionStorage.getItem("currentInfo")){
      //   let data = JSON.parse(sessionStorage.getItem("currentInfo"));
      //   console.log(data,"data------")
      //   this.currentActiveName = data.currentActiveName;
      //   this.$router.push(data.address);
      //   switch (this.currentActiveName) {
      //     case "1-1": this.drugManage();break;
      //     case "1-2": this.prescriptionCheck();break;
      //     case "1-3": this.prescriptionPrintHis();break;
      //     case "1-4": this.prescriptionPrintSeven();break;
      //   }
      // }else{
      //   this.drugManage();
      //   this.$router.push("/home/bOverStore");
      // }
    },
    created(){
      this.toBOverStore();
      this.$router.push("/home/bOverStore");
    },
    methods: {
      initUser() {
        let vm = this;
        this.$http({
          methods: "post",
          url: "api/user/userInfo?timestamp="+new Date().getTime(),
          contentType: "application/json;utf-8"
        })
          .then(function(res) {
            console.log(res,"drugHome----",);
            if (res.status == "200" && res.data.code == "0") {
              global.userInfo = res.data.data;
              vm.userName = global.userInfo.userName;
            }
          })
          .catch((err) => {
            console.log(err,'drugHome-----')
          });
      },
      toBOverStore(){
        this.currentMenuInfo="被限制药店配置";
        this.currentActiveName = "1-1";
      },
      collapsedSider () {
        this.$refs.side1.toggleCollapse();
      }
    }
  }
</script>


<style lang="less" scoped>
  .layout{
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
    border-radius: 4px;
    overflow: hidden;
    text-align: left;
  }
  Layout{
    display: block;
    width: 100%;
    height: 100%;

  }
  .layout-header-bar{
    background: #3c8dbc;
    box-shadow: 0 1px 1px rgba(0,0,0,.1);
    padding: 0;
    height:50px;
    /*height: 50px;*/
    line-height: 50px;
    /*margin-bottom: 50px;*/
  }
  .layout-logo-left{
    width: 90%;
    height: 30px;
    background: #5b6270;
    border-radius: 3px;
    margin: 0 auto;
  }
  .menu-icon{
    transition: all .3s;
  }
  .rotate-icon{
    transform: rotate(-90deg);
  }
  .menu-item span{
    display: inline-block;
    overflow: hidden;
    width: 69px;
    text-overflow: ellipsis;
    white-space: nowrap;
    vertical-align: bottom;
    transition: width .2s ease .2s;
  }
  .menu-item i{
    transform: translateX(0px);
    transition: font-size .2s ease, transform .2s ease;
    vertical-align: middle;
    font-size: 16px;
  }
  .collapsed-menu span{
    width: 0px;
    transition: width .2s ease;
  }
  .collapsed-menu i{
    transform: translateX(5px);
    transition: font-size .2s ease .2s, transform .2s ease .2s;
    vertical-align: middle;
    font-size: 22px;
  }
  .layout-footer-center{
    text-align: center;
  }
  .user-setting{
    float: right;
  }

  .menu-item span{
    width: auto;
  }
</style>

