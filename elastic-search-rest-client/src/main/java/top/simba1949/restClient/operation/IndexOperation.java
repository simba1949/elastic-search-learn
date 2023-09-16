package top.simba1949.restClient.operation;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;
import top.simba1949.restClient.config.ESClient;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author anthony
 * @version 2023/9/16 11:45
 */
@Slf4j
public class IndexOperation {

	public static void main(String[] args) throws Exception {
		RestClient restClient = ESClient.initClient();

		// syncPut(restClient);
		// asyncPut(restClient);
		// getIndex(restClient);
		// getAllIndex(restClient);
		// deleteIndex(restClient);
		// deleteMultiIndex(restClient);
		deleteAllIndex(restClient);

		restClient.close();
	}


	/**
	 * 新增索引
	 * 同步新增索引：<url href="https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/java-rest-low-usage-requests.html"></url>
	 *
	 * <p>
	 * PUT index_user
	 * </p>
	 *
	 * @param restClient
	 * @throws IOException
	 */
	public static void syncPut(RestClient restClient) throws IOException {
		Request request = new Request("PUT", "index_user");

		Response response = restClient.performRequest(request);
		String entityStr = EntityUtils.toString(response.getEntity());
		log.info("新增索引，响应结果：{}", entityStr);
	}

	/**
	 * 新增索引
	 * 异步新增索引
	 * <p>
	 * PUT index_user
	 * </p>
	 *
	 * @param restClient
	 */
	public static void asyncPut(RestClient restClient) throws InterruptedException {
		Request request = new Request("PUT", "index_user");

		restClient.performRequestAsync(request, new ResponseListener() {
			@Override
			public void onSuccess(Response response) {
				try {
					String entityStr = EntityUtils.toString(response.getEntity());
					log.info("新增索引，响应结果：{}", entityStr);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

			@Override
			public void onFailure(Exception e) {
				log.info("新增索引的失败，异常信息：{}", e.getMessage(), e);
			}
		});

		// 防止进程结束，无法收到响应结果
		new CountDownLatch(1).wait();
	}

	/**
	 * 获取单个索引
	 * <p>
	 * GET index_user
	 * </p>
	 *
	 * @param restClient
	 */
	public static void getIndex(RestClient restClient) throws IOException {
		Request request = new Request("GET", "index_user");

		Response response = restClient.performRequest(request);
		String entityStr = EntityUtils.toString(response.getEntity());
		log.info("index_user 索引信息，响应结果： {}", entityStr);
	}

	/**
	 * 获取所有索引信息
	 *
	 * <p>
	 * GET _cat/indices?v=true&pretty
	 * </p>
	 *
	 * @param restClient
	 * @throws IOException
	 */
	public static void getAllIndex(RestClient restClient) throws IOException {
		Request request = new Request("GET", "_cat/indices?v=true&pretty");
		Response response = restClient.performRequest(request);
		String entityStr = EntityUtils.toString(response.getEntity());
		log.info("获取所有索引信息，响应结果：{}", entityStr);
	}

	/**
	 * 删除单个索引
	 * <p>
	 * DELETE index_user
	 * </p>
	 *
	 * @param restClient
	 * @throws IOException
	 */
	public static void deleteIndex(RestClient restClient) throws IOException {
		Request request = new Request("DELETE", "index_user");
		Response response = restClient.performRequest(request);
		String entityStr = EntityUtils.toString(response.getEntity());
		log.info("删除单个索引，响应结果：{}", entityStr);
	}

	/**
	 * 删除多个索引（有一个不存在，则不会删除）
	 * <p>
	 * DELETE role_index,permission_index
	 * </p>
	 *
	 * @param restClient
	 * @throws IOException
	 */
	public static void deleteMultiIndex(RestClient restClient) throws IOException {
		Request request = new Request("DELETE", "index_role, index_permission");
		Response response = restClient.performRequest(request);
		String entityStr = EntityUtils.toString(response.getEntity());
		log.info("删除多个索引，响应结果：{}", entityStr);
	}

	/**
	 * 删除匹配的索引
	 * <p>
	 * DELETE index_*
	 * </p>
	 * <p>
	 * 如果出现【Wildcard expressions or all indices are not allowed】，
	 * 需要将 elasticsearch.yml 中 action.destructive_requires_name 设置为 false 并重启 ElasticSearch.
	 * </p>
	 *
	 * @param restClient
	 * @throws IOException
	 */
	public static void deleteRegexIndex(RestClient restClient) throws IOException {
		Request request = new Request("DELETE", "index_*");
		Response response = restClient.performRequest(request);
		String entityStr = EntityUtils.toString(response.getEntity());
		log.info("删除正则表达式匹配的索引，响应结果：{}", entityStr);
	}

	/**
	 * 删除所有的索引
	 * <p>
	 * DELETE _all
	 * </p>
	 *
	 * @param restClient
	 * @throws IOException
	 */
	public static void deleteAllIndex(RestClient restClient) throws IOException {
		Request request = new Request("DELETE", "_all");
		Response response = restClient.performRequest(request);
		String entityStr = EntityUtils.toString(response.getEntity());
		log.info("删除所有索引，响应结果：{}", entityStr);
	}
}