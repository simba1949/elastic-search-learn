package top.simba1949;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author anthony
 * @date 2022/12/26
 */
@Slf4j
public class DocumentQueryService {

    /**
     * 匹配查询
     * <p>
     *     GET /[索引]/_search
     *     {
     *         "query":{
     *             "match":{
     *                 "username":"李白" // 属性名：属性值
     *             }
     *         }
     *     }
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void match(RestClient restClient) throws IOException {
        HashMap<String, Object> match = Maps.newHashMap();
        match.put("username", "李白");
        HashMap<String, Object> query = Maps.newHashMap();
        query.put("match", match);
        HashMap<String, Object> condition = Maps.newHashMap();
        condition.put("query", query);

        Request request = new Request("GET", "/user_index/_search");
        request.setJsonEntity(JSONUtil.toJsonStr(condition));

        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
        log.info("DocumentService.getDocument entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 查询所有
     * <p>
     *     GET /[索引]/_search
     *     {
     *         "query":{
     *             "match_all":{}
     *         }
     *     }
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void matchAll(RestClient restClient) throws IOException {
        HashMap<String, Object> match = Maps.newHashMap();
        HashMap<String, Object> query = Maps.newHashMap();
        query.put("match_all", match);
        HashMap<String, Object> condition = Maps.newHashMap();
        condition.put("query", query);

        Request request = new Request("GET", "/user_index/_search");
        request.setJsonEntity(JSONUtil.toJsonStr(condition));

        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
        log.info("DocumentService.getDocument entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 分页查询
     * <p>
     *     GET /[索引]/_search
     *     {
     *         "query":{
     *             "match_all":{}
     *         },
     *         "from":1, // 起始位置=（页面-1）* 每页数
     *         "size":1 // 每页数量
     *     }
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void page(RestClient restClient) throws IOException {
        HashMap<String, Object> match = Maps.newHashMap();
        HashMap<String, Object> query = Maps.newHashMap();
        query.put("match_all", match);
        HashMap<String, Object> condition = Maps.newHashMap();
        condition.put("query", query);
        condition.put("from", 1); // 起始位置=（页面-1）* 每页数
        condition.put("size", 1); // 每页数量

        Request request = new Request("GET", "/user_index/_search");
        request.setJsonEntity(JSONUtil.toJsonStr(condition));

        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
        log.info("DocumentService.getDocument entity is {}", JSONUtil.toJsonStr(entityStr));
    }


    /**
     * 排序查询（TODO）
     * @param restClient
     * @throws IOException
     */
    public void sort(RestClient restClient) throws IOException {
        HashMap<String, Object> match = Maps.newHashMap();
        HashMap<String, Object> query = Maps.newHashMap();
        query.put("match_all", match);

        HashMap<String, Object> order = Maps.newHashMap();
        order.put("order", "desc");
        HashMap<String, Object> sort = Maps.newHashMap();
        sort.put("age", order);

        HashMap<String, Object> condition = Maps.newHashMap();
        condition.put("query", query);
        condition.put("sort", sort);

        Request request = new Request("GET", "/user_index/_search");
        request.setJsonEntity(JSONUtil.toJsonStr(condition));

        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
        log.info("DocumentService.getDocument entity is {}", JSONUtil.toJsonStr(entityStr));
    }
}
