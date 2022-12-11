package com.xss.filters.httpwrapper;

import static org.mockito.Mockito.when;

import com.xss.filters.service.DefaultRansackXssImpl;
import com.xss.filters.service.RansackXss;
import com.xss.filters.service.ServletRequestXssFilterManager;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    RansackXss ransackXss = new DefaultRansackXssImpl();
    CaptureRequestWrapper captureRequestWrapper = new CaptureRequestWrapper(httpServletRequest,
        ransackXss, servletRequestXssFilterManager);

    Assertions.assertEquals("123", captureRequestWrapper.getHeader("alerts"));
    Assertions.assertEquals("Bhushan", captureRequestWrapper.getParameter("userName"));
  }

}
