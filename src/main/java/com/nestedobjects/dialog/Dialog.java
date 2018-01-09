package com.nestedobjects.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A Dialog consists of both a list of s log statements, and a map of name-value
 * pairs. This list of log statements are used to say in non-technical terms
 * what the software is doing. The name-value pairs, however, are used to
 * identify outcomes and are used to create expected results when creating
 * Specification by Example views.
 * 
 * Statements are only written if the Dialog.init() method was called, so in a
 * production environment, the Dialog will not be executed. Use the isEnabled()
 * method to avoid string concatenation and object creation.
 * 
 * This class will copy the log statements to the current thread.
 * 
 * 
 *
 */
public class Dialog {

	private static boolean shouldLog = false;
	private static ObjectMapper objectMapper = new ObjectMapper();
	private static final ThreadLocal<LogData> log = new ThreadLocal<LogData>();

	/**
	 * Turn on logging and initialize.
	 */
	public static void init() {
		Dialog.log.set(new LogData());
		Dialog.shouldLog = true;
	}

	public static void log(String statement) {
		if (Dialog.shouldLog) {
			Dialog.log.get().getArray().add(statement);
		}
	}

	public static void log(String name, String value) {
		if (Dialog.shouldLog) {
			Dialog.log.get().getMap().put(name, value);
		}
	}

	public static boolean isEnabled() {
		return Dialog.shouldLog;
	}

	/*
	 * Returns a JSON string of the form: { "array": ["first log statement", *
	 * "second log statement"], "map": { "key1": "value1", "key2": "value2" } }
	 */
	public static String get() {
		try {
			return objectMapper.writeValueAsString(log.get());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map getMap() {
		return log.get().getMap();
	}

	public static List<String> getArray() {
		return log.get().getArray();
	}

	static class LogData {
		List<String> array = new ArrayList<String>();
		Map<String, String> map = new HashMap<String, String>();

		public List<String> getArray() {
			return this.array;
		}

		public Map<String, String> getMap() {
			return this.map;
		}
	}

}
