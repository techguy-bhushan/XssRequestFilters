package com.xss.filters.service;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class RansackXssTest {

  private static Stream<Arguments> xssProvider() {
    return Stream.of(
        Arguments.of("<script>alert('I am entring')</script>Jhon", "Jhon"),
        Arguments.of("onload='myFunction()'Hello", "'myFunction()'Hello"),
        Arguments.of("eval(\"x * y\")", ""),
        Arguments.of("src='img/intro-carousel/w2.jpg'", ""),
        Arguments.of("<iframe src=\"https://www.youtube.com/watch?v=djU4Lq_5EaM\" />",
            "<iframe  />"),
        Arguments.of("<div style=\"width: 200;\">Selection</div>", ""),
        Arguments.of("<script>var currentValue='25;</script>", ""),
        Arguments.of("<div>UNTRUSTED HTML</div>", ""),
        Arguments.of("<input type=\"text\" value=\"fun time\"/>", "")
    );
  }

  @MethodSource("xssProvider")
  @ParameterizedTest(name = "{index} => a={0}, b={1}")
  public void testRansackXssValue(String input, String expectedResult) {
    RansackXss ransackXss = new DefaultRansackXssImpl();

    String filteredValue = ransackXss.ransack(input);

    Assertions.assertNotNull(filteredValue);
    Assertions.assertEquals(expectedResult, filteredValue);
  }

}
