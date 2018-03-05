package kr.co.ezenac.cjy.teamproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.ezenac.cjy.teamproject.adapter.Profile_adapter;
import kr.co.ezenac.cjy.teamproject.adapter.Room_adapter;
import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.model.Room;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;
import kr.co.ezenac.cjy.teamproject.singletone.RoomInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class upload_btn_photo_activity extends AppCompatActivity {

    Room_adapter roomAdapter;
    @BindView(R.id.grid_room_gv1) GridView grid_room_gv1;
    Profile_adapter profileAdapter;
    @BindView(R.id.img_room_home1) ImageView img_room_home1;
    @BindView(R.id.img_room_search1) ImageView img_room_search1;
    @BindView(R.id.img_room_input1) ImageView img_room_input1;
    @BindView(R.id.img_room_option1) ImageView img_room_option1;
    @BindView(R.id.linearLayout_uploadphoto) LinearLayout linearLayout_uploadphoto;
    @BindView(R.id.btn_logout) ImageView btn_logout;




    Integer room_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);
        ButterKnife.bind(this);
        Integer tmpId = LoginInfo.getInstance().getMember().getId();
        String tmpMember_id = LoginInfo.getInstance().getMember().getLogin_id().toString();
        String tmpMember_img = LoginInfo.getInstance().getMember().getMember_img();

        Intent intent = getIntent();
        room_id = intent.getIntExtra("room_id", 0);

        callLoginInfo(tmpId);


    }
    @OnClick(R.id.img_room_input1)
    public void onClickChange(View view){
        initDialog();
    }

    private  void  initDialog(){
        ChooseDialog dialog = new ChooseDialog(this, new ChooseDialog.ChooseListener() {
            @Override
            public void choosePhoto() {

                Intent intent = new Intent(upload_btn_photo_activity.this,upload_Activity.class);
                startActivity(intent);
                Log.d("bjh","re: " + 3);
            }

            @Override
            public void chooseCamer() {


                Intent intent = new Intent(upload_btn_photo_activity.this,upload_btn_photo_activity.class);

                startActivity(intent);
                Log.d("bjh","re: " + 55);
            }
        });
        dialog.show();
    }
    @OnClick(R.id.img_room_home1)
    public void onReturnHome(View view) {
        Intent intent = new Intent(upload_btn_photo_activity.this, HomeActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.img_room_search1)
    public void onReturnSearch(View view) {
        Intent intent = new Intent(upload_btn_photo_activity.this, SearchActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.img_room_option1)
    public void onReturnOption(View view) {
        Intent intent = new Intent(upload_btn_photo_activity.this, MainActivity.class);
        startActivity(intent);
    }

    public void callLoginInfo(Integer tmp_memberId){
        Call<ArrayList<Room>> observ = RetrofitService.getInstance().getRetrofitRequest().profileRoomInfo(tmp_memberId);
        observ.enqueue(new Callback<ArrayList<Room>>() {
            @Override
            public void onResponse(Call<ArrayList<Room>> call, Response<ArrayList<Room>> response) {
                if (response.isSuccessful()){
                    final ArrayList<Room> items = response.body();

                    profileAdapter = new Profile_adapter(items, upload_btn_photo_activity.this);
                    grid_room_gv1.setAdapter(profileAdapter);
                    for (int i = 0; i < items.size(); i++) {
                        Log.d("profile", "profile : " + items.get(i).toString());
                    }
                    grid_room_gv1.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Room item = items.get(position);

                            Intent intent = new Intent(upload_btn_photo_activity.this, upload_imgActivity.class);
                            intent.putExtra("room_id", item.getId());
                            intent.putExtra("room_name", item.getName());
                            intent.putExtra("room_img", item.getRoom_img());
                            RoomInfo.getInstance().setRoom(item);
                            Log.d("kkk", item.toString());
                            startActivity(intent);
                        }
                    });

                    Log.d("ttt", items.toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Room>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    @OnClick(R.id.linearLayout_uploadphoto)
    public void onClickMain(View view){

    }

    @OnClick(R.id.btn_logout)
    public void onClickLogout(View view){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(upload_btn_photo_activity.this);
        alertDialog.setTitle("경고");
        alertDialog.setMessage("로그아웃 하시겠습니까?");
        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(upload_btn_photo_activity.this, LoginActivity.class);
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
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
