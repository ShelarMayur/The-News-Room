package com.mshel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class FeedAdapter extends ArrayAdapter {
    private static final String TAG = "FeedAdapter";
    private LayoutInflater layoutInflater;
    private ArrayList<FeedData> news;
    private int layoutResource;

    public FeedAdapter(@NonNull Context context, int resource, ArrayList<FeedData> news) {
        super(context, resource);
        this.layoutInflater = LayoutInflater.from(context);
        this.news = news;
        this.layoutResource = resource;
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = layoutInflater.inflate(layoutResource,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FeedData currentRecord = news.get(position);
        viewHolder.category.setText(currentRecord.getCategories());
        viewHolder.description.setText(currentRecord.getDescription());
        viewHolder.link.setText(currentRecord.getLink());
        viewHolder.newsTitle.setText(currentRecord.getTitle());
        return convertView;
    }
}
class ViewHolder{
    TextView newsTitle;
    TextView category;
    TextView link;
    TextView description;

    public ViewHolder(View v) {
        this.newsTitle = v.findViewById(R.id.newsTitle);
        this.description = v.findViewById(R.id.description);
        this.link = v.findViewById(R.id.link);
        this.category = v.findViewById(R.id.category);
    }
}