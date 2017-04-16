<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../Assets/bootstrap.jsp"/>
<link rel="stylesheet" href="../Assets/css/atomizer.addon.css">
<title>Alabel Print</title>
</head>
<body>
	<div id="layout" class="container">
		<div id="content" class="row clearfix">
			<div id="heading" class="col-xs-12 page-header" style="margin-top:10px;padding-bottom:0px;padding-left:0px;">
				<h4 style="margin-left:0px;">组件标签打印 Alabel Print</h4>
			</div>
			<form class="col-xs-12  form-group form-group-sm" id="form1">
				<table>
		  <tr class="hidden">
            <td class="table-label col-xs-2">公司 : </td>
            <td><select class="form-control col-xs-4" id="company" name="company" ></select></td>
            <td class="table-label col-xs-2">生产基地 : </td>
            <td><select class="form-control col-xs-4" id="factory" name="factory" ></select></td>
          </tr>
                    <tr>
            <td class="table-label col-xs-2">产品类型 : </td>
            <td>
              <select class="form-control col-xs-4" id="vendor" name="vendor">
              </select>
            </td>
            <td class="table-label">需要打印数量 : </td>
            <td><input class="form-control" type="number" id="printNumber" name="printNumber" min="1" value="1"/></td>
          </tr>
				  <tr>
            <td class="table-label">标签打印日期 : </td>
            <td><input class="form-control" type="date" id="date" name="date" ></td>
            <td class="table-label hidden">今日已打印标签 : </td>
            <td><input class="form-control hidden" type="number"  value="0" disabled/></td>
          </tr>
				</table>
				<hr />
				<div class="text-center">
					<button class="btn btn-primary" id="submit">打印标签  Print labels</button>
				</div>
			</form>
		</div>
	</div>
<script src="js/ALabelPrint.js?v=${date}"></script>
</body>
</html>