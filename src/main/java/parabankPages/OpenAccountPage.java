package parabankPages;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OpenAccountPage {
	
	private final static String BODY_PANEL = "#bodyPanel ",
			RIGHT_PANEL = "#rightPanel ";
	
	public OpenAccountPage() {
		open("/parabank/openaccount.htm");
	}
	
	public String openNewAccountAndReturnAccountId() {
		$(BODY_PANEL + RIGHT_PANEL + "h1")
				.shouldBe(visible)
				.shouldHave(text("Open New Account"));
		
		$(BODY_PANEL + RIGHT_PANEL + "#type").shouldBe(visible, clickable).click();
		$(BODY_PANEL + RIGHT_PANEL + "#type option[value='1']").shouldBe(visible, clickable).click();
		
		$(BODY_PANEL + RIGHT_PANEL + "input[type=submit]").shouldBe(visible, clickable).click();
		
		$(BODY_PANEL + RIGHT_PANEL + "h1").shouldBe(visible).shouldHave(text("Account Opened!"));
		$(BODY_PANEL + RIGHT_PANEL + "p").shouldBe(visible).shouldHave(text("Congratulations, your account is now open."));
		
		System.out.println("Customer successfully opened a new account - PASS!");
		
		return $("#bodyPanel #rightPanel #newAccountId").shouldBe(visible, clickable).getText();
	}
	
	public AccountActivityPage clickOnAccountId(String accountId) {
		$(BODY_PANEL + RIGHT_PANEL + "#newAccountId").shouldBe(visible, clickable).click();
		
		return new AccountActivityPage(accountId);
	}
	
}
