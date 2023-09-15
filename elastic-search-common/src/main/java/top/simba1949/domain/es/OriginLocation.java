package top.simba1949.domain.es;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author anthony
 * @version 2023/9/15 15:50
 */
@Data
public class OriginLocation implements Serializable {
    @Serial
    private static final long serialVersionUID = 3124698277314651895L;

    private BigDecimal lat;
    private BigDecimal lon;
}
