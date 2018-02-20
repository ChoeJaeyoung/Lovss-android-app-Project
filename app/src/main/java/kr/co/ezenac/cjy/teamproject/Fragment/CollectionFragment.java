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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.ezenac.cjy.teamproject.DetailActivity;
import kr.co.ezenac.cjy.teamproject.R;
import kr.co.ezenac.cjy.teamproject.RoomActivity;
import kr.co.ezenac.cjy.teamproject.adapter.Room_adapter;
import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018-02-20.
 */

public class CollectionFragment extends Fragment {
    Room_adapter room_adapter;
    @BindView(R.id.gridview_main_2) GridView gridview_main_2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Log.d("joseph","cc");
        View view = inflater.inflate(R.layout.activity_fragment_collection, container, false);
        ButterKnife.bind(this, view);
        Integer tmpId = LoginInfo.getInstance().getMember().getId();
        String tmpMember_id = LoginInfo.getInstance().getMember().getLogin_id().toString();
        String tmpMember_img = LoginInfo.getInstance().getMember().getMember_img();
        // getActivity
        // this 프래그먼트

        callImgInfo(2);


        Log.d("img", "img : " + LoginInfo.getInstance().getMember().getMember_img());

        gridview_main_2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ppp", "LongCLICK2");
                String str = gridview_main_2.getItemAtPosition(position).toString();
                Log.d("ppp", str);

                //profileAdapter.setMode(1);
                //profileAdapter.notifyDataSetChanged();

                return true;
            }
        });









        return  view;
    }

    public void callImgInfo(Integer room_id){
        Call<ArrayList<Img>> observ = RetrofitService.getInstance().getRetrofitRequest().
                callRoomImg(room_id);
        observ.enqueue(new Callback<ArrayList<Img>>() {
            @Override
            public void onResponse(Call<ArrayList<Img>> call, Response<ArrayList<Img>> response) {
                if (response.isSuccessful()) {
                    final ArrayList<Img> items = response.body();

                    Log.d("uuu", items.toString());


                    room_adapter = new Room_adapter(items, getActivity());
                    gridview_main_2.setAdapter(room_adapter);
                    gridview_main_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity(), DetailActivity.class);
                            intent.putExtra("position", position);
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

}
