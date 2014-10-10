$(function(){
    var one = $("#category_one_id_hidden").val();
    var two = $("#category_two_id_hidden").val();
    var keyword = $("#keyword_hidden").val();
    indexLoad(1,one,two,keyword);

});

function indexLoad(curpage,one,two,keyword){
    if(keyword=="" || keyword==null || keyword==undefined){
        $.get('/indexload?curpage='+curpage+"&categoryoneid="+one+"&categorytwoid="+two,function(data){
            $("#blog_list").html("");
            var json_obj=eval('(' +data+ ')');
            $.each(json_obj.list,function(i,b){
                var date = new Date(b.create_time);
                var year = date.getFullYear(); //读英文就行了
                var month = (date.getMonth()+1);//取月的时候取的是当前月-1如果想取当前月+1就可以了
                var day = date.getDate();
                var h = date.getHours();
                var m = date.getMinutes();
                var s = date.getSeconds();
                $("#blog_list").append("<div class='entry list wh_648_320'>" +
                    "<div class='entry_text'>" +
                    "<div class='entry_meta'>" +
                    "<div class='entry_date'>" +
                    "<span class='date_day'>"+day+"日</span>" +
                    "<span class='date_month'>"+year+"-"+month+"</span>" +
                    "</div></div>" +
                    "<div class='entry_content'>"
                );
                var showid = $("<h3><a href='javascript:void(0);'>"+ b.blog_title+"</a></h3>").click(function(){
                    location.href="/show?id="+ b.id;
                    $.get('/viewcount?id='+ b.id,function(data){});
                });
                $("#blog_list").append(showid);
                var $content=$("<p id='blog_content_show'></p>").html(b.blog_content).html().substring(0,1500);
                $("#blog_list").append($("<p id='blog_content_show'></p>").html($content))
                    .append("</div></div></div><div class='h_dotted'></div>")
            });
            var $pager = $("#pagination");

            $pager.html("");
            if(curpage==1){
                $pager.append("<a href='#'>首页</a>");
            }else{
                var first = $("<a href='#'>首页</a>").click(function () {
                    indexLoad(1,one,two,keyword);
                    return false;
                });
                $pager.append(first);
            }
            if(curpage>1){
                var p1=$("<a href='#'>上一页</a>").click(function () {
                    indexLoad(curpage-1,one,two,keyword);
                    return false;
                });
                $pager.append(p1);
            }else{
                $pager.append("<a href='#'>上一页</a>");
            }
            if(curpage-3>=1){
                var p2=$("<a href='#'>"+(curpage-3)+"</a>").click(function () {
                    indexLoad(curpage-3,one,two,keyword);
                    return false;
                });
                $pager.append(p2);
            }
            if(curpage-2>=1){
                var p3=$("<a href='#'>"+(curpage-2)+"</a>").click(function () {
                    indexLoad(curpage-2,one,two,keyword);
                    return false;
                });
                $pager.append(p3);
            }
            if(curpage-1>=1){
                var p4=$("<a href='#'>"+(curpage-1)+"</a>").click(function () {
                    indexLoad(curpage-1,one,two,keyword);
                    return false;
                });
                $pager.append(p4);
            }

            $pager.append("<span class='current'>"+curpage+"</span>");

            if(curpage+1<=json_obj.pagecount){
                var p6=$("<a href='#'>"+(curpage+1)+"</a>").click(function () {
                    indexLoad(curpage+1,one,two,keyword);
                    return false;
                });
                $pager.append(p6);
            }

            if(curpage+2<=json_obj.pagecount){
                var p7=$("<a href='#'>"+(curpage+2)+"</a>").click(function () {
                    indexLoad(curpage+2,one,two,keyword);
                    return false;
                });
                $pager.append(p7);
            }
            if(curpage+3<=json_obj.pagecount){
                var p8=$("<a href='#'>"+(json_obj.curpage+3)+"</a>").click(function () {
                    indexLoad(curpage+3,one,two,keyword);
                    return false;
                });
                $pager.append(p8);
            }
            if(curpage<json_obj.pagecount){
                var p9=$("<a href='#'>下一页</a>").click(function () {
                    indexLoad(curpage+1,one,two,keyword);
                    return false;
                });
                $pager.append(p9);
            }else{
                $pager.append("<a href='#'>下一页</a>");
            }
            if(curpage==json_obj.pagecount){
                $pager.append("<a href='#'>末页</a>");
            }else{
                var last = $("<a href='#'>末页</a>").click(function () {
                    indexLoad(json_obj.pagecount,one,two,keyword);
                    return false;
                });
                $pager.append(last);
            }
        });
    }else{
        $.get('/search?curpage='+curpage+"&keyword="+keyword,function(data){
            $("#blog_list").html("");
            var json_obj=eval('(' +data+ ')');
            $.each(json_obj.list,function(i,b){
                var date = new Date(b.create_time);
                var year = date.getFullYear(); //读英文就行了
                var month = (date.getMonth()+1);//取月的时候取的是当前月-1如果想取当前月+1就可以了
                var day = date.getDate();
                var h = date.getHours();
                var m = date.getMinutes();
                var s = date.getSeconds();
                $("#blog_list").append("<div class='entry list wh_648_320'>" +
                    "<div class='entry_text'>" +
                    "<div class='entry_meta'>" +
                    "<div class='entry_date'>" +
                    "<span class='date_day'>"+day+"日</span>" +
                    "<span class='date_month'>"+year+"-"+month+"</span>" +
                    "</div></div>" +
                    "<div class='entry_content'>"
                );
                var showid = $("<h3><a href='javascript:void(0);'>"+ b.blog_title+"</a></h3>").click(function(){
                    location.href="/show?id="+ b.id;
                    $.get('/viewcount?id='+ b.id,function(data){});
                });
                $("#blog_list").append(showid);
                var $content=$("<p id='blog_content_show'></p>").html(b.blog_content).html().substring(0,1500);
                $("#blog_list").append($("<p id='blog_content_show'></p>").html($content))
                    .append("</div></div></div><div class='h_dotted'></div>")
            });
            var $pager = $("#pagination");

            $pager.html("");
            if(curpage==1){
                $pager.append("<a href='#'>首页</a>");
            }else{
                var first = $("<a href='#'>首页</a>").click(function () {
                    indexLoad(1,one,two,keyword);
                    return false;
                });
                $pager.append(first);
            }
            if(curpage>1){
                var p1=$("<a href='#'>上一页</a>").click(function () {
                    indexLoad(curpage-1,one,two,keyword);
                    return false;
                });
                $pager.append(p1);
            }else{
                $pager.append("<a href='#'>上一页</a>");
            }
            if(curpage-3>=1){
                var p2=$("<a href='#'>"+(curpage-3)+"</a>").click(function () {
                    indexLoad(curpage-3,one,two,keyword);
                    return false;
                });
                $pager.append(p2);
            }
            if(curpage-2>=1){
                var p3=$("<a href='#'>"+(curpage-2)+"</a>").click(function () {
                    indexLoad(curpage-2,one,two,keyword);
                    return false;
                });
                $pager.append(p3);
            }
            if(curpage-1>=1){
                var p4=$("<a href='#'>"+(curpage-1)+"</a>").click(function () {
                    indexLoad(curpage-1,one,two,keyword);
                    return false;
                });
                $pager.append(p4);
            }

            $pager.append("<span class='current'>"+curpage+"</span>");

            if(curpage+1<=json_obj.pagecount){
                var p6=$("<a href='#'>"+(curpage+1)+"</a>").click(function () {
                    indexLoad(curpage+1,one,two,keyword);
                    return false;
                });
                $pager.append(p6);
            }

            if(curpage+2<=json_obj.pagecount){
                var p7=$("<a href='#'>"+(curpage+2)+"</a>").click(function () {
                    indexLoad(curpage+2,one,two,keyword);
                    return false;
                });
                $pager.append(p7);
            }
            if(curpage+3<=json_obj.pagecount){
                var p8=$("<a href='#'>"+(json_obj.curpage+3)+"</a>").click(function () {
                    indexLoad(curpage+3,one,two,keyword);
                    return false;
                });
                $pager.append(p8);
            }
            if(curpage<json_obj.pagecount){
                var p9=$("<a href='#'>下一页</a>").click(function () {
                    indexLoad(curpage+1,one,two,keyword);
                    return false;
                });
                $pager.append(p9);
            }else{
                $pager.append("<a href='#'>下一页</a>");
            }
            if(curpage==json_obj.pagecount){
                $pager.append("<a href='#'>末页</a>");
            }else{
                var last = $("<a href='#'>末页</a>").click(function () {
                    indexLoad(json_obj.pagecount,one,two,keyword);
                    return false;
                });
                $pager.append(last);
            }
        });
    }
}