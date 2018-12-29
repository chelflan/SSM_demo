<template>
  <div class="router-page">
    <div class="menu-pos" style="margin-top: 5px">
      <Row>
        <Col span="24">
          <Form ref="formInline" :model="formInline" inline>
            <FormItem>
              <Input type="text" v-model.trim="formInline.mac" placeholder="mac地址"></Input>
            </FormItem>
            <FormItem>
              <Input  v-model.trim="formInline.storeName" placeholder="药店名称"></Input>
              <Input  placeholder="药店名称" v-show="false"></Input>
            </FormItem>
            <FormItem>
              <Button type="success" @click="search" icon="md-search">搜索</Button>
              <Button type="primary" @click="add" icon="md-add">新增</Button>
              <Button type="success" @click="refresh" icon="md-refresh">刷新</Button>
            </FormItem>
          </Form>
        </Col>
      </Row>
    </div>
    <!--表格-->
    <div class="table-body">
      <Row>
        <Col :xs="24" :sm="24" :md="24" :lg="24">
          <Table stripe border :loading="loading" :columns="columns" :data="data"></Table>
        </Col>
      </Row>
    </div>
    <!--分页-->
    <div class="page">
      <Page :total="page.total"  :current="page.currentPage" @on-change="changePage" :page-size="page.pageSize"  prev-text="上页" next-text="下页" show-total />
    </div>

    <!--新增药品弹框-->
    <Modal
      v-model="addModel"
      title="新增"
      width="600"
      :mask-closable="false">
      <Form ref="addForm" :model="addForm" :rules="ruleValidate" :label-width="100" inline>
        <FormItem label="药店名称" prop="storeName">
          <Select
            search
            style="width:400px"
            v-model="addForm.storeName"
            filterable
            remote
            placeholder="请输入药店名称"
            :remote-method="queryStoreList"
            :loading="loading1">
            <Option v-for="(option, index) in options1" :value="option.value" :key="index">{{option.label}}</Option>
          </Select>
        </FormItem>
        <FormItem label="mac" prop="mac">
          <Input v-model="addForm.mac" placeholder="请输入mac" disabled></Input>
        </FormItem>
        <FormItem label="药店地址" prop="storeAddr">
          <Input v-model="addForm.storeAddr" placeholder="请输入药店地址" disabled></Input>
        </FormItem>
        <FormItem label="限制时间区间" prop="limitTime" style="width:500px">
          <Tag v-for="item in count" :key="item" :name="item" color="primary" type="border" closable @on-close="handleClose2">标签{{ item + 1 }}</Tag>
          <Button icon="ios-add" type="dashed" size="small" @click="handleAdd">添加标签</Button>
          <TimePicker confirm type="timerange" format="HH:mm" placement="bottom-end" v-model="addForm.limitTime"
                      placeholder="请选择时间"></TimePicker>
          <!--<Input v-model="addForm.limitTime" placeholder="输入"></Input>-->
        </FormItem>
        <FormItem label="延长工作时间是否扫码" prop="isScan">
          <Select v-model="addForm.isScan" style="width:141px">
            <Option value="0">是</Option>
            <Option value="1">否</Option>
          </Select>
        </FormItem>
        <FormItem label="是否开启vip通道" prop="isVip">
          <Select v-model="addForm.isVip" style="width:141px">
            <Option value="0">是</Option>
            <Option value="1">否</Option>
          </Select>
        </FormItem>
        <FormItem label="是否在异常池" prop="isException">
          <Select v-model="addForm.isException" style="width:141px">
            <Option value="0">否</Option>
            <Option value="1">是</Option>
          </Select>
        </FormItem>
        <FormItem label="特定时间后只使用连锁医生" prop="useChainDoctor">
          <Select v-model="addForm.useChainDoctor" style="width:141px">
            <Option value="0">是</Option>
            <Option value="1">否</Option>
          </Select>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="default"    @click="addModel = false">关闭</Button>
        <Button type="primary"   @click="confirmAdd('addForm')">提交</Button>
      </div>
    </Modal>

    <!--修改药品信息-->
    <Modal
      v-model="editModel"
      title="修改"
      width="600"
      :mask-closable="false">
      <Form ref="editForm" :model="editForm" :label-width="100" inline>
        <FormItem label="mac" prop="mac">
          <Input v-model="editForm.mac" placeholder="" disabled></Input>
        </FormItem>
        <FormItem label="药店名称" prop="storeName">
          <Input v-model="editForm.storeName" placeholder="请输入药店名称" disabled></Input>
        </FormItem>
        <FormItem label="药店地址" prop="storeAddr">
          <Input v-model="editForm.storeAddr" placeholder="请输入药店地址" disabled></Input>
        </FormItem>
        <FormItem label="限制时间区间" prop="limitTime">
          <Input v-model="editForm.limitTime" placeholder=""></Input>
        </FormItem>
        <FormItem label="延长工作时间是否扫码" prop="isScan">
          <Select v-model="editForm.isScan" style="width:141px">
            <Option value="0">是</Option>
            <Option value="1">否</Option>
          </Select>
        </FormItem>
        <FormItem label="是否开启vip通道" prop="isVip">
          <Select v-model="editForm.isVip" style="width:141px">
            <Option value="0">是</Option>
            <Option value="1">否</Option>
          </Select>
        </FormItem>
        <FormItem label="是否在异常池" prop="isException">
          <Select v-model="editForm.isException" style="width:141px">
            <Option value="0">否</Option>
            <Option value="1">是</Option>
          </Select>
        </FormItem>
        <FormItem label="特定时间后只使用连锁医生" prop="useChainDoctor">
          <Select v-model="editForm.useChainDoctor" style="width:141px">
            <Option value="0">是</Option>
            <Option value="1">否</Option>
          </Select>
        </FormItem>

      </Form>
      <div slot="footer">
        <Button type="default"    @click="editModel = false">关闭</Button>
        <Button type="primary"   @click="confirmUpdate('editForm')">提交</Button>
      </div>
    </Modal>

    <!--删除对话框-->
    <Modal v-model="delModal" width="360">
      <p slot="header" style="color:#f60;text-align:left">
        <Icon type="ios-information-circle"></Icon>
        <span>删除提示</span>
      </p>
      <div style="text-align:center">
        <p style="font-weight: 800">是否要删除该条数据！</p>
      </div>
      <div slot="footer">
        <div slot="footer">
          <Button type="default" @click="delModal = false">关闭</Button>
          <Button type="error"   @click="corfirmDelete">确定</Button>
        </div>

      </div>
    </Modal>

  </div>
</template>

<script>


  export default {
    name: "BOverrunStore",
    data () {
      return {
        count: [0, 1, 2],
        list: ['Maryland', 'Massachusetts', 'Michigan', 'Minnesota', 'Pennsylvania', 'Rhode island',  'Wyoming'],
        loading1: false,
        options1: [],
        loading:false,
        ruleValidate: {
          storeName: [
            { required: true, message: "药店名称不能为空", trigger: 'blur' },
          ]
        },
        addForm:{
          mac:'',
          storeName:'',
          storeAddr:'',
          storeId:'',
          limitTime:'',
          isScan:'0',
          isVip:'0',
          isException:'0',
          useChainDoctor:'0'
        },
        editForm:{

        },
        delId:'',
        delModal:false,
        editModel:false,
        addModel:false,
        loadingStatus: false,
        page:{
          total:1,
          pageSize:10,
          currentPage:1
        },
        formInline: {
          mac: '',
          storeName: ''
        },
        columns: [
          {
            title: 'mac地址',
            key: 'mac'
          },
          {
            title: '药店名称',
            key: 'storeName'
          },
          {
            title: '药店地址',
            key: 'storeAddr'
          },
          {
            title: '限制时间区间',
            key: 'limitTime',
            minWidth:100,
            render: (h, params) => {
              let arr = [];
              for(let i = 0;i<5;i++){
                arr.push(h('Tag', {
                  props: {
                    type:"border",
                    color:"primary"
                  },
                  style: {
                    display: 'inline-block'

                  }
                }, "08:00-09:00"))
              }
              return h('div',arr)
            }
          },
          {
            title: '延长工作时间是否扫码',
            key: 'isScan',
            render: (h, params) => {
              if(params.row.isScan){
                return h('div', [
                  h('span', {
                    style: {
                      display: 'inline-block',
                      width: '100%',
                      color:'green'
                    }
                  }, "是")
                ])
              }else{
                return h('div', [
                  h('span', {
                    style: {
                      display: 'inline-block',
                      width: '100%',
                      color:'red'
                    }
                  }, "否")
                ])
              }
            }
          },
          {
            title: '是否开启vip通道',
            key: 'isVip',
            render: (h, params) => {
              if(params.row.isVip){
                return h('div', [
                  h('span', {
                    style: {
                      display: 'inline-block',
                      width: '100%',
                      color:'green'
                    }
                  }, "开启")
                ])
              }else{
                return h('div', [
                  h('span', {
                    style: {
                      display: 'inline-block',
                      width: '100%',
                      color:'red'
                    }
                  }, "关闭")
                ])
              }
            }
          },
          {
            title: '是否在异常池',
            key: 'isException',
            render: (h, params) => {
              if(params.row.isException){
                return h('div', [
                  h('span', {
                    style: {
                      display: 'inline-block',
                      width: '100%',
                      color:'green'
                    }
                  }, "否")
                ])
              }else{
                return h('div', [
                  h('span', {
                    style: {
                      display: 'inline-block',
                      width: '100%',
                      color:'red'
                    }
                  }, "是")
                ])
              }
            }
          },
          {
            title: '特定时间后只使用连锁医生',
            key: 'useChainDoctor',
            render: (h, params) => {
              if(params.row.useChainDoctor){
                return h('div', [
                  h('span', {
                    style: {
                      display: 'inline-block',
                      width: '100%',
                      color:'green'
                    }
                  }, "是")
                ])
              }else{
                return h('div', [
                  h('span', {
                    style: {
                      display: 'inline-block',
                      width: '100%',
                      color:'red'
                    }
                  }, "否")
                ])
              }
            }
          },
          {
            title: '修改',
            key: 'modify',
            width: 70,
            align: 'center',
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'primary',
                    size: 'small',
                    icon:'md-create'
                  },
                  on: {
                    click: () => {
                      this.queryForUpdate(params.row.mac);
                    }
                  }
                })
              ]);
            }
          },
          {
            title: '删除',
            key: 'delete',
            width: 70,
            align: 'center',
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'warning',
                    size: 'small',
                    icon:'md-trash'
                  },
                  on: {
                    click: () => {
                      this.mac = params.row.mac;
                      this.delModal = true;
                    }
                  }
                })
              ]);
            }
          }
        ],
        data: []
      }
    },
    created(){
      this.initTableData();
    },
    mounted(){
      // global.userInfo = sessionStorage;
      // console.log(global.userInfo,"userInfo------")
      // this.initTableData();
    },
    methods: {
      handleAdd () {
        if (this.count.length) {
          this.count.push(this.count[this.count.length - 1] + 1);
        } else {
          this.count.push(0);
        }
      },
      handleClose2 (event, name) {
        const index = this.count.indexOf(name);
        this.count.splice(index, 1);
      },
      queryStoreList (query) {
        let vm = this;
        if (query !== '') {
          vm.loading1 = true;
          setTimeout(() => {
            vm.loading1 = false;
            const list = vm.list.map(item => {
              return {
                value: item,
                label: item
              };
            });
            vm.options1 = list.filter(item => item.label.toLowerCase().indexOf(query.toLowerCase()) > -1);
          }, 500);
        } else {
          vm.options1 = [];
        }
      },
      add(){
        this.$refs['addForm'].resetFields();
        this.addModel = true;
      },
      skipToDrugDetail(drugId){
        sessionStorage.setItem("drugId",drugId);
        open("./static/pages/store/storeDrug/drugDetail.html");
        // window.open("http://172.20.12.139:8085/store/drugInfo/"+drugId+"/_viewInfos.ft");
      },
      exchangeBoolToNum(bool){
        if(bool){
          return "0"
        }
        return "1";
      },
      queryForUpdate(mac){
        const vm = this;
        this.$http({
          method:'post',
          url:'api/bos/queryForUpdate',
          contentType:'application/json;utf-8',
          data:{
            mac:mac.toString()
          },
        }).then(function(res){
          if(res.status == '200' && res.data.code == '0'){
            let data = res.data.data;
            vm.$refs['editForm'].resetFields();
            data.isScan = vm.exchangeBoolToNum(data.isScan);
            data.useChainDoctor = vm.exchangeBoolToNum(data.useChainDoctor);
            data.isVip = vm.exchangeBoolToNum(data.isVip);
            data.isException = vm.exchangeBoolToNum(data.isException);
            console.log(data,"data----")
            vm.editForm = data;
            vm.editModel = true;
          }else{
            vm.$Message.error(res.data.msg);
          }
        }).catch(function(err){
          console.log(err,"err----queryForUpdate---")

        })
      },
      corfirmDelete(){
        const vm = this;
        this.$http({
          method:'delete',
          url:'api/bos/delete',
          contentType:'application/json;utf-8',
          data:{
            mac:this.mac.toString()
          },
        }).then(function(res){
          if(res.status == '200' && res.data.code == '0'){
            return new Promise(function(resolve, reject) {
              resolve("succ");
            }).then(function (res){
              vm.initTableData(vm.page.currentPage,vm.page.pageSize,"删除成功");
            })
          }else{
            vm.$Message.error(res.data.msg);
          }
        }).catch(function(err){
          console.log(err,"err----corfirmDelete---")

        })
      },
      changePage(currentPage){
        this.page.currentPage = currentPage;
        this.initTableData(currentPage);
      },
      search(){
        this.initTableData();
      },
      confirmAdd(name){
        let vm = this;
        this.$refs[name].validate((valid) => {
          if (valid) {
            // this.addForm
            //拼装数据
            let addData = {
              "spec":this.addForm.spec,//包装规格
              "price":this.addForm.price.toString(),//价格
              "apprNumber":this.addForm.apprNumber//批准文号
            }
            // addForm
            this.$http({
              method:'post',
              url:'api/store/drugInfo/addDrugInfo',
              contentType:'application/json;utf-8',
              data:addData,
            }).then(function(res){
              console.log(res,"res-------")
              if(res.status == '200' && res.data.code == '0'){
                // vm.addModel = false;
                return new Promise(function(resolve, reject) {
                  // vm.$Message.success("新增药品成功");
                  resolve("succ");
                }).then(function (res){
                  vm.initTableData(1,vm.page.pageSize,"新增药品成功");
                })
              }else{
                vm.$Message.error(res.data.msg);
                // vm.$Message.error("新增失败，该药品未审核或审核未通过");
              }
            }).catch(function(err){
              console.log(err,"err-------")

            })
          }
        })
      },
      confirmUpdate(name){
        let vm = this;
        this.$refs[name].validate((valid) => {
          if (valid) {
            console.log(this.editForm,"editForm")
            this.$http({
              method:'post',
              url:'api/bos/update',
              contentType:'application/json;utf-8',
              data:this.editForm,
            }).then(function(res){
              console.log(res,"res-------")
              if(res.status == '200' && res.data.code == '0'){
                return new Promise(function(resolve, reject) {
                  resolve("succ");
                }).then(function (res){
                  vm.initTableData(vm.page.currentPage,vm.page.pageSize,"修改成功");
                })
              }else{
                vm.$Message.error(res.data.msg);
              }
            }).catch(function(err){
              console.log(err,"err--confirmUpdate-----")

            })
          }
        })
      },
      initTableData(currentPage = 1,pageSize = 10,msg){
        let vm = this;
        vm.loading = true;
        this.page.currentPage = currentPage;
        let data = {
          currentPage:currentPage,
          pageSize:pageSize,
          mac: this.formInline.mac.trim(),
          storeName:this.formInline.storeName.trim()
        };
        this.$http({
          method:'post',
          url:'api/bos/getList',
          data:data,
        }).then(function(res){
          console.log(res,"res-------")
          if(res.status == '200' && res.data.code == '0'){
            const dataInfo = res.data.data;
            vm.data = dataInfo.list;

            vm.page.total = dataInfo.totalCount;
            if(!!msg){
              vm.delModal = false;
              vm.editModel = false;
              vm.addModel = false;
              vm.$Message.success(msg);
            }
          }else{
            vm.$Message.error(res.data.msg);
          }
          vm.loading = false;
        }).catch(function(err){
          console.log(err,"err----initTableData---")
          vm.loading = false;
        })
      },
      refresh(){
        this.formInline.mac = "";
        this.formInline.storeName = "";
        this.initTableData();
      }
    }
  }
</script>

<style lang="less" scoped>
  .table-body{
    border: 1px solid #ccc;
  }
  .ivu-page{
    float: right;
    margin: 10px;
  }
</style>
