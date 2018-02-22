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
import kr.co.ezenac.cjy.teamproject.adapter.Collection_adapter;
import kr.co.ezenac.cjy.teamproject.adapter.Room_adapter;
import kr.co.ezenac.cjy.teamproject.db.DBManager;
import kr.co.ezenac.cjy.teamproject.model.Collect;
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
    ArrayList<Collect> items = new ArrayList<>();
    Collection_adapter collectionAdapter;
    @BindView(R.id.gridview_main_2) GridView gridview_main_2;

    DBManager dbManager;

    public static CollectionFragment newInstance(int index) {
        CollectionFragment f = new CollectionFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Log.d("joseph","cc");
        View view = inflater.inflate(R.layout.activity_fragment_collection, container, false);
        ButterKnife.bind(this,view);
        dbManager = new DBManager(getActivity(), "Collect.db", null, 1);
        Integer userID = LoginInfo.getInstance().getMember().getId();
        items = dbManager.getCollectList(userID);
        Log.d("collection",items.toString());

        collectImg();

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

    public void collectImg(){
        collectionAdapter = new Collection_adapter(items, getActivity());
        gridview_main_2.setAdapter(collectionAdapter);
        /*gridview_main_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });*/
    }

}
