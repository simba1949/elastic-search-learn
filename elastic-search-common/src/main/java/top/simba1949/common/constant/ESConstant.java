package top.simba1949.common.constant;

/**
 * @author anthony
 * @version 2023/9/5
 */
public interface ESConstant {
    String SCHEME = "http";
    String IP = "192.168.8.17";
    int PORT = 9200;

    String SERVER_URL = SCHEME + "://" + IP + ":" + PORT;
}
