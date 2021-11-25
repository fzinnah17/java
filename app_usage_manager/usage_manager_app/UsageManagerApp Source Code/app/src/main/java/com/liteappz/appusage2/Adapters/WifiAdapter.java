package com.liteappz.appusage2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.liteappz.appusage2.R;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import bot.box.appusage.model.AppData;
import bot.box.appusage.utils.UsageUtils;

public class WifiAdapter extends RecyclerView.Adapter<WifiAdapter.ViewHolder> {

    private List<AppData> mData;
    private WeakReference<Context> mContext;

    public WifiAdapter(Context mContext) {
        this.mContext = new WeakReference(mContext);
    }

    public void updateData(List<AppData> data) {
        mData = data;
        Collections.sort(mData, new Comparator<AppData>() {
            @Override
            public int compare(AppData t1, AppData t2) {
                return Long.compare(t2.mWifi, t1.mWifi);
            }
        });
        notifyDataSetChanged();
    }

//    private AppData getUsageByPosition(int position) {
//        if (mData.size() > position) {
//            return mData.get(position);
//        }
//        return null;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_wifi_used, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        AppData item = getUsageByPosition(position);
        AppData item = mData.get(position);
        holder.mName.setText(item.mName);

//        holder.mUsage.setText(UsageUtils.humanReadableMillis(item.mUsageTime));

//        holder.mTime.setText(String.format(Locale.getDefault(),
//                "%s", "Last Launch " +
//                        new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault()).format(new Date(item.mEventTime))));

//        holder.launch_count.setText(item.mCount + " Times Launched");

        holder.data_used.setText(UsageUtils.humanReadableByteCount(item.mWifi));

        Glide.with(this.mContext.get())
                .load(UsageUtils.parsePackageIcon(item.mPackageName, R.mipmap.ic_launcher)).
                transition(new DrawableTransitionOptions().crossFade())
                .into(holder.mIcon);

//        holder.parent.setOnClickListener(v -> {
//            DetailActivity.start((Activity) v.getContext(), item.mPackageName);
//        });
    }

    @Override
    public int getItemCount() {
        if (mData != null)
            return mData.size();
        else return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout parent;
        private TextView mName;
        private TextView mUsage;
        private TextView mTime;
        private ImageView mIcon;
        private TextView launch_count;
        private TextView data_used;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            mName = itemView.findViewById(R.id.app_name);
//            mUsage = itemView.findViewById(R.id.app_usage);
//            mTime = itemView.findViewById(R.id.app_time);
            mIcon = itemView.findViewById(R.id.app_image);
//            launch_count = itemView.findViewById(R.id.launch_count);
            data_used = itemView.findViewById(R.id.data_used);
        }
    }
}