package cn.itcast.lucene;

import org.apache.commons.io.FileUtils;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;

import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;


import java.io.File;


public class CreatIndex {


    @Test
    public  void CreadIndex() throws Exception{

        //准备 索引库目录
        FSDirectory directory= FSDirectory.open(new File("E:\\index"));
        //指定分词器   此时是标准的分分词器
        //StandardAnalyzer analyzer=new StandardAnalyzer(Version.LUCENE_4_10_3);
        IKAnalyzer analyzer=new IKAnalyzer();

        //索引库的配置信息   参数1 是 版本信息 ,参数2是指定的分析器
        IndexWriterConfig config=new IndexWriterConfig(Version.LUCENE_4_10_3,analyzer);

        //1.创建写入索引库对象
        IndexWriter indexWriter=new IndexWriter(directory,config);

        //读取文件中的数据
        File fileDir = new File("E:\\sources");

        File[] files = fileDir.listFiles();

        //遍历资源
        for (File file : files) {
            System.out.println("文件名："+file.getName());
            System.out.println("文件的大小:"+FileUtils.sizeOf(file));
            System.out.println("文件内容:"+ FileUtils.readFileToString(file));
            System.out.println("文件路径:"+file.getPath());

            //创建文档对象
            Document document=new Document();
            /**
             * TextField 文本存储
             * LongField 存储数值
             * Store.YES 需要存储内容到索引库
             */
            document.add(new TextField("fileName",file.getName(), Field.Store.YES));
            document.add(new LongField("fileSize",FileUtils.sizeOf(file), Field.Store.YES));
            document.add(new TextField("fileContent",FileUtils.readFileToString(file), Field.Store.YES));
            document.add(new TextField("filePath",file.getPath(), Field.Store.YES));


            indexWriter.addDocument(document);

        }
        indexWriter.commit();
        indexWriter.close();

    }


}
