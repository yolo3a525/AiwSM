<template>
	<section>
		<!--工具条-->
		<el-row>
		<el-col  :span="12" style="padding-bottom: 0px;width:100%" >
			<el-form :model="addForm" label-width="80px" :rules="formRules" :inline="true" ref="addForm">

				<el-form-item label="模块" prop="module">
					<el-input v-model="addForm.module" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="组件" prop="component">
					<el-input v-model="addForm.component" auto-complete="off"></el-input>
				</el-form-item>
				
				<div v-for="(a,index) in fields">
					<el-form-item label="字段" prop="key">
						<el-input v-model="addForm.key[index]" auto-complete="off"></el-input>
					</el-form-item>
			
					<el-form-item label="类别" prop="type">
						<el-input v-model="addForm.type[index]" auto-complete="off"></el-input>
					</el-form-item>
			
					<el-form-item>
						<el-button type="primary" @click.native="deleteField(index)" >删除</el-button>
					</el-form-item>
				</div>

				<el-form-item>
						<el-button type="primary" @click.native="addField" >增加字段</el-button>
						<el-button @click.native="addFormVisible = false">取消</el-button>
						<el-button type="primary" @click.native="create" :loading="addLoading">提交</el-button>
			   </el-form-item>
			</el-form>
		</el-col>
	 </el-row>
	</section>
</template>

<script>
	import util from '../../common/js/util'
    import store from '../../vuex/store'
	//import NProgress from 'nprogress'
	import { create } from '../../api/api-code';

	export default {
		data() {
			return {
				filters: {
					name: ''
				},
				fields:[
					1
				],
				users: [],
				total: 0,
				pageSize:10,
				page: 1,
				listLoading: false,
				sels: [],//列表选中列
				sels2: [],//列表选中列

				roleListVisible: false,//角色列表界面是否显示
				roleListLoading: false,
				roleCurrentUserId:null,
				roleList:[],

				editFormVisible: false,//编辑界面是否显示
				editLoading: false,

				//编辑界面数据
				editForm: {
					id: 0,
					name: '',
					sex: -1,
					age: 0,
					birth: '',
					addr: ''
				},

				addFormVisible: false,//新增界面是否显示
				addLoading: false,
				//新增界面数据
				addForm: {
					key:[],
					type:[]
				},
				
				formRules: {
					username: [
						{ required: true, message: '请输入用户名', trigger: 'blur' }
					],
					password: [
						{ required: true, message: '请输入password', trigger: 'blur' }
					],
					name: [
						{ required: true, message: '请输入昵称', trigger: 'blur' }
					],
					phone: [
						{ required: true, message: '请输入手机号码', trigger: 'blur' },

					],
					email: [
						{ required: true, message: '请输入邮箱地址', trigger: 'blur' },
      			{ type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change' }
					],
					sex: [
						{ required: true, message: '请选择性别', trigger: 'blur' }
					]
				},

			}
		},
    store,
		mounted(){

	  },
		methods: {
			//初始选中
			checked(){
				setTimeout(() => {
					 for(var i = 0; i < this.roleList.length;i++){
						 if(this.roleList[i].userId != null){
							  this.$refs.table2.toggleRowSelection(this.roleList[i],true);
						 }
					 }
				}, 0)
      },

			//性别显示转换
			formatSex: function (row, column) {
				return row.sex == '1' ? '男' : row.sex == '0' ? '女' : '未知';
			},

			deleteField:function(index){
				if(this.fields.length <= 1){
					return;
				}
				this.fields.splice(index, 1);
				this.addForm.key.splice(index, 1);
				this.addForm.type.splice(index, 1);
				
			},
			addField:function(){
				this.fields.push(1)
			},
			handleCurrentChange(val) {
				this.page = val;
				this.getUsers();
			},
			//获取用户列表
			getUsers() {
				let para = {
					pageNum: this.page,
					name: this.filters.name
				};
				this.listLoading = true;
				//NProgress.start();
				//debugger;
				getUserListPage(para).then((res) => {

					this.total = res.data.data.totalSize;
					this.pageSize = res.data.data.pageSize;
					this.page = res.data.data.currentPage;
					this.users = res.data.data.list;
					this.listLoading = false;
					//NProgress.done();
				});
			},
			//删除
			handleDel: function (index, row) {
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
					let para = { id: row.id };
					removeUser(para).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getUsers();
					});
				}).catch(() => {

				});
			},
			//显示编辑界面
			handleEdit: function (index, row) {
				this.editFormVisible = true;
				this.editForm = Object.assign({}, row);
			},
			//显示新增界面
			handleAdd: function () {
				this.addFormVisible = true;
				this.addForm = {
					name: '',
					sex: -1,
					age: 0,
					birth: '',
					addr: ''
				};
			},

			//显示角色列表界面
			handleRoleList: function (index, row) {
				this.roleListVisible = true;
				getUserRoles({id:row.id}).then((res) => {
					this.roleList = res.data.data;
					this.roleCurrentUserId = row.id;
					this.listLoading = false;
					this.checked();
				});
			},
			roleListSubmit: function () {

				var roleIds= "";
				for(var i = 0; i < this.sels2.length;i++){
					roleIds = roleIds + this.sels2[i].id + ","
				}
				let para = {
					userId:this.roleCurrentUserId,
					roleIds:roleIds
				}
				updateUserRole(para).then((res) => {
						this.$message({
							message: '提交成功',
							type: 'success'
						});
						this.roleListVisible = false;
				});

			},

			//编辑
			editSubmit: function () {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.editLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.editForm);
							para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							editUser(para).then((res) => {
								this.editLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['editForm'].resetFields();
								this.editFormVisible = false;
								this.getUsers();
							});
						});
					}
				});
			},
			//新增
			create: function () {
				this.$refs.addForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.addLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.addForm);
							para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							
							create(para).then((res) => {
								this.addLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
							});
						});
					}
				});
			},
			selsChange: function (sels) {
				this.sels = sels;
			},
			selsChange2: function (sels) {
				this.sels2 = sels;
			},
			//批量删除
			batchRemove: function () {
				var ids = this.sels.map(item => item.id);
				this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
					let para = { list: ids };
					batchRemoveUser(para).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getUsers();
					});
				}).catch(() => {

				});
			}
		},
		mounted() {
			//this.getUsers();
		}
	}

</script>

<style scoped>

</style>
