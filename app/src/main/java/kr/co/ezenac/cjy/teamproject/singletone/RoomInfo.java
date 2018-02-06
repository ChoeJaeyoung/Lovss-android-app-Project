package kr.co.ezenac.cjy.teamproject.singletone;

import kr.co.ezenac.cjy.teamproject.model.Room;

/**
 * Created by Administrator on 2018-02-05.
 */

public class RoomInfo {
    private static RoomInfo curr1 = null;
    private Room room;


    public static RoomInfo getInstance() {
        if (curr1 == null) {
            curr1 = new RoomInfo();
        }
        return curr1;
    }

    private RoomInfo(){
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
