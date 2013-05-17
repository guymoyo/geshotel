

dojo.declare('booking', null, {
	
	checkInDate:0,
	
	checkOutDate:0,
	
	numDays:1,
	
//set variable	javascript
	setCheckInDate:function(checkInDate){
		this.checkInDate = new Date(dijit.byId(checkInDate).get('value'));
	},
	
	setCheckOutDate:function(checkOutDate){
		this.checkOutDate = new Date(dijit.byId(checkOutDate).get('value'));
	},
	
	setNumDays:function(numDays){
		this.numDays =  dijit.byId(numDays).get('value');
	},

//set field in html page
	setFielCheckInDate:function(checkInDate){
		 dijit.byId(checkInDate).set('value',this.checkInDate);
	},
	
	setFielCheckOutDate:function(checkOutDate){
		 dijit.byId(checkOutDate).set('value',this.checkOutDate);
	},
	
	setFielNumDays:function(numDays){
		 dijit.byId(numDays).set('value',this.numDays);
	},

//fonction to use
	changeNumDays:function(){
		this.numDays = dojo.date.difference(this.checkInDate, this.checkOutDate, 'day')+1;
		console.log(this.numDays);
		
	},
	
	changeCheckOutDate:function(){
		console.log(this.checkInDate);
		console.log(this.numDays);
		this.checkOutDate=dojo.date.add(this.checkInDate, 'day', this.numDays-1);
		console.log(this.checkOutDate);
	},
	
//fonction for handle
	eventOnNumDays:function(checkInDate,checkOutDate,numDays){
		console.log("eventOnNumDays");
		this.setCheckInDate(checkInDate);
		this.setNumDays(numDays);
		this.changeCheckOutDate();
		this.setFielCheckOutDate(checkOutDate);
	},
	
	eventOnDate:function(checkInDate,checkOutDate,numDays){
		console.log("eventOnDate");
		this.setCheckInDate(checkInDate);
		this.setCheckOutDate(checkOutDate);
		this.changeNumDays();
		this.setFielNumDays(numDays);
	},

});


dojo.declare('processReduction', null, {
		
	calculReduction:function(discount1, reduction1, room1){
		 
		var discount = dijit.byId(discount1);
		var reduction = dijit.byId(reduction1);
		var room = dojo.byId(room1);
		 
		dojo.xhrGet({
			
		    url: dojo.byId("reductionLink").value,
		    
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
	
});


dojo.ready(function(){
	var book = new booking();
	
	dojo.connect(dijit.byId("_numDays_id"), "onChange", function(evt){book.eventOnNumDays("_checkInDate_id", "_checkOutDate_id", "_numDays_id");} );
	dojo.connect(dijit.byId("_checkInDate_id"), "onChange", function(evt){book.eventOnDate("_checkInDate_id", "_checkOutDate_id", "_numDays_id");} );
	dojo.connect(dijit.byId("_checkOutDate_id"), "onChange", function(evt){book.eventOnDate("_checkInDate_id", "_checkOutDate_id", "_numDays_id");});
	
	var process = new processReduction();
	
	dojo.connect(dijit.byId("_discount_id"), "onKeyUp", function(evt){process.calculReduction("_discount_id", "_reduction_id", "_room.id_id");});
	
	console.log("onload");
});

