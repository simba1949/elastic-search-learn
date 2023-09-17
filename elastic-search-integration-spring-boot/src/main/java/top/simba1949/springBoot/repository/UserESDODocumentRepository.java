package top.simba1949.springBoot.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import top.simba1949.springBoot.domain.UserESDO;

/**
 * <div>
 *     继承 ElasticsearchRepository 可以实现简易的 CRUD，和分页排序查询
 * </div>
 *
 * @author anthony
 * @version 2023/9/16 14:17
 */
@Repository
public interface UserESDODocumentRepository extends ElasticsearchRepository<UserESDO, Long> {
}