package com.nestedobjects.sutproxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StringToObjectConverter<T> implements Converter<String, T> {

	@Autowired
	private ObjectMapper objectMapper;

	private Class<T> type;

	public StringToObjectConverter(Class clazz) {
		this.type = clazz;
	}

	public T convert(String source) {
		try {

			return objectMapper.readValue(source, type);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
}