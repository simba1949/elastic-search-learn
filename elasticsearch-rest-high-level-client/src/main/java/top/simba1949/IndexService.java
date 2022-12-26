package top.simba1949;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.*;

import java.io.IOException;

/**
 * @author anthony
 * @date 2022/12/26
 */
@Slf4j
public class IndexService {

    public static final String ENDPOINT = "/person";

    /**
     * 创建索引
     * @param restClient
     * @throws IOException
     */
    public void crateIndex(RestClient restClient) throws IOException {
        Request request = new Request("put", ENDPOINT);
        Response response = restClient.performRequest(request);
        // ES官方读取响应：https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.5/java-rest-low-usage-responses.html
        String entityStr = EntityUtils.toString(response.getEntity());
        log.info("IndexService.crateIndex response is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 获取某个索引信息
     * @param restClient
     * @throws IOException
     */
    public void getIndex(RestClient restClient) throws IOException {
        Request request = new Request("get", ENDPOINT);
        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity());
        log.info("IndexService.getIndex response is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 获取所有索引信息
     * @param restClient
     * @throws IOException
     */
    public void getAllIndex(RestClient restClient) throws IOException {
        Request request = new Request("get", "/_cat/indices?v");
        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity());
        log.info("IndexService.getAllIndex entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 删除单个索引
     * @param restClient
     * @throws IOException
     */
    public void deleteIndex(RestClient restClient) throws IOException {
        Request request = new Request("delete", ENDPOINT);
        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity());
        log.info("IndexService.deleteIndex entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 删除多个索引
     * @param restClient
     * @throws IOException
     */
    public void deleteMultiIndex(RestClient restClient) throws IOException {
        Request request = new Request("delete", "/user,shopping");
        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity());
        log.info("IndexService.deleteMultiIndex entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 删除所有索引
     * <p>
     *     如果出现【Wildcard expressions or all indices are not allowed】，
     *     需要将 elasticsearch.yml 中 action.destructive_requires_name 设置为 false 重启.
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void deleteAllIndex(RestClient restClient) throws IOException {
        Request request = new Request("delete", "/_all");
        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity());
        log.info("IndexService.deleteAllIndex entity is {}", JSONUtil.toJsonStr(entityStr));
    }
}
