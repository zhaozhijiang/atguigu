package com.atguigu.test;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.junit.Test;

import java.io.IOException;

/**
 * @author: zhijiang.zhao
 * @date: 2023/2/22 17:22
 * @Description:
 */
public class ElasticSearchTest {
    /**
     * 测试连接
     */
    @Test
    public void elasticSearchConnectionTest() throws IOException {
        //9200 端口为 Elasticsearch的Web通信端口192.168.56.101为启动ES服务的主机名
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.101", 9200)));
        client.close();
    }

    /**
     * 创建索引
     * @throws IOException
     */
    @Test
    public void elasticSearchDocCreateTest() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.101", 9200)));
        //创建索引
        CreateIndexRequest request = new CreateIndexRequest("user");
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        //创建索引的响应状态
        boolean acknowledged = response.isAcknowledged();
        System.out.println("响应状态为：" + acknowledged);
        client.close();
    }

    /**
     * 查询索引
     * @throws IOException
     */
    @Test
    public void elasticsearchDocSearchTest() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.101", 9200)));
        //查询索引
        GetIndexRequest request = new GetIndexRequest("user");
        GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
        //查询索引的响应状态
        System.out.println(response);
        System.out.println(response.getSettings());
        System.out.println(response.getAliases());
        System.out.println(response.getMappings());
        client.close();
    }

    /**
     * 删除索引
     */
    @Test
    public void elasticsearchDocDeleteTest() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.101", 9200)));
        DeleteIndexRequest request = new DeleteIndexRequest("user");
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        //删除索引的响应状态
        System.out.println("删除状态为：" + response.isAcknowledged());
        client.close();
    }
}
