package top.simba1949.operation;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import top.simba1949.config.ESClient;

/**
 * 文档搜索
 *
 * @author anthony
 * @version 2023/9/15 15:38
 */
@Slf4j
public class DocumentFullQuerySyncOperation {
    public static void main(String[] args) throws Exception {
        ElasticsearchClient esClient = ESClient.syncClient();

        query4Match(esClient);
    }

    /**
     * TODO ANTHONY
     * @param client
     * @throws Exception
     */
    public static void query4Match(ElasticsearchClient client) throws Exception {
        SearchRequest searchRequest = new SearchRequest
                .Builder()
                .index("kibana_sample_data_flights")
                .query(new Query
                        .Builder()
                        .match(
                                new MatchQuery
                                        .Builder()
                                        .field("FlightNum")
                                        .query("9HY9SWR")
                                        .build())
                        .build()
                )
                .from(0)
                .size(10)
                .build();

        // 这里直接使用 Flight.class 会存在反序列化的问题
        SearchResponse<Object> searchResponse = client.search(searchRequest, Object.class);
        long took = searchResponse.took();
        log.info("查询总耗时：{}", took);
        HitsMetadata<Object> hits = searchResponse.hits();
        for (Hit<Object> hit : hits.hits()) {
            Object source = hit.source();
            log.info("查询到的数据是{}", JSONObject.toJSONString(source));
        }
    }
}