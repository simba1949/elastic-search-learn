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
 * @date 2023/1/1
 */
@Slf4j
public class MappingService {

    /**
     * TODO
     * @param restClient
     * @throws IOException
     */
    public void putMapping(RestClient restClient) throws IOException {
        HashMap<String, Object> username = Maps.newHashMap();
        username.put("type", "text");
        username.put("index", true);

        HashMap<String, Object> age = Maps.newHashMap();
        age.put("type", "long");
        age.put("index", true);

        HashMap<String, Object> sex = Maps.newHashMap();
        sex.put("type", "keyword");
        sex.put("index", true);

        HashMap<String, Object> price = Maps.newHashMap();
        price.put("type", "double");
        price.put("index", true);

        HashMap<String, Object> properties = Maps.newHashMap();
        properties.put("username", username);
        properties.put("age", age);
        properties.put("sex", sex);
        properties.put("price", price);

        HashMap<String, Object> mappings = Maps.newHashMap();
        mappings.put("properties", properties);

        HashMap<String, Object> condition = Maps.newHashMap();
        condition.put("mappings", mappings);

        log.info("the request params is {}", JSONUtil.toJsonStr(condition));

        Request request = new Request("PUT", "/role_index/_mapping");
        request.setJsonEntity(JSONUtil.toJsonStr(properties));

        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
        log.info("DocumentService.getDocument entity is {}", JSONUtil.toJsonStr(entityStr));
    }
}
