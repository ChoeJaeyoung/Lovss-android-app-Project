package kr.co.ezenac.cjy.teamproject.retrofit;

import java.util.ArrayList;

import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.model.Main;
import kr.co.ezenac.cjy.teamproject.model.Member;
import kr.co.ezenac.cjy.teamproject.model.Room;
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
    @GET("login")
    Call<Member> login(@Query("login_id") String member_id, @Query("pw") String pw);

    @GET("insert_become_a_member")
    Call<Integer> join(@Query("login_id") String member_id, @Query("pw") String pw);

    @GET("profile")
    Call<ArrayList<Room>> profileRoomInfo(@Query("id") Integer id);

    @Multipart
    @POST("updateProfile")
    Call<Member> updateProfile(@Part MultipartBody.Part photo, @Part("id") RequestBody id);

    @Multipart
    @POST("updateRoomImg")
    Call<Room> updateRoom(@Part MultipartBody.Part photo, @Part("id") RequestBody id);

    @GET("call_img")
    Call<ArrayList<Img>> callRoomImg(@Query("room_number") Integer id);

    @Multipart
    @POST("insertRoom")
    Call<Room> makeRoom(@Part MultipartBody.Part photo, @Part("name") RequestBody name, @Part("id") RequestBody id,@Part("passwordBody") RequestBody passwordBody);

    @Multipart
    @POST("insertImg")
    Call<Room> makeImg(@Part MultipartBody.Part photo, @Part("content") RequestBody content, @Part("room_number") RequestBody room_number);

    @GET("call_main")
    Call<ArrayList<Main>> callMain(@Query("count") Integer count, @Query("id") Integer member_id);

    @GET("searchRoom")
    Call<ArrayList<Room>> searchRoom(@Query("name") String name);

    @GET("insertJoin")
    Call<Void> joinRoom(@Query("id") Integer id, @Query("room_id") Integer room_id);

    @GET("deleteJoin")
    Call<Void> deleteRoom(@Query("id") Integer id, @Query("room_id") Integer room_id);

    @GET("checkManager")
    Call<Integer> checkManager(@Query("manager_room") Integer manager_room, @Query("manager_member") Integer manager_member);

    @GET("deleteRoom")
    Call<Void> deleteRoom2(@Query("manager_room") Integer manager_room);

    @GET("deleteImg")
    Call<Void> deleteImg(@Query("img_id") Integer img_id);



}
