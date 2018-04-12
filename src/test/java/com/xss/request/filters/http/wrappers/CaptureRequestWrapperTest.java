package com.xss.request.filters.http.wrappers;

import com.xss.request.filters.service.DefaultRansackXssImpl;
import com.xss.request.filters.service.RansackXss;
import org.hamcrest.core.Is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CaptureRequestWrapperTest {
    @Mock
    private HttpServletRequest httpServletRequest;

    @Test
    public void test() {
        when(httpServletRequest.getHeader("alerts")).thenReturn("<script>devilInAction();</script>123");
        when(httpServletRequest.getParameter("userName")).thenReturn("<script>alert(\"fun time\");</script>Bhushan");

        RansackXss ransackXss = new DefaultRansackXssImpl();
        CaptureRequestWrapper captureRequestWrapper = new CaptureRequestWrapper(httpServletRequest, ransackXss);

        assertThat(captureRequestWrapper.getHeader("alerts"), Is.is("123"));
        assertThat(captureRequestWrapper.getParameter("userName"), Is.is("Bhushan"));
    }
}
