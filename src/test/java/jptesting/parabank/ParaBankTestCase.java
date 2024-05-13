package jptesting.parabank;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import jptesting.BaseTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import parabankPages.*;

import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ParaBankTestCase extends BaseTest {
	private final ParaBankHomePage homePage;
	public static Map<String, String> customer = null;
	public static String USER_NAME = null;
	public static String PASSWORD = null;
	
	public ParaBankTestCase() {
		super();
		homePage = new ParaBankHomePage();
	}
	
	@Order(1)
	@Test
	@Description("Open ParaBank page and verify some elements")
	public void openParaBankPage() {
		homePage.assertHeaderText("Experience the difference");
		homePage.assertOnlineBankingLoginButtonAndClick();
		
		System.out.println("Header and button assertions passed! Customer couldn't login without credentials");
	}
	
	@Order(2)
	@Test
	@Description("Register a bank user and verify successful registration")
	public void verifyUserRegistration() throws Exception {
		RegistrationPage registration =
				homePage.clickRegistrationLink();
		
		customer = generateCustomer();
		USER_NAME = customer.get("userName");
		PASSWORD = customer.get("password");
		
		registration.fillTheRegistrationForm(
				customer.get("firstName"),
				customer.get("lastName"),
				customer.get("address"),
				customer.get("city"),
				customer.get("state"),
				customer.get("zipCode"),
				customer.get("phoneNumber"),
				customer.get("ssn"),
				USER_NAME,
				PASSWORD
		);
		
		System.out.println("userName " + USER_NAME);
		System.out.println("passWord " + PASSWORD);
		
		try {
			registration
					.clickRegisterButton()
					.assertBeingLoggedIn(USER_NAME);
		} catch (Exception e) {
			USER_NAME = RandomStringUtils.randomAlphanumeric(12);
			System.out.println(USER_NAME);
			registration
					.correctUserName(USER_NAME, PASSWORD)
					.clickRegisterButton()
					.assertBeingLoggedIn(USER_NAME);
		}
		
		System.out.println("User registration test passed!");
	}
	
	@Order(3)
	@Test
	@Description("Login with newly registered user and verify user is being logged in")
	public void verifyUserLogin() {
		AccountsOverviewPage login =
				homePage.assertOnlineBankingLoginButtonAndClick()
						.enterCredentials(USER_NAME, PASSWORD)
						.clickLoginButton();
		
		login.assertBeingLoggedIn();
		
		System.out.println("User successfully logged in");
	}
	
	@Order(4)
	@Test
	@Description("Customer logs into his personal Banking and verifies his balance")
	public void verifyAccountBalanceDisplay() {
		AccountsOverviewPage overviewPage =
				homePage.assertOnlineBankingLoginButtonAndClick()
						.enterCredentials(USER_NAME, PASSWORD)
						.clickLoginButton();
		
		AccountActivityPage activity =
			overviewPage.verifyAccountBalanceDisplayAndClickAccountId();
		
		activity.verifyPageDisplay()
				.clickLogOut();
	}
	
	@Order(5)
	@Test
	@Description("Customer open a new account and verifies his incoming transaction")
	public void verifyFundsTransfer() {
		OpenAccountPage openAccountPage =
				homePage.enterCredentials(USER_NAME, PASSWORD)
						.clickLoginButton()
						.clickOpenNewAccountLink();
		
		String accountId = openAccountPage.openNewAccountAndReturnAccountId();
		System.out.println(accountId);
		
		AccountActivityPage activityPage =
				openAccountPage.clickOnAccountId(accountId);
		
		activityPage.verifyPageDisplay()
				.verifyTransaction();
		
	}
	
	@Step("Create a customer")
	private Map<String, String> generateCustomer() {
		String firstName = RandomStringUtils.randomAlphabetic(7);
		String lastName = RandomStringUtils.randomAlphabetic(8);
		String address = RandomStringUtils.randomAlphabetic(7);
		String city = RandomStringUtils.randomAlphabetic(7);
		String state = RandomStringUtils.randomAlphabetic(7);
		String zipCode = RandomStringUtils.randomAlphabetic(5);
		String phoneNumber = RandomStringUtils.randomNumeric(7);
		String ssn = RandomStringUtils.randomNumeric(10);
		String userName = RandomStringUtils.randomAlphanumeric(7);
		String password = RandomStringUtils.randomAlphanumeric(12);
		
		return Map.of("firstName", firstName,
				"lastName", lastName,
				"address", address,
				"city", city,
				"state", state,
				"zipCode", zipCode,
				"phoneNumber", phoneNumber,
				"ssn", ssn,
				"userName",userName,
				"password", password);
	}
}

