// models / data

var items = new kendo.data.DataSource({
    schema: 
    { 
        model: {} 
    },
    transport: 
    { 
		/*
        read: 
        { 
            url:  "http://localhost:8080/ymca-web/resources/data/menu.json", 
            dataType: "json" 
        } */
		
		read:  {
		  url: location.protocol+"//"+location.host+"/ymca-web/getItems",
		  //url: "http://166.78.104.119:8080/ymca-web/getItems",
		  dataType: "json" // "jsonp" is required for cross-domain requests; use "json" for same-domain requests
		}
		
    }
});

var contacts = new kendo.data.DataSource({
    schema: 
    { 
        model: {} 
    },
    transport: 
    { 
		read:  {
		  url: location.protocol+"//"+location.host+"/ymca-web/getContacts",
		  //url: "http://166.78.104.119:8080/ymca-web/getContacts",
		  dataType: "json" // "jsonp" is required for cross-domain requests; use "json" for same-domain requests
		}
		
    }
});

var signup = new kendo.data.DataSource({
    schema: 
    { 
        model: {} 
    },
    transport: 
    { 
		read:  {
		  async: false,
		  url: location.protocol+"//"+location.host+"/ymca-web/getSignupByType?type=MEMBERSHIP",
		  dataType: "json" // "jsonp" is required for cross-domain requests; use "json" for same-domain requests
		}
		
    }
});

var alldata = [];
if($.sessionStorage.getItem('cart')!=null){
	alldata = JSON.parse($.sessionStorage.getItem('cart'));
	//console.log(alldata);
}

var cart = kendo.observable({
    contents: alldata,
    cleared: false,
	errMsg: "",
	
    contentsCount: function() {
        return this.get("contents").length;
    },

    add: function(item,contact) {
        var found = false;
        this.set("cleared", false);
		var errMsg = this.errMsg;
		
		//console.log(contact);
			//console.log(item);
			
        for (var i = 0; i < this.contents.length; i ++) {
            var current = this.contents[i];
            if (current.item.prodId == item.prodId && current.contact.contactId == contact.contactId) {
                //current.set("quantity", current.get("quantity") + 1);
                found = true;
                break;
            }
        }
		
		var cartObj = this;
        if (found) {
			errMsg = errMsg + "\r\nProgram " + current.item.name + " and Contact " + current.contact.fname + " " + current.contact.lname + " already exists in cart.";
			$("#uniform-user_"+current.contact.contactId +" span").attr("class", "");
			$("#user_"+current.contact.contactId).attr('checked', false);
			cartObj.set("errMsg",errMsg);
			$("#dspErr").slideDown();
			setTimeout(function(){$("#dspErr").slideUp();}, 5000);
		}
		else {	
			var signupfound = false;
			
			signup.fetch(function() {
								  console.log(this);
				var data = this.data();
				for (var s = 0; s < data.length; s ++) {
					if(data[s].itemId == item.prodId && data[s].contactId == contact.contactId){
						errMsg = errMsg + "\r\nYou have already signup for Program " + item.name + " and Contact " + contact.fname + " " + contact.lname + ".";
						$("#uniform-user_"+contact.contactId +" span").attr("class", "");
						$("#user_"+contact.contactId).attr('checked', false);
						cartObj.set("errMsg",errMsg);
						$("#dspErr").slideDown();
						setTimeout(function(){$("#dspErr").slideUp();}, 5000);
						signupfound = true;
						break;
					}
				}
			});
			
			if(!signupfound){
				//cartObj.set("errMsg","");
				var uniqueId = item.itemDetailsSessionId + "_" + contact.contactId;
				this.contents.push({ item: item, contact:contact , quantity: 1, discount1: 0, discountcode1: '', dicountpriceUSD: '$0.00', uniqueId: uniqueId });
			}
        }
		//console.log(JSON.stringify(this.contents));
		$.sessionStorage.setItem('cart', JSON.stringify(this.contents));
		//console.log($.sessionStorage.getItem('key_name'));
		//console.log(item);
    },
	
	update: function(item,contact,discount1,discountcode1) {
        var found = false;
        this.set("cleared", false);

        for (var i = 0; i < this.contents.length; i ++) {
            var current = this.contents[i];
            if (current.item.prodId == item.prodId && current.contact.contactId == contact.contactId) {
                current.set("discount1", discount1);
				current.set("discountcode1", discountcode1);
				current.set("dicountpriceUSD", kendo.format("{0:c}",discount1));
				current.set("item.finalamount", discount1);
                found = true;
                break;
            }
        }

        if (found) {
            $.sessionStorage.setItem('cart', JSON.stringify(this.contents));
        }
    },
	
    remove: function(item) {
        for (var i = 0; i < this.contents.length; i ++) {
            var current = this.contents[i];
            if (current === item) {
                this.contents.splice(i, 1);
				$.sessionStorage.setItem('cart', JSON.stringify(this.contents));
                break;
            }
        }
    },

    empty: function() {
        var contents = this.get("contents");
        contents.splice(0, contents.length);
		$.sessionStorage.clear();
    },

    clear: function() {
        var contents = this.get("contents");
        contents.splice(0, contents.length);
        this.set("cleared", true);
		$.sessionStorage.clear();
    },
	
	itemamount: function(item,contact) {
         var price = 0,
            contents = this.get("contents"),
            length = contents.length,
            i = 0;

        for (; i < length; i ++) {
			var current = contents[i];
            if (current.item === item && current.contact === contact) {
				if(contact.isMember)
            		price = (parseFloat(contents[i].item.memberprice) - parseFloat(contents[i].item.memberdiscount) - parseFloat(contents[i].discount1)) * contents[i].quantity;
				else
					price = (parseFloat(contents[i].item.nonmemberprice) - parseFloat(contents[i].item.nonmemberdiscount) - parseFloat(contents[i].discount1)) * contents[i].quantity;
			}
        }

        return kendo.format("{0:c}", price);
	},
	
	totaldiscount: function() {
        var discount = 0,
            contents = this.get("contents"),
            length = contents.length,
            i = 0;

        for (; i < length; i ++) {
			var current = contents[i];
			if(current.contact.isMember)
            	discount += parseFloat(contents[i].item.memberdiscount) + parseFloat(contents[i].discount1);
			else
				discount += parseFloat(contents[i].item.nonmemberdiscount) + parseFloat(contents[i].discount1);
        }

        return kendo.format("{0:c}", discount);
	},
	
    total: function() {
        var price = 0,
            contents = this.get("contents"),
            length = contents.length,
            i = 0;

        for (; i < length; i ++) {
			var current = contents[i];
			if(current.contact.isMember)
            	price += (parseFloat(contents[i].item.memberprice) - parseFloat(contents[i].item.memberdiscount) - parseFloat(contents[i].discount1)) * contents[i].quantity;
			else
				price += (parseFloat(contents[i].item.nonmemberprice) - parseFloat(contents[i].item.nonmemberdiscount) - parseFloat(contents[i].discount1)) * contents[i].quantity;
        }

        return kendo.format("{0:c}", price);
    }
});

var layoutModel = kendo.observable({
    cart: cart
});

var cartPreviewModel = kendo.observable({
    cart: cart,

    cartContentsClass: function() {
        return this.cart.contentsCount() > 0 ? "active" : "empty";
    },

    removeFromCart: function(e) {
        //this.get("cart").remove(e.data);
		var cartData = this.get("cart");
		var kendoWindow = $("<div />").kendoWindow({
			title : "Confirm",
			resizable : false,
			modal : true,
			width : 400
		});

		kendoWindow.data("kendoWindow").content(
				$("#delete-confirmation").html()).center().open();

		kendoWindow.find(".delete-confirm,.delete-cancel").click(
		function() {
			if ($(this).hasClass("delete-confirm")) {
				//alert("Deleting record...");
				$(".k-loading-mask").show();
				cartData.remove(e.data);
				$(".k-loading-mask").hide();
			}

			kendoWindow.data("kendoWindow").close();
		}).end();
    },
	
	updateCart: function(cartItem,d_price,d_code) {
		cart.update(cartItem.item,cartItem.contact,d_price,d_code);
	},
	
    emptyCart: function() {
        cart.empty();
		$("#checkout_content").show();
		$("#program").fadeIn(100);
		commonSearch();
    },

    itemPrice: function(cartItem) {
		if(cartItem.contact.isMember)
       	 	return kendo.format("{0:c}", cartItem.item.memberprice);
		else
			return kendo.format("{0:c}", cartItem.item.nonmemberprice);
    },
	
	discountCode: function(cartItem) {
        return cartItem.item.discountcode;
    },
	
	discountPrice: function(cartItem) {
		if(cartItem.contact.isMember)
        	return kendo.format("{0:c}", cartItem.item.memberdiscount);
		else
			return kendo.format("{0:c}", cartItem.item.nonmemberdiscount);
    },
	
	finalPrice: function(cartItem) {
        //return kendo.format("{0:c}", this.get("cart").itemamount(cartItem.item,cartItem.contact));
		return this.get("cart").itemamount(cartItem.item,cartItem.contact);
    },
	
	totalDiscountAmount: function() {
		 return this.get("cart").totaldiscount();
    },
	
    totalPrice: function() {
        return this.get("cart").total();
    },
	
	updatePromoCart: function(e) {
		 $("#tcSuccessSpan").css("display", "none");		
		 $("#tcSuccessSpan" ).html("");	
		 $("#tcErrorSpan").css("display", "none");		
		 $( "#tcErrorSpan" ).html("");
		var d_promocode = e.data.discountcode1;
		var found = false;
		
		if(d_promocode!=''){
			$.ajax({
				  type: "GET",
				  async: false,
				  url:"getPromodetails?promocode="+d_promocode,
				  dataType: "json",
				  success: function( data ) {
					  if(data.length==1){
						  $.each(data, function(i,promo) {
								if(promo.itemDetails.length>0){
									  $.each(promo.itemDetails, function(j,itemInfo) {
											  if(e.data.item.itemDetailsId == itemInfo.itemDetailsId){
												  	  var p_price = parseFloat(0);
													  if(e.data.contact.isMember)
													  	p_price = e.data.item.memberprice;
													  else
													  	p_price = e.data.item.nonmemberprice;
												  	  var d_price = parseFloat(0);
													  var discount_type = promo.discounttype;
													  if(discount_type=='AMOUNT')
														  d_price = promo.discountValue;
													  else if(discount_type=='PERCENTAGE'){
														  d_price = parseFloat(promo.discountPercentage) * parseFloat(p_price);
														  d_price = d_price/100;
													  }
													  cart.update(e.data.item,e.data.contact,d_price,d_promocode);
													  found = true;
											  }
											  
									  });
							    }
							 	
						  });
					  }
					  else{
						  $("#tcSuccessSpan").css("display", "none");		
						  $("#tcSuccessSpan" ).html("");	
						  $("#tcErrorSpan").css("display", "block");		
						  $( "#tcErrorSpan" ).html("The Promo code you enetered is invalid");
						  return;
					  }
				  },
				  error: function( xhr,status,error ){
					  //alert("1" +status);
					  console.log(xhr);
					 
				  }
			});
		}
		
		if (!found) {
              $("#tcSuccessSpan").css("display", "none");		
			  $("#tcSuccessSpan" ).html("");	
			  $("#tcErrorSpan").css("display", "block");		
			  $( "#tcErrorSpan" ).html("The Promo code you enetered is invalid");
        }
		
    },
	
	hidePromoFromCart: function(e) {
        cart.update(e.data.item,e.data.contact,parseFloat(0),"");
		$("#custom_promo_"+e.data.uniqueId).hide();
    },
	
    proceed: function(e) {
        this.get("cart").clear();
        sushi.navigate("/");
    }
});

var indexModel = kendo.observable({
    items: items,
    cart: cart,

    addToCart: function(e) {
		//console.log(e.data);
        cart.add(e.data);
    }
});

var detailModel = kendo.observable({
    imgUrl: function() {
        return "/images/200/" + this.get("current").image
    },

    price: function() {
        return kendo.format("{0:c}", this.get("current").price);
    },

    addToCart: function(e) {
        cart.add(this.get("current"));
    },

    setCurrent: function(itemID) {
        this.set("current", items.get(itemID));
    },

    previousHref: function() {
        var id = this.get("current").id - 1;
        if (id === 0) {
           id = items.data().length - 1;
        }

        return "#/menu/" + id;
    },

    nextHref: function() {
        var id = this.get("current").id + 1;

        if (id === items.data().length) {
           id = 1;
        }

        return "#/menu/" + id;
    },

    kCal: function() {
        return kendo.toString(this.get("current").stats.energy /  4.184, "0.0000");
    }
});

// added by Lavy
var productModel = kendo.observable({
	setCurrent: function(itemID) {
		//console.log(items.get(itemID));
        this.set("current", items.get(itemID));
    },	
	addToCart: function(e) {
		//console.log("Add to cart");
		//console.log(this.get("current"));
        cart.add(this.get("current"));
		location.href = '#/checkout';
    },
	removeFromCart: function(e) {
		//console.log(this.get("current"));
        this.get("cart").remove(this.get("current"));
    }
});

// Views and layouts
var layout = new kendo.Layout("layout-template", { model: layoutModel });
var cartPreview = new kendo.Layout("cart-preview-template", { model: cartPreviewModel });
var index = new kendo.View("index-template", { model: indexModel });
var checkout = new kendo.View("checkout-template", {model: cartPreviewModel });
var detail = new kendo.View("detail-template", { model: detailModel });
//added by Lavy
var productindex = new kendo.View("product-template", { model: productModel });

var sushi = new kendo.Router({
    init: function() {
         //console.log("router init");
        layout.render("#application");
    }
});

var viewingDetail = false;

// Routing
sushi.route("/", function() {
    //console.log("router root route")
    viewingDetail = false;
    layout.showIn("#content", index);
    layout.showIn("#pre-content", cartPreview);
});

sushi.route("/product/:id/:cid", function(itemsID, ContactsId) {
	//console.log(itemsID);
	//console.log(ContactsId);
	viewingDetail = false;
	var itemId = itemsID.split(",");
	var contactId = ContactsId.split(",");
	//alert(contactId);
	$.each(contactId,function(j){
		contacts.fetch(function() {
		var cid =  contacts.get(contactId[j]);	
		//console.log(cid);
		   $.each(itemId,function(i){
			   items.fetch(function() {
					var iid = items.get(itemId[i])
					//console.log(iid);
					//this.set("current", items.get(array[i]));
					cart.add(iid,cid);
				});
			});
		 });
	});
	
	//console.log(cart.errMsg );
	//if(cart.errMsg == "")
		location.href = '#/checkout';
	//else
		//location.href = '#';
});

sushi.route("/checkout", function() {
    viewingDetail = false;
    layout.showIn("#checkout_content", checkout);
	//alert(cart.total());
    cartPreview.hide();

	setTimeout(function(){
		if(cartPreviewModel.totalPrice()=='$0.00'){
			$("#promo").hide();
		}else{
			$("#promo").show();
		}
	}, 500);
});

sushi.route("/menu/:id", function(itemID) {
	//alert(itemID);					  
    layout.showIn("#pre-content", cartPreview);
    var transition = "",
        current = detailModel.get("current");
	
	//console.log(detailModel);
    if (viewingDetail && current) {
        transition = current.id < itemID ? "tileleft" : "tileright";
    }

    items.fetch(function(e) {
        if (detailModel.get("current")) { // existing view, start transition, then update content. This is necessary for the correct view transition clone to be created.
			layout.showIn("#content", detail, transition);
            detailModel.setCurrent(itemID);
        } else {
            // update content first
            detailModel.setCurrent(itemID);
            layout.showIn("#content", detail, transition);
        }
    });

    viewingDetail = true;
});

$(function() {
    sushi.start();
	
});
