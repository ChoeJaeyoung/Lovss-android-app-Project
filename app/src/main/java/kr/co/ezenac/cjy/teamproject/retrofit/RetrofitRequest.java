package kr.co.ezenac.cjy.teamproject.retrofit;

import kr.co.ezenac.cjy.teamproject.model.Member;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018-02-01.
 */

public interface RetrofitRequest {
    @GET("login")
    Call<Member> login(@Query("member_id") String member_id, @Query("pw") String pw);

    @GET("insert_become_a_member")
    Call<Integer> join(@Query("member_id") String member_id, @Query("pw") String pw);

    /*@GET("")
    Call<ArrayList<Room>> profileRoomInfo(@Query("id") Integer id);*/

    @GET("profile")
    Call<Member> callMemberInfo(@Query("id") Integer id);
}
