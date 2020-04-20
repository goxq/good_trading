package client.utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * Title: ����UUID
 */
public class CodecUtil {

    private static int number;//Ψһ����,��Ⱥ��һ̨=0���ڶ�̨=200000,����̨=400000
    private static int maxNum=200000;//���ֵ,��Ⱥ��һ̨=200000���ڶ�̨=400000,����̨=600000
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");//�����ո�ʽ

    /**
     * uuid����û�� �� �ı��
     * @return
     */
    public static String createUUID(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("\\-", "");
    }

    /**
     * ���ɶ������ 17+ λ��
     *  ˼·:һ��ҵ����1�����ڲ����������ж��٣���һ�������Ƕ�����,��ζ�� һ�� ��1000��Ĳ�����kafaka,redis�����ܲ���10�����Ұ� number���������ó�20 ��,�Ǿ���һ��200000*1000=2�ڵĲ���,�����ټ�Ⱥ
     *  崻��ˣ��������ˣ����������û�й�ϵ���������ǰ�澫ȷ�������17λ��������ֻ����һ�����ڵĲ�������
     * @return
     */
    public static String createOrderId(){
        number++;//Ψһ��������
        if(number>=maxNum){ // ֵ�����ޣ������͹���
            number=maxNum-200000;
        }
        return sdf.format(new Date())+number;//����ʱ��+һ������Ψһ���ֵı�ţ����ֻ������Լ���ĸABC...
    }

}