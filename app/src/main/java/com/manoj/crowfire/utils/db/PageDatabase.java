package com.manoj.crowfire.utils.db;

import com.manoj.crowfire.model.ImageDataModel;
import com.manoj.crowfire.model.PageResponseData;

import java.util.ArrayList;
import java.util.List;

public class PageDatabase implements MainDatabaseView {
    private static PageDatabase instance;
    private List<ImageDataModel> pages;
    private int lastKnowPage;
    private int totalPages;

    private PageDatabase() {
        pages = new ArrayList<>();
        totalPages = -1;
        lastKnowPage = 0;
    }

    public static PageDatabase getInstance() {
        if (instance == null) {
            instance = new PageDatabase();
        }
        return instance;
    }

    public List<ImageDataModel> getPages() {
        return pages;
    }

    public int getLastKnowPage() {
        return lastKnowPage;
    }

    public void insertPageData(PageResponseData pageListPage) {
        int currentPage = pageListPage.getPage();
        if (currentPage == lastKnowPage + 1) {
            this.lastKnowPage = currentPage;
            this.pages.addAll(pageListPage.getImageDataModels());
        }
        if (currentPage == 1) {
            totalPages = pageListPage.getTotal();
        }
    }

    public boolean isDataComplete() {
        return lastKnowPage == totalPages;
    }

    public int getNextPage() {
        return lastKnowPage + 1;
    }

    public void clearData() {
        lastKnowPage = 0;
        totalPages = -1;
        pages.clear();
    }

    public int getDataSize() {
        return getPages().size();
    }

    public ImageDataModel getItem(int position) {
        return getPages().get(position);
    }

}
