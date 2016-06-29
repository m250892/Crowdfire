package com.manoj.crowfire.utils;

import android.net.Uri;
import android.os.AsyncTask;

import com.manoj.crowfire.Constants;
import com.manoj.crowfire.model.PageResponseData;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by manoj on 29/06/16.
 */
public class DataFetcherTask extends AsyncTask<Void, Void, PageResponseData> {
    private static final String PARAM_PAGE = "page";
    private int pageNo;
    private DataFetcherTaskCallback callback;

    public DataFetcherTask(int pageNo, DataFetcherTaskCallback callback) {
        this.pageNo = pageNo;
        this.callback = callback;
    }

    @Override
    protected PageResponseData doInBackground(Void[] params) {
        String result;
        try {
            InputStream inputStream = NetworkUtils.httpGetCall(getUrl());
            if (inputStream == null) {
                return null;
            }
            result = NetworkUtils.readDataFromInputStream(inputStream);
            return JsonParser.parsePageData(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(PageResponseData data) {
        if (callback != null) {
            if (data != null) {
                callback.onSuccess(pageNo, data);
            } else {
                callback.onFailed(pageNo, "Somthing wrong");
            }
        }
    }

    public void unregisterCallback() {
        callback = null;
    }

    public String getUrl() {
        Uri.Builder uri = Uri.parse(Constants.URL).buildUpon();
        uri.appendQueryParameter(PARAM_PAGE, String.valueOf(pageNo));
        return uri.build().toString();
    }

    public interface DataFetcherTaskCallback {
        void onSuccess(int pageNo, PageResponseData data);

        void onFailed(int pageNo, String error);
    }
}
