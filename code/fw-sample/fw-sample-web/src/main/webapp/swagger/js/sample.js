function invokeCallback(v){
	$("#console").html($("#console").html() + v);
}

function sampleInvoke(type){
	param = $('#param' + type).val();
	$.post("sampleInvoke.do", param, invokeCallback);
}

function backgroundLogsCallback(v){
	$("#console").html($("#console").html() + v);
}

function backgroundLogs(){
	$.post("backgroundLogs.do", null, backgroundLogsCallback);
}

$(function(){ 
setInterval("backgroundLogs()",2000);
$("#btnQry").click(query);
$("#btnClr").click(clearCond);
$("#chkSelectAll").click(selectAll);
$("#deletes").click(deletes);
$("#adds").click(adds);
$("#updates").click(updates);
});

function clearCond(){
	$("#id").val("");
	$("#code").val("");
}

function query(){
	window.location.href = "index.do?id="+$("#id").val()+"&code="+$("#code").val();
}

function selectAll(){
	$("input[name='selectBox']").prop("checked",$("#chkSelectAll").prop("checked"));
}

function adds(){
	
}

function updates(){
	
}

function deletes(){
	var ids = "";
	$("input[name='selectBox']").each(function(idx, ele){
		if($(ele).prop("checked")){
			if(ids == ""){
				ids = $(ele).val();
			}else{
				ids += "," + $(ele).val();
			}
		}
	});
	$.post("sampleCUD.do?type=d", "ids="+ids, function(v){
		alert(v);
		query();
	});
}