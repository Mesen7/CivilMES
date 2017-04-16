<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/Assets/bootstrap1.jsp"></jsp:include>
<link rel="stylesheet" href="/Assets/bootstrap-table/bootstrap-table.min.css">
<script src="/Assets/bootstrap-table/bootstrap-table.min.js"></script>
<title>CSV Upload</title>
</head>
<body>
<div id="content" class="container">
  <div id="heading" class="col-xs-12 nprint page-header" style="margin-top:10px;padding-bottom:0px;padding-left:0px;">
    <h4 style="margin-left:0px;">CSV Upload</h4>
  </div>
  <div id="toolbar" class="form-inline " style="padding:0!important">
    <div class="input-group input-group-sm " style="padding:0!important">
      <span class="input-group-addon">Spray File:</span>
      <input type="file" id="file" accept=".csv" style="display:none">
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

  var file=this.files[0];
  if(window.FileReader){
    var fr=new FileReader();
    fr.readAsText(file)
    fr.onload=function(){
      var data=this.result;
      var dataArray=data.split("\r\n")
      var vaildData=new Array();
      for(var i=0;i<dataArray.length;i++){
        var temp=dataArray[i].split(",");
        if(temp[0]){ vaildData[i]=temp; }
      }

      console.log(vaildData.length)
      console.log(vaildData[0].length)
      
      //get the column count
      var rowsLen;
   	  for(var i=1;i<vaildData[0].length;i++){ 
   		  if(vaildData[0][i]!=0){
   			  rowsLen=i
   			} 
   		}
   	  rowsLen++
   	  
   	  //filter the none-title data
      var json=[];
      for(var i=1;i<vaildData.length;i++){
        var temp={};
        for(var j=0;j<rowsLen;j++){
          temp[vaildData[0][j]]=vaildData[i][j]
        }
        json.push(temp);
      }
      
      jsonPost={"spray":JSON.stringify(json)}
      console.log(json[0]["Time"]);
      
      var keys=[]
      for( var i in json[0]){
          var thead={}
          thead.field=i
          thead.title=i
          keys.push(thead)
        }
      //console.log(JSON.stringify(keys))
      //console.log(keys)
      $("#sprayTable").bootstrapTable('destroy')
      $("#sprayTable").bootstrapTable({ 
        height:$(window).height()-$("#sprayTable").offset().top,
    	  data:json,
        columns:keys
      })
    }
  }
})
</script>
</body>
</html>