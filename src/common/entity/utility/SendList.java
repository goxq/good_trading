package server.utility;/*
��ΪList�������л�ͨ��socket�������Խ�һ���м��ഫList<Commodity>
 */



import server.entity.Commodity;
import java.io.*;
import java.util.List;

public class SendList implements Serializable {

    private static final long serialVersionUID = 1566042699827250704L;
    private List<Commodity> sendlist;
    public List<Commodity> getSendlist() {
        return sendlist;
    }
    public void setSendlist(List<Commodity> sendlist) {
        this.sendlist = sendlist;
    }
}
