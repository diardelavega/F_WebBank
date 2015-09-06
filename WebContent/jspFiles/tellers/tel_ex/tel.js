$(function() {
	$(".datePicker").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : 'dd/mm/yy',
	});
});

function reqReplyAlert(jsobj){
	var req=jsobj.get('replyData');
}