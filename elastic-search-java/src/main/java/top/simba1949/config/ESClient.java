package top.simba1949.config;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import top.simba1949.common.constant.ESConstant;

/**
 * 详见 <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/indexing.html">...</a>
 *
 * @author anthony
 * @version 2023/9/15 7:22
 */
public class ESClient {

    /**
     * 同步客户端
     *
     * @return ElasticsearchClient
     */
    public static ElasticsearchClient syncClient() {

        // Create the low-level client
        RestClient restClient = RestClient
                .builder(HttpHost.create(ESConstant.SERVER_URL))
                .build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        // And create the API client
        return new ElasticsearchClient(transport);
    }

    /**
     * 异步通信客户端
     *
     * @return
     */
    public static ElasticsearchAsyncClient asyncClient() {
        // Create the low-level client
        RestClient restClient = RestClient
                .builder(HttpHost.create(ESConstant.SERVER_URL))
                .build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        // And create the API client
        return new ElasticsearchAsyncClient(transport);
    }
}