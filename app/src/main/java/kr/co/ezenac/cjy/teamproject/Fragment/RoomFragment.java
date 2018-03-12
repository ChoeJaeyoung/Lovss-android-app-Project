package kr.co.ezenac.cjy.teamproject.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.ezenac.cjy.teamproject.MainActivity;
import kr.co.ezenac.cjy.teamproject.R;
import kr.co.ezenac.cjy.teamproject.RoomActivity;
import kr.co.ezenac.cjy.teamproject.adapter.Profile_adapter;
import kr.co.ezenac.cjy.teamproject.adapter.Room_adapter;
import kr.co.ezenac.cjy.teamproject.model.Room;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;
import kr.co.ezenac.cjy.teamproject.singletone.RoomInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018-02-20.
 */

public class RoomFragment extends Fragment {


    Profile_adapter profileAdapter;
    @BindView(R.id.gridview_main_1) GridView gridview_main_1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Log.d("joseph1","cc");
        View view = inflater.inflate(R.layout.activity_fragment_room, container, false);
        ButterKnife.bind(this, view);
        Integer tmpId = LoginInfo.getInstance().getMember().getId();
        String tmpMember_id = LoginInfo.getInstance().getMember().getLogin_id().toString();
        String tmpMember_img = LoginInfo.getInstance().getMember().getMember_img();
        // getActivity
        // this 프래그먼트

        callLoginInfo(tmpId);


        Log.d("img", "img : " + LoginInfo.getInstance().getMember().getMember_img());

        gridview_main_1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ppp1", "LongCLICK2");
                String str = gridview_main_1.getItemAtPosition(position).toString();
                Log.d("ppp1", str);

                profileAdapter.setMode(1);
                profileAdapter.notifyDataSetChanged();

                return true;
            }
        });

        return  view;
    }

    public void callLoginInfo(Integer tmp_memberId){
        Call<ArrayList<Room>> observ2 = RetrofitService.getInstance().getRetrofitRequest().profileRoomInfo(tmp_memberId);
        observ2.enqueue(new Callback<ArrayList<Room>>() {
            @Override
            public void onResponse(Call<ArrayList<Room>> call, Response<ArrayList<Room>> response) {
                if (response.isSuccessful()){
                    final ArrayList<Room> items = response.body();

                    Log.d("res4", "res3" + items.toString());
                    profileAdapter = new Profile_adapter(items,getActivity());
                    gridview_main_1.setAdapter(profileAdapter);
                    for (int i = 0; i < items.size(); i++) {
                        Log.d("profile", "profile : " + items.get(i).toString());
                    }
                    gridview_main_1.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Room item = items.get(position);

                            Intent intent = new Intent(getActivity(), RoomActivity.class);
                            intent.putExtra("room_id", item.getId());
                            intent.putExtra("room_name", item.getName());
                            intent.putExtra("room_img", item.getRoom_img());
                            RoomInfo.getInstance().setRoom(item);
                            Log.d("kkk2", item.toString());
                            startActivity(intent);
                        }
                    });

                    Log.d("ttt3", items.toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Room>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public Profile_adapter getProfileAdapter() {
        return profileAdapter;
    }

    public void setProfileAdapter(Profile_adapter profileAdapter) {
        this.profileAdapter = profileAdapter;
    }


}
