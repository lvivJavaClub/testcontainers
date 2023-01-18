package ua.lviv.javaclub.testcontainers;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.ClassRule;
import org.testcontainers.containers.MySQLContainer;

import static org.assertj.core.api.Assertions.assertThat;

public class MySQLContainerJUnit4Tests {

    // will be shared between test methods
    @ClassRule
    public static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>("mysql:5.7.34");

    @BeforeClass
    public static void beforeClass() {
        MY_SQL_CONTAINER.start();
    }

    @AfterClass
    public static void afterClass() {
        MY_SQL_CONTAINER.stop();
    }

    @Test
    public void test() {
        assertThat(MY_SQL_CONTAINER.isRunning()).isTrue();
    }
}
