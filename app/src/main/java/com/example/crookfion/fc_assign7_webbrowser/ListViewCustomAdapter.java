package com.example.crookfion.fc_assign7_webbrowser;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListViewCustomAdapter extends BaseAdapter {

    private List<Data> itemList;

    private LayoutInflater inflater;

    public ListViewCustomAdapter(Activity context, List<Data> itemList) {
        super();
        this.itemList = itemList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return itemList.size();
    }

    public Object getItem(int position) {
        return itemList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        TextView txtViewTitle;
        TextView txtViewSubtitle;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.single_list_item, null);
            holder.txtViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
            holder.txtViewSubtitle = (TextView) convertView.findViewById(R.id.textViewUrl);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Data pageData = (Data) itemList.get(position);
        holder.txtViewTitle.setText(pageData.getTitle());
        holder.txtViewSubtitle.setText(pageData.getUrl());
        return convertView;
    }
}

