package gift;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ApplicationTest {
  @Autowired
  private ApplicationContext context;

  @Test
  void test1(){
    assertThat(context).isNotNull();
  }

  @Test
  void test2(){
    assertThat(context).isNotNull();
  }
}
