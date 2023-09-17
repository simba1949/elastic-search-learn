package top.simba1949.springBoot.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <div>
 *     @Document(indexName = "user_index", createIndex = true)
 *     1. 配置 ES 索引名，
 *     2. createIndex = true 是否创建索引，默认为 true，还需要定义 UserESDO 的 Repository `createIndex = true` 才会生效
 * </div>
 *
 * @author anthony
 * @version 2023/9/16 14:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "user_index", createIndex = true)
public class UserESDO implements Serializable {
	@Serial
	private static final long serialVersionUID = -8424567352483833634L;

	@Id
	private Long id; // id

	@Field(type = FieldType.Text, index = true)
	private String username; // 用户名

	@Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second})
	@JsonFormat(pattern = "uuuu-MM-dd'T'HH:mm:ss")
	private LocalDateTime birthday; // 生日

	@Field(type = FieldType.Text, index = true)
	private String intro; // 简介
}