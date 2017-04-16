<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/Assets/bootstrap1.jsp"></jsp:include>
<link rel="stylesheet" href="/Assets/bootstrap-table/bootstrap-table.min.css">
<script src="/Assets/bootstrap-table/bootstrap-table.min.js"></script>
<title>Tester Data Upload</title>
</head>
<body>
<div id="content" class="container">
  <div id="heading" class="col-xs-12 nprint page-header" style="margin-top:10px;padding-bottom:0px;padding-left:0px;">
    <h4 style="margin-left:0px;">Access Upload</h4>
  </div>
  <div id="toolbar" class="form-inline " style="padding:0!important">
    <div class="input-group input-group-sm " style="padding:0!important">
      <span class="input-group-addon">Spray File:</span>
      <input type="file" id="file" accept=".mdb" style="display:none">
      <input id="filepath" class="form-control" type="text" readonly>
      <span class="input-group-btn">
        <button class="btn btn-primary " type="button" onclick="$('input[id=file]').click();">Browser</button>
      </span>
    </div>
    <button id="saveData" class="btn btn-success btn-sm" type="button">Save Data</button>
  </div>
  <br />
  <table id="sprayTable" data-toggle="table" data-pagination="true"></table>
</div>
<script>
var jsonPost={}
$("#saveData").on("click",function(){
  $.post("mSaveData?action=spray",jsonPost)
   .done(function(data){alert(data); console.log(data) })
   .fail(function(){ alert("Please contact the MES Engineer, Data load fail") })
})

$("#file").on("change",function(){
  //get the filename from filepath
  var filepath=$(this).val();
  var filename=filepath.substring(filepath.lastIndexOf("\\")+1,filepath.length)
  $("#filepath").val(filename)

  var conn = new ActiveXObject("ADODB.Connection")
  conn.Open("DBQ="+filepath +";DRIVER={Microsoft Access Driver (*.mdb)};")
  var sql="select * from SunData"
	var rs = new ActiveXObject("ADODB.Recordset")
  rs.open(sql,conn)
  
  var json=[]
  //dataTable.push("haha")
  while(! rs.EOF ){
	  var t={}
	  t.Test_Date=rs.Fields("Test_Date")+''
	  var span = Date.parse(t.Test_Date);
	  var dt = new Date(span);
	  t.Test_Date = dt.getFullYear() + "-" + ("0" +(dt.getMonth()+1)).substr(("0" +(dt.getMonth()+1)).length-2,2) + "-" + ("0"+dt.getDate()).substr(("0"+dt.getDate()).length-2,2);
	  t.Alabel=rs.Fields("ID")+''
	  t.ModEff=rs.Fields("ModEff")+0.0
	  t.Rsh=rs.Fields("Rsh")+0.0
	  t.Rs=rs.Fields("Rs")+0.0
	  t.FF=rs.Fields("FF")+0.0
	  t.Isc=rs.Fields("Isc")+0.0
	  t.Voc=rs.Fields("Voc")+0.0
	  t.Ipm=rs.Fields("Ipm")+0.0
	  t.Vpm=rs.Fields("Vpm")+0.0
	  t.Pmax=rs.Fields("Pmax")+0.0
	  t.Temp=rs.Fields("Temp")+0.0
	  t.EnvTemp=rs.Fields("EnvTemp")+0.0
	  t.TMod=rs.Fields("TMod")+0.0
	  t.Test_Time=rs.Fields("Test_Time")+''
	  if(t.Test_Time==''||t.Test_Time=='null') t.dt=t.Test_Date+" "+'00:00:00'
	  else{
		  span = Date.parse(t.Test_Time);
	    dt = new Date(span);
	    t.Test_Time=("0"+dt.getHours()).substr(("0"+dt.getHours()).length-2,2)+":"
	               +("0"+dt.getMinutes()).substr(("0"+dt.getMinutes()).length-2,2)+":"
	               +("0"+dt.getSeconds()).substr(("0"+dt.getSeconds()).length-2,2)
      t.dt=t.Test_Date+" "+t.Test_Time
	  } 
	  
	  json.push(t)
	  rs.moveNext()
  }
  //console.log(dataTable)
  alert(JSON.stringify(json))
  alert("rs next")
  
  var keys=[]
  for( var i in json[0]){
      var thead={}
      thead.field=i
      thead.title=i
      keys.push(thead)
    }
  $("#sprayTable").bootstrapTable('destroy')
  $("#sprayTable").bootstrapTable({ 
    height:$(window).height()-$("#sprayTable").offset().top,
    data:json,
    columns:keys
  })
  
})

</script>
</body>
</html>