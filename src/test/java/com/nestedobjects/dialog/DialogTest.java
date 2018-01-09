package com.nestedobjects.dialog;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.nestedobjects.dialog.Dialog;

public class DialogTest {
	@Before
	public void setup() {
		Dialog.init();
	}

	@Test
	public void sequenceTest() {
		Dialog.log("one");
		Dialog.log("two");
		System.out.println(Dialog.get());
		assertEquals("{\"map\":{},\"list\":[\"one\",\"two\"]}", Dialog.get());
	}

	@Test
	public void nameValueTest() {
		Dialog.log("hip", "hop");
		System.out.println(Dialog.get());
		assertEquals("{\"map\":{\"hip\":\"hop\"},\"list\":[]}", Dialog.get());
	}
	@Test
	public void bothTest() {
		Dialog.log("hip", "hop");
		Dialog.log("one");
		Dialog.log("two");
		System.out.println(Dialog.get());
		assertEquals("{\"map\":{\"hip\":\"hop\"},\"list\":[\"one\",\"two\"]}", Dialog.get());
	}
	@Test
	public void isEnabledPositiveTest() {
		if (Dialog.isEnabled()) {
			Dialog.log("happy");
		}
		System.out.println(Dialog.get());
		assertEquals("{\"map\":{},\"list\":[\"happy\"]}", Dialog.get());
	}

}
