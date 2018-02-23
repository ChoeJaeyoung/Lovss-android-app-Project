package kr.co.ezenac.cjy.teamproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.ezenac.cjy.teamproject.R;
import kr.co.ezenac.cjy.teamproject.db.DBManager;
import kr.co.ezenac.cjy.teamproject.model.Collect;
import kr.co.ezenac.cjy.teamproject.model.Img;
import kr.co.ezenac.cjy.teamproject.model.Room;
import kr.co.ezenac.cjy.teamproject.singletone.CollectHashMap;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;

/**
 * Created by Administrator on 2018-02-08.
 */

public class Detail_adapter extends BaseAdapter {
    ArrayList<Img> items = new ArrayList<>();
    Context context;
    HashMap<Integer, Collect> col = new HashMap<>();
    Collect collect;
    DBManager dbManager;

    public Detail_adapter(ArrayList<Img> items, Context context) {
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
                    inflate(R.layout.detail_girdadapter, parent, false);
            holder.img_detailAdapter = convertView.findViewById(R.id.img_detailAdapter);
            holder.text_detailAdapter = convertView.findViewById(R.id.text_detailAdapter);
            holder.img_detailStar = convertView.findViewById(R.id.img_detailStar);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Img item = (Img) getItem(position);
        Log.d("ddd", item.toString());
        Glide.with(context).load(item.getPath()).centerCrop().into(holder.img_detailAdapter);
        holder.text_detailAdapter.setText(item.getContent());

        final Integer imgId = item.getId();
        final Integer user_id = LoginInfo.getInstance().getMember().getId();
        final String img_path = item.getPath();
        final String img_content = item.getContent();
        Log.d("detailAd", item.toString() + " // " + user_id);
        col = CollectHashMap.getInstance().getCollect();
        collect = col.get(imgId);
        dbManager = new DBManager(context, "Collect.db", null, 1);

        if (col.get(imgId) == null){
            holder.img_detailAdapter.setBackgroundResource(R.drawable.star);
        } else {
            holder.img_detailStar.setBackgroundResource(R.drawable.star_mark);
        }

        final Holder finalHolder = holder;
        holder.img_detailStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (col.get(imgId) == null){
                    dbManager.insertData(1, user_id, imgId, img_path, img_content);
                    finalHolder.img_detailStar.setBackgroundResource(R.drawable.star_mark);
                    col.put(imgId, collect);
                    notifyDataSetChanged();
                } else {
                    dbManager.deleteData(imgId, user_id);
                    finalHolder.img_detailStar.setBackgroundResource(R.drawable.star);
                    col.remove(imgId);
                    notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }

    private class Holder{
        ImageView img_detailAdapter;
        TextView text_detailAdapter;
        ImageView img_detailStar;
    }
}
