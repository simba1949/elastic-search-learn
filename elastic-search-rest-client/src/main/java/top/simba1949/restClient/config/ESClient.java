package top.simba1949.restClient.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import top.simba1949.common.constant.ESConstant;

/**
 * @author anthony
 * @version 2023/9/16 11:28
 */
public class ESClient {

	public static RestClient initClient() {
		// Create the low-level client
		return RestClient.builder(new HttpHost(ESConstant.IP, ESConstant.PORT)).build();
	}
}