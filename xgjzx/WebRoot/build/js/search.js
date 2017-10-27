function citySelectSearch(e){function t(e){s.fadeToggle(d),e.stopPropagation()}function a(e){o.fadeToggle(d),e.stopPropagation()}function r(e){var t=$(this).text(),a=$(this).index();i.text(t);var r="";l[a].cities.forEach(function(e,t){r+="<li>"+e+"</li>"}),o.html(r),s.fadeOut(d),o.fadeIn(d),e.stopPropagation()}function n(){var e=$(this).text();""!=e&&(c.text(e),searchByWord(1,e,"","",""))}var i=e.find(".search-province strong"),c=e.find(".search-city strong"),s=e.find("#province"),o=e.find("#city"),l=[],d="fast";e.on("click",".search-province strong",t).on("click",".search-province b",t).on("click",".search-city strong",a).on("click",".search-city b",a).on("click","#province li",r).on("click","#city li",n),c.html("<i>"+paramInUrl.city+"市</i>"),$("body").click(function(){s.fadeOut(d),o.fadeOut(d)}),$.getJSON("../data/cityData.json").success(function(e){var t="";e.forEach(function(e,a){t+="<li>"+e.name+"</li>"}),s.append(t),l=e})}function searchByWord(e,t,a,r,n){window.location.href=window.location.pathname+"?city="+t+"&level="+a+"&page="+e+"&type="+r+"&time="+n}function urlSearchToObj(){var e=window.location.search,t={city:"",level:"",type:"",time:"",page:1};if(e.indexOf("?")>=0){e.split("?")[1].split("&").forEach(function(e,a){var r=e.split("=")[0],n=e.split("=")[1];for(var i in t)r==i&&(t[i]="city"==i||"type"==i||"time"==i?decodeURI(n):n)})}return t}function hotActive(e){for(var t in paramInUrl){var a=".hot-"+t,r=e.find(a).children("dd"),n=!0;r.each(function(){$(this).text();$(this).text()==paramInUrl[t]&&($(this).addClass("active"),n=!1)}),n&&r.eq(0).addClass("active")}}function hotSearch(e){function t(){searchByWord(1,$(this).text(),"","","")}function a(){var e=$(this).text();searchByWord(1,paramInUrl.city,e,paramInUrl.type,paramInUrl.time)}function r(){var e=$(this).text();searchByWord(1,paramInUrl.city,paramInUrl.level,e,paramInUrl.time)}function n(){var e=$(this).text();searchByWord(1,paramInUrl.city,paramInUrl.level,paramInUrl.type,e)}e.on("click",".hot-city dd",t).on("click",".hot-level dd",a).on("click",".hot-type dd",r).on("click",".hot-time dd",n)}function searchBar(e){function t(){searchByWord(1,r.val(),"","","")}function a(e){13==e.keyCode&&t()}var r=e.find("input");e.on("click","button",t).on("keydown","input",a)}function getDataList(e){var t=[],a=!0;for(var r in paramInUrl)"不限"==paramInUrl[r]&&(paramInUrl.city=""),""!=paramInUrl[r]&&(a=!1);if(a){var n={page:paramInUrl.page};myAjax(api.getBusPrice,n,function(a){t=a.data,maxPage=a.maxPage,e&&e(t)})}else{var i={page:paramInUrl.page,data:{id:"",province:"",city:paramInUrl.city,route:"",level:encodeLevel(paramInUrl.level),type:encodeType(paramInUrl.type),time:encodeTime(paramInUrl.time),price:""}};myAjax(api.searchBusPrice,JSON.stringify(i),function(a){t=a.results,maxPage=a.maxPage,e&&e(t)})}}function renderList(e,t){var a=e.find("tbody"),r="";t.forEach(function(e,t){var a=e.route.split("，"),n="",i=e.city,c=e.level;a.forEach(function(e,t){n+='<a href="/pages/search_detail.html?city='+i+"&level="+decodeLevel(c)+"&route="+e+'">'+e+",&nbsp;&nbsp;</a>"}),r+='<tr><td class="result-city">'+i+'</td><td class="result-level">'+decodeLevel(e.level)+'</td><td class="result-type">'+decodeType(e.type)+'</td><td class="result-time">'+e.time+'</td><td class="result-route"><div class="">'+n+'<i class="no-display"><img src="../img/search/down.png" width="15px" height="15px"/></i></div></td><td class="result-price">￥<span>'+e.price+"</span>.00起</td></tr>"}),a.append(r),a.find("div").each(function(){var e=this.offsetHeight;if(e>86){this.setAttribute("class","width-max-height"),$(this).css("height","86px");var t=$(this).find("i")[0];t.setAttribute("class",""),t.addEventListener("click",function(){$(this).parent().toggleClass("width-max-height"),""==$(this).parent().attr("class")?($(this).children("img").attr("src","../img/search/up.png"),$(this).parent().animate({height:e+14},500)):($(this).children("img").attr("src","../img/search/down.png"),$(this).parent().animate({height:86},500))})}}),generatePager(maxPage,paramInUrl.page),kkpager._clickHandler=function(e){searchByWord(e,paramInUrl.city,paramInUrl.level,paramInUrl.type,paramInUrl.time)}}var paramInUrl,maxPage=1,encodeLevel=function(e){switch(e){case"S":return 1;case"A++":return 2;case"A+":return 3;case"A":return 4;default:return""}},decodeLevel=function(e){switch(e){case 1:return"S";case 2:return"A++";case 3:return"A+";case 4:return"A";default:return""}},decodeType=function(e){switch(e){case 1:return"全车";case 2:return"半车";case 3:return"海报";default:return""}},encodeType=function(e){switch(e){case"全车":return"1";case"半车":return"2";case"海报":return"3";default:return""}},encodeTime=function(e){switch(e){case"3个月":return"3";case"6个月":return"6";case"12个月":return"12";default:return""}};$(function(){paramInUrl=urlSearchToObj(),getDataList(function(e){renderList($(".result"),e)}),citySelectSearch($(".search")),hotActive($(".hot")),hotSearch($(".hot")),searchBar($(".search-detail"))});