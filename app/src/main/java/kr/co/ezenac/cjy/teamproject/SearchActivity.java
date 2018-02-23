package kr.co.ezenac.cjy.teamproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.ezenac.cjy.teamproject.adapter.Profile_adapter;
import kr.co.ezenac.cjy.teamproject.adapter.Search_adapter;
import kr.co.ezenac.cjy.teamproject.customview.CustomDialog;
import kr.co.ezenac.cjy.teamproject.model.Room;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;
import kr.co.ezenac.cjy.teamproject.singletone.RoomInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.search_test) EditText search_test;
    @BindView(R.id.btn_searh) Button btn_searh;
    Search_adapter search_adapter;
    @BindView(R.id.grid_picture) GridView grid_picture;
    @BindView(R.id.img_home) ImageView img_home;
    @BindView(R.id.img_search) ImageView img_search;
    @BindView(R.id.img_input) ImageView img_input;
    @BindView(R.id.img_option) ImageView img_option;
    @BindView(R.id.linearLayout_search) LinearLayout linearLayout_search;
    @BindView(R.id.btn_logout) ImageView btn_logout;
    String password ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchpage_layout);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_searh)
    public void onClickJoinOk(View view){
        String name = search_test.getText().toString();
        callLoginInfo(name);
    }


    public void callLoginInfo(String name){
        Call<ArrayList<Room>> observ = RetrofitService.getInstance().getRetrofitRequest().searchRoom(name);
        observ.enqueue(new Callback<ArrayList<Room>>() {
            @Override
            public void onResponse(Call<ArrayList<Room>> call, Response<ArrayList<Room>> response) {
                if (response.isSuccessful()){
                    final ArrayList<Room> items = response.body();

                    search_adapter = new Search_adapter(items, SearchActivity.this);
                    grid_picture.setAdapter(search_adapter);
                    for (int i = 0; i < items.size(); i++) {
                        Log.d("profile", "profile : " + items.get(i).toString());
                    }
                    grid_picture.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Room item = items.get(position);
                            Integer member_id = LoginInfo.getInstance().getMember().getId();
                            Integer room_id = item.getId();

                            Log.d("sss", item.toString() +" + " + member_id.toString());
                            onClickRoomItem(member_id, room_id, item);
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
    @OnClick(R.id.img_input)
    public void onClickChange(View view){
        initDialog();
    }

    private  void  initDialog(){
        ChooseDialog dialog = new ChooseDialog(this, new ChooseDialog.ChooseListener() {
            @Override
            public void choosePhoto() {

                Intent intent = new Intent(SearchActivity.this,upload_Activity.class);
                startActivity(intent);
                Log.d("bjh","re: " + 3);
            }

            @Override
            public void chooseCamer() {


                Intent intent = new Intent(SearchActivity.this,upload_btn_photo_activity.class);

                startActivity(intent);
                Log.d("bjh","re: " + 55);
            }

        });
        dialog.show();
    }
    @OnClick(R.id.img_home)
    public void onReturnHome(View view) {
        Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.img_option)
    public void onReturnOption(View view) {
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.linearLayout_search)
    public void onClickMain(View view){

    }

    public void onClickRoomItem(final Integer id, final Integer room_id, final Room item){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchActivity.this);
        alertDialog.setTitle("경고");
        alertDialog.setMessage("이 방에 입장하시겠습니까? 입장 후에는 해당 방에 가입됩니다.");
        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(item.getRoom_ps().equals("0")){
                    Call<Void> observ = RetrofitService.getInstance().getRetrofitRequest()
                            .joinRoom(id, room_id);
                    observ.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()){
                                Intent intent = new Intent(SearchActivity.this,RoomActivity.class);
                                intent.putExtra("room_id", item.getId());
                                intent.putExtra("room_name", item.getName());
                                intent.putExtra("room_img", item.getRoom_img());
                                RoomInfo.getInstance().setRoom(item);
                                Log.d("kkk", item.toString());
                                startActivity(intent);
                            } else {
                                Log.d("sss", "error : 1");
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

                }else{

                    CustomDialog dialog = new CustomDialog(SearchActivity.this);
                    dialog.setCallbacks(new CustomDialog.Callbacks() {
                        @Override
                        public void onClickSend(String name) {
                            password = name;
                            Log.d("jjj","password"+ password);
                            Log.d("jjj",""+ item.getRoom_ps());
                            if(password.equals(item.getRoom_ps())){

                                Call<Void> observ = RetrofitService.getInstance().getRetrofitRequest()
                                        .joinRoom(id, room_id);
                                observ.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()){
                                            Intent intent = new Intent(SearchActivity.this,RoomActivity.class);
                                            intent.putExtra("room_id", item.getId());
                                            intent.putExtra("room_name", item.getName());
                                            intent.putExtra("room_img", item.getRoom_img());
                                            RoomInfo.getInstance().setRoom(item);
                                            Log.d("kkk", item.toString());
                                            startActivity(intent);
                                        } else {
                                            Log.d("sss", "error : 1");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });

                            }else{
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchActivity.this);
                                alertDialog.setTitle("경고");
                                alertDialog.setMessage("비밀번호가틀렸습니다.");
                                alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                alertDialog.show();
                                password = "00";
                            }


                        }
                    });
                    dialog.show();


                }


            }

        });
        alertDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }
    @OnClick(R.id.btn_logout)
    public void onClickLogout(View view){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchActivity.this);
        alertDialog.setTitle("경고");
        alertDialog.setMessage("로그아웃 하시겠습니까?");
        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
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
