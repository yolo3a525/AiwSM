<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.name" placeholder="姓名"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getRoles">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="roles" ref="rolesTable" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection"  width="55">
			</el-table-column>
			<el-table-column type="index" width="60">
			</el-table-column>
			<el-table-column prop="id" label="角色码" width="120" sortable>
			</el-table-column>
			<el-table-column prop="name" label="角色名" sortable>
			</el-table-column>

			<el-table-column label="操作" width="200">
				<template slot-scope="scope">
					<el-button-group>
						<el-button type="primary" size="small" @click="handlePrivilegeList(scope.$index, scope.row)">权限管理</el-button>
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
			<el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
				<el-form-item label="角色名" prop="name">
					<el-input v-model="editForm.name" auto-complete="off"></el-input>
				</el-form-item>

			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>

		<!--新增界面-->
		<el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
			<el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
				<el-form-item label="角色码" prop="id">
					<el-input v-model="addForm.id" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="角色名" prop="name">
					<el-input v-model="addForm.name" auto-complete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
			</div>
		</el-dialog>

		<!--角色列表页面-->
		<el-dialog title="权限列表"  v-model="privilegeListVisible" :close-on-click-modal="false">
			<el-table :data="privilegeList" ref="table2" highlight-current-row v-loading="listLoading" @selection-change="selsChange2" style="width: 100%;">
				<el-table-column type="selection" width="55">
				</el-table-column>
				<el-table-column type="index" width="60">
				</el-table-column>
				<el-table-column prop="name" label="权限" width="120" sortable>
				</el-table-column>
			</el-table>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="privilegeListVisible = false">取消</el-button>
				<el-button type="primary" @click.native="privilegeListSubmit" :loading="privilegeListLoading">提交</el-button>
			</div>
		</el-dialog>


	</section>
</template>

<script>
	import util from '../../common/js/util'
	//import NProgress from 'nprogress'
	import { getRoleListPage, removeRole, updateRolePrivilege,getRolePrivileges,batchRemoveRole, editRole, addRole } from '../../api/api-role';

	export default {
		data() {
			return {
				filters: {
					name: ''
				},
				roles: [],
				total: 0,
				pageSize:0,
				page: 1,
				listLoading: false,
				sels: [],//列表选中列
				sels2: [],//列表选中列
				privilegeListVisible: false,//角色列表界面是否显示
				privilegeListLoading: false,
				privilegeCurrentRoleId:null,
				privilegeList:[],

				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					name: [
						{ required: true, message: '请输入', trigger: 'blur' }
					]
				},
				//编辑界面数据
				editForm: {
					id: '',
					name: '',
				},

				addFormVisible: false,//新增界面是否显示
				addLoading: false,
				addFormRules: {
					id: [
						{ required: true, message: '请输入', trigger: 'blur' }
					],
					name: [
						{ required: true, message: '请输入', trigger: 'blur' }
					]
				},
				//新增界面数据
				addForm: {
					id: '',
					name: '',
				}

			}
		},
		mounted(){

	  },
		methods: {
			//初始选中
			checked(){
				setTimeout(() => {
					 for(var i = 0; i < this.privilegeList.length;i++){
						 if(this.privilegeList[i].roleId != null){
							  this.$refs.table2.toggleRowSelection(this.privilegeList[i],true);
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
				this.getRoles();
			},
			//获取用户列表
			getRoles() {
				let para = {
					pageNum: this.page,
					name: this.filters.name
				};
				this.listLoading = true;
				//NProgress.start();
				//debugger;
				getRoleListPage(para).then((res) => {
					//console.log(res)
					this.total = res.data.data.totalSize;
					this.pageSize = res.data.data.pageSize;
					this.page = res.data.data.currentPage;
					this.roles = res.data.data.list;
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
					removeRole(para).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getRoles();
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
					id:'',
					name: '',
				};
			},

			//显示角色列表界面
			handlePrivilegeList: function (index, row) {
				this.privilegeListVisible = true;
				getRolePrivileges({id:row.id}).then((res) => {
					this.privilegeList = res.data.data;
					this.privilegeCurrentRoleId = row.id;
					this.listLoading = false;
					this.checked();
				});
			},
			privilegeListSubmit: function () {

				var privilegeIds= '';
				for(var i = 0; i < this.sels2.length;i++){
					
					privilegeIds = privilegeIds + this.sels2[i].id + ","
				}
				let para = {
					roleId:this.privilegeCurrentRoleId,
					privilegeIds:privilegeIds
				}
				updateRolePrivilege(para).then((res) => {
						this.$message({
							message: '提交成功',
							type: 'success'
						});
						this.privilegeListVisible = false;
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
							//para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							editRole(para).then((res) => {
								this.editLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['editForm'].resetFields();
								this.editFormVisible = false;
								this.getRoles();
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
							//para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							addRole(para).then((res) => {
								this.addLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['addForm'].resetFields();
								this.addFormVisible = false;
								this.getRoles();
							}).catch((err) => {
								this.addLoading = false;
							  //console.log(err);
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
				var ids = this.sels.map(item => item.id).toString();
				this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
					let para = { ids: ids };
					batchRemoveRole(para).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getRoles();
					});
				}).catch(() => {

				});
			}
		},
		mounted() {
			this.getRoles();
		}
	}

</script>

<style scoped>

</style>
