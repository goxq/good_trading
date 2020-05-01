/*
用来存取auction详情的商品
 */
package client.utils;


import common.entity.Commodity;

import java.util.HashMap;

public class ManageCommodity {
    private static HashMap<String, Commodity> hm = new HashMap<>();
    public static void addCommodity(String commodityID,Commodity commodity){
        hm.put(commodityID,commodity);
    }
    public static Commodity getCommodity(String commodityID){
        return (Commodity)hm.get(commodityID);
    }
}
