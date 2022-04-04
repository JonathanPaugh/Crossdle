package com.example.crossdle.app;

import android.content.Context;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class FileHandler {
    public static InputStream openRawResource(Context context, String fileName) {
        int resourceId = context.getResources().getIdentifier(fileName,
                                                              "raw",
                                                              context.getPackageName());

        return context.getResources().openRawResource(resourceId);
    }

    public static<T> T openRawJson(Context context, String fileName, Class<T> classOf) {
        InputStream stream = openRawResource(context, fileName);
        Reader reader = new InputStreamReader(stream);
        return new Gson().fromJson(reader, classOf);
    }
}
