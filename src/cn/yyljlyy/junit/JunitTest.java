package cn.yyljlyy.junit;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Test;

import sun.print.resources.serviceui;

import cn.yyljlyy.bean.Article;
import cn.yyljlyy.lucene.luceneDao;
	
public class JunitTest {
	private luceneDao luceneDao  = new luceneDao();
	
	@Test
	public void testCreateIndex() throws IOException{
		Article article = null;
		for (int i = 0; i < 25; i++) {
			article = new Article();
			article.setId(i);
			article.setAuthor("�Ѻ�" + i);
			article.setTitle("����ϰ���");
			article.setLink("www.baidu.com");
			article.setContent("�����������ⷢ����");
			luceneDao.createIndex(article);
		}
	}
	
	@Test
	public void testSearcher() throws IOException, ParseException{
		//���ִַ�
		luceneDao.queryIndex("ϰ",20,30);
	}
	@Test
	public void testdelete() throws IOException, ParseException{
		String fieldName = "title";
		String fieldValue = "ϰ";
		//���ִַ�
		luceneDao.deleteIndex(fieldName, fieldValue);
	}
	@Test
	public void testUpdate() throws IOException{
		String fieldName = null;
		String fieldValue = "ϰ";
		
		Article article = new Article();
		article.setId(111);
		article.setTitle("�й�������");
		article.setContent("�й���ǿ��");
		article.setLink("www.mangguotv.com");
		luceneDao.updateIndex(fieldName, fieldValue, article);
		
		
	}
}
