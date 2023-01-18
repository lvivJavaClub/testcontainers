package ua.lviv.javaclub.testcontainers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.DefaultRecordingFileFactory;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class SeleniumContainerTest
extends BaseTest
    {

    @Container
    private static final  BrowserWebDriverContainer<?> CHROME = new BrowserWebDriverContainer<>()
        .withCapabilities(new ChromeOptions())
        .withAccessToHost(true)
        .withRecordingMode(RECORD_ALL, new File("build"))
        .withRecordingFileFactory(new DefaultRecordingFileFactory());

    @LocalServerPort
	private int port;

    @Test
    @SneakyThrows
    void seleniumTest() {
        org.testcontainers.Testcontainers.exposeHostPorts(port);

        RemoteWebDriver driver = new RemoteWebDriver(CHROME.getSeleniumAddress(), new ChromeOptions());
        driver.get("http://host.testcontainers.internal:" + port + "/users/1");
    }
}
