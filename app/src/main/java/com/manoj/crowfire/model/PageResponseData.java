package com.manoj.crowfire.model;

import java.util.List;

/**
 * Created by manoj on 29/06/16.
 */
public class PageResponseData {
    private int page;
    private int pages;
    private int perpage;
    private int total;
    private List<ImageDataModel> imageDataModels;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ImageDataModel> getImageDataModels() {
        return imageDataModels;
    }

    public void setImageDataModels(List<ImageDataModel> imageDataModels) {
        this.imageDataModels = imageDataModels;
    }

    @Override
    public String toString() {
        return "PageResponseData{" +
                "page=" + page +
                ", pages=" + pages +
                ", perpage=" + perpage +
                ", total=" + total +
                ", imageDataModels=" + imageDataModels +
                '}';
    }
}

