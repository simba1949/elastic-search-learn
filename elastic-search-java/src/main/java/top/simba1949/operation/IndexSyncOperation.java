package top.simba1949.operation;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.*;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import top.simba1949.config.ESClient;

import java.io.IOException;
import java.util.Map;

/**
 * @author anthony
 * @version 2023/9/15 7:43
 */
@Slf4j
public class IndexSyncOperation {

    public static void main(String[] args) throws IOException {
        ElasticsearchClient esClient = ESClient.syncClient();

        // 创建索引
        // create(esClient);

        // 获取 indices
        indices(esClient);

        // 删除索引
        // delete(esClient);
    }

    /**
     * 查询索引
     *
     * @param client ES client
     * @throws IOException
     */
    public static void indices(ElasticsearchClient client) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest.Builder().index("index_user").build();
        GetIndexResponse getIndexResponse = client.indices().get(getIndexRequest);
        Map<String, IndexState> result = getIndexResponse.result();
        log.info("result={}", JSON.toJSONString(result));
    }


    /**
     * 创建 index
     *
     * @param client ES client
     */
    public static void create(ElasticsearchClient client) throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest.Builder().index("index_user").build();
        CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest);

        String index = createIndexResponse.index();
        boolean acknowledged = createIndexResponse.acknowledged();
        boolean shardsAcknowledged = createIndexResponse.shardsAcknowledged();
        log.info("index={}, acknowledged={}, shardsAcknowledged={}", index, acknowledged, shardsAcknowledged);
    }

    /**
     * 删除索引
     *
     * @param client ES client
     */
    public static void delete(ElasticsearchClient client) throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest.Builder().index("index_user").build();
        DeleteIndexResponse deleteIndexResponse = client.indices().delete(deleteIndexRequest);

        boolean acknowledged = deleteIndexResponse.acknowledged();
        log.info("acknowledged={}", acknowledged);
    }
}