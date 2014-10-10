package com.blog.lucene;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.blog.models.Blog;
import com.blog.util.ConfigUtil;
/**
 * @author 龚林杰
 * @since 2014/3/3
 * @version Lucene 4.7.0
 * */
public class LuceneIndex {
	// 索引器
	private IndexWriter writer = null;
	public LuceneIndex() {
		try {
			String index_dir=ConfigUtil.readValue("index_dir");
			File file = new File(index_dir);
			if(!file.exists()){
				file.mkdirs();
			}
			//索引文件的保存位置
			Directory dir = FSDirectory.open(file);
			//分析器
//			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
			IKAnalyzer analyzer = new IKAnalyzer();
			//配置类
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47,analyzer);
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);//创建模式 OpenMode.CREATE_OR_APPEND 添加模式

			writer = new IndexWriter(dir, iwc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void writeToIndex(Blog b) throws Exception {
		Document doc = new Document();
		Field id = new Field("id",String.valueOf(b.getId()),Field.Store.YES,Field.Index.ANALYZED);
		doc.add(id);
		Field blog_title = new Field("blog_title",b.getBlog_title(),Field.Store.YES,Field.Index.ANALYZED);
		doc.add(blog_title);
		Field blog_content = new Field("blog_content",b.getBlog_content(),Field.Store.YES,Field.Index.ANALYZED);
		doc.add(blog_content);
		Date d=b.getCreate_time();
		long l=d.getTime();
		Field create_time = new Field("create_time",String.valueOf(l),Field.Store.YES,Field.Index.ANALYZED);
		doc.add(create_time);
		Field category_1_name = new Field("category_1_name",b.getCategory_1_name(),Field.Store.YES,Field.Index.ANALYZED);
		doc.add(category_1_name);
		Field category_2_name = new Field("category_2_name",b.getCategory_2_name(),Field.Store.YES,Field.Index.ANALYZED);
		doc.add(category_2_name);
		writer.addDocument(doc);
	}

	public void delete(long id) throws IOException{
		writer.deleteDocuments(new Term("id",id+""));//这里删除id=1的文档，还会留在”回收站“。x.del
		writer.commit();//清空回收站
	}

	public void close() throws Exception {
		writer.close();
	}


}