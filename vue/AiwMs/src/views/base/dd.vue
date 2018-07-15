<template>
	<section>
		
		<el-row :gutter="20">
			<el-col :span="24">				
				<!--工具条-->
			    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			      <el-form :inline="true" >
			        <el-form-item>
			          <el-input  placeholder="搜索 : item" v-model="value"> </el-input>
			        </el-form-item>
			        <el-form-item>
			          <el-button @click="getItemLists">查询item</el-button>
			          
			          
			          <el-button style="margin-left: 64px;" type="primary"  @click="getGroupLists">查看group列表</el-button>
			        </el-form-item>
			        <!-- <el-button @click="addGroup">新增group</el-button>-->
			        
			        </el-form-item>
			      </el-form>
			    </el-col>
			    
			    <!--item列表-->
		
			    <el-table :span="24" :data="tableLists" ref="usersTable" 
			    	 highlight-current-row 
			     style="width: 100%;">
			      <!--<el-table-column type="selection"  width="55">
			      </el-table-column>-->
			      <el-table-column prop="" label="#" width="50">
			      		
			      </el-table-column>
			     
			      <el-table-column prop="dgCode" label="dgCode" width="150">
			      </el-table-column>
				  <el-table-column prop="dgName" label="dgName" width="150">
			      </el-table-column>
			      <el-table-column prop="ddItem" label="ddItem">
			      	
			      </el-table-column>
				  <el-table-column prop="ddValue" label="ddValue">
			      	
			      </el-table-column>
			      <el-table-column prop="ddRemark" label="ddRemark">
			      	
			      </el-table-column>
			      <el-table-column width="150">
			      	   <template slot-scope="scope">
			      	   	<el-button  type="primary" @click="editItem(scope.$index,scope.row)" size="mini">编辑</el-button>
			      	   		<el-button type="danger" size="mini" @click="removeItem(scope.$index,scope.row)">删除</el-button>
			      	   </template>
			      </el-table-column>
			    </el-table>
			    
			   
			    <el-col :span="24" class="toolbar" v-show="tableLists.length != 0">
			
			      <el-pagination layout="total,prev, pager, next"
			      @current-change="handleCurrentChange" :page-size="pageSize" :total="total" style="float:right;">
			      </el-pagination>
			    </el-col>
			</el-col>
			
			
		</el-row>
		<!--dialog group列表-->
	    <el-dialog title="Group列表" :visible.sync="groupVisible">
		  <el-row :span="24">
				 <!--工具条-->
			    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			      <el-form :inline="true">
			        <el-form-item>
			          <el-input  placeholder="搜索 : group"> </el-input>
			        </el-form-item>
			        <el-form-item>
			          <el-button @click="getGroupLists">查询group</el-button>
			        </el-form-item>
			         <el-button @click="addGroup">新增group</el-button>
			        
			        </el-form-item>
			      </el-form>
			    </el-col>
								
				 <!--group列表-->
			    <el-table :span="24" :data="groupLists" highlight-current-row 
			     style="width: 100%;">
			      	<el-table-column prop="" label="#" width="100">
			      		<template slot-scope="scope">
			      			<el-button type="text" @click="addItem(scope.row)">新增item</el-button>
			      		</template>
			     	 </el-table-column>
			     	  <el-table-column prop="dgCode" label="dgCode">
			      	
			      	
			     	 </el-table-column>
			    	 <el-table-column prop="dgName" label="dgName">
			      	
			      	
			     	 </el-table-column>
			     
			     	
			     	 <el-table-column prop="dgRemark" label="dgRemark">
			      	
			     	 </el-table-column>
			     	 <el-table-column width="150">
			     	 		<template slot-scope="scope">
			     	 			<el-button size="mini" @click="editGroup(scope.$index,scope.row)" type="primary" >编辑</el-button>
			     	 			<el-button size="mini" @click="removeGroup(scope.$index,scope.row)" type="danger">删除</el-button>
			     	 		</template>
			     	 </el-table-column>
			    </el-table>																		
			</el-row>
		</el-dialog>
		
		
		
	    <!--dialog 新增group-->
	    <el-dialog title="新增Group" :visible.sync="dialogFormVisible">
		  <el-form :model="groupForm">
		    <el-form-item label="dgName" :label-width="formLabelWidth">
		      <el-input v-model="groupForm.dgName"></el-input>
		    </el-form-item>
		     <el-form-item label="dgCode" :label-width="formLabelWidth">
		      <el-input v-model="groupForm.dgCode"></el-input>
		    </el-form-item>
		     <el-form-item label="dgRemark" :label-width="formLabelWidth">
		      <el-input v-model="groupForm.dgRemark"></el-input>
		    </el-form-item>
		  </el-form>
		  <div slot="footer" class="dialog-footer">
		    <el-button @click="dialogFormVisible = false">取 消</el-button>
		    <el-button type="primary" @click="submitGroup">提交</el-button>
		  </div>
		</el-dialog>
	    
	    <!--dialog 新增item-->
	    <el-dialog title="新增item" :visible.sync="itemFormVisible">
		  <el-form :model="itemForm" :rules="ruleItem" ref="itemForm">
		   	<el-form-item label="dgCode" :label-width="formLabelWidth">
		      <el-input v-model="itemForm.dgCode" disabled></el-input>
		    </el-form-item>
		     <el-form-item label="dgName" prop="dgName" :label-width="formLabelWidth">
		      <el-input v-model="itemForm.dgName" disabled></el-input>
		    </el-form-item>
		    <el-form-item label="ddItem" :label-width="formLabelWidth">
		      <el-input v-model="itemForm.ddItem"></el-input>
		    </el-form-item>
			<el-form-item label="ddValue" :label-width="formLabelWidth">
		      <el-input v-model="itemForm.ddValue"></el-input>
		    </el-form-item>
		    
		     <el-form-item label="dgRemark" :label-width="formLabelWidth">
		      <el-input v-model="itemForm.dgRemark"></el-input>
		    </el-form-item>
		  </el-form>
		  <div slot="footer" class="dialog-footer">
		    <el-button @click="itemFormVisible = false">取 消</el-button>
		    <el-button type="primary" @click="submitItem('itemForm')">提交</el-button>
		  </div>
		</el-dialog>
	    
	    
	     <!--dialog 编辑group-->
	    <el-dialog title="编辑group" :visible.sync="editGroupFormVisible">
		  <el-form :model="editGroupForm">
		  	<el-form-item label="dgCode" :label-width="formLabelWidth">
		      <el-input v-model="editGroupForm.dgCode" disabled></el-input>
		    </el-form-item>
		  	 <el-form-item label="dgName" :label-width="formLabelWidth">
		      <el-input v-model="editGroupForm.dgName"></el-input>
		    </el-form-item>	 
			 <el-form-item label="dgRemark" :label-width="formLabelWidth">
		      <el-input v-model="editGroupForm.dgRemark"></el-input>
		  	 </el-form-item>
		     
		  </el-form>
		  <div slot="footer" class="dialog-footer">
		    <el-button @click="editGroupFormVisible = false">取 消</el-button>
		    <el-button type="primary" @click="saveGroup">保存</el-button>
		  </div>
		</el-dialog>
	    
	    
	    <!--dialog 编辑item-->
	    <el-dialog title="编辑itemValue" :visible.sync="editFormVisible">
		  <el-form :model="editForm">
		    <el-form-item label="dgName" :label-width="formLabelWidth">
		      <el-input v-model="editForm.dgName" disabled></el-input>
		    </el-form-item>
		     <el-form-item label="dgCode" :label-width="formLabelWidth">
		      <el-input v-model="editForm.dgCode" disabled></el-input>
		    </el-form-item>
		    <el-form-item label="ddItem" :label-width="formLabelWidth">
		      <el-input v-model="editForm.ddItem" disabled></el-input>
		    </el-form-item>
		    <el-form-item label="ddValue" :label-width="formLabelWidth">
		      <el-input v-model="editForm.ddValue" ></el-input>
		    </el-form-item>
		     <el-form-item label="ddRemark" :label-width="formLabelWidth">
		      <el-input v-model="editForm.ddRemark"></el-input>
		    </el-form-item>
		  </el-form>
		  <div slot="footer" class="dialog-footer">
		    <el-button @click="editFormVisible = false">取 消</el-button>
		    <el-button type="primary" @click="save">保存</el-button>
		  </div>
		</el-dialog>
	    
	    
		
	   
	</section>
</template>

<script>
	import {queryGroup,queryItem,deleteGroup,deleteItem,updateItem,updateGroup,addGroup,addItem,} from '../../api/api_database'
	import instance from '../../api/base'
	import {MessageBox} from 'element-ui'
	export default{
		data(){
			return{				
				tableLists:[],
				groupLists:[],
				value:'',
				dialogFormVisible:false,
				groupForm:{
					 dgName:"",
    				 dgCode:"",
    				 dgRemark:""
				},
				itemForm:{
					 ddItem:"",
   					 ddValue:"",
					dgCode:"",
					 dgName:"",	
    				 ddRemark:"",
				},
				formLabelWidth:"100px",
				editFormVisible:false,
				editGroupFormVisible:false,
				itemFormVisible:false,
				groupVisible:false,
				editGroupForm:{
					 dgName:"",
    				 dgCode:"",
    				 dgRemark:""
				},
				editForm:{
					 ddItem:"",
   					 ddValue:"",
					dgCode:"",
					 dgName:"",	
    				 ddRemark:"",
				},
				pageSize:50,
				pages:1,
				total:0,
				ruleItem:{
					itemType:[
						{ required: true, message: '请输入itemType', trigger: 'blur' },
					]
				},
			}
		},
		methods:{
			//查询group
			getGroupLists(){
				//var self = this;
				queryGroup().then((res)=>{
					debugger;
					console.log(res);
					//this.tableLists = res.data.data;
					this.groupLists = res.data.data.list;
				})
				/*instance.get('api/admin/dd/querydg').then((res)=>{
					console.log(res)
				})*/
				this.groupVisible = true;
			},
			//查询item
			getItemLists(){
				let params = {
					pageSize : this.pageSize,
					pageNum : this.pages,
				}
				queryItem(params).then((res)=>{
					console.log(res);
					
					if(res.data.code == 0){
						this.tableLists = res.data.data.list;
						this.total = res.data.data.total;
						//this.pages = res.data.data.pages;
					}
				})
			},
			
			//翻页
			handleCurrentChange(val){
				console.log(val);
				this.pages = val;
				console.log(this.pages);
				this.getItemLists();
			},
			
			//新增gourp
			addGroup(){
				this.dialogFormVisible = true;
			},			
			//submit
			submitGroup(){
				//console.log(123);
				//dialogFormVisible = true;
				var self = this;
				//console.log(this.groupForm);
				let params = this.groupForm;
				addGroup(params).then((res)=>{
					//console.log(res)
					
					if(res.data.code == 0){
						self.dialogFormVisible = false;
						self.groupForm = {};
						self.getGroupLists();
						self.$message({
							message:'添加成功'
						})
					}
					
				
				})
			},
			
			//新增item
			addItem(row){
				console.log(row);
				this.itemFormVisible = true;
				this.itemForm.dgCode = row.dgCode	
				this.itemForm.dgName = row.dgName
			},
			submitItem(formName){
				var self = this;
				 this.$refs[formName].validate((valid) => {
			          if (valid) {
			            //alert('submit!');
			            let params = self.itemForm;
						addItem(params).then((res)=>{
							console.log(res);
							if(res.data.code == 0){
								self.itemForm = {};
								self.itemFormVisible = false;
								self.getItemLists();
								self.$message({
									message:'添加成功'
								})
							}
							
						})
			          } else {
			            console.log('error submit!!');
			            return false;
			          }
			        });
				/**/
			},
			
			
			//删除Group
			removeGroup(index,row){
				//console.log(index,row);
				//this.tableLists.splice(index,1);
				var self = this;
				let params = {
					"dgCode": row.dgCode
				};
				
				this.$confirm('确定删除 '+ row.dgCode +' 吗?','提示',{
					confirmButtonText:'确定',
					cancelButtonText: '取消',
					type:'warning'
				}).then(()=>{
					deleteGroup(params).then((res)=>{
						//console.log(res);
						if(res.data.code == 0){
							self.getGroupLists();
							self.getItemLists();
							self.$message({
								message:row.dgCode + '已删除',
							})
						}
						
					})					
				}).catch(()=>{
					
				})
			},
			
			//删除item
			removeItem(index,row){
				console.log(index, row);
				var self = this;
				let params = {
					"dgCode":row.dgCode,
    				"ddItem":row.ddItem,
				};
				this.$confirm('确定删除此项吗?','提示',{
					confirmButtonText:'确定',
					cancelButtonText: '取消',
					type:'warning'
				}).then(()=>{
					deleteItem(params).then((res)=>{
						//console.log(res);
						//self.tableLists.splice(index,1);
						if(res.data.code == 0){
							self.getItemLists();
							self.$message({
								message:row.itemType + '已删除'
							})
						}
					})					
				}).catch(()=>{
					
				})
			},
			
			
			//编辑Group
			editGroup(index,row){
				console.log(index,row);
				this.editGroupFormVisible = true;
				this.editGroupForm = row;
			},
			
			saveGroup(){
				updateGroup(this.editGroupForm).then((res)=>{
					//console.log(res);
					if(res.data.code == 0){
						this.getGroupLists();
						this.editGroupFormVisible = false;
						this.$message({
							message:'已完成'
						})
					}
				})
			},
			
			//编辑itemValue
			editItem(index,row){
				console.log(index,row);
				this.editFormVisible = true;
				this.editForm = row;			
			},
			//编辑后 的保存
			save(){
				
				updateItem(this.editForm).then((res)=>{
					//console.log(res);
					//console.log(res.data.msg);
					
					if(res.data.code == 0){
						this.getItemLists();
						this.editFormVisible = false;
						this.$message({
							message:'已完成'
						})
					}
				})
			},
			//查询
			queryLists(){
				
			},
		},
		mounted(){
			
			
			this.getItemLists();
		}
	}
</script>

<style scoped="scoped">
</style>