
// insert face to textarea
(function($){
	$.fn.extend({
		insertAtCaret: function(myValue){
			var $t=$(this)[0];
			if (document.selection) {
				this.focus();
				sel = document.selection.createRange();
				sel.text = myValue;
				this.focus();
			}
			else if ($t.selectionStart || $t.selectionStart == '0') {
					var startPos = $t.selectionStart;
					var endPos = $t.selectionEnd;
					var scrollTop = $t.scrollTop;
					$t.value = $t.value.substring(0, startPos) + myValue + $t.value.substring(endPos, $t.value.length);
					this.focus();
					$t.selectionStart = startPos + myValue.length;
					$t.selectionEnd = startPos + myValue.length;
					$t.scrollTop = scrollTop;
			}
			else {
				this.value += myValue;
				this.focus();
			}
		}
	})
})(jQuery);

jQuery.cookie = function(name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        if (value === null) {
            value = '';
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
        }
        // CAUTION: Needed to parenthesize options.path and options.domain
        // in the following expressions, otherwise they evaluate to undefined
        // in the packed version for some reason...
        var path = options.path ? '; path=' + (options.path) : '';
        var domain = options.domain ? '; domain=' + (options.domain) : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
};

var types_dict;
var types={
	is_pad:function() {
		return ($('html').hasClass('ua-pad')); 
	},
	check_ie:function() {
		if (types.is_pad())
			return;
		
		if ($.browser.msie && ($.browser.version == "6.0" || $.browser.version == "7.0")) {
			if (!$.cookie('_ie6tip')) {
				$.cookie('_ie6tip', 1);
				notice.tip('你的浏览器只能获得有限体验，推荐升级到ie8以上或使用谷歌Chrome等现代浏览器');
			}
		}
	},
	init:function(uid, skip_login) {
		if (uid) types.uid=uid;
        types.skip_login = skip_login;
        board.togg();
		setTimeout(function(){ types._init(); }, 0);
	},
	_init:function() {
		/*
		if (!types.uid && !$('li.pl-auth')[0]) 
		{
			if (!types.skip_login) {
                user.login_tip();
                $('.pagenavr').css('top', $('#pagenav').offset().top);
            }
		}
		*/
		board.textarea_fixed();
		board.backtop();
		$('img.photo').on('click', function(){ photo.big_view(this); });
	},
	decode:function(resp) { return eval( "(" + resp + ")" ); },
	run:function(code) { eval(code); },
	pc:function(state)
	{
		if (state) $.cookie('pc', 1, 30);
		else $.cookie('pc', null);
		location.reload();
	},
	is_ie:function() { if (!$.support.style) return true; },
	is_ie6:function() { return $.browser.msie&&($.browser.version == "6.0"); },
	length:function(str)  
	{  
		var sum=0;
		for(var i=0; i<str.length; i++)
		{  
		 if ((str.charCodeAt(i) >= 0) && (str.charCodeAt(i) <= 255))
             sum = sum + 1;
		 else
             sum = sum + 2;
		}
		return sum;
	},
	seq:function(x){
		var seqs = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
		if (x < seqs.length) return ' ' + seqs[x];
		var ts = Math.floor(x / seqs.length);
		var ns = x % seqs.length;
		return seqs[ns]+ts;
	},
	cookieonoff:function(path){
		if (!$.cookie('crt')) $.cookie('crt', 1);
		if(!$.cookie('crt')) { notice.ad("<div class='red'>登陆需要开启Cookie</div>"); }
		else $.cookie('crt', null);
	},
	get:function(id) { return document.getElementById(id); },
	mock:function(o) { function F() {} F.prototype = o; return new F(); }
};

var pannel={
	load:function(){ return types.mock(this); },
	await:function(name){
		name = 'pannel-'+name;
		this.name = name;
		var htm = "<a class='pclose pull-right hand'>X</a><div style='clear:both;'></div><div class='pdata'></div>";
		htm = "<div class='pannel' id='"+name+"'>" + htm + "</div>";
		$('body').append(htm);

		var who=this;
		$("#"+name+" .pclose").click(function(){ who.hide(); return false; });
		this.jq().on('click', function(e){ e.stopPropagation(); });
		return this;
	},
	close:function() { name=this.name;eval(name.substring(7)+'.pannel=undefined;'); $('#'+name).remove(); },
	draw:function(htm, w,h) {
		if (!htm) return; $('#'+this.name+" .pdata").html(htm); 
		if (w) { this.w=w; this.jq().css('width', w+'px'); } 
		if (h) { this.h=h; this.jq().css('height', h+'px'); } 
		return this; 
	},
	jq:function(){ return $('#'+this.name); },
	resize:function(w, h) { 
		return this; 
	},
	iframe:function(src, w, h) { if (!w) w = "400"; if (!h) h = "80"; 
		this.draw("<iframe src='"+src+"' style='width:"+w+"px; height:"+h+"px;'></iframe>"); return this; },
	postion:function(who)
	{
		if (!who) who = '#indent';
		var o = $(who).offset();
		var w=this.jq().width();
		var h=this.jq().height();
		var l=o.left;
		var t=o.top;
		var st=$(window).scrollTop();
		
		t = ($(window).height()+st-t-h-50<0) ? (t-h+18) : (t+18);
		t = (t > st+50) ? t : ($(window).height()-h)/2+st;
		t = (t > st+50) ? t : st+50; 

		l = ($('#indent').offset().left+$('#indent').width() > l+w+30) ? l
				: $('#indent').offset().left + $('#indent').width()-w-30;
		if (l < $('#indent').offset().left) l = $('#indent').offset().left;

		this.jq().css('left', l).css('top', t);
		return this;
	},	
	toggle:function(sec)
	{
		(this.onoff == true) ? this.hide(sec) :  this.show(sec);
		return this;
	},
	show:function(sec)
	{
		if (!sec) sec=0;
		this.onoff = true;
		this.jq().show(sec);
		if (event && event.stopPropagation) event.stopPropagation(); 
		else if (window.event) window.event.cancelBubble = true; 
//			e.stopPropagation();
		who = this;
		$('body').one('click', function(){
			who.hide();
		});

		return this;
	},
	real:function() { this.real=true; return this; },
	close_callback:function(cb) { this.close_callback=cb; return this; },
	hide:function(sec)
	{
		if (!sec) sec=0;
		this.onoff = false;
		this.real ? this.close() : this.jq().hide(sec);
		if (this.close_callback) {
			var func = this.close_callback;
			func();
			if (this.real) this.close_callback = null;
		}
		return this;
	}
};

var clever={
	bind:function(who, data)
	{
		$(who+' select').on('change', function(){
			$(this).attr('val', $(this).val());
			clever.plot($(this).attr('c'));
		});
		clever.plot($(who+' select:first-child').attr('id'), data);
	},
	plot:function(who, data)
	{
		if (who == undefined) return;
		if (data == undefined) 
		{ 
			p=$('#'+$('#'+who).attr('p') ); 
			data = p.data(p.val());
		}
		//if (data == undefined)  return;
		//alert($('#'+who).attr('id'));

		var val='';
		var htm = '<option></option>';
		if (data != undefined)
		{
			for(var x1=0; x1<data.length; x1++)
			{
				if (!val && (data[x1].id+'') == $('#'+who).attr('val')) val = data[x1].id;
				$('#'+who).data(data[x1].id, data[x1].children);
	
				htm += "<option value='"+data[x1].id+"'>"+data[x1].name+"</option>";
			}
		}
		$('#'+who).html(htm);
		if (val) $("#"+who+" option[value='"+val+"']").attr("selected","true");

		if ($('#'+who).val() == '') 
		{
			$('#'+$('#'+who).attr('c')).html('');
			$('#'+who).attr('val', '');
		}
		clever.plot($('#'+who).attr('c'));
	},
	select:function(name, title, level, attr)
	{
		if (!title) title = '分类';
		if (!level) level = 3;
		
		var htm = '';
		if (title) htm = title;
		
		htm += '<select id="g'+name+'" c="p'+name+'" val="'
			+attr.gid+'" name="g'+name+'_id" class="input-'+(attr.gstyle||'medium')+'"></select>';
		if (level > 1)
			 htm += ' <select id="p'+name+'" p="g'+name+'" c="'+name+'" val="'
			 	+attr.pid+'" name="p'+name+'_id" class="input-'+(attr.pstyle||'medium')+'"></select>';
		if (level > 2)
			htm += ' <select id="'+name+'" p="p'+name+'" val="'+attr.sid+'" name="'+name+'_id" class="input-'
				+(attr.sstyle||'medium')+'"></select>';
		
		return '<div class="inline '+name+'">' + htm + '</div>';
	}
};

var tool={
	section:function(who) { return tool.wrap(who, '[h2:', ']'); },
	title:function(who) { return tool.wrap(who, '[h3:', ']'); },
	link:function(who) { return tool.wrap(who, '[link:', ']'); },
	strong:function(who) { return tool.wrap(who, '[b:', ']'); },
	segment:function(who) { return tool.wrap(who, '--', '--'); },
	table:function(who) { return tool.append(who, "\n[table:\n||\n]\n", 9); },
	list:function(who) { return tool.append(who, "\n[list:\n\n]\n", 8); },
	append:function(who, tag, len) {
		var rel = $(who).attr('rel') || 'leave_msg';
		rel = $('#'+rel);
		$t = rel[0];
		
		if (document.selection) {
			rel.insertAtCaret(tag);
		}
		else if ($t.selectionStart || $t.selectionStart == '0') {
				var startPos = $t.selectionStart;
				rel.insertAtCaret(tag);
				$t.selectionStart = startPos + len;
				$t.selectionEnd = startPos + len;
		}
		else {
			this.value += myValue;
			this.focus();
		}
		
		return false;
	},
	wrap:function(who, tag_start, tag_end) {
		var rel = $(who).attr('rel') || 'leave_msg';
		rel = $('#'+rel);
		$t = rel[0];
		
		if (document.selection) {
			sel = document.selection.createRange();
			rel.insertAtCaret(tag_start+sel.text+tag_end);
		}
		else if ($t.selectionStart || $t.selectionStart == '0') {
				var startPos = $t.selectionStart;
				var endPos = $t.selectionEnd;
				var txt = $t.value.substring(startPos, endPos);
				rel.insertAtCaret(tag_start+txt+tag_end);
				if (!txt) {
					$t.selectionStart = startPos + tag_start.length;
					$t.selectionEnd = startPos + tag_start.length;
				}
		}
		return false;
	},
	bar:function(rel, tools, host, style){
		var ts = {"facet":["表情", "return face.click(this);"],
				"photot":["图片","return photo.click(this);"],
				"sectiont":["大标题","return tool.section(this);"],
				"titlet":["标题","return tool.title(this);"],
				"strongt":["粗体","return tool.strong(this);"],
				"linkt":["链接","return tool.link(this);"],
				"tablet":["表格","return tool.table(this);"],
				"listt":["列表","return tool.list(this);"],
				"segmentt":["分割线","return tool.segment(this);"]
		};
		
		var htm = '';
		for(var t in tools){
			t = tools[t];
			if (!ts[t]) continue;
			if (t == 'photot')
			{
				if (!host) continue;
				htm += '<a href="javascript:;" rel="'+rel+'" class="'+t+'" host="'+host+'" onclick="'+ts[t][1]+'">'+ts[t][0]+'</a>';
			}
			else
			{
				htm += '<a href="javascript:;" rel="'+rel+'" class="'+t+'" onclick="'+ts[t][1]+'">'+ts[t][0]+'</a>';
			}
		}
		
		return '<div class="tool '+(style||'')+'">' + htm + '</div>';
	}
};

var face={
	_init:function()
	{
		if (!face.pannel || !face.pannel.name)
		{
			var faces = ['微笑','撇嘴','色','发呆','得意','流泪','害羞','闭嘴','睡','大哭','尴尬','发怒','调皮','龇牙','惊讶',
	 			'难过','酷','冷汗','抓狂','吐','偷笑','可爱','白眼','傲慢','饥饿','困','惊恐','流汗','憨笑','大兵',
	 			'奋斗','咒骂','疑问','嘘','晕','折磨','衰','骷髅','敲打','再见','擦汗','抠鼻','鼓掌','糗大了','坏笑',
	 			'左哼哼','右哼哼','哈欠','鄙视','委屈','快哭了','阴险','亲亲','吓','可怜','菜刀','西瓜','啤酒','篮球','乒乓',
	 			'咖啡','饭','猪头','玫瑰','凋谢','示爱','爱心','心碎','蛋糕','闪电','炸弹','刀','足球','瓢虫','便便',
	 			'月亮','太阳','礼物','拥抱','强','弱','握手','胜利','抱拳','勾引','拳头','差劲','爱你','NO','OK',
	 			'爱情','飞吻','跳跳','发抖','怄火','转圈','磕头','回头','跳绳','挥手','激动','街舞','献吻','左太极','右太极'];
 			var htm='';
 			for(var x1=0;x1<faces.length;x1++){
 				htm += "<a href='#' title='"+faces[x1]+"' rel='"+x1+"'></a>";
 			}

 			face.pannel = pannel.load().await('face').draw(htm);
 			face.pannel.jq().css('width', 456);

			$('#'+face.pannel.name+' .pdata a').each(function(){
				$(this).bind('click', function(){
					$('#'+face.target).insertAtCaret('['+$(this).attr('title')+']');
					face.pannel.hide();
					return false;
				});
			});
		}
	},
	trip:function(htm)
	{
		return htm.replace(/<img[^>]*alt=['"](\[[^[>]*\])['"][^>]*>/g, "$1");
	},
	click:function(who)
	{
		face._init();
		face.target = $(who).attr('rel') || "leave_msg";
		face.pannel.postion(who).toggle();
	}
};


var board={
	textarea_fixed:function(who)
	{
		// f:70, esc:27
		if (!who) who='.msgfixed textarea';

		$(who).on('keydown', function(e) {
			if (!e) e = window.event;
			if (e.ctrlKey && e.keyCode == 13){
				$(this).parents('form:first').find('input[type="submit"]').click();
			  }
		}).on('keyup', function(){
			if (!$(this).attr('orih')) $(this).attr('orih', $(this).outerHeight());
			var htm;
			if (types.is_ie())
				htm=$(this).val().split("\n");
			else
				htm=$(this).val().split(/\r?\n|\r/g);
			var max = $(this).width() * 80 / 526; //  6: 290 / 40, 526/80
			var len=0;
			for(var x1=0; x1<htm.length; x1++)
			{
				len += (htm[x1].length<1) ? 1 : Math.ceil((types.length(htm[x1])+10)/max);
			}
            if (types.is_pad())
                len = len * 30 + 12;
            else
                len = len * 20 + 12;
			var rel = $(this).attr('orih');
			if (len < rel) len=rel;
			$(this).css('height', len);
		});
	},
	toolbar:function(who)
	{
	    $('#toolbar').show().css((function()
	    {
	    	var w=$('#toolbar').width();
	    	if (w>350) w=350;
	    	if (w<150) w=150;
	    	var ww=who.width();
	    	ww=ww+who.offset().left-w-12;
	    	var tt=who.offset().top-$(window).scrollTop();
	    	if (tt<0) tt=0;
	    	return {width:w+"px", left:ww+"px", top:tt+"px"};
	    })());
	},
	backtop:function()
	{
        if (!types.is_pad())
        {
        	$(window).scroll(function() {
            	var st = $(this).scrollTop();
//	            	var t = $('#indent').offset().top;
//	           		$('#toolbar').css((st>t) ? {position:'fixed',top:'0'} : {position:'absolute',top:t});
                (st > 100) ? $('#back-top').show() : $('#back-top').hide();
            });

        	$('#back-top').css({left:($(window).width()+840)/2+"px", top:($(window).height()-120)+"px"});
        }

		$('#back-top').on('click', function(){ $('body,html').animate({scrollTop:0},100); });
	},
	remove:function(who, delay)
	{
		if (!delay) delay = 1000;

		setTimeout(function(){
			if (types.is_pad()) $(who).remove();
			else $(who).animate({ width:0, height:0}, delay, 'swing', function(){ $(who).remove(); });
		}, delay);
	},
	width:function(who, auto){
		var w = $(who).outerWidth();
		if (w > $('#indent').width()) w=$('#indent').width()-40;
		if (auto) w=$('#indent').width()-40;
		if (w>560) return 560;
		return w;
	},
	photo_fixed:function(w){
		if (!w) w=100;
		$(".imgfixed img").each(function(){
			if ($(this).attr('width') > w)
			{
				$(this).css({
                    'height': ($(this).attr('height') / $(this).attr('width') * w)+'px',
                    'width': w+'px'
                });
			}
		});
	},
	more:function(who, str)
	{
		if (!str) str = '展开';
		$(who).css('height', '0px').prepend('<div class="more hand">'+str+'</div>');
		$(who+' .more').css('float', 'right').css('color', 'red')
		.css('margin-right', '10px').css('margin-top', '-5px')
		.on('click', function(){
			$(who).css('height', 'auto');
			$(this).hide();
		});
	},
	togg:function(who){
		if (!who) who='#indent .tline';
		$(who).each(function(){
			$(this).bind("mouseover", function(){
				$(this).find('.tg').show();
			}).bind("mouseout", function(){
				$(this).find('.tg').hide();
			});
		});
	},
	none:function() {  }
};





var rc={
    base_path: location.pathname,
    path:function(path){
        if (path.substring(0, 1) == '/') return path;
        return this.base_path + '/' + path;
    },
    start:function(menus){
        rc_play.start(menus);
    },
	click:function(act){
		location.href=act;
		return false;
	},
    play:function(name, cfm){
        return rc_play.play(name, cfm);
    },
    edit:function(act, name){
        rc_editor.edit(act, name);
    },
    act:function(act, cfm){
        return rc_base.http_get(rc.path(act),cfm);
    },
    form:function(who, data, act){
        rc.loading('提交中...');
        $.post((act || $(who).attr('action')), data, function(resp){
            rc.resp = resp;
            $('.waiting').remove();
            if (resp.state == 200){
                rc_parser.parse_data(resp.data, who);
            } else {
                alert(resp.state);
            }
        });
        return false;
    },
    submit:function(who, cfm){
        $('form span.error').remove();
        if (rc.before_validator){
            if (!rc.before_validator()) {
                return false;
            }
        }
        return rc_base.http_form(who, cfm);
    },
    dialog:function(path, who){
        console.log(path);
        $.get(path, function(resp){
            $('#'+who+'Modal .modal-content').html(resp);
        });
    },
    json:function(who, data){
        return rc_base.http_json(who, data);
    },
    clear_errors:function(){
        $('div.field').removeClass('has-error');
        $('div.field span.error').remove();
    },
    errors:function(errors){
        rc.clear_errors();
        for (var x1 in errors){
            $('div.'+x1).append('<span class="error">'+errors[x1]+'</span>');
            $('div.'+x1).addClass('has-error');
        }
    },
    widget:function(sc){
        var script= document.createElement("script");
        script.type = "text/javascript";
        script.src = sc;
        $('script:first').after(script);
    },
    loading:function(msg, style){
        if (!msg) msg = '加载中...';
        if (!style) style = 'info';
        if ($('.waiting').length > 0) $('.waiting').remove();
        $('body').before('<div class="waiting alert alert-'+style+'">'+msg+'</div>');
        $('.waiting').css({
            'position': 'fixed',
            'top': $('#header').offset().top+$('#header').height(),
            'left': '50%',
            'color':'#000000',
            'margin-left': -$('.waiting').width()/2-40,
            'padding': '5px 40px 5px 40px'
        });
    },
    say:function(msg, style){
        if (rc.ti_say) clearTimeout(rc.ti_say);
        if (!style) style = 'warning';
        rc.loading(msg, style);
        rc.ti_say = setTimeout(function(){ $('.waiting').remove(); }, 3000);
    },
    alert:function(msg, style, who){
        if (!msg) return;
        $(who || '#indent').prepend('<h6 class="alert alert-'+(style||'info')+'">'+msg+'</h6>');
    },
    remove:function(who, delay){
		if (!delay) delay = 1000;
        $(who).addClass('active2');
		setTimeout(function(){
			if (types.is_pad()) $(who).remove();
			else $(who).animate({ width:0, height:0}, delay, 'swing', function(){ $(who).remove(); });
		}, delay);
    }
};

var rc_base={
    btnbuff:function(who){
        if (!who) return false;
        return rc_base._btnbuff(who);
		//if (!who) who='form .btn[type="submit"]';
		//$(who).on('click', function(){
		//	return rc_base._btnbuff(who);
		//});
	},
	_btnbuff:function(who, msg){
		if (rc.sending > 0)
		{
			if (!msg) msg = '正在处理数据，请稍后';
            rc.say(msg + ' <span class="sending_time">'+rc.sending+'</span>');
			return true;
		}

		rc.sending = 10;
		if (rc.si) clearInterval(rc.si);
		rc.si = setInterval(function(){
			if ($('.sending_time')[0]) $('.sending_time').text(rc.sending);
			if (rc.sending>0) { rc.sending--; } else { clearInterval(rc.si); }
		}, 500);
		return false;
	},
    http_get:function(act, cfm){
        if (cfm && !confirm(cfm)) {
            return false;
        }
        rc.loading();
        $.get(act, function(resp){
            rc.resp = resp;
            $('.waiting').remove();
            if (resp.state == 200){
                rc_parser.parse_data(resp.data);
            } else {
                rc_parser.state_code(resp);
            }
        });
        return false;
    },
    http_form:function(who, cfm){
        if (cfm && !confirm(cfm)) {
            return false;
        }
        if (rc_base.btnbuff(who)) {
            return false
        }
        data = $(who).serializeArray();
        rc.loading('提交中...');
        $.post($(who).attr('action'), data, function(resp){
            rc.resp = resp;
            $('.waiting').remove();
            if (resp.state == 200){
                rc_parser.parse_data(resp.data, who);
                if ($(who).attr('data-reset')) $(who)[0].reset();
            } else {
                rc_parser.state_code(resp);
            }
        });
        return false;
    },
    http_json:function(who, data){
        if (rc_base.btnbuff(who)) {
            return false
        }
        rc.loading('提交中...');
        if (window.JSON == undefined) rc.widget('http://lib.sinaapp.com/js/json2/json2.js');
        $.ajax({
            url: $(who).attr('action'),
            data: JSON.stringify(data),
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (resp) {
                rc.resp = resp;
                $('.waiting').remove();
                if (resp.state == 200){
                    rc_parser.parse_data(resp.data, who);
                } else {
                    rc_parser.state_code(resp);
                }
            },
            error: function (message) { rc.say('提交数据失败'); }
        });
        return false;
    }
};

var rc_editor={
    edit:function(act, name){
        if (!types.uid) {
            rc.say('请先登录...');
            return false;
        }
        if (act == 'reply_at'){
            this.__reply_at_act(name);
            return false;
        } else if (act == 'reply_hole'){
            this.__reply_hole_act(name);
            return false;
        } else if (act == 'reply_holes'){
            htm = this.__reply_holes_act(name);
            return false;
        } else if (act == 31112345){
            htm = this.__plan_group_act(name);
            return false;
        }

        if (rc.pannel) rc.pannel.close();

        var htm, cb;
        if (act == 'reply_host') {
            htm = this.__reply_host_htm(name);
            cb = this.__reply_host_callback;
            act = '/=/msg.reply';
        }  else if (act == 'edit_tags') {
            htm = this.__edit_tags_htm(name);
            cb = function(){ $('#pannel-rc input[name="tags"]').focus().val($(name+' div').text().replace(/\(-?\d*\)/g, ' ')); };
            act = '/=/common.tag';
        } else if ($(name).hasClass('edit-line')) {
            htm = this.__edit_line_htm(name)
        } else {
            htm = this.__edit_area_htm(name);
            cb = function(){ board.textarea_fixed('#pannel-rc textarea'); };
        }

        htm = '<form method="post" action="'+act+'" onsubmit="return rc.submit(this);">'
            + htm + '</form>';

        rc.pannel = pannel.load().await('rc').draw(htm);
        $('#pannel-rc').addClass('box');
        rc.pannel.postion().toggle();

        if (cb) cb(name);
    },
    __edit_area_htm:function(name){
        var htm = '';
        var data = ($(name).attr('data') || '');
        var data2 = data.split(',');
        var content = photo.trip($(name).html());
        content = content.replace(/\n/g, '').replace(/<br[^>]*>/g, "\n");

        htm += '<div class="field msgfixed" style="margin-bottom:1px;">';
        htm += '<textarea id="dialog-content" class="span7 '+(data2[0]||'div2')+'" name="content">'+content+'</textarea>';
        htm += '</div>';
        htm += '<div class="field span7" style="margin-top:1px;">';
        if (data.indexOf('face') != -1)
            htm += '<div class="tool left"><a href="javascript:;" class="facet" rel="dialog-content" onclick="return face.click(this);">表情</a></div>';
        htm += '<div class="right"><input type="submit" class="btn btn-success btn-xs span1" value="保存" /></div>';
        htm += '</div>';
        return htm;
    },
    __edit_line_htm:function(name){
        var htm = '';
        htm += '<div class="field">';
        htm += '<input type="text" class="span6" name="content" value="'+$(name).text()+'" />';
        htm += ' <input type="submit" class="btn btn-success btn-xs" value="保存" />';
        htm += '</div>';
        return htm;
    },
    __reply_host_callback:function(name){
		$('#pannel-rc textarea').focus().val($('#pannel-rc textarea').val()+' ');
		$('li.active').removeClass('active');
		$(name).addClass('active');
        board.textarea_fixed('#pannel-rc textarea');

        var tid = $('#thread_ajax_list').attr('rel');
		if (tid)
		{
			$.get("/=/msg.thread/"+tid+"?a=1", function(resp){
				if (resp.state == 200) {
					$("#thread_ajax_list").html(resp.data.htm);
				}
			});
		}
    },
	__reply_host_htm:function(name){
        var htm = '';
        var rel = ($(name).attr('rel') || '');
        var rels = rel.split(',');
        var host=rels[0], host_id=rels[1], tid=rels[2], style=rels[3]||'board';
        var pname = $(name).attr('p') || '';
        var uname = $(name+' .u').text() || '';
        var mid = name.substring(2);

        if (pname) pname = '在 ' + pname + ' ';
        htm += '<h3>'+pname+'回复 '+uname+'</h3>';
        htm += '<div class="field msgfixed" style="margin-bottom:1px;">';
        htm += '<textarea id="dialog-content" class="span7 div2" name="content">@'+uname+'</textarea>';
		htm += '<input type="hidden" name="host" value="'+host+'" />';
		htm += '<input type="hidden" name="host_id" value="'+host_id+'" />';
		htm += '<input type="hidden" name="mid" value="'+mid+'" />';
		htm += '<input type="hidden" name="style" value="'+style+'" />';
        htm += '<input type="hidden" name="box" value="reply_host" />';
        htm += '</div>';
        htm += '<div class="field span7" style="margin-top:1px;">';
        htm += '<div class="tool left"><a href="javascript:;" class="facet" rel="dialog-content" onclick="return face.click(this);">表情</a></div>';

        htm += '<div class="right">';
        if (host != 'feed')
		{
			htm += '<label><input type="checkbox" name="sync_feed" />转发</label> &nbsp;';
		}
        htm += '<input type="submit" class="btn btn-success btn-xs span1" value="回复" /></div>';
        htm += '</div><div class="clear"></div>';

        if (tid && tid != '0')
        {
            htm += '<div id="thread_ajax_list" rel="'+tid+'" class="section "></div>';
        }
        return htm;
	},
	__edit_tags_htm:function(name)
	{
        var data = ($(name).attr('data')||'').split(',');
        var host=data[0], host_id=data[1];
        var content = $(name+' div').text();
		var htm = '';
        if (host == 'topic')
            htm += "<h3>设置话题：<span class='gray'>多个话题用空格分开</span></h3>";
        else
            htm += "<h3>设置标签：<span class='gray'>多个标签用空格分开</span></h3>";
		htm += '<div class="field">';
        htm += '<input type="text" class="span6" name="tags" value="'+content+'" />';
		htm += '<input type="hidden" name="host" value="'+host+'" />';
		htm += '<input type="hidden" name="host_id" value="'+host_id+'" />';
		htm += ' <input type="submit" class="btn btn-success btn-xs" value="保存" />';
        htm += '</div>';

        return htm;
	},
    __reply_at_act:function(name){
        var box = "#"+($(name).attr('box') || 'content');
		var val = $(box).val();
		var u = $(name + ' .u').text();
		$(box).focus().val(val + (u ? "@"+u+" " : ""));
	},
    __reply_hole_act:function(name) {
		var mid = name.substring(2);
		var box = $(name).attr('box') || 'content';
		var val = $("#"+box).val();
		$("#"+box).val(val + ' //#'+mid+'# ').focus();
	},
    __reply_holes_act:function(name){
				var who = $(name);
		$('li.active').removeClass('active');
		var htm = '<div class="alert alert-warning"><h3>正在加载中，请稍后...</h3></div>';

        rc.pannel = pannel.load().await('rc').draw(htm).real();
        $('#pannel-rc').addClass('box');
        rc.pannel.postion().toggle();

		$.get("/=/hole.recently/"+name.substring(2), function(data){
			if (data != '') {
				rc.pannel.draw(who.html()+data);
				$('#pannel-rc h3').addClass('well');
				$('#pannel-rc textarea').focus();
				who.addClass('active');
			}
		});
		return false;var who = $(name);
		$('li.active').removeClass('active');
		var htm = '<div class="alert alert-warning"><h3>正在加载中，请稍后...</h3></div>';
        rc.pannel = pannel.load().await('rc').draw(htm).real();
        $('#pannel-rc').addClass('box');
        rc.pannel.postion().toggle();
		$.get("/=/hole.recently/"+name.substring(2), function(data){
			if (data != '') {
				rc.pannel.draw(who.html()+data);
				$('#pannel-rc h3').addClass('well');
				$('#pannel-rc textarea').focus();
				who.addClass('active');
			}
		});
		return false;
    },
    __plan_group_act:function(name) {
		$('li.active').removeClass('active');
		var htm = '<div class="alert alert-warning"><h3>正在加载中，请稍后...</h3></div>';
        rc.pannel = pannel.load().await('rc').draw(htm).real();
        $('#pannel-rc').addClass('box');
        rc.pannel.postion().toggle();
		$.get("/=/plan.groupit?group="+name, function(resp){
			if (resp.state == 200) {
				rc.pannel.draw(resp.data.htm);
				$('#pannel-rc h3').addClass('well');
				$('#pannel-rc textarea').focus();
			}
		});
		return false;
    }
};

var rc_parser={
    state_code:function(resp){
        if (resp.data && resp.data.msg) rc.say(resp.data.msg);
        else if (resp.data && resp.data.say) rc.say(resp.data.say);
        else if (resp.state == 401) rc.say('请先登录...');
        else if (resp.state == 403) rc.say('权限不够...');
        else if (resp.state == 404) rc.say('对象不存在...');
        else if (resp.state == 500) rc.say('系统错误...');
        else rc.say(resp.state);
    },
    htm:function(data){
        if (data['after']) { $(data['after']).after(data['htm']);
        } else if (data['before']) { $(data['before']).before(data['htm']);
        } else if (data['append']) { $(data['append']).append(data['htm']);
        } else if (data['prepend']) { $(data['prepend']).prepend(data['htm']);
        } else {
            $(data['html'] || '#sub-play').html(data['htm']);
        }
    },
    update_or_replace:function(data, replace){
        if (typeof(data) == 'string') {
            if (replace)
                $(data).replaceWith(rc.resp.data.htm)
            else
                $(data).html(rc.resp.data.htm);
            return
        }
        for (var x1 in data){
            if (replace)
                $(x1).replaceWith(data[x1])
            else
                $(x1).html(data[x1]);
        }
    },
    parse_data:function(data){
        rc.clear_errors();
        if (!data) return;
        if (data['say'] || data['msg']) {
            rc.say(data['say'] || data['msg']);
        }
        if (data['alert']) {
            if (typeof(data['alert']) == 'string'){
                data['alert'] = {'msg': data['alert']}
            }
            rc.alert(data['alert'].msg, data['alert'].style, data['alert'].who);
        }
        if (data['noop']) return;
        if (data['path']) {
            if (data['call_out']) {
                setTimeout(function(){ location.href = data['path']; }, data['call_out'])
            } else {
                location.href = data['path'];
            }
        }
        if (data['hash']) {
            rc.play(data['hash']);
        }
        if (data['remove']){
            rc.remove(data['remove']);
            //$(data['remove']).fadeOut(1000, function(){ $(this).remove();	});
        }
        if (data['close_modal']) {
            $('#'+data['close_modal']+'Modal').modal('hide');
        }
        if (data['htm']) {
            this.htm(data);
        } else {
            if (data['after']) {
                for (var x1 in data['after']){
                    $(x1).after(data['after'][x1]);
                }
            }
        }
        if (data['val']) {
            for (var x1 in data['val']){
                $(x1).val(data['val'][x1]);
            }
        }
        if (data['attr']) {
            for (var x1 in data['attr']){
                $(data['attr'][x1]['name']).attr(data['attr'][x1]['attr'], data['attr'][x1]['val']);
            }
        }
        if (data['update']) {
            this.update_or_replace(data['update']);
        }
        if (data['replace']) {
            this.update_or_replace(data['replace'], true);
        }
        if (data['errors']) {
            rc.errors(data['errors']);
        }
        if (data['gray']){
            $(data['gray']).toggleClass('gray');
        }

        if (data['panel']){

        }
        if (data['widget']) {
            if (typeof data['widget'] == 'string'){
                data['widget'] = [data['widget']];
            }
            for (var x1 in data['widget']){
                var sc = data['widget'][x1];
                if ($('[src="'+sc+'"]').length < 1) {
                    rc.widget(sc);
                }
            }
        }
        if (data['script']) {
            eval(data['script']);
        }
    }
};

var photo={
    path:"/=/site.upload/",
    preview: '',
	_init:function()
	{
		if (!photo.pannel || !photo.pannel.name)
		{
            path = photo.path+photo.host;
            if (photo.preview){
                path += '?preview=' + photo.preview;
            }
			photo.pannel = pannel.load().await('photo').iframe(path, 400, 80);
		}
	},
    set_preview:function(img, selection) {
        if (!selection.width || !selection.height)
            return;
        photo.x1 = selection.x1;
        photo.y1 = selection.y1;
        photo.x2 = selection.x2;
        photo.y2 = selection.y2;
    },
	done:function(resp)
	{
        this.resp = resp;
		if (resp.state == 200)
		{
            if (resp.data.preview) {
                $('#'+resp.data.preview).html('<img src="'+resp.data.attr[0].val +'" />');
                photo.sel = $('#'+resp.data.preview+' img').imgAreaSelect({
                    x1: 0, y1: 0, x2: 180, y2: 180,
                    aspectRatio: '1:1', handles: true,
                    onSelectChange: photo.set_preview
                });
                if ($('a.preview_ok').length < 1)
                    $('#'+resp.data.preview).before('<a href="javascript:;" class="preview_ok btn btn-success">保存新头像</a>');
                $('a.preview_ok').on('click', function(){
                    var path = '/=/user.avatar/'+resp.data.photo_id;
                    path += '?x1='+(photo.x1||0)+'&y1='+(photo.y1||0)+'&x2='+(photo.x2||180)+'&y2='+(photo.y2||180);
                    return rc.act(path);
                });
            }
            else if ($('#'+photo.target).length > 0)
            {
                //$('#photos').val(resp.data.photo_id + ',' + $('#photos').val() );
                $('#'+photo.target).insertAtCaret('[图片'+resp.data.photo_id+']');
            }
			setTimeout(function(){ $('#pannel-photo').remove(); }, 1);
			rc.say(resp.msg);
            rc_parser.parse_data(resp.data);
		}
		else
		{
            rc.say(resp.msg);
			photo.pannel.iframe(photo.path+photo.host, 420, 80);
		}

		return false;
	},
	click:function(who)
	{
		photo.target = $(who).attr('rel') || "leave_msg";
		photo.host = $(who).attr('host');
        photo.preview = $(who).attr('preview') || '';

		photo._init();
		photo.pannel.postion(who).toggle();

		return false;
	},
    big_view:function(who)
	{
        var path = $(who).attr('src').replace(/(.*\/)([^\/]*)/g, '$1_$2');
        var htm = '<div><img onload="(function(){photo.pannel.jq().css(\'width\', \'auto\'); photo.pannel.postion();})();" src="'+path+'" alt=""></div></div>';
        photo.pannel = pannel.load().await('photo').draw(htm);
        photo.pannel.jq().addClass('box');
        photo.pannel.postion().toggle();
	},
    trip:function(htm){
        return htm.replace(/<img[^>]*alt=['"](\[[^[>]*\])['"][^>]*>/g, "$1");
    }
};

var rc_play={
    start:function(menus){
        var htm = '';
        var x0 = 0;
        for (var x1 in menus){
            x0 += 1;
            var lb = x0 > 1 ? 'lb' : '';
            if (menus[x1].path) {
                htm += '<a class="'+lb+'" href="'+menus[x1].path+'">' + menus[x1].name + '</a>';
            } else {
                htm += '<a class="play '+lb+'" href="javascript:;" rel="'+menus[x1].slug+'">' + menus[x1].name + '</a>';
            }
        }
        $('#sub-bar').append(htm);
        $('#sub-bar a[href="'+location.pathname+'"]').addClass('now');

        $('#sub-bar a.play').on('click', function(){
            rc.play($(this).attr('rel'));
            return false;
        });
        if (location.hash){
            rc.play(location.hash.substring(1));
            rc.act(location.hash.substring(1));
        }

        $(window).on('hashchange', function() {
            if (location.hash) {
                rc.act(location.hash.substring(1));
            }
        });
    },
    play:function(name, cfm){
        if (cfm && !confirm(cfm)) {
            return false;
        }
        location.hash = name;
        $('#sub-bar a').removeClass('now');
        $('#sub-bar a').each(function(){
            if (name.indexOf($(this).attr('rel')) > -1) {
                $(this).addClass('now');
            }
        });
        return false;
    }
};


var page={
	_notices:'',
	notice:function()
	{
		msgs = page._notices;
		if (!msgs) return;
		for(var name in msgs)
		{
			if (!name || !msgs[name]) continue;
			page[name](msgs[name]);
		}
	},
	bannertop:function(msg) { notice.banner(msg, 'top')},
	bannermid:function(msg) { notice.banner(msg, 'mid')},
	ad:function(msgs)
	{
		var htm='';
		for(var m in msgs) htm+=msgs[m]+'<br />';
		notice.ad(htm);
	},
	tip:function(msgs)
	{
		notice.tip(msgs, 10000);
	},
	error:function(msgs)
	{
		var first;
		for(var name in msgs)
		{
			if (!first) first = name;
			page.vbox(name, msgs[name], -1, 10000);
		}
		$('#'+first).focus();
	},
	vbox:function(name, htm, sec)
	{
		if (!htm) return false;
		var who = ($('.vform [name="'+name+'"]')[0]) || ($('.vform :last')[0]);

		if (!who) return false;
		
		setTimeout(function(){
			notice.vbox(name, htm, sec, $(who));
		}, 0); 
		return ;
	},
	aside:function(msgs)
	{
		var htm='';
		for(var m in msgs)	htm += msgs[m];
		notice.aside(htm);
	}
};

var notice={
	vbox_no:1,
	clear:function(tid, keep)
	{
		$(tid).each(function(f){
			if ($(this).css("display")!="none")
				$(this).fadeOut(800, function(){ keep ? $(this).hide() : $(this).remove(); });
		});
	},
	banner:function(msg, pos)
	{
		var who = '.banner-'+pos;
		var t=(pos=='top') ? -20 : 25;
		if ($(who).html() == undefined) {
			if (types.is_pad()) $('#footer').after('<div class="banner banner-'+pos+'"></div>');
			else $('#main').prepend('<div class="banner banner-'+pos+'"></div>');
		}
		$(who).html(msg);
		if (!types.is_pad())
			$(who).css({width:$('#main').width()-22, top:($('#main').offset().top+t)});
		$(who).show();
	},
	ad:function(htm, sec, delay)
	{
		if (!delay) { setTimeout("notice.ad('"+htm.replace(/'/g, "\\'")+"', "+sec+", 1)", 1); return; }

		if ($('#ads').html() == undefined) $('#indent').prepend('<div id="ads"></div>');
		$('#ads').html(htm).css({width:$('#indent').width()-22, top:$('#indent').offset().top}).show();
		if (sec) setTimeout("notice.clear('#ads')", sec);
	},
	tip:function(htm, sec)
	{
		if ($('#tips').html() == undefined) $('#wrap_header').append('<div id="tips"></div>');
		$('#tips').html(htm).css((function(){
			var w=$('#tips').width();
			if (w<600) w=600;
			if (w>$('.container').width()) w=$('.container').width();
			var ww=$(window).width();
			ww=(ww-w)/2;
			var h=$('.navbar').height();
			
			var style={position:'absolute', left:ww, width:w, top:h};
			if ($(window).scrollTop() > h) {
				style['top']=0;
				style['position']='fixed';
			}
			return style;
		})()).show();
		if (!sec) sec=10000;
		setTimeout("notice.clear('#tips')", sec);
	},
	vbox:function(name, htm, sec, who)
	{
		if (!htm) return false;
		name = (name || notice.vbox_no)+'';
		name = name.replace(/[\[\]]/g, '-');

		if (!who && $('#'+name)[0]) who = $('#'+name);
		who = who || (event && $(event.srcElement));

		//if (!offset) return false;
		$("<div class='vbox-"+name+" vbox hidden'>"+htm+"</div>").appendTo($('body')).show().css((function(){
			var style={position:'absolute'};
			if (who&&who.offset()) {
				if (who.height() > 20)
				{
					style.left = who.offset().left+30;
					style.top = who.offset().top+30;
				}
				else
				{
					style.left = who.offset().left+who.width()+30;
					style.top = who.offset().top;
				}
			}

			if (name!=notice.vbox_no) return style;
			who = who || $('#indent');
			if (who.offset().top < $(window).scrollTop())
			{
				style.top = notice.vbox_no * 30;
				style.position = 'fixed';
			}
			else
			{
				style.top = who.offset().top;
				var add=notice.vbox_no * 30;
				style.top+=(who.offset().top - $(window).scrollTop() < $(window).height()/2) ? add : (-add);
			}
			style.top -= 30;

			var w=$(".vbox-"+name).width();
			style.left = who.offset().left+who.width()-20;
			style.left = (style.left<w) ? style.left : (style.left-w);
			if (w>$('#indent').width())
			{
				h=(w/$('#indent').width());
				h=Math.ceil(h);
				notice.vbox_no+=h;
				h*=$(".vbox-"+name).height();
				w=$('#indent').width();
				$(".vbox-"+name).css({width:w, height:h});
				style.left=($(window).width()-$(".vbox-"+name).width())/2
			}

			return style;
		})());


		if (!sec) sec = 5000;
		if (sec!=-1) setTimeout("notice.clear('.vbox-"+name+"')", sec);
		if (name == notice.vbox_no) {
			if (notice.vbox_no == 1) setTimeout("notice.vbox_no=1;", sec);
			notice.vbox_no=notice.vbox_no+1;
		}
		return false;
	},
	aside:function(htm)
	{
		htm = "<div class='right gray hand'><a onclick='notice.close_aside()'>x</a></div>"+htm;
		htm = "<div class='dialog' id='msg_notice'>" + htm + "</div>";
		if (types.is_pad()) $('#main').prepend(htm);
		else $('#left_menu').prepend(htm);
	},
	ebox:function(who, msg, sec) {
		if (who.find('span.ebox').length) {
			who.find('span.ebox').html(msg);
		}
		else {
			who.append('<span id="'+(Math.random()+'').replace('.','x')+'" class="pad3 ebox">'+msg+'</span>');
		}
		setTimeout('notice.clear("#'+who.find('span.ebox').attr('id')+'")', sec||2000);
	},
	box:function(msg, who, delay, style)
	{
		who = who || (event && $(event.srcElement));
		if (!who) who = $('#indent');
		if (!delay) delay = 1000;
		if (!style) style = 'success';
		notice.pannel = pannel.load().await('notice').draw('<div class="alert alert-'+style+'"><h5>'+msg+'</h5></div>');
		$('#pannel-notice').addClass('box').css('width', $('#indent').width()-40);
		notice.pannel.postion(who).toggle();
		
		if ($(who).attr('id') == 'indent') $('#pannel-notice').css('top', ($(window).scrollTop() + 100));
		if (delay > 0)
		notice.boxtid = setTimeout(function(){ board.remove('#pannel-notice'); }, delay);
		notice.pannel.close_callback(function(){ clearTimeout(notice.boxtid); })
	},
	close_aside:function(who)
	{
		$("#msg_notice").remove();
		$.get("/=/msg.clear_prompt?a=1", function(data) { });
		return false;
	}
};




var note={
	start:function(arg, ops){
		note.arg = arg;
		note.ops = ops;
		if (arg == 'inner') {
			innercard.run();
		}
		else if (arg == 'emotion') {
			emotion.run();
		}
		else {
			arg.run(ops);
		}
	},
	props:function(tribe){
		var htm = '';
		htm += '<div class="field props">';
		htm += '<div class="inline privacy"><div class="inline pad0"><input checked="" id="privacy-0" name="privacy" type="radio" value="False"> <label for="privacy-0">所有人可见</label></div>';
		htm += '<div class="inline pad2"><input id="privacy-1" name="privacy" type="radio" value="True"> <label for="privacy-1">仅自己可见</label></div></div>';
		htm += '<div class="inline pad10 locked"><input id="locked" name="locked" type="checkbox" value="y"> <label for="locked">不允许回复</label></div>';
		htm += '</div>';
		if (note.ops['group_id'])
			htm += '<input type="hidden" name="group_id" value="'+(note.ops['group_id']||'')+'">';
		htm += '<input type="hidden" name="tribe" value="'+(tribe||'')+'">';
		htm += '<input type="hidden" name="oh_cards_words" value="'+(note.ops['oh_cards_words']||'')+'">';
		htm += '<input type="submit" value="    保  存   " class="btn span2 btn-success">';
		return htm;
	},	
	toggle_intro:function(sec, who)
	{
		if (!sec) sec=500;
		if (!who) who = 'h1';
		$("dt.intro").toggle(sec);
		if (!$('.toggle_intro').text())
			$(who).append(" <a class=' small hand gray toggle_intro' onclick='return note.toggle_intro();'></a>");
		$('.toggle_intro').text(($('.toggle_intro').text()=='展开介绍') ? '收起介绍' : '展开介绍');
		return false;
	},
	append:function(htm){
		$(note.nodearea).append(htm);
	}
};

//自我训练，心理训练，情绪训练
var emotion={
	step:0,
	run:function(){
		$('li.pl-emotion').html('<form method="post" onsubmit="return rc.submit(this);" action="/=/note.draft/emotion" class="vform wform">'
				+ '<dl id="inner-note">' + emotion.help() + '</dl></form>');
		note.nodearea = 'li.pl-emotion dl';

		var htm = '';
		htm += '<dt class="title field" style="display: block;"><div>';
		htm += '<label for="note-name" class="inline">主题：</label>';
		htm += '<input id="note-name" class="span6" type="text" name="name" value="">';
		htm += '</div></dt>';
		$('li.pl-emotion dl').append(htm);
		
		htm = '';
		var abc = {'blues':blues, 'attribute':attribute};
		for(var x in abc)
		{
			htm += '<li class="rd4">'
				+ '<div class="left">' + '<h3>' + abc[x].name + '</h3>' + abc[x].help() + '</div>'
				+ '<div class="right">'
				+ '<input class="nextstep btn btn-success" onclick="'+x+'.nextstep();" type="button" value="开始">'
				+ '</div>'
				+ '</li>';
		}
		$('li.pl-emotion dl').after('<ul class="emotions">' + htm + '</ul>');
	},
	set_help:function(txt) { emotion.helptxt = txt; $('dt.intro').html(txt); },
	help:function(){
		if (!emotion.helptxt) 
		{
			var htm ='';
			htm += '<dt class="intro well"><div class="red">功能测试中...</div>';
			htm += '对于生活中一些困扰的事儿，总需要去慢慢的厘清才会更舒坦：<br />';
			htm += '你可以选择以下两种不同形式中的一个进行自我分析：<br />';
			htm += '1. 三栏法：按 <span class="red">自我批评、认知扭曲、 自我辩护</span> 的方式进行思考<br />';
			htm += '2. 归因法：按 <span class="red">情景、行为、结果、原因</span> 的方式进行思考<br />';
			htm += '</dt>';
			emotion.helptxt = htm;
		}
		return emotion.helptxt;
	}		
};

var blues={
	step:0,name:'三栏法',
	nextstep:function(){
		note.toggle_intro();
		var tools = ['facet', 'titlet', 'strongt', 'linkt', 'tablet', 'listt', 'segmentt'];
		// 情境，行为，结果，原因
		var names = ['自我批评：', '认知扭曲：', '自我辩护：'];
		for(var x1 in names)
		{
			if (names[x1] == '认知扭曲：')
			{
				var distortions = ['非黑即白','过于概括','心理过滤','贬损积极','测心术','先知错误',
							'扩大缩小','情绪推理','应该陈述','贴标签','归己化'];
				var sel = '';
				for(var x in distortions)
				{
					if (x % 4 == 0) sel += (x==0?'<li>':'</li><li>');
					sel += '<label class="left" style="width:22%;"><input name="distortion" value="'+x+'" type="checkbox" />'
							+distortions[x] + '</label>';
				}
				sel += '</li>';
				note.append('<dt class="tools">'+names[x1]+'<a class="gray" target="_blank" href="/topic/174106">十大认知扭曲</a></dt>');
				note.append('<dd class="info">'+'<ul>' + sel + '</ul>'+'</dd>');
			}
			else
			{
				note.append('<dt class="tools">'+names[x1]+tool.bar('note-draft'+x1, tools, '', 'inline')+'</dt>');
				note.append('<dd class="info">'+'<textarea id="note-draft'+x1+'" class="div3 span7" name="draft"></textarea>'+'</dd>');
			}
		}
		emotion.set_help(blues.help());
		$('ul.emotions').hide();
		blues.onsubmit();
		$('dt.title .nextstep').hide();
		note.append('<dd class="props">' + note.props('blues') + '</dd>');
	},
	onsubmit:function(){
		$('li.pl-emotion form').on('submit', function(){
			var okay = true;
			if (!$('#note-name').val())
			{
				notice.ebox($('dt.title'), '请输入要分析的主题');
				okay = false;
			}
			
			$('dd.info textarea').each(function(){
				if ($(this).val().length < 1)
				{
					notice.ebox($(this).parent(), '输入的内容太少了');
					okay = false;
				}
			});
			if ($('dd.info input:checked').length < 1)
			{
				notice.ebox($('dd.info ul'), '选择认知扭曲类别');
				okay = false;
			}

			return okay;
		});
	},	
	help:function(){
		var htm ='';
		htm += '<div class=" ">';
		htm += '梳理负面情绪、观念。<br />';
		htm += '1. 自我批评：<a target="_blank" href="/topic/174107">查看示例</a><br />';
		htm += '2. 认知扭曲：<a target="_blank" href="/topic/174106">十大认知扭曲</a><br />';
		htm += '3. 自我辩护：正面、积极的进行思考<br />';
		htm += '</div>';
		return htm;
	}
};


var attribute={
	step:0,name:'归因法',
	nextstep:function(){
		note.toggle_intro();
		var tools = ['facet', 'titlet', 'strongt', 'linkt', 'tablet', 'listt', 'segmentt'];
		// 前提，行为，后果，反思
		// 情境，行为，结果，原因
		var names = ['发生的情境：', '当时的行为：', '造成的结果：', '反思其原因：'];
		for(var x1 in names)
		{
			note.append('<dt class="tools">'+names[x1]+tool.bar('note-draft'+x1, tools, '', 'inline')+'</dt>');
			note.append('<dd class="info">'+'<textarea id="note-draft'+x1+'" class="div3 span7" name="draft"></textarea>'+'</dd>');
		}

		emotion.set_help(attribute.help());
		$('ul.emotions').hide();
		attribute.onsubmit();
		$('dt.title .nextstep').hide();
		note.append('<dd class="props">' + note.props('attribute') + '</dd>');
	},
	onsubmit:function(){
		$('li.pl-emotion form').on('submit', function(){
			var okay = true;
			if (!$('#note-name').val())
			{
				notice.ebox($('dt.title'), '请输入要分析的主题');
				okay = false;
			}
			
			var str = '';
			$('dd.info textarea').each(function(){
				if ($(this).val().length > 0)
				{
					str += $(this).val();
				}
			});
			if (str.length < 10)
			{
				notice.ebox($('dd.info:last'), '输入的内容太少了');
				okay = false;
			}

			return okay;
		});
	},
	help:function(){
		var htm ='';
		htm += '<div>';
		htm += '事出必有因。这是一个思考模板。<br />';
		htm += '1. 情境：先仔细回想事情发生的情境，在什么时间、环境、情况下所发生<br />';
		htm += '2. 行为：做了些什么行动、言谈<br />';
		htm += '3. 结果：导致了什么样的结果发生<br />';
		htm += '4. 原因：分析此情况出现的原因以及将要进行的改进<br />';
		htm += '</div>';
		return htm;
	}
};

var innercard={
	auto:function(ops, onoff) {
		innercard.init(ops);

		var who;
		if ($('div.readable').length > 0) who = $('div.readable');
		else who = $('div.draft');
		who.before('<dl id="inner-note" class="clearfix"><dt class="title clearfix"></dt><dd class="cards"></dd></dl>');
		
		$('dd.cards').append(innercard.card(0));
		$('dd.cards').append(innercard.card(1));
		$('dd.cards').append(innercard.card(2));
		$('dd.cards img').on('click', function(){ innercard.toggle_img(); });
		if (onoff != 'big') innercard.toggle_img(-1, 'dt.title');
	},
	init:function(ops){
		ops = ops.split('_');
		innercard.cards = [ops[0], ops[1], ops[2]];
		innercard.words = [ops[3], ops[4], ops[5]];
	},
	step:0,
	run:function(){
		$('li.pl-inner').html('<form method="post" onsubmit="return rc.submit(this);" action="/=/note.draft/inner" class="vform wform">'
				+ '<dl id="inner-note">' + innercard.help() + '</dl></form>');
		
		innercard.init(note.ops['oh_cards_words']);
		
		var htm = '';
		htm += '<dt class="title field" style="display: block;"><div>';
		htm += '<label for="note-name" class="inline">主题：</label>';
		htm += '<input id="note-name" class="span6" type="text" name="name" value="">';
		htm += ' <input class="nextstep btn btn-success" onclick="innercard.nextstep();" type="button" value="开始">';
		htm += '</div></dt>';
		htm += '<dd class="cards clearfix"></dd>';
		htm += '<dd class="tips"></dd><dd class="tools"></dd><dd class="info"></dd>';
		htm += '<dd class="props"></dd>';
				
		$('li.pl-inner dl').append(htm);
		
		$('li.pl-inner form').on('submit', function(){
			var okay = true;
			if (!$('#note-name').val())
			{
				notice.ebox($('dt.title'), '请输入要探索的主题');
				okay = false;
			}
			if ($('#note-draft').val().length < 10)
			{
				notice.ebox($('dd.info'), '输入的内容太少了');
				okay = false;
			}
			return okay;
		});
	},
	nextstep:function(){
		if (innercard.step == 3) {
			$('dt.title .nextstep').hide();
			$('dd.props').html(note.props('inner'));
			$('dd.cards img').on('click', function(){ innercard.toggle_img(); });
			innercard.toggle_img();
			return;
		}
		var htm = '';
		var tips = ['1、结合第一组图片和文字，对主题进行联想，写下来', '2、结合第一、二组图片和文字，对主题进行联想，写下来', '3、综合三组图片和文字，对主题进行联想，写下来'];
		if (innercard.step == 0)
		{
			note.toggle_intro();
			var tl = tool.bar('note-draft', ['facet', 'sectiont', 'titlet', 'strongt', 'linkt',
			                                'tablet', 'listt', 'segmentt'], '', 'inline');
			$('dd.tools').append('你的理解：'+tl);
			$('dd.info').append('<textarea id="note-draft" class="span7 div8" name="draft"></textarea>');
			$('#draft-'+innercard.step).focus();
			$('dd.tips').addClass('intro').css({'padding':'7px','margin':'10px 20px 7px 0'});
			$('.nextstep').val('下一步');
		}
		$('dd.cards').append(innercard.card(innercard.step));
		$('dd.tips').append('<div class="tip">' + tips[innercard.step] + '</div>');

		innercard.step = innercard.step + 1;
	},
	card:function(ix){
		var card = innercard.words[ix];
		var word = innercard.cards[ix];
		var htm = "<img src='http://f1.yuzeli.com/inner/small/"+card+".png' />";
		htm += "<div class='words'>"+word+"</div>";
		htm = "<div class='left card'>"+htm+"</div>";

		return htm;
	},
	help:function(){
		var htm ='';
		htm += '<dt class="intro well"><div class="red">功能测试中...</div>';
		htm += 'oh cards 不做任何设定，一切都由你，你可以随意发挥。<br />';
		htm += '以下是一种示例的使用方式，仅供参考：<br />';
		htm += '1. 先设定一个主题：你遇到的烦恼、想解决的问题、想探索的主题 等<br />';
		htm += '2. 每点一次下一步之后，会显示一幅图片，总共三幅图片<br />';
		htm += '3. 按顺序观察每幅图片，结合文字，联想自己的任意想法、事件等，想象和探索<br />';
		htm += '4. 结合三幅图、文字带来的探索，好好写下来<br />';
		htm += '5. 过一段时间，再回来看看，可以修改重新加入自己新的思考<br />';
		htm += '<br />';
		htm += '注意：<br />';
		htm += '你需要做的，不是去猜测图片所表达的意思，而是去探索自己的想法或最近发生的事<br />';
		htm += '三幅图可表示【过去、现在、未来】【第一、二、三步】【第一、二、三个人】等任意内容<br />';
		htm += '</dt>';
		return htm;
	},
	toggle_img:function(sec, who)
	{
		if (!sec) sec=500;
		if (!who) who = 'dt.title';
		if (!$('.toggle_img').text())
			$(who).after("<dt><a class='right small hand gray toggle_img' onclick='return innercard.toggle_img();'></a><div class='clear'></div></dt>");
		if ($('dd.cards img:first').css('width') == '50px')
		{
			sec < 0 ? $('dd.cards img').css('margin-left', '15px').css('width', 165)
					: $('dd.cards img').css('margin-left', '15px').animate({'width':165}, sec);
			$('.toggle_img').text('查看小图');
		}
		else
		{
			sec < 0 ? $('dd.cards img').css('margin-left', '65px').css('width', 50)
					: $('dd.cards img').css('margin-left', '65px').animate({'width':50}, sec);
			$('.toggle_img').text('查看大图');
		}
		return false;
	}
};




var user={
	locationit:function(selLocation, selCity, location, city) 
	{ 
			character.initLocation(selLocation, selCity);
			character.setLocation(selLocation, selCity, location, city);
	},
	_mbti:{ SP:{ESTP:'ESTP', ISTP:'ISTP', ESFP:'ESFP', ISFP:'ISFP'}, 
			SJ:{ESTJ:'ESTJ', ISTJ:'ISTJ', ESFJ:'ESFJ', ISFJ:'ISFJ'},
			NF:{ENFJ:'ENFJ', INFJ:'INFJ', ENFP:'ENFP', INFP:'INFP'}, 
			NT:{ENTJ:'ENTJ', INTJ:'INTJ', ENTP:'ENTP', INTP:'INTP'}
	},
	_sign:{ aries:"白羊座", taurus:"金牛座", gemini:"双子座", cancer:"巨蟹座",
		leo:"狮子座", virgo:"处女座", libra:"天秤座", scorpio:"天蝎座",
		sagittarius:"射手座", capricorn:"摩羯座", aquarius:"水瓶座", pisces:"双鱼座"
	},
	_gender:{'female':'女', 'male':'男'},
	sel_mbti:function(who, strip)
	{
		var htm = user.select("mbti", user._mbti);
		
		if (!strip) htm = "<div class='field'><label> MBTI: " + htm + "</label></div>";
		if (who) 
			$(who).append(htm);
		else
			return htm;
	},
	sel_gender:function(who, strip)
	{
		var htm = user.select("gender", user._gender);
		if (!strip) htm = "<div class='field'><label>　性　别: " + htm + "</label></div>";
		if (who) 
			$(who).append(htm);
		else
			return htm;
	},
	checkboxval:function(name, val)
	{ 
		if (name) $("input[name='"+name+"']").attr("checked", "true"); 
	},
	selectval:function(name, val)
	{ 
		if (name) $("select[name='"+name+"'] option[value='"+val+"']").attr("selected", "true"); 
	},
	checkbirth:function(birth){
		if (birth)
		{
			birth = birth.split('-');
			if (birth[0]) $("select[name='byear'] option[value='"+birth[0]+"']").attr("selected", "true");
			if (birth[1]) $("select[name='bmonth'] option[value='"+birth[1]+"']").attr("selected", "true");
			if (birth[2]) $("select[name='bday'] option[value='"+birth[2]+"']").attr("selected", "true");
		}
	},
	sel_birthday:function(who, strip)
	{
		var data={};
		var htm = "";
		for(var x1=1949; x1<2012; x1++) data[x1]=x1;
		htm += user.select("byear", data);
		htm += "年";
		data={};
		for(var x1=1; x1<=12; x1++) data[x1]=x1;
		htm += user.select("bmonth", data);
		htm += "月";
		data={};
		for(var x1=1; x1<=31; x1++) data[x1]=x1;
		htm += user.select("bday", data);
		htm += "日";
		if (!strip) htm = "<div class='field'><label>出生日期: </label>" + htm + "</div>";

		if (who) 
			$(who).append(htm);
		else
			return htm;
	},
	select:function(name, data, style)
	{
		if (!style) style="small";
		var htm = "<select name='"+name+"' class='input-"+style+"'><option></option>";
		for(var x1 in data)
		{
			var v1 = data[x1];
			if (typeof v1 == "string" || typeof v1 == "number")
			{
				htm += "<option value='"+x1+"'>"+v1+"</option>";
			}
			if (typeof v1 == "object")
			{
				htm += "<optgroup label='"+x1+"'>";
				for (var x2 in v1)
				{
					htm += "<option value='"+x2+"'>"+v1[x2]+"</option>";
				}
				htm += "</optgroup>";
			}
		}
		htm += "</select>";
		return htm;
	},
	valid_form:function()
	{
		if ($("select[name='gender']") && $("select[name='gender']").val() == '')
		{
			return notice.vbox('', "请选择性别", 3000, $('.suffix_field'));
		}
		if ($("select[name='byear']") && $("select[name='byear']").val() == '')
		{
			return notice.vbox("", "请选择出生年份", 3000, $('.suffix_field'));
		}
		if ($("select[name='bmonth']") && $("select[name='bmonth']").val() == '')
		{
			return notice.vbox("", "请选择出生月份", 3000, $('.suffix_field'));
		}
		if ($("select[name='bday']") && $("select[name='bday']").val() == '')
		{
			return notice.vbox("", "请选择出生日", 3000, $('.suffix_field'));
		}
		return true;
	},
	connect:function()
	{
		$('.prolinks').before("<a class='btn btn-mini connect-btn'>用绑定账号登陆</a><br />");
		var connects={
			google:['http://www.google.com/accounts/gmail20x20.gif','','用Gmail账号登录'],
			douban:['http://img2.douban.com/pics/fw2douban_s.png','','用豆瓣账号登录'],
			weibo:['http://www.sinaimg.cn/blog/developer/wiki/240.png','用新浪微博账号登录',''],
			qqweibo:['http://mat1.gtimg.com/app/opent/images/wiki/resource/weiboicon16.png','','用腾讯微博账号登录']
		};
		$('.connect-btn').on('click', function(){
			var htm="<h3>用绑定账号登陆</h3><div class='red'>由于第三方网站的账号系统的 API 不稳定，所以不建议使用此功能<br />你可以在绑定登陆后，去 设置-账号信息 中设置自己的邮箱和密码<br /></div>";
			htm+="<div id='connect_button'>";
			for(var c in connects)
			{
				htm+="<h3><a href='/auth/"+c+"'><img src='"+connects[c][0]+"' alt='"+connects[c][1]+"' />"+connects[c][2]+"</a></h3>";
			}
			htm+="</div>";
			var p=pannel.load();
			p.await('user').resize(436, 220);
			p.draw(htm).postion().toggle().jq().addClass('box');
		});
	},
	login_tip:function(who)
	{
		var htm="<div class='well well-small'>";
        htm += '<form method="post" action="/=/auth.login?mini=1" onsubmit="return rc_base.http_form(this);" class="form-inline">';
		htm += '<div class="field inline"><label>Email: <input type="text" style="width:130px;" name="email" /></label></div>';
		htm += '<div class="field inline"><label>&nbsp;&nbsp;密码: <input type="password" style="width:130px;" name="passa" /></label></div>';
		if (!types.is_pad())
			htm += "<label>&nbsp;<input class='checkbox' name='remember' type='checkbox' /> 记住我 </label>";
		htm += "<input type='hidden' name='back' value='"+location.href+"' />";
		htm += "&nbsp;<input type='submit' value='登录' class='btn btn-small btn-success' />&nbsp;<a href='/signup'>注册</a>&nbsp;";
		htm += "<a class='gray' href='/reset'><span style='font-weight:normal; font-size:12px;'>忘了密码</span></a></form></div>";
		$('#main').prepend("<div id='login-tip'></div>");
		$('#login-tip').html(htm);
		if (who)
		{
			$('#login-tip').css(function(){
				return {width:230,'z-index':300,position:'fixed'}
			});
			$('#login-tip form').removeClass('form-inline');
		}
	},
	init_dict:function(func){
		if (types_dict) {
			if (func) func();
			return;
		}
		//$.get("/js/data/citys.json", function(resp){
		$.get("/js/data/data.json", function(resp){
			if (typeof(resp)=="object")
				types_dict=resp;
			else
				types_dict= $.parseJSON(resp);
			if (func) func();
		});
	}
};

var match={
	bind:function(mbti, seeks, uid) {
        user.uid = uid;
        user.mbti = mbti;
        user.seeks = seeks;
        var style = '';
        var btnname = '设置求交往';
        var htm = '';
        if (user.mbti) {
            htm += '我是 ' + user.mbti;
            style = ' pad1 '
        }
        if (user.seeks) {
            htm += ' <span class="gray">欢迎</span> '
            + user.seeks.replace(/,/g, ', ')
            + ' <span class="gray">与我联系</span>';
            btnname = '设置';
        }
        if (types.uid == user.uid)
        {
            htm += '<a href="javascript:;" class="gray ' + style + ' match-btn" onclick="match.setting();">' + btnname + '</a>';
        }
        $('h3.match-seeks').html(htm);
	},
	setting:function(){
		var htm = '';
		htm += '<form class="vform match-setting" action="/=/user.match_setting" onsubmit="return rc.submit(this);">';
        htm += match.seek_htm();
        htm += '</form>';
		if (match.pannel) match.pannel.close();
		match.pannel = pannel.load().await('match').draw(htm).real();
		$('#pannel-match').addClass('box').css('width', board.width('#pannel-match'));
		match.pannel.postion('h1').toggle();
		
		user.selectval('mbti', user.mbti);
		if (user.seeks)
		{
			var seeks = user.seeks.split(',');
			for(var a in seeks) user.checkboxval(seeks[a], seeks[a]);
			match.check();
		}
		
		$('#'+match.pannel.name+" input[type='checkbox']").on('click', function(){
			match.check();
		});
	},
	check:function(){
		var ckd = $('#'+match.pannel.name+" input:checked");
		if (ckd.length >= 4)
		{
			$('#'+match.pannel.name+" input[type='checkbox']:not(:checked)").attr('disabled', true);
		}
		else
		{
			$('#'+match.pannel.name+" input:disabled").attr('disabled', false);
		}
	},
	seek_htm:function(){
		var htm = '你的类型是：';
		if (user.mbti) {
			htm += user.mbti;
		}
		else {
			htm += user.sel_mbti('', 'strip');
		}
		htm += '，请选择你想寻找的类型：<span class="gray">(最多选四个)</span>';
		htm += '<br />';
		htm += "<table class='matchbox table table-striped table-bordered table-hover'>";
		for(var k in user._mbti) {
			var mbti = user._mbti[k];
			htm += "<tr><td><label>"+k+"</label></td>";
			for (var t in mbti) {
				htm += "<td><label><input type='checkbox' name='"+mbti[t]+"' /> "+mbti[t]+"</label></td>";
			}
			htm += "</tr>";
		}
		htm += "</table>";
		htm += '<input class="btn btn-success right" type="submit" onclick="return match.seek_submit(this);" value="登记" />';
		return htm;
	}
};



var career={
	edu:function(){
		$('.eduarea').append(career_card.edu());
	},
	noop:function(){}
};

var career_card={
	edu:function(){
		//types_dict.edus
		//学历, 年份
		var htm = '';
		
		htm += '<div class="field">学校名称：<input /></div>';
		htm += '<div class="field">就读时间：<input class="input-mini" />年 至 <input class="input-mini" />年</div>';
		
		var ee = '';
		for(var x1 in types_dict.edus)
		{
			var sel = '';
			ee += '<option value="'+types_dict.edus[x1].id+'">'+types_dict.edus[x1].name+'</option>';
		}
		htm += '<div class="field">学历：' + '<select name="edu" onchange="career_card.edusel(this);" class="input-small"><option></option>' + ee + '</select></div>';
		
		
		return htm;
	},
	edusel:function(who){
		var edu = $(who).val();

		if (!edu || edu == 10550 || edu == 10551)
			$("#divmajor").hide();
		else
			$("#divmajor").show();
	},
	major:function(){
		return '<div id="divmajor"><div>' 
			+ clever.select('major', '专业', 3, 
					{'gstyle':'small','gid':(position.data.gmajor||''),'pid':(position.data.pmajor||''),'sid':(position.data.major||'')})
			+ '</div>'		
			+ position.meta('cmajor', '没找到所学专业', position.data.cmajor||'')
			+ '</div><br />';
	},
	noop:function(){}
};

var position={
//	data:function()
//	{
//		var script = document.createElement('script');
//		script.src = "http://types.yuzeli.com/js/data/citys.json";
//		document.body.appendChild(script);
//	},
	meta:function(name, info, val, style)
	{
		var htm = '';
        var hide = val ? '' : 'hide';
		htm += ' <div class="custom '+(style || '')+'">';
		htm += '<span class="'+hide+'">自己输入：<input type="text" name="'+name+'" value="'+(val||'')+'" /></span>';
		htm += '<a href="javascript;" class="gray">'+info+'</a>';
		htm += '</div>';
		return htm;
	},
	init_meta:function(data, attr){
		for(var a in attr)
		{
			if (data && attr[a] in data && data[attr[a]])
			{
				$('#div'+attr[a].substring(1)+' .custom a').hide();
				$('#div'+attr[a].substring(1)+' .custom span').show();
			}
			else
			{
				$('#div'+attr[a].substring(1)+' .custom a').on('click', function(){
					$(this).hide().parent().find('span').removeClass('hide').show();
					return false;
				});
			}
		}
	},
	init:function(who, funcs, attr){
		var htm = '';
		
		htm += '<label for="edu">学历(包括在读):</label><br />';
		for(var x1 in types_dict.edus)
		{
			var sel = position.data.edu==types_dict.edus[x1].id?' checked="checked" ' : '';
			htm += '<label><input name="edu" '+sel+' type="radio" value="'
					+types_dict.edus[x1].id+'" />'+types_dict.edus[x1].name+'</label><br />';
		}
		
		htm += '<div id="divmajor" class="field"><div>'
			+ clever.select('major', '专业', 3, 
					{'gstyle':'small','gid':(position.data.gmajor||''),'pid':(position.data.pmajor||''),'sid':(position.data.major||'')})
			+ '</div>'		
			+ position.meta('cmajor', '没找到所学专业', position.data.cmajor||'')
			+ '</div><br />';
		
		htm += '<label for="status">工作性质:</label><br />';

		for(var x1 in types_dict.status)
		{
			var sel = position.data.status==types_dict.status[x1].id?' checked="checked" ' : '';
			htm += '<label><input name="status" '+sel+' type="radio" value="'
					+types_dict.status[x1].id+'"/>'+types_dict.status[x1].name+'</label><br />';
		}
		
		htm += '<div id="divdomain" class="field">' + clever.select('domain', '行业', 2,
				{'gstyle':'small','gid':(position.data.gdomain||''),'pid':(position.data.pdomain||'')}) 
			+ position.meta('cdomain', '没找到所在行业', position.data.cdomain||'', 'inline')
			+ '</div>';
		htm += '<div id="divjob" class="field">' + clever.select('job', '职业', 2,
				{'gstyle':'xlarge','pstyle':'xlarge','gid':(position.data.gjob||''),'pid':(position.data.pjob||'')})
			+ position.meta('cjob', '没找到所从事职业', position.data.cjob||'')
			+ '</div>';
		
		
		$(who).append('<div>' + htm + '<br /></div>');

		position.init_meta(position.data, ['cmajor','cdomain','cjob']);
		
		clever.bind(".major", types_dict.majors);
		clever.bind(".domain", types_dict.domains);
		clever.bind(".job", types_dict.jobs);
		position.edu(position.data.edu);
		position.job(position.data.status);
	},
	edu:function(edu)
	{
		$("input[name='edu']").on('click', function() {
			if ($(this).val() == 10550 || $(this).val() == 10551)
				$("#divmajor").hide();
			else
				$("#divmajor").show();
		});
		
		if (!edu|| edu == 10550 || edu == 10551) {
			$("#divmajor").hide();
		}
	},
	job:function(status)
	{
		$("input[name='status']").click(function() {
			if($(this).val() == 12364)
			{
				$("#divdomain").show();
				$("#divjob").show();
			}
			else
			{
				$("#divdomain").hide();
				$("#divjob").hide();
			}
		});

		if(status != 12364) {
			$("#divdomain").hide();
			$("#divjob").hide();
		}
	},
	valid_form:function()
	{
		// unuse

		if ($("#divedu input[name='edu']").val()!=undefined 
				&& $("#divedu :radio[checked='true']").val() == undefined)
		{
			return notice.vbox('edu', "请选择学历");
		}
	
		if ($("#divedu :radio[checked='true']").val() != undefined 
				&& $("#divedu :radio[checked='true']").val().indexOf("高中") < 0)
		{
			if ($("select[name='major']") && $("select[name='major']").val() == '')
			{
				return notice.vbox('major', "请选择专业");
			}
		}
	
		if ($("#divstatus :radio").val() != undefined && $("#divstatus :radio[checked='true']").val() == undefined)
		{
			return notice.vbox('', "请选择工作状态");
		}
	
		if ($("#divstatus :radio[checked='true']").val() == '固定（自由）职业者')
		{
			if ($("select[name='jobtype']") && $("select[name='jobtype']").val() == '')
			{
				return notice.vbox('jobtype', "请选择职业领域");
			}
			if ($("select[name='occupation']") && $("select[name='occupation']").val() == '')
			{
				return notice.vbox('occupation', "请选择职业类别");
			}		
		}	

		return true;
	}
};



var quiz_validator={
	checkempty:function(questionContainers)
	{
		for(i = 0; i < questionContainers.length; ++i)
		{
			var selected = $(questionContainers[i]).find("input:checked").length;
			if(selected < 1)
			{
				$(questionContainers[i]).find("input:first").focus();
				rc.say("请答完所有问题");
				//$(questionContainers[i]).animate({'color':'#ff0000'}, 800);
				return false;
			}
		}
		return true;
	},
	checkmin:function(questionContainers)
	{
		var min = questionContainers.length / 3;
		var ckd = questionContainers.find("input:checked").length;
		if (ckd < min)
		{
			rc.say('请至少选择 '+min+' 项，你已经选择了 '+ckd+' 项');
			return false;
		}
		return true;
	},
	checksum:function(questionContainers)
	{
		for (var x0=0; x0<questionContainers.length; x0++)
		{
			var vs=0;
			$(questionContainers[x0]).find("option:selected").each(function(){
				vs += parseInt($(this).text());
			});
			if (vs > 10)
			{
				rc.say('分值分配不正确，多分配了 '+(vs-10)+' 分');
				$(questionContainers[x0]).find("select:first").focus();
				return false;
			}
			else if (vs < 10)
			{
				rc.say('分值分配不正确，还有 '+(10-vs)+' 分没分配');
				$(questionContainers[x0]).find("select:first").focus();
				return false;
			}
		}
		return true;
	},
	checkdiff:function(questionContainers)
	{
		for(i = 0; i < questionContainers.length; ++i)
		{
			var sels = $(questionContainers[i]).find("select");
			var texts = ",";
			for(j = 0; j < sels.length; ++j)
			{
				var sel = $(sels[j]);
				if(sel.val() == '')
				{
					rc.say("请选择");
					sel.focus();
					return false;
				}
				var selText = sel.find("option:selected").text();
				if(texts.indexOf("," + selText + ",") >= 0)
				{
					rc.say("有重复选项");
					sel.focus();
					return false;
				}
				texts += selText + ",";
			}
		}
		return true;
	}
};


var quiz={
	label_radio_click:function() {
		$('label').click(function () {
			$(this).parents('.q:first').find('label').removeClass('now');
			$(this).addClass('now');
		});
	},
	label_check_click:function(){
		$('label').click(function(){
			$(this).parents('.q:first').find('label').removeClass('now');
			if ($(this).find('input:first').prop('checked')==true)
				$(this).addClass('now');
		});
	},
	test_page:function(slug, func, len)
	{
		console.log(slug, func, len);
		rc.before_validator = quiz.test_answer;
		quiz.func = func;
		quiz.slug = slug;
		quiz.page_length = len;
		quiz.curr_page=0;
		if(quiz.page_length > 0)
			quiz.show_questions(0);
		if ($('#quiz').hasClass('label_radio_click')) {
			quiz.label_radio_click();
		} else if ($('#quiz').hasClass('label_check_click')) {
			quiz.label_check_click();
		}
	},
	valid_form:function (ob)
	{
		if (!quiz.func(ob)) return false;
		if (!user.valid_form()) return false;
		return true;
	},
	show_questions:function(page)
	{
		$('#quiz .q').each(function(i) {
			if(i >= page * quiz.page_length && i < (page + 1) * quiz.page_length)
				$(this).show();
			else
				$(this).hide();
		});
		++quiz.curr_page;
	},
	test_answer:function()
	{
		if (quiz.slug == "anchors")
		{
			return quiz.check_anchors();
		}
		if(quiz.page_length > 0)
		{
			var pageCount = Math.ceil($('#quiz .q').length / quiz.page_length);
			if(quiz.curr_page >= pageCount)
			{
				return quiz.valid_form($("#quiz .q"));
			}
			else
			{
				if (quiz.func($("#quiz .q:visible")))
				{
					quiz.show_questions(quiz.curr_page);
				}
			}
			return false;
		}
		return quiz.valid_form($("#quiz .q"));
	},
	check_anchors:function()
	{
		if(quiz.curr_page == 1)
		{
			if(quiz_validator.checkempty($("#quiz .q:visible")))
			{
				var questions = $("#quiz .q:visible");
				$(questions).each(function(i) {
					$(this).attr("orderId", i);
				});
				questions = questions.sort(function(o1, o2) {
					var ins1 = $(o1).find("input");
					var i1 = 0;
					for(i = 0; i < ins1.length; ++i)
					{
						if($(ins1[i]).attr("checked"))
						{
							i1 = i;
							break;
						}
					}
					var ins2 = $(o2).find("input");
					var i2 = 0;
					for(i = 0; i < ins1.length; ++i)
					{
						if($(ins2[i]).attr("checked"))
						{
							i2 = i;
							break;
						}
					}
					if(i1 > i2)
						return -1;
					else if(i1 < i2)
						return 1;
					return 0;
				});
				var qs = $("#quiz .q:hidden");
				var as = qs.find("input");
				for(i = 0; i < as.length; ++i)
					$(as[i]).parent("label").hide();

				var htm="";
				for(i = 0; i < 20; ++i)
				{
					var orderId = $(questions[i]).attr("orderId");
					var o = $(as[orderId]);

					var title = o.parent("label").find(".answerTitle");
					var p = title.text().indexOf('.');
					if(p >= 0)
						title.text( title.text().substring(p + 1));
					htm += "<dd><label>"+o.parent("label").html()+"</label></dd>";

				}
				htm = "<dl id='anchors2'><dt>"+qs.find("dt").html()+"</dt>"+htm+"</dl>";
				qs.html(htm);
				for(i = 0; i < questions.length; ++i)
					$(questions[i]).hide();
				qs.show();
				++quiz.curr_page;
			}
			return false;
		}
		else
		{
			var ckd = $('#anchors2').find("input:checked").length;
			if (ckd < 3)
			{
				rc.say('需要选择 3 项，你已经选择了 '+ckd+' 项');
				return false;
			}
			if (ckd > 3)
			{
				rc.say('只能选择 3 项，你已经选择了 '+ckd+' 项');
				return false;
			}
			return true;
		}
	}
};



var scale_htm={
	pannel:function()
	{
		return '<div id="scale-pannel">'
			+'<div class="pannel-questions"></div>'
			+'<div class="scale-act"><a href="javascript:;" class="right scale-start">>>设置问题</a></div>'
			+'<div class="pannel-results"></div>'
			+'<div class="scale-act"><a href="javascript:;" class="right scale-result"></a></div>'
			+'<input type="hidden" name="csid" />'
			+'</div>';
	},
	quiz_kind:function(){
		var htm = '<div class="quiz-kind"><span>问题类型：</span>';
		var kinds = {'toupiao':'投票', 'tongji':'统计', 'jifen':'积分', 'leixing':'类型'};
		for(var k in kinds){
			htm += '<span class="pad'+(k=='toupiao'?1:3)+'"><label><input name="kind" type="radio" value="'+k+'" />'+kinds[k]+'</label></span>';
		}
		htm += '<span><a class="pad7" target="_blank" href="/about/faq#scales">查看帮助</a>'
			+'<a class="gray hand right" onclick="scale_quiz.quiz_reset();">x</a></span></div>';
		return htm;
	},
    opinion:function(val, sid, dval){
		scale.seq += 1;
		var a_seq = scale.seq;
		var sc = '';
		if (scale.kind == 'jifen' || scale.kind == 'leixing')
		{
			sc = ' <input name="score-'+a_seq+'" data-cid="'+a_seq+'" cseq="" data-sid="'+(sid||'')
				+'" class="span1" type="text" value="'+(dval||'') +'" />';
		}
		return '<dd><span></span>'
			+'<input name="opinion-'+a_seq+'" data-rel="opinion" data-cid="'+a_seq+'" data-sid="'+(sid||'')
				+'" class="span4" type="text" value="'+(val||'') +'" />'
			+sc
			+'<a class="gray hand pad1" onclick="scale_question.opinion_remove(this);"></a></dd>';
	},
	ask:function(q_seq, val, sid){
		return '<dt><span></span>问题名称：'+
			'<input name="question-'+q_seq+'" data-cid="'+q_seq+'" data-sid="'+sid+'" class="span4" type="text" value="'+(val||'')+'" />'
			+'<a class="gray hand pad1" onclick="scale_question.ask_remove(this);">x</a></dt>';
	},
	question:function(qq, aa){
		var kinds = {'toupiao':'','tongji':'','jifen':'分值','leixing':'类型'};
		scale.seq += 1;
		var q_seq = scale.seq;
		var opsnum = '<select name="selnum-'+q_seq+'" dval="'+(qq.selnum||1)+'" class="selnum input-mini"></select>'
		var htm = '';
		htm += scale_htm.ask(q_seq, qq.name||'', qq.id||'');
		htm += '<dt>　 答案选项，可选 '+opsnum+'：<span class="pad20">'+kinds[scale.kind]+'</span></dt>';
		if (aa) {
            for(var x1=0; x1<aa.length; x1++)
            {
                htm += this.opinion(aa[x1].name||'', aa[x1].id||'', this.scv(aa[x1].keywords));
            }
        }
		htm += '<dt><a class="pad6" href="javascript:;" onclick="$(this).parent().before(scale_htm.opinion('
				+'));scale_question.opinion_fresh();">添加选项</a></dt>';
		return '<dl class="quiz-question" data-cid="'+q_seq+'">'
            + htm
            + '</dl>';
	},
	scv:function(word){
		if (!word) return '';
		if (scale.kind == 'toupiao' || scale.kind == 'tongji') return '';
		else if (scale.kind == 'jifen') return word.replace('A=', '');
		else {
			var ww = word.split('=');
			if (ww.length == 1) return word;
			if (ww.length == 2 && ww[1] == '1') return ww[0];
			return word;
		}
	},
	result_htm:function(ret){
		scale.seq += 1;
		var r_seq = scale.seq;
		if (!ret) ret = {};

		var tl = tool.bar('result-content-'+r_seq, ['photot', 'titlet', 'strongt', 'linkt', 'tablet', 'listt', 'segmentt'], 'quiz');
		var htm = '';
		if (scale.kind == 'jifen')
			htm += '<dt>范围：<input type="text" name="rlow-'+r_seq+'" value="'+(ret.low||'')+'" class="input-mini" />-'
				+'<input type="text" name="rhigh-'+r_seq+'" value="'+(ret.high||'')+'" class="input-mini" />';
		else
			htm += '<dt>类型：<input type="text" name="rkeyword-'+r_seq+'" value="'+(ret.keyword||'')+'" class="input-mini" />';
		htm += '<a class="gray hand pad1" onclick="scale_result.result_remove(this);">x</a></dt>';
		htm += '<dt>标题：<input type="text" name="rname-'+r_seq+'" value="'+(ret.name||'')+'" class="input-large" /></dt>';
		htm += '<dd>描述：'+tl+'</dd><dd><textarea id="result-content-'+r_seq+'" name="rinfo-'+r_seq+'" class="div5 span7">'+(ret.info||'')+'</textarea></dd>';
		return '<dl class="quiz-result" data-cid="'+r_seq+'" data-sid="'+(ret.id||'')+'">' + htm + '</dl>';
	},
	result_bar:function(){
		return '<div class="quiz-result-bar">设置' + (scale.kind=="jifen"?'得分':'类型') +'的描述</div>';
	}
};

var scale_quiz={
	start:function(){
		$('#scale-pannel').addClass('now').prepend(scale_htm.quiz_kind());
		$('a.scale-start').html('');
		$('div.quiz-kind').find('input:radio').each(function(){
			$(this).on('click', function(){
				scale.kind = $(this).val();
				$('#scale-pannel .pannel-questions').html('');
				$('#scale-pannel .pannel-results').html('');
				scale_question.newq();
				if (scale.kind == 'tongji') scale_question.newq();
				scale_quiz.init_act(scale.kind);
			});
		});
	},
	quiz_reset:function(){
		$('div.quiz-kind').remove();
		$('#scale-pannel .pannel-questions').html('');
		$('#scale-pannel .pannel-results').html('');
		$('#scale-pannel').removeClass('now')
		$('a.scale-result').html('');
		$('a.scale-start').html('>>设置问题');
	},
	edit:function(quiz){
		if (!quiz) return;
		$('#scale-pannel').addClass('now').prepend(scale_htm.quiz_kind());
		scale.kind = quiz.kind;
		$('div.quiz-kind input:radio[value="'+quiz.kind+'"]').prop('checked', true);
		$('div.quiz-kind input:radio').prop('disabled', true);
		scale_quiz.init_act(quiz.kind);
		scale_question.edit(quiz.questions);
		scale_result.edit(quiz.results);
	},
	init_act:function(kind){
		if (kind == 'toupiao' || kind == 'tongji') {
			$('a.scale-start').html('');
			$('a.scale-result').html('');
			return;
		}
		$('a.scale-start').html('>>增加问题');
		$('a.scale-result').html('>>设置结果');
	}
};

var scale_question={
	ask_fresh:function(){
		$('dl.quiz-question').each(function(ix){
			ix = ix + 1;
			if (ix < 10) ix = ' ' + ix; 
			$(this).find('dt:first span').html(ix + '. ');
		});
	},
	opinion_fresh:function(){
		$('dl.quiz-question').each(function(){
			var cnt = $(this).find('dd').length;
			$(this).find('dd').each(function(x){
				$(this).find('input').attr('cseq', (x+1));
				$(this).find('span').html('　　　 　'+types.seq(x)+'. ');
				$(this).find('a').html('');
				if (cnt > 2) $(this).find('a').html('x');
			});
			var sel = $(this).find('select.selnum');
			var dval = sel.val() || sel.attr('dval');
			var selhtm = '';
			for(var x1=0; x1<cnt; x1++)
			{
				selhtm += '<option value="'+(x1+1)+'">' + (x1+1) + '个</option>';
			}
			sel.html(selhtm);
			sel.val(dval);
		});
	},	
	ask_remove:function(who){
		$(who).parents('dl').remove();
		this.ask_fresh();
	},
	opinion_remove:function(who){
		$(who).parent().remove();
		this.opinion_fresh();
	},
	newq:function(){
		$('#scale-pannel .pannel-questions').append(scale_htm.question({}, [{}, {}]));
		this.ask_fresh();
		this.opinion_fresh();
	},
	edit:function(questions){
		if (!questions) return;
		var htm = '';
		for(var x1=0; x1<questions.length; x1++)
		{
			htm += scale_htm.question(questions[x1], questions[x1]['answers']);
		}
		$('#scale-pannel .pannel-questions').html(htm);
		this.ask_fresh();
		this.opinion_fresh();
	}
};

var scale_result={
	newr:function(){
		if (!$('#scale-pannel .quiz-result-bar').html()){
			$('#scale-pannel .pannel-results').prepend(scale_htm.result_bar());
		}
		$('#scale-pannel .pannel-results').append(scale_htm.result_htm({}));
		$('a.scale-result').html('>>增加结果');
	},
	result_remove:function(who){
		$(who).parents('dl').remove();
	},
	edit:function(results){
		if (!results || results.length<1) return;
		var htm = scale_htm.result_bar();
		for(var x1=0; x1<results.length; x1++)
		{
			htm += scale_htm.result_htm(results[x1]);
		}
		if (htm) $('a.scale-result').html('>>增加结果');
		$('#scale-pannel .pannel-results').html(htm);
	}
};

var scale_submit={
	validator:function(){
		var fail = 0;
		if ($('form').find('dl.quiz-question').length>0) {
			$('form').find('dl.quiz-question').each(function(){
				$(this).find('input').each(function(){
                    $(this).removeClass('scale-error')
					if (!$(this).val())
					{
						fail += 1;
                        $(this).addClass('scale-error')
						rc.say('问题相关内容不能为空...');
					}
				});
			});
		}
		if (fail > 0) return false;

		scale_submit.csid();
		return true;
	},
	csid:function(){
        var data={'questions':[], 'results':[], 'csdict':{}};
		$('dl.quiz-question').each(function(){
            var question = {};
            question['ask'] = $(this).attr('data-cid')
            question['opinions'] = [];
            $(this).find('input').each(function(){
                if ($(this).attr('data-rel') == 'opinion') {
                    question['opinions'].push($(this).attr('data-cid'));
                }
                if ($(this).attr('data-sid')) {
                    data['csdict'][$(this).attr('data-cid')] = $(this).attr('data-sid');
                }
            });
            data['questions'].push(question);
		});

		$('dl.quiz-result').each(function(){
            data['results'].push($(this).attr('data-cid'));
            if ($(this).attr('data-sid')) {
                data['csdict'][$(this).attr('data-cid')] = $(this).attr('data-sid');
            }
		});
		$('input[name="csid"]').val(JSON.stringify(data));
		return $('input[name="csid"]').val();
	}
};

var scale_draw={
	percent:function(a, b) { return (a * 100 / b).toFixed(1) + '%'; },
	draw_toupiao:function() {
		$('#quiz-scale dl').each(function(){
			var qc = $(this).attr('qc');
			$(this).find('dd').each(function(){
				var x = scale_draw.percent($(this).attr('ac'), qc);
				$(this).find('.scale-percent').html(x);
                $(this).find('.scale-chart div').css('width', x);
			});
		});
	},
	draw_tongji:function() {
		scale_draw.draw_toupiao();
		var htm = '';
		var abs = {};
		for(var x1=0; x1<scale.stats.length; x1++)
		{
			var stat = scale.stats[x1];
			abs[stat.a_id + '_' + stat.b_id] = stat.total;
		}
		htm += scale_draw.draw_table(abs, scale.items[0], scale.items[1]);
		htm += scale_draw.draw_table(abs, scale.items[1], scale.items[0]);

		$('#quiz-scale').append(htm);
	},
	draw_table:function(abs, it0, it1){
		var t0 = '';
		var t1 = '';
		for(var x0=-1; x0< it0.length; x0++) {
			t0 += '<tr>';
			for(var x1=-1; x1< it1.length; x1++) {
				if (x0 == -1)
				{
					if (x1 == -1) t0 += '<td>' + '</td>';
					else t0 += '<td>' +$('#quiz-scale #a-'+it1[x1]+' span.item').html()+ '</td>';
				}
				else
				{
					if (x1 == -1) t0 += '<td>' +$('#quiz-scale #a-'+it0[x0]+' span.item').html() + '</td>';
					else t0 += '<td>' +(abs[it0[x0]+'_'+it1[x1]] || '0')+ '</td>';
				}
			}
			t0 += '</tr>';
		}
		t0 = '<table class="stat table table-striped table-bordered table-hover">' + t0 + '</table>';
		return t0;
	}
};

var scale={
	seq: 0,
    kind: 'toupiao',
	init:function(){
		$('form.topic-draft div.submit').before(scale_htm.pannel());
		$('a.scale-start').on('click', function(){
			if (!$('#scale-pannel div.quiz-kind').html())
				scale_quiz.start();
			else
				scale_question.newq();
		});
		$('a.scale-result').on('click', function(){
			scale_result.newr();
		});
        rc.before_validator = scale_submit.validator
	},
	edit:function(quiz){
        if (!quiz) return;
		scale.quiz=quiz; // by debug
		scale.init();
		scale_quiz.edit(quiz);
	},
	done:function(who, pid){
		var fail = false;
		$('#quiz-scale dl').each(function(){
			var sn = parseInt($(this).attr('selnum'));
			var cn = $(this).find('input:checked').length;
			if (cn == 0) {
				notice.ebox($(this).find('dt:first'), '请选择选项');
				fail = true;
			}
			if (sn > 1 && cn > sn) {
				notice.ebox($(this).find('dt:first'), '最多选' + sn + '项，你多选了' +(cn-sn)+ '项');
				fail = true;
			}
		});
		if (fail) return false;
		var data = $(who).serializeArray();
		$.post('/=/topic.scale/'+pid, data, function(resp){
            if (resp.state == 200) {
                $('#quiz-scale').before(resp.data.htm).remove();
                scale.draw();
            }
		});
		return false;
	},
	draw:function() {
		if ($('#quiz-scale').attr('kind') == 'toupiao')
			scale_draw.draw_toupiao();
		if ($('#quiz-scale').attr('kind') == 'tongji')
			scale_draw.draw_tongji();
	},
	labelnow:function(){
		$('label').on('click', function(){
			$(this).parents('dl').find('label').each(function(){
				if ($(this).find('input').prop('checked')==true) 
					if (!$(this).hasClass('now')) $(this).addClass('now');
				else 
					if ($(this).hasClass('now')) $(this).removeClass('now'); 
			});
		});
	}
};



var baidu_share={
    bdSnsKey:{"tsina":"1268823051","tqq":"ab627dfd066742c9907ac54a724df2fb"},
	innerpage:function(str, title){
        if (!title) title = '分享到：';
        if (!str) str = ''
        window._bd_share_config={"common":{"bdSnsKey":baidu_share.bdSnsKey,"bdText":str,"bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"16"},"share":{"bdSize":16},"image":{"viewList":["qzone","tsina","tqq","renren","weixin"],"viewText":title,"viewSize":"16"},"selectShare":{"bdContainerClass":null,"bdSelectMiniList":["qzone","tsina","tqq","renren","weixin"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];
	},
    rightbar:function(str){
        if (!str) str = ''
        window._bd_share_config={"common":{"bdSnsKey":baidu_share.bdSnsKey,"bdText":str,"bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"16"},"slide":{"type":"slide","bdImg":"8","bdPos":"right","bdTop":"195.5"},"image":{"viewList":["qzone","tsina","tqq","renren","weixin"],"viewText":"分享到：","viewSize":"16"},"selectShare":{"bdContainerClass":null,"bdSelectMiniList":["qzone","tsina","tqq","renren","weixin"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];
    }
};
var define,require;!function(e){function r(e,r){q(e);var t=tr.waitSeconds;return j(e)&&t&&(z&&clearTimeout(z),z=setTimeout(n,1e3*t)),C(e,r)}function n(){var e,r=[],n=[],t={};for(var i in F)f(i)||(e=1,r.push(i)),A(F[i].depMs||[],function(r){var i=r.absId;F[i]||t[i]||(e=1,n.push(i),t[i]=1)});if(e)throw new Error("[MODULE_TIMEOUT]Hang( "+(r.join(", ")||"none")+" ) Miss( "+(n.join(", ")||"none")+" )")}function t(){var e=arguments.length;if(e){for(var r,n,t=arguments[--e];e--;){var i=arguments[e];"string"==typeof i?r=i:j(i)&&(n=i)}var u=window.opera;if(!r&&document.attachEvent&&(!u||"[object Opera]"!==u.toString())){var s=R();r=s&&s.getAttribute("data-require-id")}r?(a(r,n,t),H&&clearTimeout(H),H=setTimeout(o,1)):rr.push({deps:n,factory:t})}}function i(){var e=tr.config[this.id];return e&&"object"==typeof e?e:{}}function a(e,r,n){if(!F[e]){var t={id:e,deps:r||["require","exports","module"],factoryDeps:[],factory:n,exports:{},config:i,state:P,require:k(e),depMs:[],depMsIndex:{},depRs:[],depPMs:{}};F[e]=t,_.push(t)}}function o(){function e(e){F[e]||n[e]||(r.push(e),n[e]=1)}var r=[],n={};A(_,function(r){if(!(r.state>P)){var n=r.deps.slice(0),t=n.length,i=0,a=r.factory;"function"==typeof a&&(i=Math.min(a.length,t),a.toString().replace(G,"").replace(Q,function(e,r,t){n.push(t)})),A(n,function(n,a){var o,u,s=U(n),c=E(s.module,r.id);c&&!er[c]?(s.resource&&(u={id:n,module:c,resource:s.resource},r.depPMs[c]=1,r.depRs.push(u)),o=r.depMsIndex[c],o||(o={id:s.module,absId:c,hard:i>a,circular:W},r.depMs.push(o),r.depMsIndex[c]=o,e(c))):o={absId:c},t>a&&r.factoryDeps.push(u||o)}),r.state=B,A(r.depMs,function(e){u(r.id,e.absId)}),s(r)}}),b(r)}function u(e,r){function n(){p(e)}g(r,function(){var t=F[e];t.depPMs[r]&&A(t.depRs,function(t){t.absId||t.module!==r||(t.absId=E(t.id,e),g(t.absId,n),b([t.absId],null,e))}),n()})}function s(r){function n(){var e=V;return A(r.depRs,function(r){return r.absId&&f(r.absId)?void 0:(e=J,!1)}),e!==V?e:(A(r.depMs,function(r){if(!f(r.absId))switch(r.circular===W&&(r.circular=l(a,r.absId)),r.circular){case Y:r.hard&&(e=K);break;case X:e=K;break;case W:return e=J,!1}}),e)}function t(){if(!(r.state>=L)){var t=n();if(t>=K&&i(),!(V>t)){var o=[];A(r.factoryDeps,function(e){o.push(e.absId)});var u=v(o,{require:r.require,exports:r.exports,module:r});try{var s=r.factory,c="function"==typeof s?s.apply(e,u):s;null!=c&&(r.exports=c)}catch(f){if(/^\[MODULE_MISS\]"([^"]+)/.test(f.message)){var d=r.depMsIndex[RegExp.$1];return d&&(d.hard=1),void 0}throw f}r.state=L,r.invokeFactory=null,h(a)}}}function i(){o||(o=1,A(r.depMs,function(e){e.circular===Y&&p(e.absId)}))}var a=r.id;r.invokeFactory=t,t();var o}function c(e){return F[e]&&F[e].state>=B}function f(e){return F[e]&&F[e].state>=L}function d(e,r){var n=F[e];if(r=r||{},r[e]=1,!n||n.state<L)return!1;if(n.state===N)return!0;for(var t=n.depMs,i=t.length;i--;){var a=t[i].absId;if(!r[a]&&!d(a,r))return!1}return n.state=N,!0}function v(e,r){var n=[];return A(e,function(e){n.push(r[e]||m(e))}),n}function l(e,r,n){if(!c(r))return W;n=n||{},n[r]=1;var t=F[r];if(r===e)return Y;var i=t&&t.depMs;if(i)for(var a=i.length;a--;){var o=i[a].absId;if(!n[o]){var u=l(e,o,n);switch(u){case Y:case W:return u}}}return X}function p(e){var r=F[e];r&&r.invokeFactory&&r.invokeFactory()}function h(e){for(var r=Z[e]||[],n=r.length;n--;){var t=r[n];t&&t()}r.length=0,delete Z[e]}function g(e,r,n){if(f(e))return r(e),void 0;var t=Z[e];t||(t=Z[e]=[]),n?t.unshift(r):t.push(r)}function m(e){return f(e)?F[e].exports:null}function y(e){var r=rr.slice(0);rr.length=0,rr=[],A(r,function(r){a(r.id||e,r.deps,r.factory)}),o()}function b(r,n,t){function i(){if(!a){var t=1;A(r,function(e){return er[e]?void 0:t=d(e)}),t&&(a=1,"function"==typeof n&&n.apply(e,v(r,er)))}}if("string"==typeof r){if(!f(r))throw new Error('[MODULE_MISS]"'+r+'" is not exists!');return m(r)}var a=0;j(r)&&(i(),!a&&A(r,function(e){er[e]||(g(e,i,1),(e.indexOf("!")>0?M:I)(e,t))}))}function I(e){function r(){var r=n.readyState;("undefined"==typeof r||/^(loaded|complete)$/.test(r))&&(n.onload=n.onreadystatechange=null,n=null,y(e))}if(!nr[e]&&!F[e]){nr[e]=1;var n=document.createElement("script");n.setAttribute("data-require-id",e),n.src=w(e+".js"),n.async=!0,n.readyState?n.onreadystatechange=r:n.onload=r,T(n)}}function M(e,r){function n(r){o.state=N,o.exports=r||!0,h(e)}function t(t){var o=r?F[r].require:C;t.load(a.resource,o,n,i.call({id:e}))}if(!F[e]){var a=U(e),o={id:e,state:B};F[e]=o,n.fromText=function(e,r){new Function(r)(),y(e)},b([a.module],t)}}function x(){tr.baseUrl=tr.baseUrl.replace(/\/$/,"")+"/";var e=D();ir=O(tr.paths),ir.sort(e),or=O(tr.map),or.sort(e),A(or,function(r){var n=r.k;r.v=O(r.v),r.v.sort(e),r.reg="*"===n?/^/:$(n)}),ar=[],A(tr.packages,function(e){var r=e;"string"==typeof e&&(r={name:e.split("/")[0],location:e,main:"main"}),r.location=r.location||r.name,r.main=(r.main||"main").replace(/\.js$/i,""),ar.push(r)}),ar.sort(D("name")),sr=O(tr.urlArgs),sr.sort(e)}function w(e){function r(e){c||(s+=(s.indexOf("?")>0?"&":"?")+e,c=1)}var n=/(\.[a-z0-9]+)$/i,t=/(\?[^#]*)$/,i="",a=e,o="";t.test(e)&&(o=RegExp.$1,e=e.replace(t,"")),n.test(e)&&(i=RegExp.$1,a=e.replace(n,""));var u,s=a;A(ir,function(e){var r=e.k;return $(r).test(a)?(s=s.replace(r,e.v),u=1,!1):void 0}),u||A(ar,function(e){var r=e.name;return $(r).test(a)?(s=s.replace(r,e.location),!1):void 0}),/^([a-z]{2,10}:\/)?\//i.test(s)||(s=tr.baseUrl+s),s+=i+o;var c;return A(sr,function(e){return $(e.k).test(a)?(r(e.v),!1):void 0}),ur&&r(ur),s}function k(e){function r(r,t){if("string"==typeof r){var i=n[r];return i||(i=n[r]=b(E(r,e))),i}if(j(r)){var a=[];A(r,function(r){var n=U(r);n.resource&&a.push(E(n.module,e))}),b(a,function(){var n=[];A(r,function(r){n.push(E(r,e))}),b(n,t,e)},e)}}var n={};return r.toUrl=function(r){return w(E(r,e))},r}function E(e,r){if(!e)return"";r=r||"";var n=U(e);if(!n)return e;var t=n.resource,i=S(n.module,r);if(A(ar,function(e){var r=e.name;return r===i?(i=r+"/"+e.main,!1):void 0}),A(or,function(e){return e.reg.test(r)?(A(e.v,function(e){var r=e.k,n=$(r);return n.test(i)?(i=i.replace(r,e.v),!1):void 0}),!1):void 0}),t){var a=m(i);t=a.normalize?a.normalize(t,function(e){return E(e,r)}):E(t,r),i+="!"+t}return i}function S(e,r){if(0===e.indexOf(".")){var n=r.split("/"),t=e.split("/"),i=n.length-1,a=t.length,o=0,u=0;e:for(var s=0;a>s;s++){var c=t[s];switch(c){case"..":if(!(i>o))break e;o++,u++;break;case".":u++;break;default:break e}}return n.length=i-o,t=t.slice(u),n.concat(t).join("/")}return e}function q(e){function r(e){0===e.indexOf(".")&&n.push(e)}var n=[];if("string"==typeof e?r(e):A(e,function(e){r(e)}),n.length>0)throw new Error("[REQUIRE_FATAL]Relative ID is not allowed in global require: "+n.join(", "))}function U(e){var r=e.split("!");return dr.test(r[0])?{module:r[0],resource:r[1]}:null}function O(e){var r=[];for(var n in e)e.hasOwnProperty(n)&&r.push({k:n,v:e[n]});return r}function R(){if(cr)return cr;if(fr&&"interactive"===fr.readyState)return fr;for(var e=document.getElementsByTagName("script"),r=e.length;r--;){var n=e[r];if("interactive"===n.readyState)return fr=n,n}}function T(e){cr=e,lr?vr.insertBefore(e,lr):vr.appendChild(e),cr=null}function $(e){return new RegExp("^"+e+"(/|$)")}function j(e){return e instanceof Array}function A(e,r){if(j(e))for(var n=0,t=e.length;t>n&&r(e[n],n)!==!1;n++);}function D(e){return e=e||"k",function(r,n){var t=r[e],i=n[e];return"*"===i?-1:"*"===t?1:i.length-t.length}}var z,F={},_=[],P=1,B=2,L=3,N=4,C=k();r.toUrl=C.toUrl;var H;t.amd={};var Q=/require\(\s*(['"'])([^'"]+)\1\s*\)/g,G=/(\/\*([\s\S]*?)\*\/|([^:]|^)\/\/(.*)$)/gm,J=0,K=1,V=2,W=0,X=1,Y=2,Z={},er={require:r,exports:1,module:1},rr=[],nr={},tr={baseUrl:"./",paths:{},config:{},map:{},packages:[],waitSeconds:0,urlArgs:{}};r.config=function(e){for(var r in tr)if(e.hasOwnProperty(r)){var n=e[r],t=tr[r];if("urlArgs"===r&&"string"==typeof n)ur=n;else{var i=typeof t;if("string"===i||"number"===i)tr[r]=n;else if(j(t))A(n,function(e){t.push(e)});else for(var r in n)t[r]=n[r]}}x()},x();var ir,ar,or,ur,sr,cr,fr,dr=/^[-_a-z0-9\.]+(\/[-_a-z0-9\.]+)*$/i,vr=document.getElementsByTagName("head")[0],lr=document.getElementsByTagName("base")[0];lr&&(vr=lr.parentNode),e.define=t,e.require=r}(this);
// 路径配置
require.config({
    paths:{
        //echarts: 'http://echarts.baidu.com/build/dist'
        'echarts' : 'http://echarts.baidu.com/build/echarts',
        'echarts/chart/bar' : 'http://echarts.baidu.com/build/echarts',
        'echarts/chart/pie' : 'http://echarts.baidu.com/build/echarts'
    }
});

var echart={
    draw:function(option, dom_id){
        if (!dom_id) dom_id = 'survey-chart';
         // 使用
        require(
            [
                'echarts',
                'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById(dom_id));
                // 为echarts对象加载数据
                myChart.setOption(option);
            }
        );
    }
};
