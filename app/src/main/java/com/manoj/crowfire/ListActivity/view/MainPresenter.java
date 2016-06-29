package com.manoj.crowfire.ListActivity.view;

import android.support.annotation.NonNull;
import android.util.Log;

import com.manoj.crowfire.utils.db.MainDatabaseView;
import com.manoj.crowfire.utils.db.PageDatabase;
import com.manoj.crowfire.model.ImageDataModel;
import com.manoj.crowfire.model.PageResponseData;
import com.manoj.crowfire.utils.DataFetcherTask;

import java.lang.ref.WeakReference;

/**
 * Created by manoj on 20/06/16.
 */
public class MainPresenter implements DataFetcherTask.DataFetcherTaskCallback {

    private WeakReference<MainView> view;
    private MainDatabaseView pageDatabase;
    private DataFetcherTask dataFetcherTask;

    public MainPresenter(MainView mainView) {
        pageDatabase = PageDatabase.getInstance();
        bindView(mainView);
    }

    public void bindView(@NonNull MainView view) {
        this.view = new WeakReference<>(view);
    }


    protected MainView view() {
        if (view == null) {
            return null;
        } else {
            return view.get();
        }
    }

    public void loadData() {
        Log.d("manoj", "load data called");
        if (pageDatabase.isDataComplete()) {
            return;
        }
        if (pageDatabase.getLastKnowPage() == 0) {
            loadFirstPage();
        } else {
            loadNextPage();
        }
    }

    public void loadFirstPage() {
        view().showLoading();
        loadNextPage();
    }

    private void loadNextPage() {
        int pageNo = pageDatabase.getNextPage();
        fetchData(pageNo);
    }

    private void fetchData(int pageNo) {
        Log.d("manoj", "fetch result for page : " + pageNo);
        if (dataFetcherTask == null) {
            dataFetcherTask = new DataFetcherTask(pageNo, this);
            dataFetcherTask.execute();
        }
    }

    public void onDataLoadSuccess(int pageNo, PageResponseData pageResponseData) {
        //if first page than hide loaded and show recycler view
        Log.d("manoj", "Data load success, " + pageNo);
        if (pageNo == 1) {
            view().showRecyclerView();
        }
        pageDatabase.insertPageData(pageResponseData);
        view().onRecyclerDataChanged();
    }

    public void onDataLoadFailed(int pageNo, String error) {
        Log.d("manoj", "Data load failed, " + pageNo);
        if (pageNo == 1) {
            view().showError();
        }
        showToast(error);
    }

    private void showToast(String error) {
        view().showTaost(error);
    }

    public void onPageSelected(int position) {
        ImageDataModel data = pageDatabase.getItem(position);
        view().openImageDetailPage(data);
    }

    public void onRetryBtnClick() {
        loadData();
    }

    @Override
    public void onSuccess(int pageNo, PageResponseData data) {
        dataFetcherTask = null;
        onDataLoadSuccess(pageNo, data);
    }

    @Override
    public void onFailed(int pageNo, String error) {
        dataFetcherTask = null;
        onDataLoadFailed(pageNo, error);
    }

    public void unbindView() {
        if (dataFetcherTask != null) {
            dataFetcherTask.unregisterCallback();
        }
        this.view = null;
    }
}
