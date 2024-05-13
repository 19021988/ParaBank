package jptesting;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {
	
	public BaseTest() {
		Selenide.open();
	}
	protected final static PropertiesFile props = new PropertiesFile();
	public static String getConfig(String str) {
		return props.readProperties(str);
	}
	
	@BeforeAll
	public static void init() throws MalformedURLException {
		switch (getConfig("envir")) {
			case "docker":
				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setBrowserName(getConfig("browserName"));
				RemoteWebDriver driver = new RemoteWebDriver(new URL(getConfig("URL")),dc);
				WebDriverRunner.setWebDriver(driver);
				Configuration.baseUrl = getConfig("openUrl");
				setupAllureReports();
				break;
			case "local":
				WebDriverManager.chromedriver().setup();
				Configuration.browser = getConfig("browserName");
				Configuration.browserSize = getConfig("browserSize");
				Configuration.headless = Boolean.parseBoolean(getConfig("headless"));
				Configuration.baseUrl = getConfig("openUrl");
				setupAllureReports();
				break;
		}
	}
	
	@AfterAll
	public static void tearDown() {
		WebDriverRunner.closeWebDriver();
	}
	
	private static void setupAllureReports() {
		SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
				.screenshots(true)
				.savePageSource(true)
		);
	}
}
