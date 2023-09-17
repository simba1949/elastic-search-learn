package top.simba1949.springBoot.operation;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.CriteriaQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import top.simba1949.springBoot.ESSpringBootApplication;
import top.simba1949.springBoot.domain.UserESDO;

import java.util.List;

/**
 * @author anthony
 * @version 2023/9/17 11:00
 */
@Slf4j
@SpringBootTest(classes = ESSpringBootApplication.class)
public class DocumentFullQueryTest {

	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;

	@Test
	@DisplayName(value = "查询所有文档，（默认 from=0,size=10）")
	public void matchAll() {
		// region helper methods
		Query query = elasticsearchTemplate.matchAllQuery();

		SearchHits<UserESDO> search = elasticsearchTemplate.search(query, UserESDO.class);
		if (search.hasSearchHits()) {
			List<SearchHit<UserESDO>> searchHits = search.getSearchHits();
			log.info("查询到数据的数量是：{}", searchHits.size());

			searchHits.forEach(searchHit -> {
				UserESDO content = searchHit.getContent();
				log.info("{}", content);
			});
		}
	}

	@Test
	@DisplayName(value = "通过 NativeQuery 查询")
	public void nativeQuery() {
		NativeQuery nativeQuery = new NativeQueryBuilder()
				.withQuery(
						QueryBuilders.match(
								matchBuild ->
										matchBuild.field("username").query("开发者")))
				.withPageable(PageRequest.of(1, 1))
				.withSort(
						SortOptions.of(
								sortBuild -> sortBuild.field(
										fieldBuild ->
												// 字段支持排序
												fieldBuild.field("id").order(SortOrder.Desc))))
				.build();

		SearchHits<UserESDO> searchHits = elasticsearchTemplate.search(nativeQuery, UserESDO.class);
		for (SearchHit<UserESDO> searchHit : searchHits.getSearchHits()) {
			UserESDO content = searchHit.getContent();
			log.info("{}", content);
		}
	}

	@Test
	@DisplayName(value = "通过 CriteriaQuery 查询")
	public void criteriaQuery() {
//		Criteria criteria = new Criteria()
//				.and(new Criteria("username").is("开发者"))
//				.and(new Criteria("intro").is("17"));

		// 和上面语法一致
		Criteria criteria = new Criteria()
				.and("username").is("开发者")
				.and("intro").is("17");
		CriteriaQuery criteriaQuery = new CriteriaQueryBuilder(criteria).build();

		SearchHits<UserESDO> searchHits = elasticsearchTemplate.search(criteriaQuery, UserESDO.class);
		for (SearchHit<UserESDO> searchHit : searchHits.getSearchHits()) {
			UserESDO content = searchHit.getContent();
			log.info("{}", content);
		}
	}
}