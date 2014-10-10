var commonService = require("../service/commonService.js");
var Url = require('../util/url.js');
var http_url = require('../util/http_url.js');
var DateUtil = require('../util/DateUtil.js');
//首页
exports.index = function(req, res){
    var categoryoneid = req.query["categoryoneid"]==undefined ? "": req.query["categoryoneid"];
    var categorytwoid = req.query["categorytwoid"]==undefined ? "": req.query["categorytwoid"];
    var keyword = req.query["keyword"]==undefined ? "": req.query["keyword"];
    res.render('index',{
        categoryoneid:categoryoneid,
        categorytwoid:categorytwoid,
        keyword:keyword
    })
};

exports.indexload = function(req,res){
    var categoryoneid = req.query['categoryoneid']==undefined ? "" : req.query['categoryoneid'];
    var categorytwoid = req.query['categorytwoid']==undefined ? "" : req.query['categorytwoid'];
    var curpage = req.query['curpage']==undefined ? 1 : Number(req.query['curpage']);

    var options = http_url.getUrl(Url.service_host,Url.service_port,"/blog-server/blog/list.json","GET");
    options.headers.categoryoneid = categoryoneid;
    options.headers.categorytwoid = categorytwoid;
    options.headers.curpage = curpage;
    options.headers.pagesize = Url.pagesize;
    commonService.request(options,function(data){
        res.writeHead(200,{"Content-Type":"text/plain"});
        res.end(data);
    })
}
//全文检索
exports.search = function(req,res){
    var keyword = req.query["keyword"]==undefined ? "" : req.query["keyword"];
    var curpage = req.query['curpage']==undefined ? 1 : Number(req.query['curpage']);
    var word=encodeURIComponent(keyword,"utf-8");
    var options = http_url.getUrl(Url.service_host,Url.service_port,"/blog-server/blog/search.json","GET");
    options.headers.curpage = curpage;
    options.headers.pagesize = Url.pagesize;
    options.headers.keyword = word;
    commonService.request(options,function(data){
        res.writeHead(200,{"Content-Type":"text/plain"});
        res.end(data);
    })
}
//博客分类
exports.category = function(req,res){
    var options = http_url.getUrl(Url.service_host,Url.service_port,"/blog-server/category/tree.json","GET");
    commonService.request(options,function(data){
//        res.writeHead(200,{"Content-Type":"text/plain"});
//        res.end(data);
        res.render('category',{
            data:JSON.parse(data)
        })
    })
}
/**
 * 获取一级分类
 * @param req
 * @param res
 */
exports.getone = function(req,res){
    var options = http_url.getUrl(Url.service_host,Url.service_port,"/blog-server/category/one.json","GET");
    commonService.request(options,function(data){
        res.writeHead(200,{"Content-Type":"text/plain"});
        res.end(data);
    })
}

exports.gettwo = function(req,res){
    var options = http_url.getUrl(Url.service_host,Url.service_port,"/blog-server/category/two.json","GET");
    options.headers.category_1=req.query['oneid'];
    commonService.request(options,function(data){
        res.writeHead(200,{"Content-Type":"text/plain"});
        res.end(data);
    })
}

exports.check = function(req,res){
    var options = http_url.getUrl(Url.service_host,Url.service_port,"/blog-server/mail/check.json","GET");
    options.headers.email=req.query['email'];
    commonService.request(options,function(data){
        res.writeHead(200,{"Content-Type":"text/plain"});
        res.end(data);
    })
}

exports.send = function(req,res){
    var options = http_url.getUrl(Url.service_host,Url.service_port,"/blog-server/mail/send.json","GET");
    options.headers.email=req.query['email'];
    commonService.request(options,function(data){
        res.writeHead(200,{"Content-Type":"text/plain"});
        res.end(data);
    });
}

exports.register = function(req,res){
    var options = http_url.getUrl(Url.service_host,Url.service_port,"/blog-server/mail/create.json","POST");
    var email=req.query['message'];
    var json={"email":email};
    commonService.request(options,function(data){
        res.render('register',{});
    },json);
}
//进入发表页
exports.writeView = function(req,res){
    res.render('blog_add',{});
}

exports.write = function(req,res){
    var options = http_url.getUrl(Url.service_host,Url.service_port,"/blog-server/blog/create.json","POST");
    var title=req.body.title;
    var user=req.body.user;
    var email=req.body.email;
    var category1=req.body.category1;
    var a1=category1.split("_");
    var category_1=a1[0];
    var category_1_name=a1[1];
    var category2=req.body.category2;
    var a2=category2.split("_");
    var category_2=a2[0];
    var category_2_name=a2[1];
    var content=req.body.content;
    var obj={"user":user,"email":email,"blog_title":title,"blog_content":content,"category_1":category_1,"category_1_name":category_1_name,"category_2":category_2,"category_2_name":category_2_name}
    commonService.request(options,function(data){
        var id=JSON.parse(data).id;
        res.redirect('/show?id='+id);
    },obj);
}
//
//exports.blog = function(req,res){
//    var id=req.query["id"];
//    res.render('blog_in',{
//        id:id
//    });
//}

exports.show = function(req,res){
    var options = http_url.getUrl(Url.service_host,Url.service_port,"/blog-server/blog/getblog.json","GET");
    var id=req.query["id"];
    options.headers.id=id;
    commonService.request(options,function(data){
        res.render('blog_in',{
            blog:JSON.parse(data)
        });
    });
}

exports.comment = function(req,res){
    var blog_id=req.query["blog_id"];
    var curpage=req.query['curpage']==undefined ? 1 : Number(req.query['curpage']);
    var pagesize=Url.pagesize;
    var options = http_url.getUrl(Url.service_host,Url.service_port,"/blog-server/comment/list.json","GET");
    options.headers.blog_id=blog_id;
    options.headers.curpage=curpage;
    options.headers.pagesize=pagesize;
    commonService.request(options,function(data){
        res.writeHead(200,{"Content-Type":"text/plain"});
        res.end(data);
    });
}

exports.create_comment = function(req,res){
    var options = http_url.getUrl(Url.service_host,Url.service_port,"/blog-server/comment/create.json","POST");
    var user = req.body.user;
    var blog_id = req.body.blog_id;
    var content = req.body.content;
    var obj={"user":user,"blog_id":blog_id,"content":content}
    commonService.request(options,function(data){
        res.redirect('/show?id='+blog_id);
    },obj);

}


exports.viewcount = function(req,res){
    var options = http_url.getUrl(Url.service_host,Url.service_port,"/blog-server/blog/view/count.json","GET");
    options.headers.id=req.query['id'];
    commonService.request(options,function(data){
        res.writeHead(200,{"Content-Type":"text/plain"});
        res.end(data);
    });
}

exports.hot = function(req,res){
    var options = http_url.getUrl(Url.service_host,Url.service_port,"/blog-server/blog/hot.json","GET");
    commonService.request(options,function(data){
        res.writeHead(200,{"Content-Type":"text/plain"});
        res.end(data);
    });
}