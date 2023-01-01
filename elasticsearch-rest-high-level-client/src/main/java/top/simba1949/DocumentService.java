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
public class DocumentService {

    /**
     * 新增文档（不指定id，会返回随机id）
     * <p>
     *     POST /[索引]/_doc
     *     {
     *         “username”: "李白",
     *         "age": 1111,
     *         "price": 11.11,
     *         "sex": "男"
     *     }
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void postDocumentRandomId(RestClient restClient) throws IOException {
        HashMap<String, Object> hashMap4Insert = Maps.newHashMap();
        hashMap4Insert.put("username", "李白");
        hashMap4Insert.put("age", 1111);
        hashMap4Insert.put("price", 11.11);
        hashMap4Insert.put("sex", "男");

        log.info("the request params is {}", JSONUtil.toJsonStr(hashMap4Insert));

        Request request = new Request("POST", "/user_index/_doc");
        request.setJsonEntity(JSONUtil.toJsonStr(hashMap4Insert));

        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity());
        log.info("DocumentService.postDocumentRandomId entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 新增文档（指定id）
     * <p>
     *     POST /[索引]/_doc/[自定义id值]
     *     {
     *         “username”: "李白",
     *         "age": 2222,
     *         "price": 22.22,
     *         "sex": "男"
     *     }
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void postDocumentAssignId(RestClient restClient) throws IOException {
        HashMap<String, Object> hashMap4Insert = Maps.newHashMap();
        hashMap4Insert.put("username", "李白");
        hashMap4Insert.put("age", 2222);
        hashMap4Insert.put("price", 22.22);
        hashMap4Insert.put("sex", "男");

        log.info("the request params is {}", JSONUtil.toJsonStr(hashMap4Insert));

        Request request = new Request("POST", "/user_index/_doc/2");
        request.setJsonEntity(JSONUtil.toJsonStr(hashMap4Insert));

        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity());
        log.info("DocumentService.postDocumentAssignId entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 获取单个数据
     * <p>
     *     GET /[索引]/_doc/[id值]
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void getDocument(RestClient restClient) throws IOException {
        Request request = new Request("GET", "/user_index/_doc/2");
        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
        log.info("DocumentService.getDocument entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 获取所有文档
     * <p>
     *     GET /[索引]/_search
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void getAllDocument(RestClient restClient) throws IOException {
        Request request = new Request("GET", "/user_index/_search");
        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
        log.info("DocumentService.getAllDocument entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 修改数据——全部覆盖
     * <p>
     *     PUT /[索引]/_doc/[ID值]
     *     {
     *         “username”：“李白3”，
     *         “age”：3333，
     *         “price”：33.33，
     *         “sex”：“男3”
     *     }
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void putAllOverDataDocument(RestClient restClient) throws IOException {
        HashMap<String, Object> hashMap4Insert = Maps.newHashMap();
        hashMap4Insert.put("username", "李白3");
        hashMap4Insert.put("age", 3333);
        hashMap4Insert.put("price", 33.33);
        hashMap4Insert.put("sex", "男3");

        log.info("the request params is {}", JSONUtil.toJsonStr(hashMap4Insert));

        Request request = new Request("PUT", "/user_index/_doc/2");
        request.setJsonEntity(JSONUtil.toJsonStr(hashMap4Insert));
        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
        log.info("DocumentService.putAllOverDataDocument entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 修改数据——部分修改
     * <p>
     *     POST /[索引]/_update/[ID值]
     *     {
     *         "doc":{
     *             "username":"李太白" // 需要修改的字段及其值
     *         }
     *     }
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void putPartOverDataDocument(RestClient restClient) throws IOException {
        HashMap<String, String> inHashMap = Maps.newHashMap();
        inHashMap.put("username", "李太白");
        HashMap<String, Object> outHashMap = Maps.newHashMap();
        outHashMap.put("doc", inHashMap);

        log.info("the request params is {}", JSONUtil.toJsonStr(outHashMap));

        Request request = new Request("POST", "/user_index/_update/1");
        request.setJsonEntity(JSONUtil.toJsonStr(outHashMap));
        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
        log.info("DocumentService.putPartOverDataDocument entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 删除文档
     * <p>
     *     DELETE /[索引]/_doc/[ID值]
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void deleteDocument(RestClient restClient) throws IOException {
        Request request = new Request("DELETE", "/user_index/_doc/1");
        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
        log.info("DocumentService.putPartOverDataDocument entity is {}", JSONUtil.toJsonStr(entityStr));
    }
}
