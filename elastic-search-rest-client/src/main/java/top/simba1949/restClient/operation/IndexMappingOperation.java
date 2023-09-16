package top.simba1949.restClient.operation;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;
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
 * @version 2023/9/16 13:41
 */
@Slf4j
public class IndexMappingOperation {
	public static void main(String[] args) throws IOException {
		RestClient restClient = ESClient.initClient();

		putMapping(restClient);
	}

	/**
	 * 映射
	 * <p>
	 * PUT index_role/_mapping
	 * {
	 * "properties":{
	 * "username":{"type":"text","index":true}
	 * "age":{"type":"long","index":false}
	 * "sex":{"type":"keyword","index":false}
	 * "price":{"type":"double","index":false}
	 * }
	 * }
	 * </p>
	 *
	 * @param restClient
	 * @throws IOException
	 */
	public static void putMapping(RestClient restClient) throws IOException {
		HashMap<String, Object> username = Maps.newHashMap();
		username.put("type", "text");
		username.put("index", true);

		HashMap<String, Object> age = Maps.newHashMap();
		age.put("type", "long");
		age.put("index", false);

		HashMap<String, Object> sex = Maps.newHashMap();
		sex.put("type", "keyword");
		sex.put("index", false);

		HashMap<String, Object> price = Maps.newHashMap();
		price.put("type", "double");
		price.put("index", false);

		HashMap<String, Object> properties = Maps.newHashMap();
		properties.put("username", username);
		properties.put("age", age);
		properties.put("sex", sex);
		properties.put("price", price);

		HashMap<String, Object> requestBody = Maps.newHashMap();
		requestBody.put("properties", properties);

		log.info("the request params is {}", JSONObject.toJSONString(requestBody));

		Request request = new Request("PUT", "index_role/_mapping");
		request.setJsonEntity(JSONObject.toJSONString(requestBody));

		Response response = restClient.performRequest(request);
		String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
		log.info("DocumentService.getDocument entity is {}", entityStr);
	}
}