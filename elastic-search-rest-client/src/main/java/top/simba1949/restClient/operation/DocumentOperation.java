package top.simba1949.restClient.operation;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import top.simba1949.restClient.config.ESClient;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author anthony
 * @version 2023/9/16 13:53
 */
@Slf4j
public class DocumentOperation {
	public static void main(String[] args) throws IOException {
		RestClient restClient = ESClient.initClient();

		// insertOrUpdate(restClient);
		// get(restClient);
		delete(restClient);

		restClient.close();
	}

	/**
	 * 新增或者修改文档
	 * <p>
	 * PUT index_user/_doc
	 * {
	 * "username":"李白",
	 * "age": 18,
	 * "price": 9999.99,
	 * "sex":"男"
	 * }
	 * </p>
	 *
	 * @param restClient
	 * @throws IOException
	 */
	public static void insertOrUpdate(RestClient restClient) throws IOException {
		Request request = new Request("POST", "index_user/_doc");

		HashMap<String, Object> requestBody = new HashMap<>();
		requestBody.put("username", "李白");
		requestBody.put("age", 18);
		requestBody.put("price", 9999.99);
		requestBody.put("sex", "男");

		request.setJsonEntity(JSONObject.toJSONString(requestBody));
		Response response = restClient.performRequest(request);
		String entityStr = EntityUtils.toString(response.getEntity());
		log.info("新增或者修改文档，响应结果：{}", entityStr);
	}

	/**
	 * 获取单个文档
	 * <p>
	 * GET index_user/_doc/DreRnIoBnZAKHUU3vE2U
	 * </p>
	 *
	 * @param restClient
	 * @throws IOException
	 */
	public static void get(RestClient restClient) throws IOException {
		Request request = new Request("GET", "index_user/_doc/DreRnIoBnZAKHUU3vE2U");

		Response response = restClient.performRequest(request);
		String entityStr = EntityUtils.toString(response.getEntity());
		log.info("获取单个文档，响应结果：{}", entityStr);
	}

	/**
	 * 删除单个文档
	 * <p>
	 * DELETE index_user/_doc/DreRnIoBnZAKHUU3vE2U
	 * </p>
	 *
	 * @param restClient
	 * @throws IOException
	 */
	public static void delete(RestClient restClient) throws IOException {
		Request request = new Request("DELETE", "index_user/_doc/DreRnIoBnZAKHUU3vE2U");

		Response response = restClient.performRequest(request);
		String entityStr = EntityUtils.toString(response.getEntity());
		log.info("删除单个文档，响应结果：{}", entityStr);
	}
}