package top.simba1949.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestHighLevelClientBuilder;
import top.simba1949.common.constant.ESConstant;

/**
 * @author anthony
 * @version 2023/9/16 10:57
 */
public class ESRestHighLevelClient {

	public static ElasticsearchClient initClient() {
		// Create the low-level client
		RestClient restClient = RestClient.builder(new HttpHost(ESConstant.SERVER_URL)).build();

		// Create the High Level Rest Client (HLRC)
		RestHighLevelClient hlrc = new RestHighLevelClientBuilder(restClient)
				.setApiCompatibilityMode(true)
				.build();

		// Create the Java API Client with the same low level client
		ElasticsearchTransport transport = new RestClientTransport(
				restClient,
				new JacksonJsonpMapper()
		);

		return new ElasticsearchClient(transport);
	}
}