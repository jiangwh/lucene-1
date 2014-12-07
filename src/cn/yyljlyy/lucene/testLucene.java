package cn.yyljlyy.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

public class testLucene {
	/**
	 * ʹ��lucene �������� Indexwirte ��������...
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCreateIndex() throws IOException {
		// ������ŵ�Ŀ¼....
		Directory directory = FSDirectory.open(new File("indexDir/"));
		// ��ǰƥ��汾
		Version version = Version.LUCENE_44;
		/**
		 * �ִ��� Analyzer��һ�������࣬������зִʵĹ���������ʵ��
		 */
		Analyzer analyzer = new StandardAnalyzer(version);
		// ����д����ʱ��һЩ����Ե���...
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(version,
				analyzer);
		// ����IndexWrite����ʹ�ô˶�������������....
		IndexWriter iWriter = new IndexWriter(directory, indexWriterConfig);
		// ��lucenc ���������д�ŵĶ�����docement���ⲿ����Ҫת����lucene����
		Document document = new Document();
		// �ֶ�
		IndexableField field = new IntField("id", 1, Store.YES);
		IndexableField stringfField = new StringField("title",
				"Apache Lucene ��һ�����ʵļ�������", Store.YES);
		IndexableField textField = new TextField("content", "Apache Lucene",
				Store.YES);
		document.add(field);
		document.add(textField);
		document.add(stringfField);
		iWriter.addDocument(document);
		iWriter.close();
	}

	/**
	 * ʹ��indexsearcher���document��������
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSearcher() throws IOException {
		Directory directory = FSDirectory.open(new File("indexDir/"));
		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		// ��ѯ��������
		Query query = new TermQuery(new Term("title",
				"Apache Lucene ��һ�����ʵļ�������"));
		// ��������query������ǰn����¼
		TopDocs topDocs = indexSearcher.search(query, 100);
		System.out.println("�����ܼ�¼��" + topDocs.totalHits);
		ScoreDoc scoreDocs[] = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			int docID = scoreDoc.doc;
			// ����id����document
			Document document = indexSearcher.doc(docID);
			System.out.println(document.get("title"));
			System.out.println(document.get("content"));
		}
	}
}
