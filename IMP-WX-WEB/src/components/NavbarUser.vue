<template>
  <div class="nav-user">

    <div class="nav-set">
      <ul>
        <!--<li>-->
          <!--&lt;!&ndash;<Icon type="md-key"/>&ndash;&gt;-->
          <!--<a  type="text" @click="modalModifyPswMeth" >修改密码</a>-->
        <!--</li>-->
        <li>
          {{userName}}
        </li>
        <li>
          <a  type="text" @click="logout">登出</a>
        </li>
      </ul>
    </div>


  </div>
</template>

<script>
  import * as util from '../utils/util.js'
export default {
  name: "NavbarUser",
  props: ["userName", "isChain", "avatarUrl"],
  data() {
    return {

    }
  },
  created() {

  },
  computed: {},
  methods: {
    logout() {
      util.global.userInfo = "";
      util.global.impWxid = "";
      sessionStorage.clear();
      util.deleteSession();
      sessionStorage.setItem("ieControl","ieControl");
      this.$router.replace({ path: "/" });
      this.$http({
        method: "post",
        url: "/api/user/logout"
      })
        .then(function(res) {})
        .catch(err => {
          console.log(err, "logout");
        });
    }
  }
};
</script>

<style lang="less" scoped>
.nav-set ul li {
  list-style: none;
  float: left;
  height: 100%;
  width: 80px;
  color: #fff;
  margin: auto 0;
  /*padding: 0 25px;*/
  text-align: center;
}

.nav-set a {
  display: inline-block;
  box-sizing: border-box;
  width: 100%;
  height: 100%;
  color: #fff;
}
.user-logo {
  position: relative;
  padding-left: 30px;
  img {
    width: 25px;
    height: 25px;
    border-radius: 50%;
    position: absolute;
    left: 8px;
    top: 50%;
    transform: translateY(-50%);
  }
}
.nav-set li a:hover {
  cursor: pointer;
  background-color: #337ab7;
}
</style>
