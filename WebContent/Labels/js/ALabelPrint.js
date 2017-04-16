	 var myDate=new Date(); 
	 var strDate=myDate.getFullYear()+"-"+("0"+(myDate.getMonth()+1)).slice(-2)+"-"+("0"+myDate.getDate()).slice(-2)
	 //set the label date to be today
	 $("#date").val(strDate)
	 
	 $("#submit").on("click",function(event){
		 event.preventDefault();
		 $(this).attr("disabled","disabled")
		 $(this).html("正在发送打印命令，请稍等。。。")
		 console.log($("#form1").serialize())
		 $.post("AlabelPrintServlet?action=print",$("#form1").serialize())
		 .done(function(data){
			 console.log(data)
			 $("#submit").removeAttr("disabled")
			 $("#submit").html("打印标签 Print labels")
		 })
		 .fail(function(){
			 alert("sorry you're failed")
		 })
		 
		 
	 })
	 
	 //get the company
	 $(function(){
		 $.post("AlabelPrintServlet?action=get&infoGet=Company")
		 .done(function(data){
			 console.log(data)
			 var json=JSON.parse(data)
			 //console.log(json)
			 var str=""
			 for( var o in json){
				 str+="<option value='"+json[o].code+"'>"+json[o].name+"</option>"
			 }
			 $("#company").html(str)
			 $("#company").click()
		 })
		 .fail(function(data){
			 alert("抱歉，可能网络故障，无法连接")
		 })
	 })

	 //get the factory site
   $("#company").on("click",function(){ 
	   $.post("AlabelPrintServlet?action=get&infoGet=Factory&company="+$("#company").val())
     .done(function(data){
       var json=JSON.parse(data)
       var str=""
       for( var o in json){
    	   str+="<option value='"+json[o].code+"'>"+json[o].name+"</option>"
       }
       $("#factory").html(str)
       $("#factory").click()
       console.log(str)
     })
     .fail(function(data){
       alert("抱歉，厂房名称失败，可能网络问题，如无法解决，请联系MES工程师")
     })
   })
   
   //get the vendor info
    $("#factory").on("click",function(){
    	$.post("AlabelPrintServlet?action=get&infoGet=Vendor&factory="+$("#factory").val())
        .done(function(data){
        	console.log(data)
          var json=JSON.parse(data)
          var str=""
          for( var o in json){
        	  str+="<option value='"+json[o].code+"'>"+json[o].name+"</option>"
          }
          $("#vendor").html(str)
        })
        .fail(function(data){
          alert("抱歉，获取组件类型失败，可能网络问题，如无法解决，请联系MES工程师")
        })
    })