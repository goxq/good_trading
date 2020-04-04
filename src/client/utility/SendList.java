/*
因为List不能序列化通过socket传，所以建一个中间类传List<Commodity>
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
