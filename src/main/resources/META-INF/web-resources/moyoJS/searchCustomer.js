

dojo.declare('searchCustomer', null, {
	
	showDialog:function(){
		ul=dojo.byId("clientUl");
		ul.style("display","block");
		console.log("out");
		console.log(ul.style("display"));
		dojo.fx.wipeOut({ node: ul }).play();
	},
	
	hideDialog:function(){
		
			ul=dojo.query("#clientUl");	
			ul.style("display","none");
			console.log(ul.style("display"));
			console.log("in");
			out = dojo.fx.wipeIn({ node: ul }).play();
			console.log(out);
		 
	},
	
	search:function(nom){
		 
		if(nom.length==0){
			ul=dojo.query("#clientUl");	
			ul.style("display","none");
			console.log(ul.style("display"));
			console.log("in");
			out = dojo.fx.wipeIn({ node: ul }).play();
			console.log(out);
		}else{
		dojo.empty(dojo.byId("clientUl"));
		
		dojo.xhrGet({
			
			//url: ${searchCustomer} is readyy define in headerBooking.jspx
		    url: dojo.byId("search").value,
		    
		    content: {
		        name: nom
		        
		    },
		    
		    handleAs: "json",
		    
		    load: function(result) {
		    	ul=dojo.query("#clientUl");
				ul.style("display","block");
				console.log("out");
				console.log(ul.style("display"));
				dojo.fx.wipeOut({ node: ul }).play();
				
				var arrLi=[];
		    	for(i = 0; i < result.length; i++){
		    		
		    		/*dojo.create("li", {
		    			innerHTML: "",
		    	        id: i
		    	    }, dojo.byId("clientUl"));
		    		*/
		    		var li = dojo.create("li", {
		    			
		    			id: result[i].id,
		    			innerHTML: '<a href="#" class="customer" id="'+result[i].id+'">'+result[i].fullName+'</a>',
		    			
		    	    }, dojo.byId("clientUl"));
		    		
		    		arrLi[i]=li;
		    		
		  
		    	}
		    	//to connnect event on Dom created dynamically
		    	dojo.forEach(arrLi, function(item, index){
		    		dojo.connect(item, 'onclick', function(evt){
		    			
		    			console.log("item"+item.id);
		    			ul=dojo.query("#clientUl");	
		    			ul.style("display","none");
		    			console.log(ul.style("display"));
		    			console.log("in");
		    			out = dojo.fx.wipeIn({ node: ul }).play();
		    			console.log(out);
		    			caution= item.account;
		    			console.log(item);
		    			dojo.xhrGet({
		    				
		    			    url: dojo.byId("reserByCust").value,
		    			    
		    			    content: {
		    			    	customer:item.id
		    			    },
		    			    handleAs: "json",
		    			    
		    			    load: function(data) {
		    			    	
		    			    	var content = "<table>" 
		    			    					+"<tr>"
		    			    					+"<th>checkInDate</th>"
		    			    					+"<th>checkOutDate</th>"
		    			    					+"<th>Number Days</th>"
		    			    					+"<th>roomNumber</th>"
		    			    					+"<th>amount</th>"
		    			    					+"<th>customer</th>"
		    			    					+"<th>etat</th>"
		    			    					+"<th>detail</th>"
		    			    					+"</tr>";
		    			        // For every news item we received...
		    			        dojo.forEach(data,function(item) {
		    			            content += "<tr>";
		    			            content += "<td>" + dojo.date.locale.format(new Date(item.checkInDate), {datePattern: "yyyy-MM-dd", selector: "date"}); + "</td>";
		    			            content += "<td>" +dojo.date.locale.format(new Date(item.checkOutDate), {datePattern: "yyyy-MM-dd", selector: "date"}); + "</td>";
		    			            content += "<td>" + item.room.roomNumber + "</td>";
		    			            content += "<td>"+item.numDays+"</td>";
		    			            content += "<td>"+item.amount+"</td>";
		    			            content += "<td>"+item.customer.lastName+" "+item.customer.firstName+"</td>";
		    			            content += "<td>"+item.invoice.payementState+"</td>";
		    			            
		    			            content += '<td><a href="'+dojo.byId("detailReservation").value+'?reservationId='+item.id+'" >link</a></td>';
		    			            content += "</tr>";
		    			            client = item.customer.account;
		    			        });
		    			        content += "</table>";
		    			        content += "<div>Account: "+client+"</div>";
		    			        myDialog = new dijit.Dialog({
		    			            title: "Liste Reservation",
		    			            content: content  			    
		    			        });
		    			        myDialog.show();
		    			      
		    			       /* dojo.byId("jd").innerHTML = content;
		    			        
		    			    	dijit.byId('dialog').show();*/
		    			        
		    			    },
		    			    
		    			    error: function() {
		    			       console.log("no good");
		    			    }
		    			});
		    			
		    			
		    		});
		    	});
		    },
		    
		    error: function() {
		       console.log("no good");
		       $('#clientUl').html("customer no found");
		    }
		});
		}
   },
   
   /*addEvent:function(){
		dojo.query(".customer").forEach(function(node, index, arr){
			console.log("node"+node);
			dojo.connect(node, 'onclick', function(evt){
	
				console.log(node);
				
			});
			
		});
	},*/
	
});


dojo.ready(function(){
	
	var customer = new searchCustomer();
	
	dojo.connect(dijit.byId("client"), "onKeyUp", function(evt){customer.search(dijit.byId("client").get('value'));});
	//dojo.connect(dijit.byId("client"), "onBlur", function(evt){customer.hideDialog();});
	dojo.connect(dijit.byId('hide'), "onClick", function(evt){dijit.byId('dialog').hide();});
	console.log("onload search customer");
	url = dojo.byId("search");
	
	//code for show tip
	ul=dojo.byId("aide");
	if(ul != null){
	dojo.fx.wipeIn({ node: ul, delay:3000, duration:2000 }).play();
	dojo.connect(dojo.byId("close"), 'onclick', function(evt){
		ul=dojo.byId("aide");
		console.log("out");
		dojo.fx.wipeOut({ node: ul, duration:2000 }).play();});
	}
});

