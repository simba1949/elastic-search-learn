package top.simba1949.operation;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
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
        log.info("DocumentQueryService.match entity is {}", JSONUtil.toJsonStr(entityStr));
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
        log.info("DocumentQueryService.matchAll entity is {}", JSONUtil.toJsonStr(entityStr));
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
        log.info("DocumentQueryService.page entity is {}", JSONUtil.toJsonStr(entityStr));
    }


    /**
     * 排序查询
     * <p>
     *     GET /索引/_search
     *  {
     *     "sort":{
     *       	// price为文档中的属性
     *         "price":{
     *             "order":"desc"
     *         }
     *     }
     * }
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void sort(RestClient restClient) throws IOException {
        HashMap<String, Object> order = Maps.newHashMap();
        order.put("order", "desc");
        HashMap<String, Object> sort = Maps.newHashMap();
        sort.put("price", order);

        HashMap<String, Object> condition = Maps.newHashMap();
        condition.put("sort", sort);

        log.info("the params of request what {}", JSONUtil.toJsonStr(condition));

        Request request = new Request("GET", "/user_index/_search");
        request.setJsonEntity(JSONUtil.toJsonStr(condition));

        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
        log.info("DocumentQueryService.sort entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 查询自定字段
     * <p>
     *     GET /索引/_search
     *     {
     *     "query": {
     *         "match_all": {}
     *     },
     *   	// 查询指定字段
     *     "_source": [
     *         "title"
     *     ]
     * }
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void fieldSearch(RestClient restClient) throws IOException {
        HashMap<String, Object> match = Maps.newHashMap();
        HashMap<String, Object> query = Maps.newHashMap();
        query.put("match_all", match);

        HashMap<String, Object> filed = Maps.newHashMap();
        filed.put("_source", "username");

        HashMap<String, Object> condition = Maps.newHashMap();
        condition.put("query", query);

        log.info("the params of request what {}", JSONUtil.toJsonStr(condition));

        Request request = new Request("GET", "/user_index/_search");
        request.setJsonEntity(JSONUtil.toJsonStr(condition));

        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
        log.info("DocumentQueryService.fieldSearch entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 多条件同时成立
     * <p>
     *     GET /索引/_search
     *     {
     *     "query":{
     *         "bool":{
     *           	 // must 表示多个条件同时成立
     *             "must":[
     *                 {
     *                     "match":{
     *                         "username":"李白"
     *                     }
     *                 },
     *                 {
     *                     "match":{
     *                         "price":22.22
     *                     }
     *                 }
     *             ]
     *         }
     *     }
     * }
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void allConditionSearch(RestClient restClient) throws IOException {
        HashMap<String, Object> match1 = Maps.newHashMap();
        HashMap<String, Object> match1Username = Maps.newHashMap();
        match1Username.put("username", "李白");
        match1.put("match", match1Username);

        HashMap<String, Object> match2 = Maps.newHashMap();
        HashMap<String, Object> match2price = Maps.newHashMap();
        match2price.put("price", 22.22);
        match2.put("match", match2price);

        HashMap<String, Object> bool = Maps.newHashMap();
        bool.put("must", Lists.newArrayList(match1, match2));

        HashMap<String, Object> query = Maps.newHashMap();
        query.put("bool", bool);

        HashMap<String, Object> condition = Maps.newHashMap();
        condition.put("query", query);


        log.info("the params of request what {}", JSONUtil.toJsonStr(condition));

        Request request = new Request("GET", "/user_index/_search");
        request.setJsonEntity(JSONUtil.toJsonStr(condition));

        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
        log.info("DocumentQueryService.allConditionSearch entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 多条件，任意条件成立
     * <p>
     *     GET /索引/_search
     *     {
     *     "query":{
     *         "bool":{
     *           	// should 表示满足其中一个条件
     *             "should":[
     *                 {
     *                     "match":{
     *                         "username":"李白"
     *                     }
     *                 },
     *                 {
     *                     "match":{
     *                         "price":22.22
     *                     }
     *                 }
     *             ]
     *         }
     *     }
     * }
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void anyConditionSearch(RestClient restClient) throws IOException {
        HashMap<String, Object> match1 = Maps.newHashMap();
        HashMap<String, Object> match1Username = Maps.newHashMap();
        match1Username.put("username", "李白");
        match1.put("match", match1Username);

        HashMap<String, Object> match2 = Maps.newHashMap();
        HashMap<String, Object> match2price = Maps.newHashMap();
        match2price.put("price", 22.22);
        match2.put("match", match2price);

        HashMap<String, Object> bool = Maps.newHashMap();
        bool.put("should", Lists.newArrayList(match1, match2));

        HashMap<String, Object> query = Maps.newHashMap();
        query.put("bool", bool);

        HashMap<String, Object> condition = Maps.newHashMap();
        condition.put("query", query);


        log.info("the params of request what {}", JSONUtil.toJsonStr(condition));

        Request request = new Request("GET", "/user_index/_search");
        request.setJsonEntity(JSONUtil.toJsonStr(condition));

        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
        log.info("DocumentQueryService.anyConditionSearch entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 范围查询
     * <p>
     *      GET /索引/_search
     *     {
     *     "query":{
     *         "bool":{
     *           	// 表示范围查询
     *             "filter":{
     *                 "range":{
     *                   	// 属性名
     *                     "price":{
     *                         "gt":1
     *                     }
     *                 }
     *             },
     *             "should":[
     *                 {
     *                     "match":{
     *                         "username":"李白"
     *                     }
     *                 },
     *                 {
     *                     "match":{
     *                         "price":22.22
     *                     }
     *                 }
     *             ]
     *         }
     *     }
     * }
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void anyConditionAndRangeSearch(RestClient restClient) throws IOException {
        HashMap<String, Object> match1 = Maps.newHashMap();
        HashMap<String, Object> match1Username = Maps.newHashMap();
        match1Username.put("username", "李白");
        match1.put("match", match1Username);

        HashMap<String, Object> match2 = Maps.newHashMap();
        HashMap<String, Object> match2price = Maps.newHashMap();
        match2price.put("price", 22.22);
        match2.put("match", match2price);

        HashMap<String, Object> filter = Maps.newHashMap();
        HashMap<String, Object> range = Maps.newHashMap();
        HashMap<String, Object> price = Maps.newHashMap();
        price.put("gt", 1);
        range.put("price", price);
        filter.put("range", range);

        HashMap<String, Object> bool = Maps.newHashMap();
        bool.put("should", Lists.newArrayList(match1, match2));
        bool.put("filter", filter);

        HashMap<String, Object> query = Maps.newHashMap();
        query.put("bool", bool);

        HashMap<String, Object> condition = Maps.newHashMap();
        condition.put("query", query);

        log.info("the params of request what {}", JSONUtil.toJsonStr(condition));

        Request request = new Request("GET", "/user_index/_search");
        request.setJsonEntity(JSONUtil.toJsonStr(condition));

        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
        log.info("DocumentQueryService.anyConditionAndRangeSearch entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 全文搜索，分词查询
     * <p>
     *     GET /索引/_search
     * {
     *     "query":{
     *         "match":{
     *             "username":"李太白"
     *         }
     *     }
     * }
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void fullSearch(RestClient restClient) throws IOException {
        HashMap<String, Object> match = Maps.newHashMap();
        match.put("username", "李太白");

        HashMap<String, Object> query = Maps.newHashMap();
        query.put("match", match);

        HashMap<String, Object> condition = Maps.newHashMap();
        condition.put("query", query);

        log.info("the params of request what {}", JSONUtil.toJsonStr(condition));

        Request request = new Request("GET", "/user_index/_search");
        request.setJsonEntity(JSONUtil.toJsonStr(condition));

        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
        log.info("DocumentQueryService.fullSearch entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 全文搜索，不分词查询
     * <p>
     *     GET /索引/_search
     * {
     *     "query":{
     *         "match_phrase":{
     *             "username":"李太白"
     *         }
     *     }
     * }
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void fullSearchNo(RestClient restClient) throws IOException {
        HashMap<String, Object> match = Maps.newHashMap();
        match.put("username", "李太白");

        HashMap<String, Object> query = Maps.newHashMap();
        query.put("match_phrase", match);

        HashMap<String, Object> condition = Maps.newHashMap();
        condition.put("query", query);

        log.info("the params of request what {}", JSONUtil.toJsonStr(condition));

        Request request = new Request("GET", "/user_index/_search");
        request.setJsonEntity(JSONUtil.toJsonStr(condition));

        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
        log.info("DocumentQueryService.fullSearchNo entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 全文搜索，高亮显示
     * <p>
     *     GET /索引/_search
     * {
     *   	// 高亮显示
     *     "highlight":{
     *         "fields":{
     *             "username":{
     *
     *             }
     *         }
     *     },
     *     "query":{
     *         "match":{
     *             "username":"李太白"
     *         }
     *     }
     * }
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void fullSearchAndHighlight(RestClient restClient) throws IOException {
        HashMap<String, Object> match = Maps.newHashMap();
        match.put("username", "李太白");

        HashMap<String, Object> query = Maps.newHashMap();
        query.put("match", match);

        HashMap<String, Object> highlight = Maps.newHashMap();
        HashMap<String, Object> fields = Maps.newHashMap();
        fields.put("username", new HashMap<>());
        highlight.put("fields", fields);

        HashMap<String, Object> condition = Maps.newHashMap();
        condition.put("query", query);
        condition.put("highlight", highlight);

        log.info("the params of request what {}", JSONUtil.toJsonStr(condition));

        Request request = new Request("GET", "/user_index/_search");
        request.setJsonEntity(JSONUtil.toJsonStr(condition));

        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
        log.info("DocumentQueryService.fullSearchAndHighlight entity is {}", JSONUtil.toJsonStr(entityStr));
    }


    /**
     * 聚合分组
     * <p>
     *     GET /索引/_search
     * {
     *     "size":0, //表示无需返回原始数据，只需要返回结果
     *     "aggs":{ // 表示聚合操作
     *         "price_group":{ // 名称，自定义命名
     *             "terms":{ // 分组
     *                 "field":"price" // 分组字段
     *             }
     *         }
     *     }
     * }
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void aggregation4Group(RestClient restClient) throws IOException {
        HashMap<String, Object> terms = Maps.newHashMap();
        terms.put("field", "price");

        HashMap<String, Object> price_group = Maps.newHashMap();
        price_group.put("terms", terms);

        HashMap<String, Object> aggs = Maps.newHashMap();
        aggs.put("price_group", price_group);

        HashMap<String, Object> condition = Maps.newHashMap();
        condition.put("aggs", aggs);
        condition.put("size", 0);

        log.info("the params of request what {}", JSONUtil.toJsonStr(condition));

        Request request = new Request("GET", "/user_index/_search");
        request.setJsonEntity(JSONUtil.toJsonStr(condition));

        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
        log.info("DocumentQueryService.aggregation4Group entity is {}", JSONUtil.toJsonStr(entityStr));
    }

    /**
     * 聚合求平均数
     * <p>
     *     GET /索引/_search
     * {
     *     "size":0, //表示无需返回原始数据，只需要返回结果
     *     "aggs":{ // 表示聚合操作
     *         "price_avg":{ / 名称，自定义命名
     *             "avg":{ // 求平均数
     *                 "field":"price" // 平均值字段
     *             }
     *         }
     *     }
     * }
     * </p>
     * @param restClient
     * @throws IOException
     */
    public void aggregation4Avg(RestClient restClient) throws IOException {
        HashMap<String, Object> avg = Maps.newHashMap();
        avg.put("field", "price");

        HashMap<String, Object> price_avg = Maps.newHashMap();
        price_avg.put("avg", avg);

        HashMap<String, Object> aggs = Maps.newHashMap();
        aggs.put("price_avg", price_avg);

        HashMap<String, Object> condition = Maps.newHashMap();
        condition.put("aggs", aggs);
        condition.put("size", 0);

        log.info("the params of request what {}", JSONUtil.toJsonStr(condition));

        Request request = new Request("GET", "/user_index/_search");
        request.setJsonEntity(JSONUtil.toJsonStr(condition));

        Response response = restClient.performRequest(request);
        String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
        log.info("DocumentQueryService.aggregation4Avg entity is {}", JSONUtil.toJsonStr(entityStr));
    }
}
