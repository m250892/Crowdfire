package com.manoj.crowfire.utils.db;

import com.manoj.crowfire.model.ImageDataModel;
import com.manoj.crowfire.model.PageResponseData;

import java.util.List;

public interface MainDatabaseView {

    List<ImageDataModel> getPages();

    int getLastKnowPage();

    void insertPageData(PageResponseData imageDataModel);

    boolean isDataComplete();

    int getNextPage();

    void clearData();

    int getDataSize();

    ImageDataModel getItem(int position);
}
