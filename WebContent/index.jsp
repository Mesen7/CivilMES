<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="Assets/bootstrap.jsp"/>
    <title>Heyuan Civil MES</title>

</head>
<body class="none-padding">
  <header class="container-fluid">
	  <div class="row">
	    <table class="table text-center" style="margin:0">
	      <tr>
	        <td class="col-md-2 col-xs-2 text-left" ><img src="Assets/images/logo.png"></td>
	        <td class="col-md-8 col-xs-8"><h3 class="text-center" style="margin:0">Heyuan Civil MES</h3></td>
	        <td class="col-md-2 col-xs-2"></td>
	      </tr>
	    </table>
	    <nav class="navbar navbar-default" style="margin-bottom:0">
	      <ul class="nav navbar-nav" >
	        <li><a href=""><span class="glyphicon glyphicon-home"></span>&nbsp;Home</a></li>
	        <li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown">标签管理 <span class="caret"></span></a>
	          <ul class="dropdown-menu">
	            <li><a href="Labels/ALabelPrint.jsp" >组件标签打印</a></li>
	            <li><a href="Labels/PackagePrint2.jsp">箱号标签打印</a></li>
	          </ul>
	        </li>
	        <li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown">数据上传 <span class="caret"></span></a>
	          <ul class="dropdown-menu">
	            <li><a href="DataUpload/AccessUpload.jsp" >测试数据上传</a></li>
	            <li><a href="#">......</a></li>
	          </ul>
	        </li>
	        <li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown" >报表管理 <span class="caret"></span></a>
	          <ul class="dropdown-menu">
	            <li><a href="#" title="">生产报表</a></li>
	          </ul>
	        </li>
	      </ul>
	      <ul class="nav navbar-nav navbar-right">
	        <li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown">
	          <span class="glyphicon glyphicon-user"></span>
	          User:<span class="caret"></span></a>
	          <ul class="dropdown-menu">
	            <li><a><span class="glyphicon glyphicon-log-out"></span> Login Out</a></li>
	            <li><a href="#" title=""><span class="glyphicon glyphicon-info-sign"></span> Setting</a></li>
	          </ul>
	        </li>
	      </ul>
	    </nav>
	  </div>
	</header>
  <iframe id="iFramePage" name="iFramePage" style="border:none; width:100%;" src="" onload="frameInit()"></iframe>
  <div id="footer">
    <nav class="navbar none-margin" style="min-width:970px!important;">
		    <div class="navbar-inner navbar-content-center">
		      <hr class=" none-margin" >
		        <p class="text-muted credit" style="font-size:80%; text-align:center; margin-top:15px">
		            <a href="" title=""> 隐私声明 </a>|
					<a href="" title=""> 使用条款 </a>|
					<a href="" title=""> 网站联系 </a>| Copyright ©2016. n 铂阳精工有限公司 Software All Rights Reserved.
				</p>
		    </div>
		</nav>
  </div>
  <script>
	  function frameInit(){
	       $("#footer").addClass("hidden");
	       $( "#iFramePage").height(function(){return document.documentElement.clientHeight-$(this).offset().top-10;});
	       window.frames["iFramePage"].onclick=function(){$("#iFramePage").click()}
	   }
	   $(function(){
	       //set the iFrame height
	       $("#iFramePage").height(function(){return document.documentElement.clientHeight-$(this).offset().top-$("#footer").height()-10;});
	       //to get the click event from the iFrame
	       window.frames["iFramePage"].onclick=function(){$("#iFramePage").click()};
	       //set iframe to load the a-link page
	       $("nav").find("a").each(function(){
	         if($(this).attr("href")!=="" && $(this).attr("href")!=="#")
	           $(this).attr("target","iFramePage")
	       })
	   })
    </script>
</body>
</html>