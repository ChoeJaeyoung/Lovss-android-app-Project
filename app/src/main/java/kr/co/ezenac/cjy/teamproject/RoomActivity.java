package kr.co.ezenac.cjy.teamproject;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.ezenac.cjy.teamproject.adapter.MemberList_adapter;
import kr.co.ezenac.cjy.teamproject.adapter.Room_adapter;
import kr.co.ezenac.cjy.teamproject.customview.BackPressCloseHandler;
import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.model.Member;
import kr.co.ezenac.cjy.teamproject.model.Room;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;
import kr.co.ezenac.cjy.teamproject.singletone.RoomInfo;
import kr.co.ezenac.cjy.teamproject.util.RealPathUtil;
import lombok.Builder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomActivity extends AppCompatActivity {
    Room_adapter roomAdapter;
    @BindView(R.id.grid_room_gv) GridView grid_room_gv;
    @BindView(R.id.img_roomImg) ImageView img_roomImg;
    @BindView(R.id.text_room_name) TextView text_room_name;
    @BindView(R.id.in_room_img_add) ImageView in_room_img_add;
    @BindView(R.id.img_room_home) ImageView img_room_home;
    @BindView(R.id.img_room_search) ImageView img_room_search;
    @BindView(R.id.img_room_input) ImageView img_room_input;
    @BindView(R.id.img_room_option) ImageView img_room_option;
    @BindView(R.id.linearLayout_room) LinearLayout linearLayout_room;
    @BindView(R.id.btn_logout) ImageView btn_logout;
    @BindView(R.id.in_room_room_delete) ImageView in_room_room_delete;
    @BindView(R.id.room_out) ImageView room_out;

    @BindView(R.id.memberList2) GridView memberList2;

    Integer room_id;
    Integer tmp = 1;
    Toolbar toolbar;
    DrawerLayout dlDrawer;
    ActionBarDrawerToggle dtToggle;
    MemberList_adapter memberList_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        room_id = intent.getIntExtra("room_id", 0);
        String room_name = intent.getStringExtra("room_name");
        String room_img = intent.getStringExtra("room_img");


        Log.d("room", "room_info : " + room_name + " / " + room_img +"/"+ room_id);
        text_room_name.setText(room_name);
        Glide.with(RoomActivity.this).load(room_img).centerCrop().
                into(img_roomImg);
        callImgInfo(room_id);

        grid_room_gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ppp", "LongCLICK2");
                String str = grid_room_gv.getItemAtPosition(position).toString();
                Log.d("ppp", str);

                roomAdapter.setMode(1);
                roomAdapter.notifyDataSetChanged();
                roomAdapter.setRoom_id(room_id);

                return true;
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (null != ab) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, R.string.app_name, R.string.app_name);
        dlDrawer.setDrawerListener(dtToggle);
    }

    @OnClick(R.id.room_out)
    public void onClickRoomOut(View view){
        final Integer memberId = LoginInfo.getInstance().getMember().getId();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RoomActivity.this);
        alertDialog.setTitle("경고");
        alertDialog.setMessage("이 방을 나가시겠습니까?");
        alertDialog.setPositiveButton("나가기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Integer member_id = LoginInfo.getInstance().getMember().getId();
                Call<Void> obser = RetrofitService.getInstance().getRetrofitRequest().deleteRoom(memberId,room_id);
                obser.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Log.d("sucDelete","sucDelete");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        alertDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        Log.d("jjj","aa");
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        dtToggle.syncState();
        Log.d("jjj","bb");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
        Log.d("jjj","cc");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (dtToggle.onOptionsItemSelected(item)) {
            Log.d("jjj", "dd");

            Call<ArrayList<Member>> observ2= RetrofitService.getInstance().getRetrofitRequest().
                    getMemberList2(room_id);
            observ2.enqueue(new Callback<ArrayList<Member>>() {
                @Override
                public void onResponse(Call<ArrayList<Member>> call, Response<ArrayList<Member>> response) {
                    if (response.isSuccessful()) {
                        final ArrayList<Member> items = response.body();
                        Log.d("memberlist", items.toString());

                        memberList_adapter = new MemberList_adapter(items, RoomActivity.this);
                        memberList2.setAdapter(memberList_adapter);


                    } else {
                        Log.d("uuu", "1");
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Member>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
            return true;
        }
        Log.d("jjj","gg");
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.img_roomImg)
    public void onClickRoomImg(View view){
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select picture"), 0);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        };

        TedPermission.with(RoomActivity.this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Integer member_id = LoginInfo.getInstance().getMember().getId();
        Call<Integer> obser = RetrofitService.getInstance().getRetrofitRequest()
                .checkManager(room_id, member_id);
        Log.d("ppp","proDelete" + room_id);
        Log.d("ppp","proDelete" + member_id);
        obser.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()){
                    Integer tmp = response.body();
                    Log.d("ttt",""+tmp);

                    if(tmp == 1){

                        if (resultCode == RESULT_OK){
                            if (requestCode == 0){
                                File file = new File(RealPathUtil.getRealPath(RoomActivity.this, data.getData()));
                                if (file.exists()){
                                    Log.d("fff", "exist");
                                }
                                MultipartBody.Part filePart =
                                        MultipartBody.Part.createFormData("file", file.getName(),
                                                RequestBody.create(MediaType.parse("image/*"),file));

                                RequestBody roomId =
                                        RequestBody.create(MediaType.parse("text/plain"), String.valueOf(room_id));

                                Call<Room> observ = RetrofitService.getInstance().getRetrofitRequest().updateRoom(filePart, roomId);
                                observ.enqueue(new Callback<Room>() {
                                    @Override
                                    public void onResponse(Call<Room> call, Response<Room> response) {
                                        if (response.isSuccessful()){
                                            Room room = response.body();
                                            RoomInfo.getInstance().setRoom(room);
                                            Log.d("yyy", room.toString());

                                            String updateImg = RoomInfo.getInstance().getRoom().getRoom_img();
                                            Glide.with(RoomActivity.this).load(updateImg).centerCrop().
                                                    into(img_roomImg);
                                            Log.d("profile", "success : " + room.toString());


                                        } else {
                                            Log.d("profile", "fail");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Room> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                            }
                        }




                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RoomActivity.this);
                        alertDialog.setTitle("경고");
                        alertDialog.setMessage("프로필이바뀌었습니다.");
                        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        });
                        alertDialog.show();
                    }else{
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RoomActivity.this);
                        alertDialog.setTitle("경고");
                        alertDialog.setMessage("방장권한이 없습니다..");
                        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog.show();
                    }

                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                t.printStackTrace();
            }


        });
    }






    @OnClick(R.id.in_room_img_add)
    public void onClick_in_room_img_add(View view){
        Intent intent = new Intent(RoomActivity.this, upload_imgActivity.class);
        intent.putExtra("room_id", room_id);
        Log.d("kkk","room_id" + room_id);
        startActivity(intent);
    }

    @OnClick(R.id.in_room_room_delete)
    public void onClick_in_room_room_delete(View view){

        Log.d("ppp","proDelete");

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RoomActivity.this);
        alertDialog.setTitle("경고");
        alertDialog.setMessage("방을 삭제하시겠습니까?");
        alertDialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Integer member_id = LoginInfo.getInstance().getMember().getId();
                Call<Integer> obser = RetrofitService.getInstance().getRetrofitRequest()
                        .checkManager(room_id, member_id);
                Log.d("ppp","proDelete" + room_id);
                Log.d("ppp","proDelete" + member_id);
                obser.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful()){
                            Integer tmp = response.body();
                            Log.d("ttt",""+tmp);

                            if(tmp == 1){

                                final Integer member_id = LoginInfo.getInstance().getMember().getId();
                                Call<Void> obser = RetrofitService.getInstance().getRetrofitRequest()
                                        .deleteRoom2(room_id);
                                obser.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()){

                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });




                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(RoomActivity.this);
                                alertDialog.setTitle("경고");
                                alertDialog.setMessage("방이 삭제되었습니다.");
                                alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(RoomActivity.this,MainActivity.class);

                                        startActivity(intent);

                                    }
                                });
                                alertDialog.show();
                            }else{
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(RoomActivity.this);
                                alertDialog.setTitle("경고");
                                alertDialog.setMessage("방장권한이 없습니다..");
                                alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                alertDialog.show();
                            }

                        }
                    }
                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        alertDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();

    }



    public void callImgInfo(Integer room_id){
        final Call<ArrayList<Img>> observ2 = RetrofitService.getInstance().getRetrofitRequest().
                callRoomImg(room_id);
        observ2.enqueue(new Callback<ArrayList<Img>>() {
            @Override
            public void onResponse(Call<ArrayList<Img>> call, Response<ArrayList<Img>> response) {
                if (response.isSuccessful()) {
                    Log.d("abccho", "re: " + 2);
                    final ArrayList<Img> items = response.body();


                    roomAdapter = new Room_adapter(items, RoomActivity.this);
                    grid_room_gv.setAdapter(roomAdapter);
                    grid_room_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(RoomActivity.this, DetailActivity.class);
                            intent.putExtra("position", position);
                            intent.putExtra("type", 1);
                            startActivity(intent);
                        }
                    });

                } else {
                    Log.d("uuu", "1");
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Img>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    @OnClick(R.id.img_room_input)
    public void onClickChange(View view){
        initDialog();
    }

    private  void  initDialog(){
        ChooseDialog dialog = new ChooseDialog(this, new ChooseDialog.ChooseListener() {
            @Override
            public void choosePhoto() {

                Intent intent = new Intent(RoomActivity.this,upload_Activity.class);
                startActivity(intent);
                Log.d("bjh","re: " + 3);
            }

            @Override
            public void chooseCamer() {


                Intent intent = new Intent(RoomActivity.this,upload_btn_photo_activity.class);

                startActivity(intent);
                Log.d("bjh","re: " + 55);
            }
        });
        dialog.show();
    }
    @OnClick(R.id.img_room_home)
    public void onReturnHome(View view) {
        Intent intent = new Intent(RoomActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    @OnClick(R.id.img_room_search)
    public void onReturnSearch(View view) {
        Intent intent = new Intent(RoomActivity.this, SearchActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.img_room_option)
    public void onReturnOption(View view) {
        Intent intent = new Intent(RoomActivity.this, MainActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.linearLayout_room)
    public void onClickMain(View view){

    }
    @OnClick(R.id.btn_logout)
    public void onClickLogout(View view){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RoomActivity.this);
        alertDialog.setTitle("경고");
        alertDialog.setMessage("로그아웃 하시겠습니까?");
        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(RoomActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
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
