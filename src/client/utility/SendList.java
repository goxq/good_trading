/*
��ΪList�������л�ͨ��socket�������Խ�һ���м��ഫList<Commodity>
 */
package client.utility;


import client.entity.Commodity;
import java.io.*;
import java.util.List;

public class SendList implements Serializable {

    private List<Commodity> sendlist;

    public List<Commodity> getSendlist() {
        return sendlist;
    }

    public void setSendlist(List<Commodity> sendlist) {
        this.sendlist = sendlist;
    }
}
