package com.example.helloworld;

import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<HelloWorldApplication> {
    public ApplicationTest() {
        super(HelloWorldApplication.class);
    }

    public void testGCMIdSet() {
        assertEquals(getApplication().ANALYTICS_ENABLED, false);
        assertEquals(getApplication().LOCATION_ENABLED, false);
        assertEquals(getApplication().CLOUD_PAGES_ENABLED, false);
    }

}

