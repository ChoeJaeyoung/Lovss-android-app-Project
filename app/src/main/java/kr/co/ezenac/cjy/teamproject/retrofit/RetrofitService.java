package kr.co.ezenac.cjy.teamproject.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018-02-01.
 */

public class RetrofitService {
    public static RetrofitService curr = null;
    private RetrofitRequest retrofitRequest;

    public static RetrofitService getInstance(){
        if (curr ==null){
            curr = new RetrofitService();
        }
        return curr;
    }

    private RetrofitService(){
        retrofitRequest = init();
    }

    public RetrofitRequest init(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-13-125-186-152.ap-northeast-2.compute.amazonaws.com:8080/TEAM/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(RetrofitRequest.class);
    }

    public RetrofitRequest getRetrofitRequest(){
        return retrofitRequest;
    }
}
