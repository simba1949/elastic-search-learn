package top.simba1949;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

/**
 * @author anthony
 * @date 2023/1/1
 */
public class ElasticSearchJavaApplication {
    public static void main(String[] args) {

    }

    /**
     * 同步客户端
     * @return
     */
    public static ElasticsearchClient syncClient(){
        // 创建一个低级客户端
        RestClient restClient = RestClient.builder(new HttpHost("120.48.47.7", 9200, "http")).build();
        RestClientTransport restClientTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        return new ElasticsearchClient(restClientTransport);
    }

    /**
     * 异步客户端
     * @return
     */
    public static ElasticsearchAsyncClient asyncClient(){
        // 创建一个低级客户端
        RestClient restClient = RestClient.builder(new HttpHost("120.48.47.7", 9200, "http")).build();
        RestClientTransport restClientTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        return new ElasticsearchAsyncClient(restClientTransport);
    }
}
