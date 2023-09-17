package top.simba1949.springBoot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

/**
 * <div>
 *     配置此类，可以自动注入
 *     1. ElasticsearchOperations，默认实现 ElasticsearchTemplate
 *     2. ElasticsearchClient，相当于使用 elasticsearch-java 依赖的 API
 *     3. RestClient，相当于使用 elasticsearch-rest-client 依赖的 API
 * </div>
 *
 * @author anthony
 * @version 2023/9/16 14:56
 */
@Configuration
public class ESImperativeRestClientConfig extends ElasticsearchConfiguration {

	@Value("${spring.elasticsearch.uris}")
	private String esUrl;

	@Override
	public ClientConfiguration clientConfiguration() {
		return ClientConfiguration.builder()
				.connectedTo(esUrl)
				.build();
	}
}