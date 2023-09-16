package top.simba1949.common.domain.es;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author anthony
 * @version 2023/9/15 15:49
 */
@Data
public class DestLocation implements Serializable {
    @Serial
    private static final long serialVersionUID = 4388005472726109522L;

    private BigDecimal lat;
    private BigDecimal lon;
}
