<template>
  <div class="logo">
    <!-- mini logo for sidebar mini 50x50 pixels -->
    <span class="logo-mini" v-show="isCollapsed"><b>FT</b></span>
    <!-- logo for regular state and mobile devices -->
    <span class="logo-lg" v-show="!isCollapsed"><b>FT-</b>微问诊微信管理</span>
  </div>
</template>

<script>
    export default {
        name: "Logo",
        props:["isCollapsed","platName"],
       methods:{
         loginInfo(){

         }
       }
    }
</script>

<style lang="less" scoped>
 .logo{
   font-size: 20px;
   line-height: 50px;
   text-align: center;
   font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
   padding: 0 15px;
   font-weight: 300;
   background-color: #3c8dbc;
   color: #fff;
   border-bottom: 0 solid transparent;
   .logo-mini{
     width: 50px;
   }
   .logo-lg{
     width: 230px;
   }
 }
</style>
