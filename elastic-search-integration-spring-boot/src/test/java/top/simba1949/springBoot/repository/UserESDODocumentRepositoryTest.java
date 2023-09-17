package top.simba1949.springBoot.repository;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.simba1949.springBoot.ESSpringBootApplication;
import top.simba1949.springBoot.domain.UserESDO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author anthony
 * @version 2023/9/17 16:36
 */
@Slf4j
@SpringBootTest(classes = ESSpringBootApplication.class)
public class UserESDODocumentRepositoryTest {

	@Resource
	private UserESDODocumentRepository repository;

	@Test
	@DisplayName("保存")
	public void save() {
		UserESDO userESDO = UserESDO.builder().id(20L).username("李白").birthday(LocalDateTime.now()).intro("李白乘舟将欲行").build();
		UserESDO save = repository.save(userESDO);
		log.info("保存后在查询：{}", save);
	}

	@Test
	@DisplayName("根据id查询")
	public void findById() {
		Iterable<UserESDO> all = repository.findAllById(List.of(10L, 11L));
		all.forEach(System.out::println);
	}

	/**
	 * ElasticsearchRepository 可以查询所有、查询
	 */
	@Test
	@DisplayName("查询所有")
	public void query() {
		Iterable<UserESDO> all = repository.findAll();
		all.forEach(System.out::println);
	}

	/**
	 * 可以根据实体、id删除，可以删除所有
	 */
	@Test
	@DisplayName("删除")
	public void delete() {
		repository.deleteById(10L);

		Optional<UserESDO> optional = repository.findById(10L);
		if (optional.isPresent()) {
			log.info("查询成功");
		} else {
			log.info("查询不到");
		}
	}
}