package kr.co.ezenac.cjy.teamproject.singletone;

import java.util.HashMap;

import kr.co.ezenac.cjy.teamproject.model.Collect;

/**
 * Created by Administrator on 2018-02-22.
 */

public class CollectHashMap {
    private static CollectHashMap curr = null;
    private HashMap<Integer, Collect> collect;

    public static CollectHashMap getInstance(){
        if (curr == null){
            curr = new CollectHashMap();
        }
        return curr;
    }

    private CollectHashMap(){
    }

    public HashMap<Integer, Collect> getCollect() {
        return collect;
    }

    public void setCollect(HashMap<Integer, Collect> collect) {
        this.collect = collect;
    }
}
