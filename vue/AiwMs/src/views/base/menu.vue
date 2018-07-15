<template>
	<section>


	  <el-col :span="6">
			<el-tree :data="data"
			 node-key="id"
			 ref="treeabc"
			:props="defaultProps" :default-expand-all="true" :expand-on-click-node="false" :highlight-current="true" @node-click="handleNodeClick">
		</el-tree>
		</el-col>
	  <el-col :span="6">
				<el-form :model="editForm" label-width="80px" :rules="formRules" ref="editForm">
					<el-form-item label="名称" prop="name">
						<el-input v-model="editForm.name" auto-complete="off"></el-input>
					</el-form-item>
					<el-form-item label="url" prop="url">
						<el-input v-model="editForm.url" auto-complete="off"></el-input>
					</el-form-item>
					<el-form-item label="权限吗" prop="privilegeCode">
						<el-input v-model="editForm.privilegeCode" auto-complete="off"></el-input>
					</el-form-item>
					<el-form-item label="顺序" prop="order">
						<el-input v-model="editForm.order" auto-complete="off"></el-input>
					</el-form-item>
					<el-form-item label="icon" prop="icon">
						<el-input v-model="editForm.icon" auto-complete="off"></el-input>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="editSubmit">修改</el-button>
						<el-button @click="handleDel">删除</el-button>
						<el-button type="primary" @click="handleAdd">新增</el-button>
					</el-form-item>
				</el-form>
		</el-col>

	

	<!--新增界面-->
	<el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false">
		<el-form :model="addForm" label-width="80px" :rules="formRules" ref="addForm">
			<el-form-item label="名称" prop="name">
				<el-input v-model="addForm.name" auto-complete="off"></el-input>
			</el-form-item>
			<el-form-item label="url" prop="url">
				<el-input v-model="addForm.url" auto-complete="off"></el-input>
			</el-form-item>
			<el-form-item label="权限吗" prop="privilegeCode">
				<el-input v-model="addForm.privilegeCode" auto-complete="off"></el-input>
			</el-form-item>
			<el-form-item label="顺序" prop="order">
				<el-input v-model="addForm.order" auto-complete="off"></el-input>
			</el-form-item>
			<el-form-item label="icon" prop="icon">
				<el-input v-model="addForm.icon" auto-complete="off"></el-input>
			</el-form-item>
		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button @click.native="addFormVisible = false">取消</el-button>
			<el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
		</div>
	</el-dialog>




	</section>
</template>

<script>
	import util from '../../common/js/util'
	//import NProgress from 'nprogress'
	import { getMenuListPage, removeMenu,batchRemoveMenu, editMenu, addMenu } from '../../api/api-menu';

	export default {
		data() {
			return {
				addLoading: false,
				addFormVisible:false,
				formRules: {
					name: [
						{ required: true, message: '请输入', trigger: 'blur' }
					]
				},
				addForm:{
				},
				currentId:0,
				currentDepth:0,
				editForm: {
				},
				data:[],
				defaultProps: {
          children: 'children',
          label: 'name'
        }
			}
		},
		mounted(){

	  },
		methods: {
			handleNodeClick(data) {
				this.currentId = data.id;
				this.currentDepth = data.depth;
				this.editForm = data;
		  },



			//性别显示转换
			formatSex: function (row, column) {
				return row.sex == '1' ? '男' : row.sex == '0' ? '女' : '未知';
			},


			handleCurrentChange(val) {
				this.page = val;
				this.getMenus();
			},
			//获取用户列表
			getMenus() {
				let para = {

				};
				this.listLoading = true;
				//NProgress.start();
				getMenuListPage(para).then((res) => {
					var a = [];
					//debugger;
					a.push(res.data.data);
					this.data = a;
					//NProgress.done();
				});
			},
			//删除
			handleDel: function () {
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
					let para = { id: this.currentId };
					removeMenu(para).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getMenus();
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
				};
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
							editMenu(para).then((res) => {
								this.editLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['editForm'].resetFields();
								this.editFormVisible = false;
								this.getMenus();
							});
						});
					}
				});
			},
			//新增
			addSubmit: function () {
				var that = this;
				this.$refs.addForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.addLoading = true;
							//NProgress.start();
							let para = Object.assign({pid:this.currentId,depth:this.currentDepth+1}, this.addForm);
							//para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							
							addMenu(para).then((res) => {
								this.addLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.addFormVisible = false;
								this.getMenus();
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
					batchRemoveMenu(para).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getMenus();
					});
				}).catch(() => {

				});
			}
		},
		mounted() {
			this.getMenus();
		}
	}

</script>

<style scoped>

</style>
