package kr.co.ezenac.cjy.teamproject.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.co.ezenac.cjy.teamproject.R;
import kr.co.ezenac.cjy.teamproject.RoomActivity;
import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.model.Room;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018-02-05.
 */

public class Room_adapter extends BaseAdapter {
    ArrayList<Img> items = new ArrayList<>();
    Integer mode = 0;
    Context context;
    Integer room_id = 0;

    public Room_adapter(ArrayList<Img> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();

        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.room_adapter, parent, false);
            holder.img_roomAdapter = convertView.findViewById(R.id.img_roomAdapter);
            holder.btn_imgDelete = convertView.findViewById(R.id. btn_imgDelete);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

            Img item = (Img) getItem(position);
            Glide.with(context).load(item.getPath()).centerCrop().into(holder.img_roomAdapter);
        final Integer img_id = item.getId();

        if (mode == 1){
            holder.btn_imgDelete.setVisibility(View.VISIBLE);
        } else {
            holder.btn_imgDelete.setVisibility(View.GONE);
        }

        holder.btn_imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ppp","proDelete");

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("경고");
                alertDialog.setMessage("사진을 삭제하시겠습니까?");
                alertDialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Integer member_id = LoginInfo.getInstance().getMember().getId();
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
                                                .deleteImg(img_id);
                                        obser.enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                if (response.isSuccessful()){
                                                    setMode(0);
                                                    setRoom_id(0);
                                                    notifyDataSetChanged();
                                                }
                                            }
                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                t.printStackTrace();
                                            }
                                        });

                                        Log.d("ttt", ""+tmp);
                                        Log.d("ppp", member_id.toString() + "//" + img_id.toString() );
                                        items.remove(position);
                                        setMode(0);
                                        setRoom_id(0);
                                        notifyDataSetChanged();

                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                                        alertDialog.setTitle("경고");
                                        alertDialog.setMessage("사진이 삭제되었습니다.");
                                        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                        alertDialog.show();
                                    }else{
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
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
        });



        return convertView;
    }

    private class Holder{
        ImageView img_roomAdapter;
        Button btn_imgDelete;
    }
    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public void setRoom_id(Integer room_id) {
        this.room_id = room_id;
    }
}
