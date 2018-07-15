<template>
	<section>
		<!--查询UI-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.name" placeholder="用户名"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="get${component}s">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表UI-->
		<el-table :data="list" ref="listTable" highlight-current-row v-loading="$store.state.loading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection"  width="55">
			</el-table-column>
			<el-table-column type="index" width="60">
			</el-table-column>
			<#list entity?keys as key>  
                <el-table-column prop="${key}" label="${key}" sortable>
                </el-table-column>
            </#list>  
			<el-table-column label="操作" width="200">
				<template slot-scope="scope">
					<el-button-group>
						<el-button type="primary" size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
						<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
				  </el-button-group>
				</template>
			</el-table-column>
		</el-table>

		<!--批量删除UI-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
			<el-pagination layout="total,prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" style="float:right;">
			</el-pagination>
		</el-col>

		<!--编辑UI-->
		<el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="80px" :rules="formRules" ref="editForm">
			
			    <#list entity?keys as key>  
                    <el-form-item label="${key}" prop="${key}">
                        <el-input v-model="editForm.${key}" auto-complete="off"></el-input>
                    </el-form-item>
                </#list> 
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>

		<!--新增UI-->
		<el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
			<el-form :model="addForm" label-width="80px" :rules="formRules" ref="addForm">
			
			    <#list entity?keys as key>  
                    <el-form-item label="${key}" prop="${key}">
                        <el-input v-model="addForm.${key}" auto-complete="off"></el-input>
                    </el-form-item>
                </#list> 
				
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
    import store from '../../vuex/store'
	//import NProgress from 'nprogress'
	import { get${component}ListPage, remove${component}, update${component}Role,get${component}Roles,batchRemove${component}, edit${component}, add${component} } from '../../api/api-${componentLower}';

	export default {
		data() {
			return {
				filters: {
					name: ''
				},
				list: [],
				total: 0,
				pageSize:10,
				page: 1,
				listLoading: false,
				sels: [],//列表选中列


				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				//编辑界面数据
				editForm: {
				},

				addFormVisible: false,//新增界面是否显示
				addLoading: false,
				//新增界面数据
				addForm: {
				},
				
				formRules: {
				    <#list entity?keys as key>  
                    ${key}: [
                        { required: true, message: '请输入${key}', trigger: 'blur' }
                    ],
                    </#list> 
				},
			}
		},
        store,
		mounted(){

	  },
		methods: {
			
			//性别显示转换
			formatSex: function (row, column) {
				return row.sex == '1' ? '男' : row.sex == '0' ? '女' : '未知';
			},

			handleCurrentChange(val) {
				this.page = val;
				this.get${component}s();
			},
			//获取用户列表
			get${component}s() {
				let para = {
					pageNum: this.page,
					name: this.filters.name
				};
				this.listLoading = true;
				//NProgress.start();
				//debugger;
				get${component}ListPage(para).then((res) => {

					this.total = res.data.data.totalSize;
					this.pageSize = res.data.data.pageSize;
					this.page = res.data.data.currentPage;
					this.list = res.data.data.list;
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
					remove${component}(para).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.get${component}s();
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

			

			//编辑
			editSubmit: function () {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.editLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.editForm);
							para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							edit${component}(para).then((res) => {
								this.editLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['editForm'].resetFields();
								this.editFormVisible = false;
								this.get${component}s();
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
							add${component}(para).then((res) => {
								this.addLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['addForm'].resetFields();
								this.addFormVisible = false;
								this.get${component}s();
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
					batchRemove${component}(para).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.get${component}s();
					});
				}).catch(() => {

				});
			}
		},
		mounted() {
			this.get${component}s();
		}
	}

</script>

<style scoped>

</style>
