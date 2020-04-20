package client.utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * Title: 生成UUID
 */
public class CodecUtil {

    private static int number;//唯一数字,集群第一台=0，第二台=200000,第三台=400000
    private static int maxNum=200000;//最大值,集群第一台=200000，第二台=400000,第三台=600000
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");//年月日格式

    /**
     * uuid生成没有 — 的编号
     * @return
     */
    public static String createUUID(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("\\-", "");
    }

    /**
     * 生成订单编号 17+ 位数
     *  思路:一个业务在1毫秒内并发的数量有多少，有一万那真是顶天了,意味着 一秒 有1000万的并发。kafaka,redis的性能不过10万，那我把 number的上限设置成20 万,那就是一秒200000*1000=2亿的并发,不够再集群
     *  宕机了，进程死了，这个跟程序没有关系。你别忘了前面精确到毫秒的17位数，我们只关心一毫秒内的并发问题
     * @return
     */
    public static String createOrderId(){
        number++;//唯一数字自增
        if(number>=maxNum){ // 值的上限，超过就归零
            number=maxNum-200000;
        }
        return sdf.format(new Date())+number;//返回时间+一毫秒内唯一数字的编号，区分机器可以加字母ABC...
    }

}