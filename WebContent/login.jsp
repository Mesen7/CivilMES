<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.Locale" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include page="Assets/bootstrap.jsp"></jsp:include>
	<title>Quanzhou Target Login</title>

  </head>
  <body style="background-color: #eee;">	
    <fmt:bundle basename="lang/login_${locale}">
    <div class="container" style="margin-top:100px;">
      <form action='checkLogin' class="col-md-4 col-sm-6 col-xs-8 col-md-offset-4 col-sm-offset-3 col-xs-offset-2" data-toggle='validator'>
		<div class="row" >
			<div class='col-xs-6'>
				<h3 style='margin:0;'><fmt:message key="login"/></h3>
			</div>
			<div class='col-xs-6 text-right'>
				<p class="none-margin">
					<a class="label label-primary" href="login.jsp?locale=en_US">English</a>
					<a class="label label-info" href="login.jsp?locale=zh_CN">中文</a>
				</p>
			</div>
		</div>
		<br>
		<div class="form-group">
			<div class="input-group input-group-lg">
		 	<span class='input-group-addon'>
		 		<span class='glyphicon glyphicon-user'></span>
			</span>
		 	<input type="text" name="username" id="username" onblur="checkUsername()" value="${requestScope.username}"
		 		 class="form-control" placeholder='<fmt:message key="username"/>' required data-error='<fmt:message key="warning-username"/>'>
			</div>
			<div class="help-block with-errors"></div>
		</div>
		<div class="form-group">
			<div class=' input-group input-group-lg'>
		 		<span class='input-group-addon'>
		 			<span class='glyphicon glyphicon-lock'></span>
				</span>
		 		<input type="password" name="password" id="password" value="${requestScope.password }" 
		 			class="form-control" placeholder='<fmt:message key="password"/>' required data-error='<fmt:message key="warning-pwd"/>'>
			</div>
			<div class='help-block with-errors'></div>
		</div>
		<div class='form-group form-group-sm' style="padding-left:120px;">
			<label for='rememberme' class='' style="margin-top:4px;position:absolute;margin-left:-120px;">
		   		<input id="rememberme" type="checkbox" value="remember-me"> <fmt:message key="rememberme"/>
		  	</label>
			<select id='cookiesave' class="form-control">
				<option value="1"><fmt:message key="oneday"/></option>
				<option value="7"><fmt:message key="oneweek"/></option>
				<option value="30"><fmt:message key="onemonth"/></option>
			 </select>
		</div>
		<button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="login"/></button>
      </form>
    </div> 
    </fmt:bundle>
  </body>
</html>
