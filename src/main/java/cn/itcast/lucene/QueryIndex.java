package cn.itcast.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;

public class QueryIndex {


    @Test
    public  void  QueryIndex() throws Exception{

        //打开本地文件
        DirectoryReader reader = DirectoryReader.open(FSDirectory.open(new File("E:\\index")));


        System.out.println(111);




        //创建索引库搜索对象
        IndexSearcher indexSearcher=new IndexSearcher(reader);

        //查询所有索引库
        //MatchAllDocsQuery query = new MatchAllDocsQuery();
        //基于词条查询
        //Query query = new TermQuery( new Term("fileName","传智播客"));
        //模糊查询   * 表示任意个字符   ？  表示单个字符
        //WildcardQuery query = new WildcardQuery( new Term("fileName","传智*"));
        //相似度查询    针对英文    最高修改不能超过两次
       // FuzzyQuery query = new FuzzyQuery( new Term("fileName","lucene"));
        //参数 范围查询
        //参数 1  范围也就是在哪里查   参数2   起始字节  参数3 结束字节  参数 4  时候包含起始 参数5 是否包含  结束位
        NumericRangeQuery<Long> query =  NumericRangeQuery.newLongRange("fileSize",0L,50L,true,true);

        //通过搜索对象的search方法进行查询
        TopDocs topDocs = indexSearcher.search(query, 10);

        //后去所有的文档
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            System.out.println("文档id"+scoreDoc.doc);

            //通过文档id来获取文档对象
            Document document = indexSearcher.doc(scoreDoc.doc);
            System.out.println("文件名"+document.getField("fileName"));
            System.out.println("文件大小"+document.getField("fileContent"));
            System.out.println("文件内容"+document.getField("fileSize"));
            System.out.println("文件路径"+document.getField("filePath"));

        }
        System.out.println("查询到的文件数量是："+topDocs.totalHits);

    }
}
