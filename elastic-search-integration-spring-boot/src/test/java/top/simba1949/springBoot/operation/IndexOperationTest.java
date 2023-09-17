package top.simba1949.springBoot.operation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.IndexInformation;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import top.simba1949.springBoot.ESSpringBootApplication;
import top.simba1949.springBoot.domain.UserESDO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author anthony
 * @version 2023/9/16 14:11
 */
@Slf4j
@SpringBootTest(classes = ESSpringBootApplication.class)
public class IndexOperationTest {
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Test
	@DisplayName(value = "系统初始化自动创建索引")
	public void initIndex() {
		// @Document(indexName = "user_index", createIndex = true)
		// 1. 配置 ES 索引名，
		// 2. createIndex = true 是否创建索引，默认为 true，还需要定义 UserESDO 的 Repository `createIndex = true` 才会生效

		log.info("索引创建完成");
	}

	@Test
	@DisplayName(value = "创建索引")
	public void createIndex() {
		IndexOperations indexOperations = elasticsearchTemplate.indexOps(UserESDO.class);
		boolean createFlag = indexOperations.create();
		if (createFlag) {
			log.info("索引创建成功");
		} else {
			log.info("索引创建失败");
		}
	}

	@Test
	@DisplayName(value = "删除索引")
	public void deleteIndex() {
		IndexOperations indexOperations = elasticsearchTemplate.indexOps(UserESDO.class);
		boolean existsFlag = indexOperations.exists();
		log.info("索引是否存在，{}", existsFlag);

		boolean deleteFlag = indexOperations.delete();
		if (deleteFlag) {
			log.info("索引删除成功");
		} else {
			log.info("索引删除失败");
		}
	}

	@Test
	@DisplayName(value = "获取索引信息")
	public void infoIndex() {
		IndexOperations indexOperations = elasticsearchTemplate.indexOps(UserESDO.class);
		// 获取当前的索引坐标
		IndexCoordinates indexCoordinates = indexOperations.getIndexCoordinates();
		log.info("获取当前的索引坐标：{}", indexCoordinates);

		// 获取当前索引的 name, settings, mappings, aliases 等信息
		List<IndexInformation> informationList = indexOperations.getInformation();
		informationList.forEach(info -> {
			try {
				log.info("info：{}", new JsonMapper().writeValueAsString(info));
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Test
	@DisplayName(value = "创建索引映射")
	public void createIndexMapping() {
		IndexOperations indexOperations = elasticsearchTemplate.indexOps(UserESDO.class);
		indexOperations.createMapping();

		// 创建映射后，需要新增数据才会创建映射
		UserESDO user = UserESDO.builder().id(1L).username("李白").birthday(LocalDateTime.now()).intro("李白乘舟将欲行").build();
		UserESDO save = elasticsearchTemplate.save(user);
		log.info("新增后在查询的用户信息：{}", save);
	}

	// @AfterEach
	@DisplayName(value = "用于打印索引信息")
	public void afterAll() {
		IndexOperations indexOperations = elasticsearchTemplate.indexOps(UserESDO.class);

		// 获取当前索引的 name, settings, mappings, aliases 等信息
		List<IndexInformation> informationList = indexOperations.getInformation();
		informationList.forEach(info -> {
			try {
				log.info("info：{}", new JsonMapper().writeValueAsString(info));
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		});
	}
}