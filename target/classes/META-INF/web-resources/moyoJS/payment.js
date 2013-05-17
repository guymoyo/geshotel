

dojo.declare('pay', null, {
	
	
	showDialog:function(){
		dijit.byId('pay').show();
		 console.log(dijit.byId("pay"));
	},
	
	hideDialog:function(){
		
		dijit.byId('pay').hide();
		 console.log(dijit.byId("pay").hide());
	}
   

});


/*dojo.declare('processReduction', null, {
		
	calculReduction:function(discount1, reduction1, room1){
		 
		var discount = dijit.byId(discount1);
		var reduction = dijit.byId(reduction1);
		var room = dojo.byId(room1);
		 
		dojo.xhrGet({
			
		    url: "/reservations/calculReduction",
		    
		    content: {
		        roomId: room.value,
		        remise: discount.get('value')
		    },
		    
		    handleAs: "text",
		    
		    load: function(result) {
		   
		    	console.log(result);
		        reduction.set('value', result);
		        
		    },
		    
		    error: function() {
		       console.log("no good");
		    }
		});
		
	}
	
});*/


dojo.ready(function(){
	var paie = new pay();
	
	dojo.connect(dijit.byId('showpay'), "onClick", function(evt){paie.showDialog();});
	dojo.connect(dijit.byId('hidePay'), "onClick", function(evt){paie.hideDialog();});
	dojo.connect(dijit.byId('print'), "onClick", function(evt){window.print();});
	console.log("ready");
});

