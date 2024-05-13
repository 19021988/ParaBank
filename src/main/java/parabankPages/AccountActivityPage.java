package parabankPages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.Objects;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class AccountActivityPage {
	
	private final static String BODY_PANEL = "#bodyPanel ",
			RIGHT_PANEL = "#rightPanel ";
	
	private final String[] ids = new String[] {"#accountId", "#accountType", "#balance", "#availableBalance", "#month", "#transactionType"};
	
	public AccountActivityPage(String accountID) {
		open("/parabank/activity.htm?id=" + accountID);
	}
	
	@Step("Verify account activity page main elements being displayed")
	public AccountActivityPage verifyPageDisplay() {
		$$(BODY_PANEL + RIGHT_PANEL + "h1").shouldHave(texts("Account Details", "Account Activity"));
		
		$$(BODY_PANEL + RIGHT_PANEL + "tbody td:first-child").shouldHave(texts(
				"Account Number:",
				"Account Type:",
				"Balance:",
				"Available:",
				"Activity Period:",
				"Type:", ""
		));
		
		for (String id : ids) {
			$(BODY_PANEL + RIGHT_PANEL + "tbody " + id).shouldBe(visible);
		}
		
		String url = webdriver().driver().url();
		String accountId = url.substring(url.length() - 5);
		String balance = $(BODY_PANEL + RIGHT_PANEL + "tbody #balance").getText();
		
		//($(BODY_PANEL + RIGHT_PANEL + "tbody " + ids[0]).getText().equals(accountId));
		assert ($(BODY_PANEL + RIGHT_PANEL + "tbody " + ids[3]).getText().equals(balance));
		
		return this;
	}
	
	@Step("Click log out button - log out")
	public void clickLogOut() {
		$$("#bodyPanel #leftPanel li").findBy(text("Log Out")).click();
	}
	
	@Step("Verify transaction data")
	public void verifyTransaction() {
		$$("#bodyPanel #rightPanel #transactionTable thead th").shouldHave(texts(
				"Date", "Transaction", "Debit (-)", "Credit (+)"));
		
		$("#bodyPanel #rightPanel #transactionTable tbody tr:first-child td:nth-child(2)").click();
		
		$("#bodyPanel #rightPanel h1").shouldBe(visible).shouldHave(text("Transaction Details"));
		
		$$("#bodyPanel #rightPanel table tbody b").shouldHave(texts(
				"Transaction ID:", "Date:", "Description:", "Type:", "Amount:"));
		
		String transactionId =
		webdriver().driver().getAndCheckWebDriver().findElement(with(By.cssSelector("#bodyPanel #rightPanel table tbody td"))
				.toRightOf(By.xpath("//b[contains(text(), 'Transaction ID:')]"))).getText();
		
		String url = webdriver().driver().url();
		String transactionIdFromUrl = url.substring(url.length() - 5);
		
		assert (Objects.equals(transactionId, transactionIdFromUrl));
	}
}
