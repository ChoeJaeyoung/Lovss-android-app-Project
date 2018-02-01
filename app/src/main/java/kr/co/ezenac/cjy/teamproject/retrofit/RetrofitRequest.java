package kr.co.ezenac.cjy.teamproject.retrofit;

import kr.co.ezenac.cjy.teamproject.model.Member;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018-02-01.
 */

public interface RetrofitRequest {
    @GET("")
    Call<Member> login(@Query("member_id") String member_id, @Query("pw") String pw);

    @GET("insert_become_a_member")
    Call<Integer> join(@Query("member_id") String member_id, @Query("pw") String pw);
}
