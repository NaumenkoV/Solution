package com.softserveinc.ita.utils;

public interface JsonUtil {
	<T>  T fromJson(String s, Class<T> classOfT);

	String toJson(Object o);
}
