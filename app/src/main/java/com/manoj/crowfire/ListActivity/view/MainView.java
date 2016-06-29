
package com.manoj.crowfire.ListActivity.view;

import com.manoj.crowfire.model.ImageDataModel;

public interface MainView {

    void showLoading();

    void showError();

    void showRecyclerView();

    void onRecyclerDataChanged();

    void openImageDetailPage(ImageDataModel data);

    boolean isLoadingViewVisible();

    boolean isRecyclerViewVisible();

    boolean isErrorViewVisible();

    void showTaost(String error);
}
