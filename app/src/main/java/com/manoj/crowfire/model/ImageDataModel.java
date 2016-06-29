package com.manoj.crowfire.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by manoj on 29/06/16.
 */
public class ImageDataModel implements Parcelable {
    private String title;
    private String url;

    public ImageDataModel(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "ImageDataModel{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.url);
    }

    protected ImageDataModel(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
    }

    public static final Creator<ImageDataModel> CREATOR = new Creator<ImageDataModel>() {
        @Override
        public ImageDataModel createFromParcel(Parcel source) {
            return new ImageDataModel(source);
        }

        @Override
        public ImageDataModel[] newArray(int size) {
            return new ImageDataModel[size];
        }
    };
}

