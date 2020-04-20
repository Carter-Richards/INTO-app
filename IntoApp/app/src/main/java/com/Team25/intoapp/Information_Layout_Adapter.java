package com.Team25.intoapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Information_Layout_Adapter extends ArrayAdapter {
    private static final String TAG = "Information_layout";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<InformationObject> information;

    public Information_Layout_Adapter(@NonNull Context context, int resource, List<InformationObject> information) {
        super(context, resource);
        this.layoutInflater = LayoutInflater.from(context);
        this.layoutResource = resource;
        this.information = information;
    }

    /**
     * Used to determine the objects in the view, Length of the list ;).
     */
    @Override
    public int getCount() {
        return information.size();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.e(TAG, "getView: working?");
        Information_Layout_Adapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder = new Information_Layout_Adapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Information_Layout_Adapter.ViewHolder) convertView.getTag();
        }
        InformationObject currentEntry = information.get(position);
        viewHolder.ioName.setText(currentEntry.getTitle());
        viewHolder.ioSummary.setText(currentEntry.getDescription());

        return convertView;
    }
    private class ViewHolder {
        final TextView ioName;
        final TextView ioSummary;

        ViewHolder(View v) {
            this.ioName = v.findViewById(R.id.information_layout_name);
            this.ioSummary = v.findViewById(R.id.information_layout_summary);
        }
    }
}
