package kr.co.ezenac.cjy.teamproject;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.model.Room;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.RoomInfo;
import kr.co.ezenac.cjy.teamproject.util.RealPathUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class upload_imgActivity extends AppCompatActivity {

    @BindView(R.id.upload_img_btn_upload) Button upload_img_btn_upload;
    @BindView(R.id.upload_img_text_title) EditText upload_img_text_title;
    @BindView(R.id.upload_img_img_add) ImageView upload_img_img_add;

    File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_img);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.upload_img_btn_upload)
    public void onClickBtnAdd(View view) {

        final MultipartBody.Part filePart =
                MultipartBody.Part.createFormData("file",
                        file.getName(),
                        RequestBody.create(MediaType.parse("image/*"), file));
        Log.d("bjh","file : " + file.toString());
        String content = upload_img_text_title.getText().toString();

        Intent intent = getIntent();
        Integer troom_id = intent.getIntExtra("room_id", 0);



        String room_id = troom_id.toString();

        final RequestBody contentBody =
                RequestBody.create(MediaType.parse("text/plain"),
                        content);
        final RequestBody room_numberBody =
                RequestBody.create(MediaType.parse("text/plain"),
                        room_id); // 수정해야됩니다. ☆☆★





        Call<Room> obserV = RetrofitService.getInstance().getRetrofitRequest().makeImg(filePart, contentBody, room_numberBody);
        obserV.enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {
                    Log.d("bjh", "suc");
                    Room item = response.body();
                    RoomInfo.getInstance().setRoom(item);
                    Log.d("upup", item.toString());
                 Log.d("ccc7" ,""+ item.toString());
                    Intent intent = new Intent(upload_imgActivity.this, RoomActivity.class);
                    intent.putExtra("room_id", item.getId());
                    intent.putExtra("room_name", item.getName());
                    intent.putExtra("room_img", item.getRoom_img());
                    startActivity(intent);

                    //finish();
                } else {
                    Log.d("bjh", "fail");
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @OnClick(R.id.upload_img_btn_titleImg)
    public void onClickBtnTitleImg(View view) {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, "Select picture"), 0);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        };

        TedPermission.with(upload_imgActivity.this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Glide.with(upload_imgActivity.this).load(data.getData()).into(upload_img_img_add);
        //String room_Img = img_add.getDrawable().toString();

        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                file = new File(
                        RealPathUtil.getRealPath(upload_imgActivity.this,
                                data.getData()));
                if (file.exists()) {
                    Log.d("bjh", "exist");
                }
            }
        }
    }
}