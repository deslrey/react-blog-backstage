package org.deslre;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class ReactBlogBootApplicationTests {

    @Test
    void name() {
        log.info(this.toString());

    }
}
