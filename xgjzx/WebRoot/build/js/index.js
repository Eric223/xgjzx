function slide(){function t(){r++,r==l&&(r=1,$(n).css("left","0px")),o=r*-c+"px",$(n).stop().animate({left:o},500),e()}function e(){var t=r;$(i).css("background","white"),r==l-1&&(t=0),$(i[t]).css("background","#ff6537")}var n=$(".banner-slide-pic"),i=$(".banner-slide-dot li"),a=$(".banner-slide"),r=0,c=890,l=4,o=0;clearInterval(s);var s=setInterval(function(){t()},4e3);$(a).mouseenter(function(){clearInterval(s)}),$(a).mouseleave(function(){clearInterval(s),s=setInterval(function(){t()},4e3)}),$(i).hover(function(){r=$(this).index(),o=r*-c+"px",$(n).stop().animate({left:o},500),e()})}function searchBlock(t){function e(){window.location.href="/pages/search.html?city="+i.val()}function n(t){13==t.keyCode&&e()}var i=t.find("input");t.on("click",".search-bar button",e).on("keydown",".search-bar input",n)}function contactBlock(t){function e(){var t=0;return i.each(function(){""!=$(this).val()&&t++}),t!=i.length}function n(){if(e())return void myAlert("格式错误或者信息遗漏");var t={page:"",data:{id:"id",company:i.eq(0).val(),name:i.eq(1).val(),phone:i.eq(2).val(),city:i.eq(3).val(),status:""}};myAjax(api.contactUs,JSON.stringify(t),function(t){100==t?myAlert("申请成功，我们会尽快联系您"):myAlert("网络错误")})}var i=t.find("input");t.on("click","button",n)}function renderBusPriceBlock(t){var e=t.find("ul li"),n={page:1};myAjax(api.getBusPrice,n,function(t){var n=t.data.splice(0,6);e.each(function(t){var e=n[t],i=$(this).find("a"),a=$(this).find("p span").eq(0),r=$(this).find("p span").eq(1);i.attr("href","/pages/search.html?city="+e.city),a.text(e.city),r.text(e.time)})})}function renderNews(t){var e=t.find(".news-banner .news-item"),n=t.find(".news-content .news-item");myAjax(api.getNewsInfo,{num:6,TypeId:3},function(t){e.each(function(e){var n=t[e],i=$(this).find("a"),a=$(this).find("img"),r=$(this).find("strong"),c=n.img.textFilter();a.attr("src",c),r.text(n.title),i.attr("href","/pages/acitvity_details.html?newsid="+n.id)}),n.each(function(e){var n=t[e+2],i=$(this).find("a"),a=$(this).find("img"),r=$(this).find("strong"),c=$(this).find("b"),l=$(this).find("em"),o=n.imgx.textFilter();a.attr("src",o),c.text(n.title.lengthHandel(15)),r.text(n.date),l.text(n.txt.lengthHandel(35)),i.attr("href","/pages/acitvity_details.html?newsid="+n.id)})})}function renderInfoBlock(t){var e=t.find("li strong"),n=t.find("li b"),i=t.find("li a"),a={num:8,TypeId:1},r=0;myAjax(api.getNewsInfo,a,function(t){$.each(t,function(t,a){e.eq(r).text(a.title.lengthHandel(18)),n.eq(r).text(a.date),i.eq(r).attr("href","/pages/acitvity_details.html?newsid="+a.id),r++})})}function renderTenderBlock(t){var e=t.find("li a"),n={num:8,TypeId:4},i=0;myAjax(api.getNewsInfo,n,function(t){$.each(t,function(t,n){e.eq(i).text(n.title),e.eq(i).attr("href","/pages/acitvity_details.html?newsid="+n.id),i++})})}function renderActivityBlock(t){var e=t.find("li strong"),n=t.find("li img"),i=t.find("li a"),a={num:6,TypeId:2},r=0;myAjax(api.getNewsInfo,a,function(t){$.each(t,function(t,a){e.eq(r).text(a.title.lengthHandel(12)),n.eq(r).attr("src",a.imgx.textFilter()),i.eq(r).attr("href","/pages/acitvity_details.html?newsid="+a.id),r++})})}String.prototype.textFilter=function(){var t=this.split(",")[0],e=t.replace("[","").replace("]","").replace(/"/g,"");return host+e},String.prototype.lengthHandel=function(t){return this.length<t?this:this.slice(0,t)+"..."},$(function(){slide(),searchBlock($(".search-block")),contactBlock($(".banner-contact")),renderBusPriceBlock($(".source")),renderNews($(".news")),renderInfoBlock($(".info-content")),renderTenderBlock($(".tender ul")),renderActivityBlock($(".activity ul"))});