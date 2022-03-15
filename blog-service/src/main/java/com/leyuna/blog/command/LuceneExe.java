package com.leyuna.blog.command;

import com.leyuna.blog.bean.blog.BlogBean;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.co.blog.BlogCO;
import com.leyuna.blog.co.blog.LuceneCO;
import com.leyuna.blog.domain.BlogE;
import com.leyuna.blog.constant.enums.SystemErrorEnum;
import com.leyuna.blog.lucene.SpiltCharAnalyzer;
import com.leyuna.blog.util.AssertUtil;
import com.leyuna.blog.util.TransformationUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pengli
 * @create 2021-09-09 10:25
 * <p>
 * Lucene 操作指令
 */
@Service
@Transactional
public class LuceneExe {

    /**
     * 创建blog 索引库文档
     */
    public void addBlogDir (List<BlogBean> blogs) {
        if(CollectionUtils.isEmpty(blogs)){
            //默认创建所有博客索引文档
            List<BlogCO> blogCOS = BlogE.queryInstance().selectByCon();
            blogs= TransformationUtil.copyToLists(blogCOS,BlogBean.class);
        }
        IndexWriter indexWriter = null;
        try {
            List<Document> documents = new ArrayList<>();
            //创建索引库位置
            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("C:/dir/blogDir"));
            //IK 分词器
            Analyzer analyzer = new SpiltCharAnalyzer();
            //创建输出流 write
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            indexWriter = new IndexWriter(directory, indexWriterConfig);

            for (BlogBean blogDTO : blogs) {
                Document document = new Document();
                //记录标题和id即可
                Field id = new TextField("id", String.valueOf(blogDTO.getId()), Field.Store.YES);
                Field title = new TextField("title", blogDTO.getTitle(), Field.Store.YES);
                document.add(id);
                document.add(title);
                documents.add(document);
            }
            //一次处理
            indexWriter.addDocuments(documents);
        } catch (Exception e) {
            AssertUtil.isTrue(SystemErrorEnum.CREATE_DOCUMENT_FALE.getMsg());
        } finally {
            if(indexWriter!=null){
                //关闭输出流
                try {
                    indexWriter.close();
                } catch (IOException e) {
                    AssertUtil.isTrue(SystemErrorEnum.CREATE_DOCUMENT_FALE.getMsg());
                }
            }
        }
    }

    /**
     * 关键词搜索博客索引库
     *
     * @param key
     * @param size
     * @param index
     * @return
     */
    public DataResponse<LuceneCO> getBlogDir (String key, Integer index, Integer size){
        List<BlogCO> result = new ArrayList<>();
        LuceneCO luceneDTO = new LuceneCO();
        IndexReader indexReader=null;
        try {
            Analyzer analyzer = new SpiltCharAnalyzer();
            //关键词
            QueryParser qp = new QueryParser("title", analyzer);
            Query query = qp.parse(key);

            //高亮关键字
            SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
            Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));

            //打开索引库输入流
            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("C:/dir/blogDir"));
            indexReader = DirectoryReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            //上一页的结果
            ScoreDoc lastScoreDoc = getLastScoreDoc(index, size, query, indexSearcher);

            //从上一页最后一条数据开始查询  达到分页的目的
            TopDocs topDocs = indexSearcher.searchAfter(lastScoreDoc, query, size);
            long totle = topDocs.totalHits;
            luceneDTO.setTotole(totle);
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                //获得对应的文档
                Document doc = indexSearcher.doc(scoreDoc.doc);
                String title = doc.get("title");
                TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
                result.add(BlogCO.builder().id(doc.get("id")).title(highlighter.getBestFragment(tokenStream, title)).build());
            }
        }catch (Exception e){

        }finally {
            if(indexReader!=null){
                try {
                    indexReader.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        luceneDTO.setListData(result);
        return DataResponse.of(luceneDTO);
    }

    private ScoreDoc getLastScoreDoc (int pageIndex, int pageSize, Query query, IndexSearcher indexSearcher) throws IOException {
        if (pageIndex == 1) return null;
        //获取上一页的数量
        int num = pageSize * (pageIndex - 1);
        TopDocs tds = indexSearcher.search(query, num);
        return tds.scoreDocs[num - 1];
    }

    /**
     * 指定更新博客的索引文档
     *
     * @throws IOException
     */
    public void updateBlogDocument (BlogBean blogDTO) {
        IndexWriter indexWriter = null;

        try {
            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("C:/dir/blogDir"));
            Analyzer analyzer = new SpiltCharAnalyzer();
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            Document document = new Document();//替换的文档
            Field id = new TextField("id", String.valueOf(blogDTO.getId()), Field.Store.YES);
            Field title = new TextField("title", blogDTO.getTitle(), Field.Store.YES);
            indexWriter = new IndexWriter(directory, indexWriterConfig);
            document.add(title);
            document.add(id);
            Term term = new Term("title", blogDTO.getTitle());
            indexWriter.updateDocument(term, document);
            indexWriter.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (indexWriter != null) {
                try {
                    indexWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
