package com.manoj.crowfire.ListActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.manoj.crowfire.R;
import com.manoj.crowfire.utils.imageloader.ImageLoader;
import com.manoj.crowfire.model.ImageDataModel;
import com.manoj.crowfire.ListActivity.view.AdapterView;
import com.manoj.crowfire.utils.db.PageDatabase;

public class PageListAdapter extends RecyclerView.Adapter<PageListAdapter.ViewHolder> {

    private PageDatabase pageDatabase;
    private Context context;
    private AdapterView adapterView;

    public PageListAdapter(Context context, AdapterView adapterView) {
        this.context = context;
        this.adapterView = adapterView;
        this.pageDatabase = PageDatabase.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageDataModel dataModel = pageDatabase.getItem(position);
        holder.titleView.setText(dataModel.getTitle());
        ImageLoader.getInstance().loadImage(dataModel.getUrl(), holder.imageView);
        //sending call for next item
        if (position == getItemCount() - 1) {
            adapterView.onLastItemDisplayed();
        }
    }

    @Override
    public int getItemCount() {
        return pageDatabase.getDataSize();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView titleView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            titleView = (TextView) itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            adapterView.onItemClicked(getAdapterPosition());
        }
    }

}
