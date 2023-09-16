package top.simba1949.common.domain.es;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author anthony
 * @version 2023/9/15 15:45
 */
@Data
public class Flight implements Serializable {
    @Serial
    private static final long serialVersionUID = 7236170473649189987L;

    private String FlightNum;
    private String DestCountry;
    private String OriginWeather;
    private String OriginCityName;
    private BigDecimal AvgTicketPrice;
    private BigDecimal DistanceMiles;
    private Boolean FlightDelay;
    private String DestWeather;
    private String Dest;
    private String FlightDelayType;
    private String OriginCountry;
    private Integer dayOfWeek;
    private BigDecimal DistanceKilometers;
    private Date timestamp;
    private DestLocation DestLocation;
    private String DestAirportID;
    private String Carrier;
    private BigDecimal Cancelled;
    private BigDecimal FlightTimeMin;
    private String Origin;
    private OriginLocation OriginLocation;
    private String DestRegion;
    private String OriginAirportID;
    private String OriginRegion;
    private String DestCityName;
    private BigDecimal FlightTimeHour;
    private Integer FlightDelayMin;
}
