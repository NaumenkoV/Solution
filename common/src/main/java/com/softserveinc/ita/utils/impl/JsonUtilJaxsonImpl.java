package com.softserveinc.ita.utils.impl;

import com.softserveinc.ita.utils.JsonUtil;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JsonUtilJaxsonImpl implements JsonUtil {
	ObjectMapper mapper = new ObjectMapper();
	@Override
	public <T> T fromJson(String s, Class<T> classOfT) {
		try {
			return mapper.readValue(s, classOfT);
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public String toJson(Object o) {
		try {
			return mapper.writeValueAsString(o);
		} catch (IOException e) {
			return null;
		}
	}
}
