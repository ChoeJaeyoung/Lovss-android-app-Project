package kr.co.ezenac.cjy.teamproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.ezenac.cjy.teamproject.adapter.Detail_adapter;
import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.RoomInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.btn_detail_addPhoto) Button btn_detail_addPhoto;
    @BindView(R.id.grid_detail_gv) GridView grid_detail_gv;
    @BindView(R.id.img_detail_back) ImageView img_detail_back;
    @BindView(R.id.img_home) ImageView img_home;
    @BindView(R.id.img_search) ImageView img_search;
    @BindView(R.id.img_input) ImageView img_input;
    @BindView(R.id.img_option) ImageView img_option;
    @BindView(R.id.linearLayout_detail) LinearLayout linearLayout_detail;


    Detail_adapter detailAdapter;
    Integer dRoom_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        dRoom_id = RoomInfo.getInstance().getRoom().getId();
        String dRoom_name = RoomInfo.getInstance().getRoom().getName();
        String dRoom_img = RoomInfo.getInstance().getRoom().getRoom_img();

        Log.d("room", "room_info : " + dRoom_name + " / " + dRoom_img +" / "+ dRoom_id);
        callImgDetail(dRoom_id);
    }

    @OnClick(R.id.img_detail_back)
    public void onClickDetailBack(View view){
        finish();
    }

    @OnClick(R.id.btn_detail_addPhoto)
    public void onClickUploadImg(View view){
        Intent intent = new Intent(DetailActivity.this, upload_imgActivity.class);
        intent.putExtra("room_id", dRoom_id);
        Log.d("kkk","room_id" + dRoom_id);
        startActivity(intent);
    }

    public void callImgDetail(Integer dRoom_id){
        Call<ArrayList<Img>> obser = RetrofitService.getInstance().getRetrofitRequest()
                .callRoomImg(dRoom_id);
        obser.enqueue(new Callback<ArrayList<Img>>() {
            @Override
            public void onResponse(Call<ArrayList<Img>> call, Response<ArrayList<Img>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Img> imgs = response.body();
                    Log.d("ddd", imgs.toString());
                    Intent intent = getIntent();
                    Integer dPosition = intent.getIntExtra("position",0);

                    detailAdapter = new Detail_adapter(imgs, DetailActivity.this);
                    grid_detail_gv.setAdapter(detailAdapter);
                    grid_detail_gv.setSelection(dPosition);
                } else {
                    Log.d("fail","1");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ArrayList<Img>> call, Throwable t) {
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

                Intent intent = new Intent(DetailActivity.this,upload_Activity.class);
                startActivity(intent);
                Log.d("bjh","re: " + 3);
            }

            @Override
            public void chooseCamer() {


                Intent intent = new Intent(DetailActivity.this,upload_btn_photo_activity.class);

                startActivity(intent);
                Log.d("bjh","re: " + 55);
            }
        });
        dialog.show();
    }
    @OnClick(R.id.img_home)
    public void onReturnHome(View view) {
        Intent intent = new Intent(DetailActivity.this, HomeActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.img_search)
    public void onReturnSearch(View view) {
        Intent intent = new Intent(DetailActivity.this, SearchActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.img_option)
    public void onReturnOption(View view) {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.linearLayout_detail)
    public void onClickMain(View view){

    }
}
