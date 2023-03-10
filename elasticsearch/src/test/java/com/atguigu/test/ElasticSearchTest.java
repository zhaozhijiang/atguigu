package com.atguigu.test;

import com.atguigu.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

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
     *
     * @throws IOException
     */
    @Test
    public void elasticSearchIndexCreateTest() throws IOException {
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
     *
     * @throws IOException
     */
    @Test
    public void elasticsearchIndexSearchTest() throws IOException {
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
    public void elasticsearchIndexDeleteTest() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.101", 9200)));
        DeleteIndexRequest request = new DeleteIndexRequest("user");
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        //删除索引的响应状态
        System.out.println("删除状态为：" + response.isAcknowledged());
        client.close();
    }

    /**
     * 创建文档
     */
    @Test
    public void elasticsearchDocInsertTest() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.101", 9200)));
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index("user").id("1001");
        //创建数据对象
        User user = new User("xiaobear", 18, "boy");
        //数据对象转为JSON
        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(user);
        indexRequest.source(userJson, XContentType.JSON);
        //获取响应对象
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println("_index：" + response.getIndex());
        System.out.println("_id：" + response.getId());
        System.out.println("_result：" + response.getResult());
        client.close();
    }

    /**
     * 修改文档
     *
     * @throws IOException
     */
    @Test
    public void elasticsearchDocUpdateTest() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.101", 9200)));
        //修改文档
        UpdateRequest request = new UpdateRequest();
        request.index("user").id("1001");
        // 设置请求体，对数据进行修改
        request.doc(XContentType.JSON, "sex", "girl");
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println("_index：" + response.getIndex());
        System.out.println("_id：" + response.getId());
        System.out.println("_result：" + response.getResult());
        client.close();
    }

    /**
     * 查询文档
     *
     * @throws IOException
     */
    @Test
    public void elasticsearchDocGetTest() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.101", 9200)));
        //创建请求对象
        GetRequest request = new GetRequest().index("user").id("1001");
        //创建响应对象
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        System.out.println("_index:" + response.getIndex());
        System.out.println("_type:" + response.getType());
        System.out.println("_id:" + response.getId());
        System.out.println("source:" + response.getSourceAsString());
        client.close();
    }

    /**
     * 删除文档
     *
     * @throws IOException
     */
    @Test
    public void elasticsearchDocDeleteTest() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.101", 9200)));
        DeleteRequest request = new DeleteRequest("user").id("1001");
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
        client.close();
    }

    /**
     * 批量新增
     *
     * @throws IOException
     */
    @Test
    public void elasticsearchBatchInsertTest() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.101", 9200)));
        //创建批量新增请求对象
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest().index("user").id("1004").source(XContentType.JSON, "name", "xiaohuahua"));
        request.add(new IndexRequest().index("user").id("1005").source(XContentType.JSON, "name", "zhangsan"));
        request.add(new IndexRequest().index("user").id("1006").source(XContentType.JSON, "name", "lisi"));
        //创建响应对象
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println("took：" + response.getTook());
        System.out.println("items：" + response.getItems());
    }

    /**
     * 批量删除
     *
     * @throws IOException
     */
    @Test
    public void elasticsearchBatchDeleteTest() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.101", 9200)));
        BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest().index("user").id("1001"));
        request.add(new DeleteRequest().index("user").id("1002"));
        request.add(new DeleteRequest().index("user").id("1003"));
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println("took：" + response.getTook());
        Arrays.stream(response.getItems()).forEach(System.out::println);
        System.out.println("items：" + Arrays.toString(response.getItems()));
        System.out.println("status：" + response.status());
        System.out.println("失败消息：" + response.buildFailureMessage());
    }

    //高级查询

    /**
     * 查询所有索引数据
     *
     * @throws IOException
     */
    @Test
    public void RequestBodyQuery() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.101", 9200)));
        //创建搜索对象
        SearchRequest request = new SearchRequest();
        request.indices("user");
        //构建查询的请求体
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //查询所有对象
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        request.source(searchSourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took：" + response.getTook());
        System.out.println("是否超时：" + response.isTimedOut());
        System.out.println("TotalHits：" + hits.getTotalHits());
        System.out.println("MaxScore：" + hits.getMaxScore());
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * term查询
     *
     * @throws IOException
     */
    @Test
    public void TremQuery() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.101", 9200)));
        //创建搜索对象
        SearchRequest request = new SearchRequest();
        request.indices("user");
        //构建查询的请求体
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //查询所有对象
        searchSourceBuilder.query(QueryBuilders.termQuery("name", "zhangsan"));
        request.source(searchSourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took：" + response.getTook());
        System.out.println("是否超时：" + response.isTimedOut());
        System.out.println("TotalHits：" + hits.getTotalHits());
        System.out.println("MaxScore：" + hits.getMaxScore());
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 分页查询
     *
     * @throws Exception
     */
    @Test
    public void PageQuery() throws Exception {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.101", 9200)));
        //创建搜索对象
        SearchRequest request = new SearchRequest();
        request.indices("user");
        //构建查询请求体
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //查询所有对象
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //分页查询 当前页其实索引 第一条数据的顺序号 from
        searchSourceBuilder.from(0);
        //每页显示多少条
        searchSourceBuilder.size(2);
        request.source(searchSourceBuilder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took：" + response.getTook());
        System.out.println("是否超时：" + response.isTimedOut());
        System.out.println("TotalHits：" + hits.getTotalHits());
        System.out.println("MaxScore：" + hits.getMaxScore());
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    @Test
    public void DataSorting() throws Exception {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.101", 9200)));
        //创建搜索对象
        SearchRequest request = new SearchRequest();
        request.indices("user");
        //构建查询请求体
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //查询所有对象
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //数据排序
        searchSourceBuilder.sort("age", SortOrder.DESC);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took：" + response.getTook());
        System.out.println("是否超时：" + response.isTimedOut());
        System.out.println("TotalHits：" + hits.getTotalHits());
        System.out.println("MaxScore：" + hits.getMaxScore());
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 过滤字段
     *
     * @throws Exception
     */
    @Test
    public void FilterFiled() throws Exception {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.101", 9200)));
        //创建搜索对象
        SearchRequest request = new SearchRequest();
        request.indices("user");
        //构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //查询所有对象
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        //数据排序
        sourceBuilder.sort("age", SortOrder.DESC);
        //查询过滤字段
        String[] excludes = {};
        //过滤掉name属性
        String[] includes = {"age"};
        sourceBuilder.fetchSource(includes, excludes);

        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took：" + response.getTook());
        System.out.println("是否超时：" + response.isTimedOut());
        System.out.println("TotalHits：" + hits.getTotalHits());
        System.out.println("MaxScore：" + hits.getMaxScore());
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    @Test
    public void BoolSearch() throws Exception {
        //创建客户端连接
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.101", 9200)));
        //创建查询对象
        SearchRequest request = new SearchRequest();
        request.indices("user");
        //创建查询请求体
        SearchSourceBuilder sourceBuilder =new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //必须包含
        boolQueryBuilder.must(QueryBuilders.matchQuery("age", 18));
        //一定不包含
        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("name", "lisi"));
        //可能包含
        boolQueryBuilder.should(QueryBuilders.matchQuery("name", "zhangsan"));
        //查询所有对象
        sourceBuilder.query(boolQueryBuilder);

        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took：" + response.getTook());
        System.out.println("是否超时：" + response.isTimedOut());
        System.out.println("TotalHits：" + hits.getTotalHits());
        System.out.println("MaxScore：" + hits.getMaxScore());
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }
}
