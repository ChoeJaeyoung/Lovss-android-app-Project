package kr.co.ezenac.cjy.teamproject.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.co.ezenac.cjy.teamproject.R;
import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.model.Main;
import kr.co.ezenac.cjy.teamproject.model.Room;
import kr.co.ezenac.cjy.teamproject.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018-02-08.
 */

public class Home_adapter extends BaseAdapter {
    ArrayList<Main> items = new ArrayList<>();
    Context context;

    public Home_adapter(ArrayList<Main> items, Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();

        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.home_adapter, parent, false);
            holder.img_roomImg = convertView.findViewById(R.id.img_roomImg);
            holder.text_roomName = convertView.findViewById(R.id.text_roomName);
            holder.img_homeAdapter = convertView.findViewById(R.id.img_homeAdapter);
            holder.text_homeAdapter = convertView.findViewById(R.id.text_homeAdapter);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Main item = (Main) getItem(position);
        Log.d("ddd", item.toString());
        Glide.with(context).load(item.getPath()).centerCrop().into(holder.img_homeAdapter);
        holder.text_homeAdapter.setText(item.getContent());
        holder.text_roomName.setText(item.getName());
        Glide.with(context).load(item.getRoom_img()).centerCrop().into(holder.img_roomImg);

        return convertView;
    }

    private class Holder{
        ImageView img_roomImg;
        TextView text_roomName;
        ImageView img_homeAdapter;
        TextView text_homeAdapter;
    }
}
