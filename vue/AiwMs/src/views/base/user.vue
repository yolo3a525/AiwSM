<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.name" placeholder="用户名"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getUsers">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>


		<!--列表-->
		<el-table :data="users" ref="usersTable" highlight-current-row v-loading="$store.state.loading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection"  width="55">
			</el-table-column>
			<el-table-column type="index" width="60">
			</el-table-column>
			<el-table-column prop="username" label="用户名" width="120" sortable>
			</el-table-column>
			<el-table-column prop="password" label="密码" width="100" sortable>
			</el-table-column>
			<el-table-column prop="name" label="实名" width="120" sortable>
			</el-table-column>
			<el-table-column prop="phone" label="手机号码" width="180" sortable>
			</el-table-column>
			<el-table-column prop="email" label="邮箱" width="180" sortable>
			</el-table-column>
			<el-table-column prop="remark" label="备注" min-width="180" sortable>
			</el-table-column>
			<el-table-column label="操作" width="200">
				<template slot-scope="scope">
					<el-button-group>
						<el-button type="primary" size="small" @click="handleRoleList(scope.$index, scope.row)">角色管理</el-button>
						<el-button type="primary" size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
						<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
				  </el-button-group>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
			<el-pagination layout="total,prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" style="float:right;">
			</el-pagination>
		</el-col>

		<!--编辑界面-->
		<el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="80px" :rules="formRules" ref="editForm">
				<el-form-item label="用户名" prop="username">
					<el-input v-model="editForm.username" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="密码" prop="password">
					<el-input v-model="editForm.password" auto-complete="off"></el-input>
				</el-form-item>

				<el-form-item label="实名" prop="name">
					<el-input v-model="editForm.name" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="手机号码" prop="phone">
					<el-input v-model="editForm.phone" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="邮箱" prop="email">
					<el-input v-model="editForm.email" auto-complete="off"></el-input>
				</el-form-item>
				
				<el-form-item label="备注">
					<el-input type="textarea" v-model="editForm.remark"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>

		<!--新增界面-->
		<el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
			<el-form :model="addForm" label-width="80px" :rules="formRules" ref="addForm">
				<el-form-item label="用户名" prop="username">
					<el-input v-model="addForm.username" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="密码" prop="password">
					<el-input v-model="addForm.password" auto-complete="off"></el-input>
				</el-form-item>



				<el-form-item label="昵称" prop="nickName">
					<el-input v-model="addForm.name" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="手机号码" prop="phone">
					<el-input v-model="addForm.phone" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="邮箱" prop="email">
					<el-input v-model="addForm.email" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="备注">
					<el-input type="textarea" v-model="addForm.remark"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
			</div>
		</el-dialog>

		<!--角色列表页面-->
		<el-dialog title="角色列表"  v-model="roleListVisible" :close-on-click-modal="false">
			<el-table :data="roleList" ref="table2" highlight-current-row v-loading="listLoading" @selection-change="selsChange2" style="width: 100%;">
				<el-table-column type="selection" width="55">
				</el-table-column>
				<el-table-column type="index" width="60">
				</el-table-column>
				<el-table-column prop="name" label="角色" width="120" sortable>
				</el-table-column>
			</el-table>
			<div slot="footer" class="dialog-footer">

				<el-button @click.native="roleListVisible = false">取消</el-button>
				<el-button type="primary" @click.native="roleListSubmit" :loading="roleListLoading">提交</el-button>
			</div>
		</el-dialog>


	</section>
</template>

<script>
	import util from '../../common/js/util'
  import store from '../../vuex/store'
	//import NProgress from 'nprogress'
	import { getUserListPage, removeUser, updateUserRole,getUserRoles,batchRemoveUser, editUser, addUser } from '../../api/api-user';

	export default {
		data() {
			return {
				filters: {
					name: ''
				},
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
					name: '',
					sex: -1,
					age: 0,
					birth: '',
					addr: ''
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
			addSubmit: function () {
				this.$refs.addForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.addLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.addForm);
							para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							addUser(para).then((res) => {
								this.addLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['addForm'].resetFields();
								this.addFormVisible = false;
								this.getUsers();
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
			this.getUsers();
		}
	}

</script>

<style scoped>

</style>
