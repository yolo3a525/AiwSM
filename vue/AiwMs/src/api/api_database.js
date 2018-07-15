import instance from './base'
let dd = 'dd';
let dg = 'dg';

//查询group
const queryGroup = params =>{
	return instance.get(`${dg}` + '/list',params)
}

//删除group
const deleteGroup = params =>{
	return instance.get(`${dg}` + '/delete/' + params.dgCode)
}
//更新gourp
const updateGroup = params =>{
	return instance.post(`${dg}` + '/update',params)
}
//新增Group
const addGroup = params =>{
	return instance.post(`${dg}` + '/save',params)
}



//查询item
const queryItem = params =>{
	return instance.get(`${dd}` + '/list',{ params : params })
}
//删除item
const deleteItem = params =>{
	return instance.get(`${dd}` + '/delete/' + params.dgCode + "/" + params.ddItem)
}

//更新item
const updateItem = params =>{
	return instance.post(`${dd}` + '/update',params)
}

//新增Item
const addItem = params =>{
	return instance.post(`${dd}` + '/save',params)
}

export{
	queryGroup,
	queryItem,
	deleteGroup,
	deleteItem,
	updateGroup,
	updateItem,
	addGroup,
	addItem,
}
