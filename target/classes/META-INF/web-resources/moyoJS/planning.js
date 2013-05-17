
dojo.declare('planning', null, {
	
	makeBookVisible:function(){
	
		dojo.query(".book").forEach(function(node, index, arr){

			dojo.connect(node, 'onmouseover', function(evt){
				console.log(node);
				dojo.query("#"+node.id+" ul").style("visibility", "visible");
				console.log(dojo.query("#"+node.id));
				
			});
			
			dojo.connect(node, 'onmouseout', function(evt){
				dojo.query("#"+node.id+" ul").style("visibility", "hidden");
				
				
			});
		});
	},

makeFreeVisible:function(){
	dojo.query(".free").forEach(function(node, index, arr){
		console.log(node);
		
		dojo.connect(node, 'onmouseover', function(evt){
			dojo.query("#"+node.id+" a").style("visibility", "visible");
			console.log(node.id);
			
		});
		
		dojo.connect(node, 'onmouseout', function(evt){
			dojo.query("#"+node.id+" a").style("visibility", "hidden");
			console.log(node.id);
			
		});
	});
},

eventOnDate:function(){
	d = dijit.byId("from");
	console.log(d);
	dojo.connect(d, "onChange", function(evt){
		console.log("dj");
		window.location.href=dojo.byId("planningUrl").value+'?from='+dijit.byId("from").get('value')+'&amp;category='+dojo.byId("category").value;
		
	});
},

event:function(){
	d = dojo.byId("category");
	console.log(d);
	dojo.connect(d, 'onchange', function(evt){
		d1 = dojo.date.locale.parse(new Date(dijit.byId("from").get('value')), {datePattern: "yyyy-MM-dd", selector: "date"});
		console.log(d1);
		window.location.href=dojo.byId("planningUrl").value+'?from='+d1+'&amp;category='+dojo.byId("category").value;
		
	});
}

	
	
});


dojo.ready(function(){
	
	var plane = new planning();
	plane.makeBookVisible();
	plane.makeFreeVisible();
	//plane.eventOnDate();
	//plane.event();
});