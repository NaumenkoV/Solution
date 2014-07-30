package com.softserveinc.ita.utils.impl;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softserveinc.ita.utils.JsonUtil;

import java.text.DateFormat;

public class InterviewJsonUtilGsonImpl implements JsonUtil {
    private Gson gson = new GsonBuilder().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setDateFormat(DateFormat.DEFAULT)
            .setPrettyPrinting()
            .create();

    @Override
    public <T> T fromJson(String s, Class<T> classOfT) {
        return gson.fromJson(s, classOfT);
    }

    @Override
    public String toJson(Object o) {
        return gson.toJson(o);
    }

}

