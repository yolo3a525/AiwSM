var $ = layui.jquery;

jacommon = {};

function _empty() {}

// 需要确认弹出框
jacommon.confirm = function (text, callback, title) {
	layer.confirm(text, {
        title: title || '操作提醒',
        btnAlign: 'c',
        resize: false,
        icon: 3,
        btn: ['确定', '取消'],
        yes: callback
    });
}

// 成功弹出提醒
jacommon.alertSuccess = function (text, title) {
    layer.alert(text, { title: title || '温馨提示', icon: 1, time: 5000, resize: false, zIndex: layer.zIndex, anim: Math.ceil(Math.random() * 6) });
}

// 错误弹出提示
jacommon.alertError = function (text, title) {
    layer.alert(text, { title: title || '操作提醒', icon: 2, time: 5000, resize: false, zIndex: layer.zIndex, anim: Math.ceil(Math.random() * 6) });
}

// 成功提醒
jacommon.success = function (text, callback) {
	layer.msg(text, { icon: 1, time: 500 }, callback || _empty); 
}

// 错误提示
jacommon.error = function (text, callback) {
	layer.msg(text, { icon: 2, time: 2000 }, callback || _empty); 
}

// ajax请求
jacommon.ajax = function(url, type, data, success, fail, complete) {
	if ($.isFunction(data)) complete = fail, fail = success, success = data, data = undefined;
	$.ajax({
        url: url,
        type: type,
        dataType: 'json',
        data: data,
        success: function (res) {
        	success(res);
        	//res.errorCode == 0 ? (success && success(res)) : (fail && fail(res));
        },
        error: function(xhr, status, error) {
        	(fail && fail({errorCode:-1, msg:error}, xhr, status, error));
        },
        complete: complete
    });
}

// ajax get请求
jacommon.ajaxGet = function(url, data, success, error, complete) {
	jacommon.ajax(url, 'get', data, success, error, complete);
}

// ajax post请求
jacommon.ajaxPost = function(url, data, success, error, complete) {
	jacommon.ajax(url, 'post', data, success, error, complete);
}



// 操作按钮组
var actions = {
	ajaxDelete: function(url) {
		jacommon.confirm('确认删除吗，此操作不可逆？', function() {
			jacommon.ajaxGet(url, function(res) {
				jacommon.success('删除成功', function() {
					window.location.reload();
				});
            }, function(res) {
            	jacommon.error('删除失败，'+res.msg+'(错误代码：'+res.errorCode+')');
            })
		});
	}
}
$(document).on("click", ".do-action", function(e) {
    var type = $(this).data("type")
    ,url = $(this).data('url');
    if(actions[type])
    	actions[type](url);
    else
    	window.location.href = url;
})



function loadJsonDataToForm(jsonStr,formId){
	var obj
	if(typeof(jsonStr) == "object"){
		obj = jsonStr
	}else{
		obj = eval("("+jsonStr+")");
	}
	var key,value,tagName,type,arr;
	for(x in obj){
		key = x;
		value = obj[x];
		$("#"+formId+" [name='"+key+"'],[name='"+key+"[]']").each(function(){
			tagName = $(this)[0].tagName;
			type = $(this).attr('type');
			if(tagName=='INPUT'){
				if(type=='radio'){
					$(this).attr('checked',$(this).val()==value);
				}else if(type=='checkbox'){
					value = value + "";
					arr = value.split(',');
					for(var i =0;i<arr.length;i++){
						if($(this).val()==arr[i]){
							$(this).attr('checked',true);
							break;
						}
					}
				}else{
					$(this).val(value);
				}
			}else if(tagName=='TEXTAREA'){
				$(this).val(value);
			}else if(tagName=='SELECT'){
				//$(this).val(value);
				//暂时注释掉,这个写法与layui的select组件不匹配.
			}
			
		});
	}
}

function setIframeHeight(iframe) {
	  if (iframe) {
	  var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
	  if (iframeWin.document.body) {
	  iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
	  }
	  }
	  };

$.prototype.serializeObject=function(){  
    var obj=new Object();  
    $.each(this.serializeArray(),function(index,param){  
        if(!(param.name in obj)){  
            obj[param.name]=param.value;  
        }  
    });  
    return obj;  
};  