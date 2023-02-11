package com.xss.filter.httpwrapper;

import com.xss.filter.config.httpwrapper.CaptureRequestWrapper;
import com.xss.filter.service.RansackXssService;
import com.xss.filter.service.ServletRequestXssFilterManager;
import com.xss.filter.service.impl.DefaultRansackXssServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CaptureRequestWrapperTest {

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private ServletRequestXssFilterManager servletRequestXssFilterManager;

    @Test
    public void testShouldRansackXssFromHeaderAndParameter() {
        when(httpServletRequest.getHeader("alerts")).thenReturn("<script>devilInAction();</script>123");
        when(httpServletRequest.getParameter("userName")).thenReturn(
            "<script>alert(\"fun time\");</script>Bhushan");

        RansackXssService ransackXssService = new DefaultRansackXssServiceImpl();
        CaptureRequestWrapper captureRequestWrapper = new CaptureRequestWrapper(httpServletRequest,
            ransackXssService, servletRequestXssFilterManager);

        Assertions.assertEquals("123", captureRequestWrapper.getHeader("alerts"));
        Assertions.assertEquals("Bhushan", captureRequestWrapper.getParameter("userName"));
    }

}
