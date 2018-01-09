package com.nestedobjects.ci;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class JsonProvider {
	@Autowired
	ObjectMapper objectMapper;

	private String[] provideObjects(String url) {
		RestTemplate template = new RestTemplate();
		String[] str = null;
		try {
			str = template.getForObject(url, String[].class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	private Class[] getMethodParameters(Class testMethodClass, String testDataMethod){
		
		
		Method [] methods = testMethodClass.getDeclaredMethods();
		Method selectedMethod = null;
		for(int m = 0; m < methods.length; m++){
			if(testDataMethod.equalsIgnoreCase(methods[m].getName())){
				selectedMethod = methods[m];
				break;
			}
		}
		if(selectedMethod == null){
			System.out.println("The method name " + testDataMethod + " did not correspond to a test method.");
			throw new RuntimeException("method " + testDataMethod + " not found");
		}
		Class[] paramTypes = selectedMethod.getParameterTypes();
		return paramTypes;
	}
	public Object[] getObjects(Class testMethodClass, String testDataMethod, String url) {
		Class [] paramTypes = this.getMethodParameters(testMethodClass, testDataMethod);
		String[] hashMapArray = provideObjects(url);
		List returnList = new ArrayList();
		for (int row = 0; row < hashMapArray.length; row++) {
			List rowList = new ArrayList<>();
			String jsonString = hashMapArray[row];
			Map<String, String> jsonMap;
			try {
				jsonMap = this.objectMapper.readValue((String) jsonString, HashMap.class);
			} catch (IOException e1) {
				System.out.println("Could not convert json string to map: " + jsonString);
				e1.printStackTrace();
				rowList.add(null);
				continue;
			}
			for (int n = 0; n < paramTypes.length; n++) {
				Object object = null;
				Class clazz = paramTypes[n];
				try {
					String bodyString = jsonMap.get(clazz.getSimpleName());
					object = this.objectMapper.readValue((String) bodyString, clazz);
				} catch (Exception e) {
					System.out.println("Could not create class " + clazz.getSimpleName() + " key not found in UDT map." );
					e.printStackTrace();
				}
				rowList.add(object);
			}
			Object[] objectRow = rowList.toArray(new Object[0]);
			returnList.add(objectRow);
		}

		return returnList.toArray(new Object[0]);
	}

}
