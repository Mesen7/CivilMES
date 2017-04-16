<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../Assets/bootstrap.jsp"/>
<link rel="stylesheet" href="../Assets/css/atomizer.addon.css">
<link rel="stylesheet" href="../Assets/bootstrap-table/bootstrap-table.min.css">
<script src="../Assets/bootstrap-table/bootstrap-table.min.js"></script>
<script src="../Assets/bootstrap-table/extensions/export/bootstrap-table-export.min.js"></script>
<script src="../Assets/tableExport/tableExport.min.js"></script>
<script src="../Assets/tableExport/libs/jsPDF/jspdf.min.js"></script>
<script src="../Assets/tableExport/libs/jsPDF-AutoTable/jspdf.plugin.autotable.js"></script>
<title>Package Label Print</title>
</head>
<body>
	<div id="layout" class="container">
		<div id="content" class="row clearfix">
			<div id="heading" class="col-xs-12 page-header" style="margin-top:10px;padding-bottom:0;padding-left:0;">
				<h4 style="margin-left:0;">箱号标签打印 Package Label Print</h4>
			</div>
			<form class="col-xs-12  form-group form-group-sm" id="form1" name="form1">
				<table>
                    <tr>
                        <td class="table-label"><span><input id="label-checkbox" placeholder="" type="checkbox"/></span> 组件标签 : </td>
                        <td><input class="form-control" placeholder="" type="text"  id="Label" name="Label"></td>
                    </tr>
					<tr>
                        <td class="table-label col-xs-2"><span><input id="package-checkbox" placeholder="" type="checkbox"/></span> 包装箱号 : </td>
                        <td class="col-xs-4"><input class="form-control" placeholder="" type="text" id="Package" name="Package"/></td>
						<td class="table-label col-xs-2">包装箱组件数量 : </td>
						<td><input class="form-control col-xs-4" placeholder="" type="number" min="0" id="Count" name="Count" readonly/></td>
					</tr>
				</table>
			</form>
			<div id="toolbar" class="hidden">
                <button id="remove" name="remove" class="btn btn-danger btn-sm" disabled >
                    <span class="glyphicon glyphicon-remove"></span> Delete
                </button>
            </div>
			<table id="dataTable" data-toggle="table" data-align="center" data-pagination="true" 
			       data-toolbar="#toolbar"  data-click-to-select="true" data-search="true"
                   data-show-pagination-switch="true" data-show-refresh="true" data-show-columns="true"
                   data-show-export="true" data-show-footer="false">
			</table>
			<hr />
			<div class="text-center">
          <button class="btn btn-primary" id="submit">打印包装箱标签  Print Package labels</button>
      </div>
		</div>
	</div>
	<script src="js/PackagePrint.js"></script>
	<script>
	 $(function(){
		 $("#Label").focus();
		 $("#dataTable").bootstrapTable('destroy');
		 $("#label-checkbox").on("change",function(){
			 if(this.checked) $("#Label").removeAttr("readonly").select().focus();
			 else $("#Label").attr("readonly","readonly")
		 }).attr("checked",true);
		 $("#package-checkbox").on("change",function(){
           if(this.checked) $("#Package").removeAttr("readonly").select().focus();
           else $("#Package").attr("readonly","readonly")
		 }).attr("checked",true);
	 });
	 
	 $("#submit").on("click",function(event){
     event.preventDefault();
     $(this).attr("disabled","disabled");
     $(this).html("正在发送打印命令，请稍等。。。");
     var json={"packId":$("#Package").val()};
     $.post("PackageServlet?action=print",json)
     .done(function(data){
       console.log(data);
       $("#submit").removeAttr("disabled")
       .html("打印包装箱标签  Print Package labels")
     })
     .fail(function(){
       alert("sorry you're failed")
     })
     .always(function(){
    	 $("#submit").removeAttr("disabled")
     })
   });
	 
	 $("#dataTable").on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table',function(){
		 $("#remove").prop("disabled",!$("#dataTable").bootstrapTable('getSelections').length)
	 });
	 
	 $("#Package").on("keypress",function(e){
     if(e.keyCode===13){
    	 $(this).attr("readonly","readonly");
    	 $("#Label").attr("readonly","readonly");
    	 $("#label-checkbox").attr("checked",false);
         $("#package-checkbox").attr("checked",false);
    	 var str=$(this).val().trim();
    	 var regex=new RegExp("FGC[0-9]{14,14}$","i");
       if(!str.match(regex)){
         alert("包装箱错误");
         $("#Package").removeAttr("readonly")
       }else{
           var json={"packId":$("#Package").val()};
           $.post("PackageServlet?action=getbyPackId",json)
           .done(function(data){
               console.log(data);
               var json=JSON.parse(data);
           if(json.Result===0){
             alert(json.Message);
           }
           else{
             var keys=[];
             keys.push({"field":"state","checkbox":"true"});
             for(var i in json[0]){
               var thead={};
               thead.field=i;
               thead.title=i;
               thead.align='center';
               keys.push(thead)
             }
             console.log(keys);
             $("#toolbar").removeClass("hidden");
             $("#dataTable").bootstrapTable('destroy')
                 .bootstrapTable({
                   data:json,
                   columns:keys,
                   exportDataType: "all",
                   exportTypes: ['csv', 'txt', 'png', 'excel', 'doc']
                 })
           }
           })
           .fail(function(){
               alert("可能网络异常刷新看看？")
           })
           .always(function(){
               $("#Package").removeAttr("readonly");
               $("#package-checkbox").attr("checked","checked")
           })
       }
     }
	 });
	 $("#remove").on("click",function(){
		 var data=$("#dataTable").bootstrapTable('getSelections');
		 var label="";
		 for(i in data){
			 label=label+"'"+data[i].Label+"',"
		 }
		 var json={"alabel":label.substring(0,label.length-1),"packId":$("#Package").val()};
		 console.log(label);
		 $.post("PackageServlet?action=delete",json)
		 .done(function(data){
			 console.log(data);
			 var json=JSON.parse(data);
			 if (json.Result !== 0) {
                 var keys = [];
                 keys.push({"field": "state", "checkbox": "true"});
                 for (var i in json[0]) {
                     var thead = {};
                     thead.field = i;
                     thead.title = i;
                     thead.align = 'center';
                     keys.push(thead)
                 }
                 console.log(keys);
                 $("#toolbar").removeClass("hidden");
                 $("#dataTable").bootstrapTable('destroy')
                     .bootstrapTable({
                         data: json,
                         columns: keys,
                         exportDataType: "all",
                         exportTypes: ['csv', 'txt', 'png', 'excel', 'doc', 'pdf']
                     })
             } else {
                 $("#toolbar").addClass("hidden");
                 $("#dataTable").bootstrapTable('destroy');
                 alert(json.Message);
             }
			 
		 })
		 .fail(function(){
			 alert("数据删除失败,可能网络异常，请联系管理员")
		 });
		 $("#remove").prop("disabled",true)
	 });
	
	 $("#Label").on("keypress",function(e){
		 if(e.keyCode===13){
			 $("#Package").attr("readonly","readonly");
             $(this).attr("readonly","readonly");
             $("#label-checkbox").attr("checked",false);
             $("#package-checkbox").attr("checked",false);
			 var str=$(this).val().trim();
			 var regex=new RegExp("H[0-9]{15,15}$","i");
			 if(!str.match(regex)){
				 alert("组件标签错误");
				 $("#Label").removeAttr("readonly");
                 $("#label-checkbox").attr("checked",true)
			 }
			 else{
				 $.post("PackageServlet?action=query",$("#form1").serialize())
				 .done(function(data){
					 console.log(data);
					 var json=JSON.parse(data);
					 if(json.Result===0){
						 alert(json.Message);
						 $("#Label").focus().select();
					 }
					 else{
						 if($("#Package").val()===""){ $("#Package").val(json[0].PackId) }
						 $("#Count").val(json.length);
						 var keys=[];
						 keys.push({"field":"state","checkbox":"true"});
						 for(var i in json[0]){
							 var thead={};
							 thead.field=i;
							 thead.title=i;
							 thead.align='center';
							 keys.push(thead)
						 }
						 console.log(keys);
						 $("#toolbar").removeClass("hidden");
						 $("#dataTable").bootstrapTable('destroy')
                             .bootstrapTable({
                                 data:json,
							    columns:keys,
							    exportDataType: "all",
							    exportTypes: ['csv', 'txt', 'png', 'excel', 'doc','pdf']
						    })
					 }
				 })
				 .fail(function(){
					 alert("网络不好，请再试试？")
				 })
				 .always(function(){
					 $("#Label").removeAttr("readonly").focus().select();
					 $("#label-checkbox").attr("checked",true);
				 })
			 }
		 }
	 })
	</script>
</body>
</html>