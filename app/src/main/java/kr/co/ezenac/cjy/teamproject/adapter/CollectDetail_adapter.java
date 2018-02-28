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
import kr.co.ezenac.cjy.teamproject.singletone.CollectHashMap;
import kr.co.ezenac.cjy.teamproject.singletone.LoginInfo;

/**
 * Created by Administrator on 2018-02-23.
 */

public class CollectDetail_adapter extends BaseAdapter {
        ArrayList<Collect> items = new ArrayList<>();
        Context context;
        HashMap<Integer, Collect> col = new HashMap<>();
        DBManager dbManager;

    public CollectDetail_adapter(ArrayList<Collect> items, Context context) {
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

            final Collect item = (Collect) getItem(position);
            Log.d("ddd", item.toString());
            Glide.with(context).load(item.getImg_path()).centerCrop().into(holder.img_detailAdapter);
            holder.text_detailAdapter.setText(item.getImg_content());

            dbManager = new DBManager(context, "Collect.db", null, 1);
            col = CollectHashMap.getInstance().getCollect();
            final Integer imgId = item.getImg_id();
            final Integer user_id = LoginInfo.getInstance().getMember().getId();

            if (col.get(imgId) == null){
                Log.d("collectDetail", imgId.toString());
            } else {
                holder.img_detailStar.setBackgroundResource(R.drawable.star_mark);
            }

            final Holder finalHolder = holder;
            holder.img_detailStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(col.get(item.getImg_id()) == null){
                        dbManager.insertData(1,user_id,imgId,item.getImg_path(),item.getImg_content());
                        finalHolder.img_detailStar.setBackgroundResource(R.drawable.star_mark);
                        Collect collect = new Collect();
                        collect = dbManager.collectInfo(user_id,imgId);
                        col.put(imgId,collect);
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
