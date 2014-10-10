$(function(){
    var content=$("#blog_content_hidden").val();
    $("#blog_content").html(content);
    var blog_id=$("#blog_id_hidden").val();
    commentPage(blog_id,1);

    $("#comment_save_button").click(function(){
        var user=$("#comment_user").val();
        var content=$("#comment_content").val();
        if(!user){
            $("#comment_user_message").html("请填写您的昵称!").css({position:"absolute",color:"red",margin:"7px 12px",fontSize:"12px"});
            return false;
        }else if(!content){
            $("#comment_user_message").html("请填写评论内容!").css({position:"absolute",color:"red",margin:"7px 12px",fontSize:"12px"});
            return false;
        }else{
            $("#comment_save_form").submit();
        }
    });
});

function commentPage(blog_id,curpage){
    $.get('/comment?blog_id='+blog_id+'&curpage='+curpage,function(data){
        $("#comments").html("");
        var json_obj=eval('(' +data+ ')');
        $.each(json_obj.list,function(i,c){
            var date = new Date(c.create_time);
            var now = "";
            now = date.getFullYear()+"-"; //读英文就行了
            now = now + (date.getMonth()+1)+"-";//取月的时候取的是当前月-1如果想取当前月+1就可以了
            now = now + date.getDate()+" ";
            now = now + date.getHours()+":";
            now = now + date.getMinutes()+":";
            now = now + date.getSeconds()+"";
            $("#comments").append("<div class='comment'>" +
                "<div class='user_avatar'><img src='images/no_avatar_big.png' /></div>" +
                "<div class='user_text'>" +
                "<span class='user_name'>" + c.user + "</span>" +
                "<div class='entry_meta'>" + now + "</div>" +
                "<p>" + c.content +"</p>" +
                "</div></div>"
            );
        });
        var $pager = $("#page");

        $pager.html("");
        if(json_obj.curpage==1){
            $pager.append("<a href='#comments'>首页</a>");
        }else{
            var first = $("<a href='#comments'>首页</a>").click(function () {
                commentPage(blog_id,1);
                return false;
            });
            $pager.append(first);
        }
        if(json_obj.curpage>1){
            var p1=$("<a href='#comments'>上一页</a>").click(function () {
                commentPage(blog_id,json_obj.curpage-1);
                return false;
            });
            $pager.append(p1);
        }else{
            $pager.append("<a href='#comments'>上一页</a>");
        }
        if(json_obj.curpage-3>=1){
            var p2=$("<a href='#comments'>"+(json_obj.curpage-3)+"</a>").click(function () {
                commentPage(blog_id,json_obj.curpage-3);
                return false;
            });
            $pager.append(p2);
        }
        if(json_obj.curpage-2>=1){
            var p3=$("<a href='#comments'>"+(json_obj.curpage-2)+"</a>").click(function () {
                commentPage(blog_id,json_obj.curpage-2);
                return false;
            });
            $pager.append(p3);
        }
        if(json_obj.curpage-1>=1){
            var p4=$("<a href='#comments'>"+(json_obj.curpage-1)+"</a>").click(function () {
                commentPage(blog_id,json_obj.curpage-1);
                return false;
            });
            $pager.append(p4);
        }

        $pager.append("<span class='current'>"+json_obj.curpage+"</span>");

        if(json_obj.curpage+1<=json_obj.pagecount){
            var p6=$("<a href='#comments'>"+(json_obj.curpage+1)+"</a>").click(function () {
                commentPage(blog_id,json_obj.curpage+1);
                return false;
            });
            $pager.append(p6);
        }

        if(json_obj.curpage+2<=json_obj.pagecount){
            var p7=$("<a href='#comments'>"+(json_obj.curpage+2)+"</a>").click(function () {
                commentPage(blog_id,json_obj.curpage+2);
                return false;
            });
            $pager.append(p7);
        }
        if(json_obj.curpage+3<=json_obj.pagecount){
            var p8=$("<a href='#comments'>"+(json_obj.curpage+3)+"</a>").click(function () {
                commentPage(blog_id,json_obj.curpage+3);
                return false;
            });
            $pager.append(p8);
        }
        if(json_obj.curpage<json_obj.pagecount){
            var p9=$("<a href='#comments'>下一页</a>").click(function () {
                commentPage(blog_id,json_obj.curpage+1);
                return false;
            });
            $pager.append(p9);
        }else{
            $pager.append("<a href='#comments'>下一页</a>");
        }
        if(json_obj.curpage==json_obj.pagecount){
            $pager.append("<a href='#comments'>末页</a>");
        }else{
            var last = $("<a href='#comments'>末页</a>").click(function () {
                commentPage(blog_id,json_obj.pagecount);
                return false;
            });
            $pager.append(last);
        }
    });
}