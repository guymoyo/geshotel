/**
 * To hide and show menu
 * 
 * @author Guy Moyo
 */

dojo.declare('menu', null, {
	
	animate:function(id){
		console.log(id);
		console.log(dojo.query("#"+id+" h2"));
		
		ul = dojo.query("#"+id+" ul");
		ul.style("display","none");
		dojo.fx.wipeOut({ node: ul }).play();
		
		dojo.query("#"+id+" h2").connect('onclick', function(evt){
			
			ul = dojo.query("#"+id+" ul");
			
			
			if(ul.style("display")=="none"){
				
				dojo.query("#_menu li ul").forEach(function(nodeul, index, nodelist){
					console.log(nodeul);
					if(nodeul.style.display=="block"){
						nodeul.style.display="none";
						dojo.fx.wipeOut({ node: nodeul }).play();
					}
				});
				
				ul.style("display","block");
				console.log(ul.style("display"));
				console.log("in");
				out = dojo.fx.wipeIn({ node: ul }).play();
				console.log(out);
				
			}else{
				ul.style("display","none");
				console.log("out");
				console.log(ul.style("display"));
				dojo.fx.wipeOut({ node: ul }).play();
			}
		});
	},
	
	changeStyle:function(id){
		
		dojo.query("#"+id+" h2").connect('onmouseover', function(evt){
			/*dojo.query("#"+id+" h2").style("background","black");*/
			dojo.query("#"+id+" h2").style("cursor","pointer");
			console.log(dojo.query("#"+id+" h2").style("background"));
		});
		
		dojo.query("#"+id+" h2").connect('onmouseout', function(evt){
			/*dojo.query("#"+id+" h2").style("background","#648C1D");*/
			console.log(dojo.query("#"+id+" h2").style("background"));
		});
	}
	
});


dojo.ready(function(){
	
	var anim = new menu();
	anim.animate("c_otherservice");
	anim.animate("c_room");
	anim.animate("c_invoice");
	anim.animate("c_invoiceitem");
	anim.animate("c_roomcategory");
	anim.animate("c_reservation");
	anim.animate("c_useraccount");
	anim.animate("c_payment");
	anim.animate("stat");
	anim.animate("switch");
	anim.animate("resto_seller_cashregister");
	anim.animate("c_productinventory");
	anim.animate("c_barrestaupayment");
	anim.animate("c_product");
	anim.animate("c_brtable");
	anim.animate("c_provider");
	anim.animate("c_productgroup");
	anim.animate("resto_manager_cash");
	anim.animate("menu_category_procurement_0001");
	anim.animate("configuration");
	anim.animate("c_producttype");
	anim.animate("c_customer");
	
	anim.changeStyle("c_otherservice");
	anim.changeStyle("c_room");
	anim.changeStyle("c_invoice");
	anim.changeStyle("c_invoiceitem");
	anim.changeStyle("c_roomcategory");
	anim.changeStyle("c_reservation");
	anim.changeStyle("c_useraccount");
	anim.changeStyle("c_payment");
	anim.changeStyle("stat");
	anim.changeStyle("switch");
	anim.changeStyle("resto_seller_cashregister");
	anim.changeStyle("c_productinventory");
	anim.changeStyle("c_barrestaupayment");
	anim.changeStyle("c_product");
	anim.changeStyle("c_brtable");
	anim.changeStyle("c_provider");
	anim.changeStyle("c_productgroup");
	anim.changeStyle("resto_manager_cash");
	anim.changeStyle("menu_category_procurement_0001");
	anim.changeStyle("configuration");
	anim.changeStyle("c_producttype");
	anim.changeStyle("c_customer");
});



