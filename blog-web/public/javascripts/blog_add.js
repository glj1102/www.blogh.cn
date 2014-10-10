$(function(){
    $("#Authorize_button").hide();

    $.get('/catone',function(data){
        $("#category1_select").html("");
        var json_obj=eval('(' +data+ ')');
        $.each(json_obj.list,function(i,c){
            $("#category1_select").append("<option value="+ c.id+"_"+ c.name+">"+ c.name+"</option>");
        });

        var one=$("#category1_select").val();

        var array=one.split("_");
        var oneid=array[0];
        $.get('/catwo?oneid='+oneid,function(data){
            $("#category2_select").html("");
            var json=eval('(' +data+ ')');
            $.each(json.list,function(i,c2){
                $("#category2_select").append("<option value="+ c2.id+"_"+ c2.name+">"+ c2.name+"</option>");
            });
        });
    });

    $("#category1_select").change(function(){
        $("#category2_select").html("");
        var one=$("#category1_select").val();
        var array=one.split("_");
        var oneid=array[0];
        $.get('/catwo?oneid='+oneid,function(data){
            var json=eval('(' +data+ ')');
                $.each(json.list,function(i,c2){
                $("#category2_select").append("<option value="+ c2.id+"_"+ c2.name+">"+ c2.name+"</option>");
            });
        });
    });

    $("#blog_save_button").click(function(){
        var email_reg = /^[0-9a-z][a-z0-9._-]{1,}@[a-z0-9-]{1,}[a-z0-9].[a-z.]{1,}[a-z]$/;
        var blog_title=$("#blog_title").val();
        var blog_user=$("#blog_user").val();
        var blog_email=$("#blog_email").val();
        if(blog_title==null || blog_title==""){
            $("#blog_title_message").html("请填写标题!").css({position:"absolute",color:"red",margin:"7px 12px",fontSize:"12px"});
            return false;
        }else if(blog_user==""){
            $("#blog_user_message").html("请填写您的网名!").css({position:"absolute",color:"red",margin:"7px 12px",fontSize:"12px"});
            return false;
        }else if(!email_reg.test(blog_email) || blog_email==""){
            $("#blog_email_message").html("请填写正确的邮箱地址!").css({position:"absolute",color:"red",margin:"7px 12px",fontSize:"12px"});
            return false;
        }else{
            $.get('/check?email='+blog_email,function(data){
                var json_obj=eval('(' +data+ ')');
                var status=json_obj.result;
                if(status=="not"){
                    $("#blog_email_message").html("您的邮箱还没被授权，请点击授权按钮").css({position:"absolute",color:"red",margin:"7px 50px",fontSize:"12px"});
                    $("#Authorize_button").show();
                    return false;
                }else if(status=="cancel"){
                    $("#blog_email_message").html("您的邮箱已被管理员封杀，如需激活请联系管理员").css({position:"absolute",color:"red",margin:"7px 12px",fontSize:"12px"});
                    return false;
                }else
                    $("#blog_save_form").submit();
            });
        }
    });

    $("#Authorize_button").click(function(){
        var email=$("#blog_email").val();
        $.get('/send?email='+email,function(data){
        })
        $("#Authorize_button").hide();
        $("#blog_email_message").html("系统已经为您发送邮件，请查收并授权").css({position:"absolute",color:"red",margin:"7px 12px",fontSize:"12px"});
    });
});