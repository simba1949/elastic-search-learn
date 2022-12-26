package top.simba1949;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

/**
 * @author anthony
 * @date 2022/12/26
 */
public class ElasticSearchRestHighLevelClientApplication {
    public static void main(String[] args) throws IOException {
        // 创建 ES 客户端
        RestClient restClient = RestClient.builder(new HttpHost("120.48.47.7", 9200, "http")).build();

        IndexService indexService = new IndexService();
        indexService.deleteAllIndex(restClient);

        // 关闭 ES 客户端
        restClient.close();
    }
}
