package com.Team25.intoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Information_ListView_Adapter extends ArrayAdapter {
    private static final String TAG = "FeedAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<InformationObject> applications;

    public Information_ListView_Adapter(@NonNull Context context, int resource, List<InformationObject> applications) {
        super(context, resource);
        this.layoutInflater = LayoutInflater.from(context);
        this.layoutResource = resource;
        this.applications = applications;
    }

    /**
     * Used to determine the objects in the view, Length of the list ;).
     */
    @Override
    public int getCount() {
        return applications.size();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Information_ListView_Adapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder = new Information_ListView_Adapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Information_ListView_Adapter.ViewHolder) convertView.getTag();
        }
        InformationObject currentEntry = applications.get(position);
        viewHolder.ioName.setText(currentEntry.getName());
        viewHolder.ioSummary.setText(currentEntry.getDescription());
//        viewHolder.ioImage.setImageResource(R.id.newimage);
        return convertView;
    }

    private class ViewHolder {
        final TextView ioName;
        final ImageView ioImage;
        final TextView ioSummary;

        ViewHolder(View v) {
            this.ioName = v.findViewById(R.id.information_layout_name);
            this.ioSummary = v.findViewById(R.id.information_layout_summary);
            this.ioImage = v.findViewById(R.id.information_layout_image);
        }
    }
}