package com.manoj.crowfire.ListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.manoj.crowfire.R;
import com.manoj.crowfire.utils.db.PageDatabase;
import com.manoj.crowfire.detailpage.DetailPageAcitvity;
import com.manoj.crowfire.model.ImageDataModel;
import com.manoj.crowfire.ListActivity.view.AdapterView;
import com.manoj.crowfire.ListActivity.view.MainPresenter;
import com.manoj.crowfire.ListActivity.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView, AdapterView, View.OnClickListener {


    private View progressBar;
    private View errorLayout;
    private RecyclerView recyclerView;
    private MainPresenter presenter;
    private PageListAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        initViews();
        //After all intialize complete
        if (PageDatabase.getInstance().getLastKnowPage() == 0) {
            presenter.loadData();
        }

    }

    private void initViews() {
        progressBar = findViewById(R.id.progress_bar);
        errorLayout = findViewById(R.id.error_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerAdapter = new PageListAdapter(this, this);
        recyclerView.setAdapter(recyclerAdapter);
        findViewById(R.id.retry_button).setOnClickListener(this);
    }

    private void hiderLoadingView() {
        if (isLoadingViewVisible()) {
            progressBar.setVisibility(View.GONE);
        }
    }

    public boolean isLoadingViewVisible() {
        return progressBar.getVisibility() == View.VISIBLE;
    }

    private void hideRecyclerView() {
        if (isRecyclerViewVisible()) {
            recyclerView.setVisibility(View.GONE);
        }
    }

    public boolean isRecyclerViewVisible() {
        return recyclerView.getVisibility() == View.VISIBLE;
    }

    private void hideErrorLayout() {
        if (isErrorViewVisible()) {
            errorLayout.setVisibility(View.GONE);
        }
    }

    public boolean isErrorViewVisible() {
        return errorLayout.getVisibility() == View.VISIBLE;
    }

    @Override
    public void showTaost(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        hideErrorLayout();
        hideRecyclerView();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        hiderLoadingView();
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showRecyclerView() {
        hiderLoadingView();
        if (recyclerView.getVisibility() == View.GONE) {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onRecyclerDataChanged() {
        if (recyclerAdapter != null) {
            recyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void openImageDetailPage(ImageDataModel data) {
        Log.d("manoj", "Image clicked to open detail page");
        Intent intent = new Intent(this, DetailPageAcitvity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailPageAcitvity.PARAM_PAGE_DATA, data);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onLastItemDisplayed() {
        presenter.loadData();
    }

    @Override
    public void onItemClicked(int position) {
        presenter.onPageSelected(position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.retry_button:
                onRetryButtonClick();
                break;
        }
    }

    private void onRetryButtonClick() {
        presenter.onRetryBtnClick();
    }
}
