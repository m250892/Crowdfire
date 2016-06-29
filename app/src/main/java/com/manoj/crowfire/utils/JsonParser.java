package com.manoj.crowfire.utils;

import com.manoj.crowfire.model.ImageDataModel;
import com.manoj.crowfire.model.PageResponseData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manoj on 29/06/16.
 */
public class JsonParser {

    private static final String KEY_PHOTOS = "photos";
    private static final String KEY_PAGE = "page";
    private static final String KEY_PAGES = "pages";
    private static final String KEY_TOTAL = "total";
    private static final String KEY_PHOTO = "photo";
    private static final String KEY_TITLE = "title";
    private static final String KEY_URL = "url_z";

    public static PageResponseData parsePageData(String data) {
        JSONObject jsonObject;
        PageResponseData pageResponseData = null;
        try {
            jsonObject = new JSONObject(data);
            JSONObject pageJson = jsonObject.getJSONObject(KEY_PHOTOS);
            int page = pageJson.getInt(KEY_PAGE);
            int pages = pageJson.getInt(KEY_PAGES);
            int total = pageJson.getInt(KEY_TOTAL);
            JSONArray photoArray = pageJson.getJSONArray(KEY_PHOTO);
            List<ImageDataModel> imageList = parseImageListArray(photoArray);

            pageResponseData = new PageResponseData();
            pageResponseData.setPage(page);
            pageResponseData.setPages(pages);
            pageResponseData.setTotal(total);
            pageResponseData.setImageDataModels(imageList);
            return pageResponseData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<ImageDataModel> parseImageListArray(JSONArray photoArray) {
        List<ImageDataModel> dataModelList = new ArrayList<>();
        if (photoArray != null) {
            for (int i = 0; i < photoArray.length(); i++) {
                try {
                    ImageDataModel imageDataModel = parseImageModel(photoArray.getJSONObject(i));
                    if (imageDataModel != null) {
                        dataModelList.add(imageDataModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataModelList;
    }

    private static ImageDataModel parseImageModel(JSONObject data) {
        if (data != null) {
            try {
                String title = data.getString(KEY_TITLE);
                String url = data.getString(KEY_URL);
                return new ImageDataModel(title, url);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
