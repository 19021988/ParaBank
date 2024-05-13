package parabankPages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ParaBankHomePage {
	private final static String BODY_PANEL = "#bodyPanel ",
			LEFT_PANEL = "#leftPanel ",
			RIGHT_PANEL = "#rightPanel ",
			LOGIN_PANEL = "#loginPanel ";
	private final SelenideElement headerText = $("#mainPanel #topPanel p");
	private final SelenideElement onlineBankingButton = $("#loginPanel input[type=submit]");
	
	public ParaBankHomePage() {
		open("/parabank/index.htm");
	}
	
	@Step("Assert header text")
	public void assertHeaderText(String expectedText) {
		headerText.shouldHave(text(expectedText));
	}
	
	@Step("Assert Login button")
	public ParaBankHomePage assertOnlineBankingLoginButtonAndClick() {
		onlineBankingButton
				.shouldBe(visible, clickable)
				.click();
		
		$(BODY_PANEL + RIGHT_PANEL + "h1")
				.shouldHave(text("ERROR!"));
		$(BODY_PANEL + RIGHT_PANEL + "p")
				.shouldHave(text("Please enter a username and password."));
		
		return this;
	}
	
	@Step("Click on the 'Register' link")
	public RegistrationPage clickRegistrationLink() {
		return new RegistrationPage();
	}
	
	public ParaBankHomePage enterCredentials(String username, String password) {
		$(BODY_PANEL + LEFT_PANEL + "h2")
				.shouldHave(text("Customer Login"));
		$(BODY_PANEL + LEFT_PANEL + LOGIN_PANEL + "form input[name=username]")
				.shouldBe(editable)
				.setValue(username);
		$(BODY_PANEL + LEFT_PANEL + LOGIN_PANEL + "form input[name=password]")
				.shouldBe(editable)
				.setValue(password);
		
		return this;
	}
	
	@Step("Click on the login button")
	public AccountsOverviewPage clickLoginButton() {
		$(BODY_PANEL + LEFT_PANEL + LOGIN_PANEL + "form input[type=submit]")
				.shouldBe(visible, clickable)
				.click();
		
		return new AccountsOverviewPage();
	}
	
}
