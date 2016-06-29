package com.manoj.crowfire.detailpage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.manoj.crowfire.R;
import com.manoj.crowfire.utils.imageloader.ImageLoader;
import com.manoj.crowfire.model.ImageDataModel;

public class DetailPageAcitvity extends AppCompatActivity {

    public static final String PARAM_PAGE_DATA = "param_page_data";
    private ImageDataModel imageDataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page_acitvity);

        getSupportActionBar().setTitle("Detail Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (getIntent() != null) {
            imageDataModel = getIntent().getParcelableExtra(PARAM_PAGE_DATA);
        }
        if (imageDataModel == null) {
            throw new IllegalStateException("movie data is null");
        }

        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        TextView textView = (TextView) findViewById(R.id.title);
        textView.setText(imageDataModel.getTitle());
        ImageLoader.getInstance().loadImage(imageDataModel.getUrl(), imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
