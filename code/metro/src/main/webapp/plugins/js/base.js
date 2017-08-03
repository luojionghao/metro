/**
 * 基础属性设置和fix，每个页面都建议引入
 */
/**
function autoFrameSize() {
	$(".content-wrapper").css('height', function() {
		var screenHeight = window.innerHeight;
		var offset = $(".content-wrapper")[0].offsetTop;
		return screenHeight - offset;
	});
}
$(window).resize(autoFrameSize);
autoFrameSize();
*/

/**
 * loading
 * @param root
 * @returns
 */
function loadingShow(root){
	var cover = $('<div>').addClass("globCover").css({
		width: "100%",
		height: "100%",
		position: "fixed",
		top: "0",
		left: "0",
		background: "rgba(0,0,0,0.8)",
		zIndex: "999",
		display:"flex",
		alignItems:"center",
		justifyContent:"center"
	});
	var loadingImage = $('<img>').attr("src",root+"/plugins/images/loading.gif").css({
		width:"60px",
		height:"60px"
	});
	$(cover).append(loadingImage);
	$("body").append(cover);
	$(cover).show();
}
/**
 * 取消loading
 * @returns
 */
function loadingDismiss(){
	var cover = $(".globCover");
	if (cover){
		$(cover).remove();
	}
}

/**
 * 注册文件上传显示文件本地目录时间
 * inputIds fileInputId 的id数组
 * spanIds spanId的id数组
 * @returns
 */
function registerUpfileName(inputIds,spanIds){
	$.each(inputIds,function(idx,ele){
		$("#"+ele).change(function(){
			$("#"+spanIds[idx]).text($(this).val());
		});
	});
}

/**
 * 刀盘、螺旋数据获取器
 */

function DataFetcher(source){
	// 初始化
	this.prefix = source.head;
	this.sufix = source.sufix;
	this.data = source;
	
	// 数据填充，填入容器，自动根据容器tag、map、prop、sfx、pfx等信息填充数据
	this.fill = function(elms){

		var self = this;
		$.each(elms,function(index,elm){
			var tag = $(elm).attr('tag');
			var prop = $(elm).attr('prop');
			var sfx = $(elm).attr('sfx');
			var smap = $(elm).attr('map');
			var pfx = $(elm).attr('pfx');
			var val;

			// 取值
			if(tag){
				val = self.fetchByTag(tag,"-");
			}else if(prop){
				val = self.fetchOrDefault(self.data,prop,"-");
			}else{
				val = "-";
			}
			// 映射
			if(smap){
				var map = JSON.parse(smap);
				var def = map["def"]?map["def"]:'-';
				val = self.fetchOrDefault(map,val,def);
			}
			// 添加前缀
			if(pfx){
				val = pfx+val;
			}
			// 添加后缀
			if(sfx){
				val = val+sfx;
			}
			$(elm).html(val)    			
		});
	}
	
	// 获取key值，如果key对应的value为空，返回默认值
	this.fetchOrDefault = function(data,key,def){
		if(data.hasOwnProperty(key)&&data[key]!=null){

			return this.hasDot(data[key])?data[key].toFixed(2):data[key];
		}else{
			return def;
		}
	}

	this.hasDot =function(num) {
		if (!isNaN(num)) {
			return ((num + '').indexOf('.') != -1) ? true : false;
		}
	}
	
	// 通过tag标签获取对应的属性值
	this.fetchByTag = function(tag,def){
		var key = this.prefix+tag+this.sufix;
		return this.fetchOrDefault(this.data,key,def);
	}
};

