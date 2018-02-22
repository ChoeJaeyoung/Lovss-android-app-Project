package kr.co.ezenac.cjy.teamproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.co.ezenac.cjy.teamproject.R;
import kr.co.ezenac.cjy.teamproject.model.Collect;

/**
 * Created by Administrator on 2018-02-22.
 */

public class Collection_adapter extends BaseAdapter {
    ArrayList<Collect> collects = new ArrayList<>();
    Context context;

    public Collection_adapter(ArrayList<Collect> collects, Context context) {
        this.collects = collects;
        this.context = context;
    }

    @Override
    public int getCount() {
        return collects.size();
    }

    @Override
    public Object getItem(int position) {
        return collects.get(position);
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
                    inflate(R.layout.room_adapter, parent, false);
            holder.img_roomAdapter = convertView.findViewById(R.id.img_roomAdapter);
            holder.btn_imgDelete = convertView.findViewById(R.id. btn_imgDelete);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Collect item = (Collect) getItem(position);
        Log.d("ccc",item.toString());
        Glide.with(context).load(item.getImg_path()).centerCrop().into(holder.img_roomAdapter);

        return convertView;
    }

    private class Holder{
        ImageView img_roomAdapter;
        Button btn_imgDelete;
    }
}
