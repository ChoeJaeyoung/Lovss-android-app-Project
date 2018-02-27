package kr.co.ezenac.cjy.teamproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.ezenac.cjy.teamproject.adapter.CollectDetail_adapter;
import kr.co.ezenac.cjy.teamproject.adapter.Detail_adapter;
import kr.co.ezenac.cjy.teamproject.db.DBManager;
import kr.co.ezenac.cjy.teamproject.model.Collect;
import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.CollectHashMap;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;
import kr.co.ezenac.cjy.teamproject.singletone.RoomInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.btn_detail_addPhoto) Button btn_detail_addPhoto;
    @BindView(R.id.grid_detail_gv) GridView grid_detail_gv;
    @BindView(R.id.btn_logout) ImageView btn_logout;
    @BindView(R.id.img_home) ImageView img_home;
    @BindView(R.id.img_search) ImageView img_search;
    @BindView(R.id.img_input) ImageView img_input;
    @BindView(R.id.img_option) ImageView img_option;
    @BindView(R.id.linearLayout_detail) LinearLayout linearLayout_detail;


    Detail_adapter detailAdapter;
    Integer dRoom_id;
    HashMap<Integer, Collect> hashMap;
    DBManager dbManager;
    ArrayList<Collect> collects = new ArrayList<>();
    CollectDetail_adapter collectDetailAdapter;
    Integer selPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Integer typeRoom = intent.getIntExtra("type",0);
        selPosition = intent.getIntExtra("position",0);
        hashMap = CollectHashMap.getInstance().getCollect();

        if (typeRoom == 1){
            dRoom_id = RoomInfo.getInstance().getRoom().getId();
            String dRoom_name = RoomInfo.getInstance().getRoom().getName();
            String dRoom_img = RoomInfo.getInstance().getRoom().getRoom_img();

            Log.d("room", "room_info : " + dRoom_name + " / " + dRoom_img +" / "+ dRoom_id);
            callImgDetail(selPosition, dRoom_id);
        } else if (typeRoom == 2){
            collectionDetail(selPosition);
        }
    }


    @OnClick(R.id.btn_detail_addPhoto)
    public void onClickUploadImg(View view){
        Intent intent = new Intent(DetailActivity.this, upload_imgActivity.class);
        intent.putExtra("room_id", dRoom_id);
        Log.d("kkk","room_id" + dRoom_id);
        startActivity(intent);
    }

    public void collectionDetail(Integer cPosition){
        dbManager = new DBManager(DetailActivity.this, "Collect.db", null, 1);
        Integer tmpId = LoginInfo.getInstance().getMember().getId();
        collects = dbManager.getCollectList(tmpId);
        collectDetailAdapter = new CollectDetail_adapter(collects, DetailActivity.this);
        grid_detail_gv.setAdapter(collectDetailAdapter);
        grid_detail_gv.setSelection(cPosition);

    }

    public void callImgDetail(final Integer dPosition, Integer dRoom_id){
        Call<ArrayList<Img>> obser = RetrofitService.getInstance().getRetrofitRequest()
                .callRoomImg(dRoom_id);
        obser.enqueue(new Callback<ArrayList<Img>>() {
            @Override
            public void onResponse(Call<ArrayList<Img>> call, Response<ArrayList<Img>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Img> imgs = response.body();
                    Log.d("ddd", imgs.toString());
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
    @OnClick(R.id.btn_logout)
    public void onClickLogout(View view){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailActivity.this);
        alertDialog.setTitle("경고");
        alertDialog.setMessage("로그아웃 하시겠습니까?");
        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(DetailActivity.this, LoginActivity.class);
                startActivity(intent);
            }

        });
        alertDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }
}
