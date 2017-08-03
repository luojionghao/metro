<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>地铁盾构信息管理系统</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<link rel="shortcut icon" type="image/x-icon" href="<@s.url'/plugins/images/favicon.png'/>" />
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="<@s.url'/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
	<!-- 图标1 -->
    <link rel="stylesheet" href="<@s.url'/plugins/bower/font-awesome/css/font-awesome.min.css'/>" media="all">
	<!-- 图标2 -->
    <link rel="stylesheet" href="<@s.url'/plugins/bower/Ionicons/css/ionicons.min.css'/>" media="all">

    <link rel="stylesheet" href="<@s.url'/plugins/bower/AdminLTE/dist/css/AdminLTE.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/AdminLTE/dist/css/skins/_all-skins.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/jClocksGMT/css/jClocksGMT.css'/>" media="all">

	<link rel="stylesheet" href="<@s.url'/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/index.css'/>">
</head>
<body class="sidebar-mini skin-blue">
	<div class="wrapper">
		<!-- 顶部导航 -->
		<header class="main-header">
			<nav class="navbar navbar-static-top" role="navigation">
		    	<!-- 标题-->
		      	<div class="title_block ver_center">
		      		<img src="<@s.url'/plugins/images/logo.png'/>" class="logo_img">
		      		<span class="title">广州地铁盾构施工监控信息管理系统</span>
		      	</div>
			    <!-- 用户名 -->
			    <div class="navbar-custom-menu">
				    <ul class="nav navbar-nav">
						<li class="dropdown notifications-menu">
						    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
						      	<i class="news_icon"></i>
						      	<span class="label label-warning total_rec1"></span>
						    </a>
						    <ul class="dropdown-menu">
						      	<li class="header">一共 <span class="total_rec">0</span> 条预警信息</li>
						      	<li>
						        	<ul class="menu warnsrec">

						        	</ul>
						      	</li>
						      	<li class="footer"><a class="warn_link" href="${request.contextPath}/monitor/warn/index?intervalId=0" target="frameContent">查看全部</a></li>
						    </ul>
						</li>
						<li class="dropdown user_dropdown">
			                <a href="#" class="dropdown-toggle user_dropdown_toggle" data-toggle="dropdown">
			                  	<div class="user_content middle">
			                  		<img src="<@s.url'/plugins/images/user_icon.png'/>">
				                  	<span class="user_text">${(name)!''}</span>
				                  	<img src="<@s.url'/plugins/images/down_icon.png'/>">
			                  	</div>
			                </a>
			                <ul class="dropdown-menu user_dropdown_menu">
			                  	<li>
			                  		<a href="<@s.url'/login/to/pass/edit/index'/>" target="frameContent" class="change_btn">修改密码</a>
			                  	</li>
			                    <li><a href="javascript:logout();" class="exit_btn">退出</a></li>
			                </ul>
	            		</li>
					</ul>
			    </div>
		    </nav>
		</header>
		<!-- 左边导航栏 -->
		<aside class="main-sidebar">
		    <section class="sidebar">
                <div class="guide-bar clearfix">
                    <em class="menu_title">导航菜单</em>
                    <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                        <div class="menu_btn middle"></div>
                    </a>
                </div>
		      	<ul class="sidebar-menu">
		      		<#if menus??>
		      			<#list menus as m>
				        <li class="<#if m_index==0>active</#if> treeview">
				          	<a href="#" target="frameContent">
				            	<i class="${(m.menuLogUrl)!''}"></i>
				            	<span>${(m.menuName)!''}</span>
				            	<i class="fa fa-angle-left pull-right"></i>
				          	</a>
				          	<ul class="treeview-menu">
				          		<#if m.childMenus??>
					          		<#list m.childMenus as mc>
					          		<li <#if m_index==0&&mc_index==0>class="active"</#if>>
						            	<a href="${request.contextPath}${(mc.menuUrl)!''}" target="frameContent" class="tube_map">
						            		<i class="fa fa-circle-o"></i><span>${(mc.menuName)!''}</span>
						            	</a>
					            	</li>
					          		</#list>
				          		</#if>
				          	</ul>
				        </li>
		      			</#list>
		      		<#else>
		      			您还没有权限，请联系管理员
		      		</#if>
			        <li class="treeview">
			          	<a href="javascript:void(0);" target="frameContent">
			            	<i class="menu_icon menu_icon4"></i>
			            	<span>智能数据分析</span>
			          	</a>
			        </li>
		        </ul>
                <div id="clock"></div>
		    </section>
		</aside>
		<!-- 主体内容 -->
		<div class="content-wrapper">
			<section class="content-header">
                <div class="content_title">当前位置：<i id="breadcrumb">盾构远程监控/地铁全网图</i></div>
			</section>
			<section id="content" class="content">
	            <iframe id="frameContent" name="frameContent" class="frame-content" src="" frameborder="0">
	            </iframe>
            </section>
		</div>
	</div>
</body>
    <!-- jQuery 2.2.0 -->
    <script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
	<!-- Bootstrap 3.3.6 -->
    <script src="<@s.url'/plugins/bower/bootstrap/dist/js/bootstrap.min.js'/>"></script>
	<!-- FastClick -->
    <script src="<@s.url'/plugins/bower/AdminLTE/dist/js/app.min.js'/>"></script>
	<!-- SlimScroll 1.3.0 -->
    <script src="<@s.url'/plugins/bower/jquery-slimscroll/jquery.slimscroll.min.js'/>"></script>
	<!-- AdminLTE for demo purposes -->
    <script src="<@s.url'/plugins/bower/jClocksGMT/js/jquery.rotate.js'/>"></script>
    <script src="<@s.url'/plugins/bower/jClocksGMT/js/jClocksGMT.js'/>"></script>
    <script src="<@s.url'/plugins/js/base.js'/>"></script>
    <script>
    //退出
    function logout(){
    	var c = confirm("确认退出？");
    	if(c == true){
    		top.location.href = "${request.contextPath}/login/logout";
    	}
    }
        // 时钟
        $('#clock').jClocksGMT({
            offset:'+8',
            skin:5,
            imgpath:"<@s.url'/plugins/'/>",
            date: true,                
            dateformat: 'YYYY年MM月DD日',
            timeformat: 'A hh 时 mm 分',       
        });

        // breadcrumb 
        $(".treeview-menu li").on("click",function(){
            $(".treeview-menu li").removeClass('active');
            $("li.treeview").removeClass('active');
            $(this).addClass('active');
            $(this).parents('.treeview').addClass('active');
            var breadArray = $.map($('.active>a').find('span'),function(val,i){
                return $(val).text();
            });
            $('#breadcrumb').text(breadArray.reduce(function(pre,cur){
                return pre+"/"+cur;
            },"").slice(1));
        });
        // 页面一加载就触发a标签的链接
        $(".tube_map")[0].click();
        
       	setInterval(function(){
			$.ajax({
				type:'POST',
				url:"<@s.url'/monitor/warn/find/warns/all'/>",
				dataType:"json",
				success:function(data){					
					if(data.total>0){
						var htm = '';
						$.each(data.res,function(i, o){
							htm = htm + "<li><a class=\"warn_link\" href=\"${request.contextPath}/monitor/warn/index\" target=\"frameContent\" class=\"clearfix\"><i class=\"warn_icon\"></i>"+o.dic.dicMean+"："+o.numValue+" "+o.dic.dicUnit+"<em class=\"warn_right\">"+(o.warningLevel==1?"红色上限预警":(o.warningLevel==2?"橙色上限预警":(o.warningLevel==3?"橙色下限预警":"红色下限预警")))+"</em></a></li>";
						});
						$(".warnsrec").prepend(htm);
						var t = parseInt($(".total_rec").text())+parseInt(data.total);
						var origin = parseInt($(".total_rec1").text()) || 0;
						var t1 = origin+parseInt(data.total);
						var t1Text = t1 === 0 ? '':t1;
						$(".total_rec").text(t);
						$(".total_rec1").text(t1Text);
						$(".warn_link").click(function(){
							$(".total_rec").text('0');
							$(".total_rec1").text('');
							$(".warnsrec").empty();
						});
					}
				}
			});
       	},1000*30*2);
        
    </script>
</html>
