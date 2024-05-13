package parabankPages;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AccountsOverviewPage {
	
	ElementsCollection accountServices = $$( BODY_PANEL + LEFT_PANEL + "li");
	
	public AccountsOverviewPage() {
		open("/parabank/overview.htm");
	}
	private final static String BODY_PANEL = "#bodyPanel ",
			LEFT_PANEL = "#leftPanel ",
			RIGHT_PANEL = "#rightPanel ",
			ACCOUNT_TABLE = "#accountTable ";
	
	@Step("Verify that customer has successfully logged in")
	public void assertBeingLoggedIn() {
		$(BODY_PANEL + LEFT_PANEL + "p.smallText")
				.shouldBe(visible);
		
		$(BODY_PANEL + LEFT_PANEL + "h2")
				.shouldBe(visible)
				.shouldHave(text("Account Services"));
		
		accountServices.shouldHave(texts("Open New Account",
				"Accounts Overview",
				"Transfer Funds",
				"Bill Pay",
				"Find transactions",
				"Update contact info",
				"Request Loan",
				"Log Out"));
		
		accountServices.findBy(text("Log Out")).shouldBe(visible, clickable).click();
	}
	
	@Step("Verify account balance display and click on the accountId link")
	public AccountActivityPage verifyAccountBalanceDisplayAndClickAccountId() {
		$(BODY_PANEL + RIGHT_PANEL + "h1").shouldBe(visible);
		
		ElementsCollection accountTable = $$( BODY_PANEL + RIGHT_PANEL + ACCOUNT_TABLE + "th");
		
		accountTable.get(0).shouldHave(text("Account"));
		accountTable.get(1).shouldHave(text("Balance*"));
		accountTable.get(2).shouldHave(text("Available Amount"));
		
		String accountID = $(BODY_PANEL + RIGHT_PANEL + ACCOUNT_TABLE + "tbody tr:first-child td:first-child a").getText();
		System.out.println(accountID);
		$(BODY_PANEL + RIGHT_PANEL + ACCOUNT_TABLE + "tbody tr:first-child td:first-child a").click();
		
		return new AccountActivityPage(accountID);
		
	}
	
	@Step("Click on the 'Open New Account' link")
	public OpenAccountPage clickOpenNewAccountLink() {
		accountServices.findBy(text("Open New Account")).click();
		
		return new OpenAccountPage();
	}
}
