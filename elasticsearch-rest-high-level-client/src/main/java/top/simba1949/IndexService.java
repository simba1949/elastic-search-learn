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

    public static final String ENDPOINT = "/user_index";

    /**
     * 创建索引
     * <p>
     *     PUT /user_index
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void putIndex(RestClient restClient) throws IOException {
        Request request = new Request("PUT", ENDPOINT);
        Response response = restClient.performRequest(request);
        // ES官方读取响应：https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.5/java-rest-low-usage-responses.html
        String entityStr = EntityUtils.toString(response.getEntity());
        log.info("IndexService.putIndex response is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 获取某个索引信息
     * <p>
     *     GET /user_index
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void getIndex(RestClient restClient) throws IOException {
        Request request = new Request("GET", ENDPOINT);
        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity());
        log.info("IndexService.getIndex response is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 获取所有索引信息
     * <p>
     *     GET /_cat/indices?v=true&prett
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void getAllIndex(RestClient restClient) throws IOException {
        Request request = new Request("GET", "/_cat/indices?v=true&pretty");
        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity());
        log.info("IndexService.getAllIndex entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 删除单个索引
     * <p>
     *     DELETE /user_index
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void deleteIndex(RestClient restClient) throws IOException {
        Request request = new Request("DELETE", ENDPOINT);
        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity());
        log.info("IndexService.deleteIndex entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 删除多个索引（有一个不存在，则不会删除）
     * <p>
     *     DELETE /role_index,permission_index
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void deleteMultiIndex(RestClient restClient) throws IOException {
        Request request = new Request("DELETE", "/role_index,permission_index");
        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity());
        log.info("IndexService.deleteMultiIndex entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 删除匹配的索引
     * <p>
     *     DELETE /*index
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void deleteRegexIndex(RestClient restClient) throws IOException {
        Request request = new Request("DELETE", "/*index");
        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity());
        log.info("IndexService.deleteRegexIndex entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 删除所有索引
     * <p>
     *     DELETE /_all
     * </p>
     * <p>
     *     如果出现【Wildcard expressions or all indices are not allowed】，
     *     需要将 elasticsearch.yml 中 action.destructive_requires_name 设置为 false 重启.
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void deleteAllIndex(RestClient restClient) throws IOException {
        Request request = new Request("DELETE", "/_all");
        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity());
        log.info("IndexService.deleteAllIndex entity is {}", JSONUtil.toJsonStr(entityStr));
    }
}