package com.blog.api.command;

import com.blog.api.dto.BlogDTO;
import com.blog.api.dto.LuceneDTO;
import com.blog.api.lucene.MyIKAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pengli
 * @create 2021-09-09 10:25
 *
 * Lucene 操作指令
 */
@Service
@Transactional
public class LuceneExe {

    /**
     * 创建blog 索引库文档
     */
    public void addBlogDir(List<BlogDTO> blogs) throws IOException {
        List<Document> documents=new ArrayList<>();
        //创建索引库位置
        Directory directory= FSDirectory.open(FileSystems.getDefault().getPath("C:/dir/blogDir"));
        //IK 分词器
        Analyzer analyzer = new MyIKAnalyzer();
        //创建输出流 write
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);

        for(BlogDTO blogDTO:blogs){
            Document document=new Document();
            //记录标题和id即可
            Field id=new TextField("id",String.valueOf(blogDTO.getId()), Field.Store.YES);
            Field title=new TextField("title",blogDTO.getTitle(), Field.Store.YES);
            document.add(id);
            document.add(title);
            documents.add(document);
        }
        //一次处理
        indexWriter.addDocuments(documents);
        //关闭输出流
        indexWriter.close();
    }

    /**
     * 关键词搜索博客索引库
     * @param key
     * @param size
     * @param index
     * @return
     */
    public LuceneDTO getBlogDir(String key, Integer index, Integer size) throws IOException, ParseException, InvalidTokenOffsetsException {
        List<BlogDTO> result=new ArrayList<>();
        Analyzer analyzer=new MyIKAnalyzer();
        //关键词
        QueryParser qp = new QueryParser("title",analyzer);
        Query query=qp.parse(key);

        //高亮关键字
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));

        //打开索引库输入流
        Directory directory=FSDirectory.open(FileSystems.getDefault().getPath("C:/dir/blogDir"));
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher=new IndexSearcher(indexReader);
        //上一页的结果
        ScoreDoc lastScoreDoc = getLastScoreDoc(index, size, query, indexSearcher);

        //从上一页最后一条数据开始查询  达到分页的目的
        TopDocs topDocs = indexSearcher.searchAfter(lastScoreDoc, query, size);
        long totle=topDocs.totalHits;
        for(ScoreDoc scoreDoc:topDocs.scoreDocs){
            //获得对应的文档
            Document doc = indexSearcher.doc(scoreDoc.doc);
            String title=doc.get("title");
            TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
            result.add(BlogDTO.builder().id(Integer.valueOf(doc.get("id"))).title(highlighter.getBestFragment(tokenStream,title)).build());
        }
        indexReader.close();
        LuceneDTO luceneDTO=new LuceneDTO();
        luceneDTO.setListData(result);
        luceneDTO.setTotole(totle);
        return luceneDTO;
    }

    private ScoreDoc getLastScoreDoc(int pageIndex, int pageSize, Query query, IndexSearcher indexSearcher) throws IOException{
        if(pageIndex==1)return null;
        //获取上一页的数量
        int num = pageSize*(pageIndex-1);
        TopDocs tds = indexSearcher.search(query, num);
        return tds.scoreDocs[num-1];
    }

    /**
     * 指定更新博客的索引文档
     * @throws IOException
     */
    public void updateBlogDocument(BlogDTO blogDTO) throws IOException {
        Directory directory=FSDirectory.open(FileSystems.getDefault().getPath("C:/dir/blogDir"));
        Analyzer analyzer=new MyIKAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);
        Document document=new Document();//替换的文档
        Field id=new TextField("id",String.valueOf(blogDTO.getId()), Field.Store.YES);
        Field title=new TextField("title",blogDTO.getTitle(), Field.Store.YES);
        document.add(title);
        document.add(id);
        Term term=new Term("title",blogDTO.getTitle());
        indexWriter.updateDocument(term,document);
        indexWriter.commit();
        indexWriter.close();
    }
}
