package top.simba1949.restClient.operation;

import com.alibaba.fastjson2.JSONObject;
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
		asyncPut(restClient);

		restClient.close();
	}


	/**
	 * 新增索引
	 * 同步新增索引：<url href="https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/java-rest-low-usage-requests.html"></url>
	 *
	 * @param restClient
	 * @throws IOException
	 */
	public static void syncPut(RestClient restClient) throws IOException {
		Request request = new Request("PUT", "index_user");

		Response response = restClient.performRequest(request);
		String entityStr = EntityUtils.toString(response.getEntity());
		log.info("索引操作：新增索引的响应是：{}", entityStr);
	}

	/**
	 * 新增索引
	 * 异步新增索引
	 *
	 * @param restClient
	 */
	public static void asyncPut(RestClient restClient) throws InterruptedException {
		Request request = new Request("PUT", "index_user");

		restClient.performRequestAsync(request, new ResponseListener() {
			@Override
			public void onSuccess(Response response) {
				log.info("索引操作：新增索引的成功：{}", JSONObject.toJSONString(response));

				try {
					String entityStr = EntityUtils.toString(response.getEntity());
					log.info("索引操作：新增索引的响应是：{}", entityStr);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

			@Override
			public void onFailure(Exception e) {
				log.info("索引操作：新增索引的失败，异常信息：{}", e.getMessage(), e);
			}
		});

		// 防止进程结束，无法收到响应结果
		new CountDownLatch(1).wait();
	}
}