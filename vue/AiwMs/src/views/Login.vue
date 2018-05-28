<template>
  <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-position="left" label-width="0px" class="demo-ruleForm login-container">
    <h3 class="title">登录</h3>
    <el-form-item prop="account">
      <el-input type="text" v-model="ruleForm2.account" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="checkPass">
      <el-input type="password" v-model="ruleForm2.checkPass" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-checkbox v-model="checked" checked class="remember">记住密码</el-checkbox>
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click.native.prevent="handleSubmit2" :loading="logining">登录</el-button>
      <!--<el-button @click.native.prevent="handleReset2">重置</el-button>-->
    </el-form-item>
  </el-form>
</template>

<script>
  import { requestLogin } from '../api/api-user';
  import { getMenuListPage, removeMenu,batchRemoveMenu, editMenu, addMenu } from '../api/api-menu';
  //import NProgress from 'nprogress'
  export default {
    data() {
      return {
        logining: false,
        ruleForm2: {
          account: 'admin',
          checkPass: '111111'
        },
        rules2: {
          account: [
            { required: true, message: '请输入账号', trigger: 'blur' },
            //{ validator: validaePass }
          ],
          checkPass: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            //{ validator: validaePass2 }
          ]
        },
        checked: true
      };
    },
    methods: {
      handleReset2() {
        this.$refs.ruleForm2.resetFields();
      },
      handleSubmit2(ev) {
        var _this = this;
        this.$refs.ruleForm2.validate((valid) => {
          if (valid) {
            //_this.$router.replace('/table');
            this.logining = true;
            //NProgress.start();
            var loginParams = { userName: this.ruleForm2.account, password: this.ruleForm2.checkPass };
            requestLogin(loginParams).then(data => {
              //debugger;
              this.logining = false;
              //NProgress.done();
              let {msg} = data.data;
              if (msg != "OK") {
                this.$message({
                  message: msg,
                  type: 'error'
                });
              } else {
                //debugger;
                var old = this.$router.options.routes;
                var new2 = [];

                localStorage.setItem('user', JSON.stringify(data.data.data.info));
                
                localStorage.setItem('menu', JSON.stringify(data.data.data.menu));
                  // var menu2 = null;
                  // var node = null;
                  // for(var i =0;i < menu.length;i++){
                  //    node = {
                  //     path: '/',
                  //     component: require('./Home.vue'),
                  //     name: menu[i].name,
                  //     iconCls: 'fa fa-bar-chart',
                  //     children: []
                  //   }
                  //   for(var k = 0; k < menu[i].children.length;k++){
                  //     menu2 = menu[i].children[k];
                  //     node.children.push({ path: '/'+ menu2.url, component:  require('./user/'+menu2.url+'.vue'), name: menu2.name })
                  //   }
                  //   new2.push(node);
                  // }
                  //
                  // for(var i = 0 ; i < old.length;i++){
                  //   if(old[i].path != "/"){
                  //     new2.push(old[i]);
                  //   }
                  // }

                  //this.$router.addRoutes(new2)
                  //this.$router.options.routes = new2;
                location.href = "/#/base/menu";
                  //this.$router.push({ path: '/role' });

              }
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }
    }
  }

</script>

<style lang="scss" scoped>

   body {
    background-color: red;
  }
  .login-container {
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 1px;
    border-radius: 1px;
    -moz-border-radius: 1px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 300px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    .title {
      margin: 0px auto 40px auto;
      text-align: center;
      color: #505458;
    }
    .remember {
      margin: 0px 0px 35px 0px;
    }
  }
</style>
