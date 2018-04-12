package com.xss.request.filters.service;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


@RunWith(value = Parameterized.class)
public class RansackXssTest {

    private String parameterValue;
    private String expected;

    public RansackXssTest(String parameterValue, String expected) {
        this.parameterValue = parameterValue;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"<script>alert('I am entring')</script>Jhon", "Jhon"},
                {"onload='myFunction()'Hello", "'myFunction()'Hello"},
                {"eval(\"x * y\")", ""},
                {"src='img/intro-carousel/w2.jpg'", ""},
        });
    }

    @Test
    public void testRansackXssValue() {
        RansackXss ransackXss = new DefaultRansackXssImpl();

        String filteredValue = ransackXss.ransackXss(parameterValue);

        assertNotNull(filteredValue);
        assertThat(filteredValue, Is.is(expected));
    }
}
