$(function(){
    $.ajax({
        type :"GET",
        url :'/category',
        sync:false,
        data: {date: + new Date()},
        beforeSend : function() {
        },
        success:function(msg){
            $("#category_menu_item").html(msg);
        }
    });

    $.get('/hot',function(data){
        var json_obj=eval('(' +data+ ')');
        $("#blog_hot_list").html();
        $.each(json_obj.list,function(i,b){
            $("#blog_hot_list").append($("<li><a href='javascript:void(0);' title='"+ b.blog_title+"'>"+ b.blog_title.substring(0,18) +"</a></li>").click(function(){
                location.href='/show?id='+ b.id;
            }));
        });
    });
});