package top.simba1949.springBoot.operation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.query.ByQueryResponse;
import top.simba1949.springBoot.ESSpringBootApplication;
import top.simba1949.springBoot.domain.UserESDO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author anthony
 * @version 2023/9/17 10:18
 */
@Slf4j
@SpringBootTest(classes = ESSpringBootApplication.class)
public class DocumentOperationTest {

	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;

	@Test
	@DisplayName(value = "新增/或者更新文档")
	public void save() throws InterruptedException {
		UserESDO user4Insert = UserESDO.builder().id(3L).username("苏轼").birthday(LocalDateTime.now()).intro("老夫聊发少年狂").build();
		UserESDO resultAfterInsertQuery = elasticsearchTemplate.save(user4Insert);
		log.info("保存后，再查询的数据是：{}", resultAfterInsertQuery);

		// 建议断点查看
		Thread.sleep(10000L);

		UserESDO user4Update = UserESDO.builder().id(3L).username("苏轼").birthday(LocalDateTime.now()).intro("左牵黄 右擎苍").build();
		UserESDO resultAfterUpdateQuery = elasticsearchTemplate.save(user4Update);
		log.info("保存后，再查询的数据是：{}", resultAfterUpdateQuery);
	}

	@Test
	@DisplayName(value = "批量新增/或者更新文档")
	public void batchSave() {
		List<UserESDO> userESDOList = Stream.iterate(10, i -> i + 1)
				.limit(10)
				.map(index ->
						UserESDO.builder()
								.id(index.longValue())
								.username("开发者" + index + "号")
								.birthday(LocalDateTime.now())
								.intro("Code changes the world. and changes " + index + " times")
								.build())
				.toList();

		Iterable<UserESDO> userListAfterSave = elasticsearchTemplate.save(userESDOList);
		for (UserESDO userESDO : userListAfterSave) {
			log.info("保存后，再查询的数据是：{}", userESDO);
		}
	}

	@Test
	@DisplayName(value = "删除文档")
	public void delete() throws JsonProcessingException {
		// 根据主键和实体查询数据
		String deleteById = elasticsearchTemplate.delete("10", UserESDO.class);
		log.info("获取删除后的结果：{}", deleteById);

		// 根据实体删除数据
		UserESDO userESDO = UserESDO.builder()
				.id(11L)
				.username("开发者" + 11 + "号")
				.birthday(LocalDateTime.now())
				.intro("Code changes the world. and changes " + 11 + "times")
				.build();
		String deleteByEntity = elasticsearchTemplate.delete(userESDO);
		log.info("获取删除后的结果：{}", deleteByEntity);

		// 根据查询条件删除
		NativeQuery nativeQuery = new NativeQueryBuilder()
				.withQuery(
						queryBuild -> queryBuild.match(
								matchBuild -> matchBuild.field("id").query(12L)
						)
				)
				.build();

		ByQueryResponse deleteByQuery = elasticsearchTemplate.delete(nativeQuery, UserESDO.class);
		log.info("获取删除后的结果：{}", new JsonMapper().writeValueAsString(deleteByQuery));
	}
}