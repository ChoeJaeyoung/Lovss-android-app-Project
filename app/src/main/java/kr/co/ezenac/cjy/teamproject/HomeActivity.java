package kr.co.ezenac.cjy.teamproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.ezenac.cjy.teamproject.adapter.Home_adapter;
import kr.co.ezenac.cjy.teamproject.adapter.InfiniteScrollAdapter;
import kr.co.ezenac.cjy.teamproject.model.Main;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends Activity implements InfiniteScrollAdapter.InfiniteScrollListener {
    @BindView(R.id.grid_home_gv) GridView grid_home_gv;
    Home_adapter homeAdapter;
    @BindView(R.id.img_home) ImageView img_home;
    @BindView(R.id.img_search) ImageView img_search;
    @BindView(R.id.img_input) ImageView img_input;
    @BindView(R.id.img_option) ImageView img_option;
    @BindView(R.id.linearLayout_home) LinearLayout linearLayout_home;

    private static final int GRID_ITEM_HEIGHT = 128;
    private static final int GRID_ITEM_WIDTH = 128;
    private GridView mGridView;
    private InfiniteScrollAdapter<Home_adapter> mAdapter;
    private Handler mHandler;
    Integer tmpId;
    Integer count = 1;
    Context context;
    ArrayList<Main> imgs2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        tmpId = LoginInfo.getInstance().getMember().getId();
        callHomeImg(count,tmpId);

        mGridView = (GridView)findViewById(R.id.grid_home_gv);
        mAdapter = new InfiniteScrollAdapter<Home_adapter>(HomeActivity.this,
                new Home_adapter(imgs2,HomeActivity.this), GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT);
        mAdapter.addListener(HomeActivity.this);
        mGridView.setAdapter(mAdapter);

        mHandler = new Handler();

    }

    public void callHomeImg(Integer count, Integer member_id){
        Call<ArrayList<Main>> observ = RetrofitService.getInstance().getRetrofitRequest()
                .callMain(count,member_id);
        observ.enqueue(new Callback<ArrayList<Main>>() {
            @Override
            public void onResponse(Call<ArrayList<Main>> call, Response<ArrayList<Main>> response) {
                if (response.isSuccessful()){
                    ArrayList<Main> imgs = response.body();
                    imgs2.addAll(imgs);
                    Log.d("jjj","qwe"+ imgs);
                    Log.d("jjj","qwe"+ imgs2);


                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Main>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @OnClick(R.id.img_input)
    public void onClickChange(View view){
        initDialog();
    }

    private  void  initDialog(){
        ChooseDialog dialog = new ChooseDialog(this, new ChooseDialog.ChooseListener() {
            @Override
            public void choosePhoto() {

                Intent intent = new Intent(HomeActivity.this,upload_Activity.class);
                startActivity(intent);
                Log.d("bjh","re: " + 3);
            }

            @Override
            public void chooseCamer() {
                Intent intent = new Intent(HomeActivity.this,upload_btn_photo_activity.class);
                startActivity(intent);
                Log.d("bjh","re: " + 55);
            }
        });
        dialog.show();
    }
    @OnClick(R.id.img_search)
    public void onReturnSearch(View view) {
        Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.img_option)
    public void onReturnOption(View view) {
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.linearLayout_home)
    public void onClickMain(View view){

    }

    @Override
    public void onInfiniteScrolled() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callHomeImg(count,tmpId);
                count++;
              mAdapter.getAdapter().addCount(0); // ★★★증감되서 나오는 갯수
                mAdapter.handledRefresh();
                // when the adapter load more then 100 items. i will disable the
                // feature of load more.
                if (mAdapter.getOriginalAdapter().getCount() > 100) { //★★★나오는 최대갯수
                    mAdapter.canReadMore(false);
                }
            }
        }, 1000); //나오는 시간딜레이
    }
}