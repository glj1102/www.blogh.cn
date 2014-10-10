package com.blog.lucene;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.blog.models.Blog;
import com.blog.util.ConfigUtil;
import com.blog.vo.BlogListVo;

/**
 * @author 龚林杰
 * @since 2014/3/3
 * @version Lucene 4.7.0
 * */
public class LuceneSearch {
	// 声明一个IndexSearcher对象
	private IndexSearcher searcher = null;
	// 声明一个Query对象
	private Query query = null;

	public LuceneSearch() {
		try {
			String index_dir=ConfigUtil.readValue("index_dir");
			File file = new File(index_dir);
			if(!file.exists()){
				file.mkdirs();
			}
			IndexReader reader = DirectoryReader.open(FSDirectory.open(file));
			searcher = new IndexSearcher(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    //返回查询结果
	public BlogListVo search(int curpage, int pagesize, String keyword) {
		BlogListVo bloglistVo = new BlogListVo();
		List<Blog> list = new ArrayList<Blog>();
		try {
//			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
			Analyzer analyzer = new IKAnalyzer();
			//查询单个字段
//			QueryParser parser = new QueryParser(Version.LUCENE_47, field,analyzer);
			//查询多个字段
			MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_47, new String[]{"blog_title","blog_content","category_1_name","category_2_name"}, analyzer);
			// 将关键字包装成Query对象
			query = parser.parse(keyword);
			TopDocs results = searcher.search(query, null, 100);
			ScoreDoc[] h = results.scoreDocs;
			int pagecount=page_count(h.length,pagesize);
			if(h.length == 0){
				return bloglistVo;
			}else{
				if(h.length-(curpage-1)*pagesize>0 && h.length-(curpage-1)*pagesize-pagesize>=0){
					for(int i=(curpage-1)*pagesize; i<(curpage-1)*pagesize+pagesize; i++){
						Document doc = searcher.doc(h[i].doc);
						Blog b = new Blog();
						b.setId(Long.parseLong(doc.get("id")));
						b.setBlog_content(doc.get("blog_content"));
						b.setBlog_title(doc.get("blog_title"));
						long l=Long.parseLong(doc.get("create_time"));
						Date d = new Date(l);
						b.setCreate_time(d);
						b.setCategory_1_name(doc.get("category_1_name"));
						b.setCategory_2_name(doc.get("category_2_name"));
						list.add(b);
					}
				}else if(h.length-(curpage-1)*pagesize>0 && h.length-(curpage-1)*pagesize-pagesize<0){
					for(int i=(curpage-1)*pagesize; i<h.length; i++){
						Document doc = searcher.doc(h[i].doc);
						Blog b = new Blog();
						b.setId(Long.parseLong(doc.get("id")));
						b.setBlog_content(doc.get("blog_content"));
						b.setBlog_title(doc.get("blog_title"));
						long l=Long.parseLong(doc.get("create_time"));
						Date d = new Date(l);
						b.setCreate_time(d);
						b.setCategory_1_name(doc.get("category_1_name"));
						b.setCategory_2_name(doc.get("category_2_name"));
						list.add(b);
					}
				}
				bloglistVo.setList(list);
				bloglistVo.setPagecount(pagecount);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return bloglistVo;
		}
		return bloglistVo;
	}

	//分页
		public static int page_count(int recordCount, int perPage) {
			    int pc = (int)Math.ceil(recordCount / (double)perPage);
			    return (pc==0)?1:pc;
		}

}
