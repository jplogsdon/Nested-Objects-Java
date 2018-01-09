package com.nestedobjects.ci;

import org.junit.ClassRule;
import org.junit.Rule;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

public class JUnitBase {
	// The SpringClssRule and StringMethodRule classes enable JUnit to use
		// a Spring Application context without having to have a Spring runner.
		// These classes are necessary here because we are using the
		// JUnitParmsRunner runner. We just add them, but don't directly use them.
		//@ClassRule
		//public static final SpringClassRule SCR = new SpringClassRule();
		//@Rule
		//public final SpringMethodRule springMethodRule = new SpringMethodRule();
	private TestContextManager testContextManager;


	public JUnitBase(){
	    this.testContextManager = new TestContextManager(getClass());
	    try {
			this.testContextManager.prepareTestInstance(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
}
