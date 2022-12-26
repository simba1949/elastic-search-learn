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
     *                 "username":"李白"
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
}
