package kr.co.ezenac.cjy.teamproject.retrofit;

import java.util.ArrayList;

import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.model.Member;
import kr.co.ezenac.cjy.teamproject.model.Room;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018-02-01.
 */

public interface RetrofitRequest {
    @GET("login")
    Call<Member> login(@Query("login_id") String member_id, @Query("pw") String pw);

    @GET("insert_become_a_member")
    Call<Integer> join(@Query("login_id") String member_id, @Query("pw") String pw);

    @GET("profile")
    Call<ArrayList<Room>> profileRoomInfo(@Query("id") Integer id);

    @GET("")
    Call<ArrayList<Img>> callRoomInfo(@Query("id") Integer id);
}
