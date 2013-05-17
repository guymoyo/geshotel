dojo.ready(function(){
	var sale = dijit.byId("saleKeyForm");
	dojo.connect(sale, "onChange", function(evt){
		console.log("connected"+sale.get('value'));
		
	});
	console.log("onload");
});