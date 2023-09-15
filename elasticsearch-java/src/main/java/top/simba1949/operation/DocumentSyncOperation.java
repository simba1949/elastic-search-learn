package top.simba1949.operation;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import top.simba1949.config.ESClient;
import top.simba1949.domain.User;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author anthony
 * @version 2023/9/15 14:50
 */
@Slf4j
public class DocumentSyncOperation {
    public static void main(String[] args) throws Exception {
        ElasticsearchClient esClient = ESClient.syncClient();

        // createOrUpdate(esClient);

        // batchCreateOrUpdate(esClient);

        delete(esClient);
    }

    /**
     * 新增文档或者更新文档
     *
     * @param client ES client
     * @throws Exception
     */
    public static void createOrUpdate(ElasticsearchClient client) throws Exception {
        User user = User.builder()
                .id(1L)
                .username("李白")
                .birthday(new Date())
                .intro("李白乘舟将欲行")
                .build();

        IndexRequest<Object> indexRequest = new IndexRequest.Builder<>()
                .index("index_user")
                .id(String.valueOf(user.getId()))
                .document(user)
                .build();

        IndexResponse indexResponse = client.index(indexRequest);
        log.info("indexResponse={}", JSONObject.toJSONString(indexResponse));

        String index = indexResponse.index();
        String id = indexResponse.id();
        Result result = indexResponse.result();
        long version = indexResponse.version();
        log.info("index={}, id={}, result={}, version={}", index, id, result, version);
    }

    /**
     * 批次新增文档或者更新文档
     *
     * @param client ES client
     * @throws Exception
     */
    public static void batchCreateOrUpdate(ElasticsearchClient client) throws Exception {
        User user1 = User.builder().id(1L).username("李白").birthday(new Date()).intro("李白 intro").build();
        User user2 = User.builder().id(2L).username("杜甫").birthday(new Date()).intro("杜甫 intro").build();
        User user3 = User.builder().id(3L).username("苏轼").birthday(new Date()).intro("苏轼 intro").build();

        // 构建批量操作对象 bulk
        BulkRequest.Builder bulkRequestBuild = new BulkRequest.Builder();
        Stream.of(user1, user2, user3)
                .forEach(user -> bulkRequestBuild.operations(
                        op -> op.index(
                                index -> index
                                        .index("index_user")
                                        .id(String.valueOf(user.getId()))
                                        .document(user))));

        BulkRequest bulkRequest = bulkRequestBuild.build();
        BulkResponse bulkResponse = client.bulk(bulkRequest);

        long took = bulkResponse.took();
        boolean errors = bulkResponse.errors();
        log.info("总共耗时{}，是否成功{}", took, errors);
        List<BulkResponseItem> items = bulkResponse.items();
        for (BulkResponseItem item : items) {
            String index = item.index();
            String id = item.id();
            String result = item.result();
            log.info("index={}, id={}, result={}", index, id, result);
        }
    }


    /**
     * 文档删除操作
     *
     * @param client ES client
     * @throws IOException
     */
    public static void delete(ElasticsearchClient client) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest.Builder().index("index_user").id("3").build();
        DeleteResponse deleteResponse = client.delete(deleteRequest);

        String index = deleteResponse.index();
        String id = deleteResponse.id();
        Result result = deleteResponse.result();
        log.info("result={}, index={}, id={}", JSONObject.toJSONString(result), index, id);
    }
}