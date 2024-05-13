package parabankPages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.editable;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationPage extends ParaBankHomePage{
	
	private final static String BODY_PANEL = "#bodyPanel ",
	RIGHT_PANEL = "#rightPanel ",
	LEFT_PANEL = "#leftPanel ",
	REGISTRATION_FORM = "#customerForm ";
	public RegistrationPage() {
		open("/parabank/register.htm");
	}
	
	@Step("Fill all customer registration related fields")
	public void fillTheRegistrationForm(String firstName, String lastName, String address, String city,
	                                    String state, String zipCode, String phoneNumber, String ssn,
	                                    String userName, String password) {
		$(RIGHT_PANEL + REGISTRATION_FORM + "input[id='customer.firstName']").shouldBe(editable).setValue(firstName);
		$(RIGHT_PANEL + REGISTRATION_FORM + "input[id='customer.lastName']").shouldBe(editable).setValue(lastName);
		$(RIGHT_PANEL + REGISTRATION_FORM + "input[id='customer.address.street']").shouldBe(editable).setValue(address);
		$(RIGHT_PANEL + REGISTRATION_FORM + "input[id='customer.address.city']").shouldBe(editable).setValue(city);
		$(RIGHT_PANEL + REGISTRATION_FORM + "input[id='customer.address.state']").shouldBe(editable).setValue(state);
		$(RIGHT_PANEL + REGISTRATION_FORM + "input[id='customer.address.zipCode']").shouldBe(editable).setValue(zipCode);
		$(RIGHT_PANEL + REGISTRATION_FORM + "input[id='customer.phoneNumber']").shouldBe(editable).setValue(phoneNumber);
		$(RIGHT_PANEL + REGISTRATION_FORM + "input[id='customer.ssn']").shouldBe(editable).setValue(ssn);
		$(RIGHT_PANEL + REGISTRATION_FORM + "input[id='customer.username']").shouldBe(editable).setValue(userName);
		
		$("#customerForm input[type='submit']").click();
		
		$(RIGHT_PANEL + REGISTRATION_FORM + "span[id='customer.password.errors'] ")
				.shouldHave(text("Password is required."));
		$(RIGHT_PANEL + REGISTRATION_FORM + "span[id='repeatedPassword.errors']")
				.shouldHave(text("Password confirmation is required."));
		System.out.println("Can't register a user without password - errorMessages received");
		
		$(RIGHT_PANEL + REGISTRATION_FORM + "input[id='customer.password']").setValue(password);
		$(RIGHT_PANEL + REGISTRATION_FORM + "input[id='repeatedPassword']").setValue(password);
		
	}
	
	@Step("user different userName in case username is taken")
	public RegistrationPage correctUserName(String userName,String password) {
		$(RIGHT_PANEL + REGISTRATION_FORM + "input[id='customer.username']").setValue(userName);
		
		$("#customerForm input[type='submit']").click();
		
		$(RIGHT_PANEL + REGISTRATION_FORM + "span[id='customer.password.errors'] ")
				.shouldHave(text("Password is required."));
		$(RIGHT_PANEL + REGISTRATION_FORM + "span[id='repeatedPassword.errors']")
				.shouldHave(text("Password confirmation is required."));
		System.out.println("Can't register a user without password - errorMessages received - Follow click inside catch");
		
		$(RIGHT_PANEL + REGISTRATION_FORM + "input[id='customer.password']").setValue(password);
		$(RIGHT_PANEL + REGISTRATION_FORM + "input[id='repeatedPassword']").setValue(password);
		
		return this;
	}
	
	@Step("verify customer being logged in")
	public void assertBeingLoggedIn(String userName) {
		$(BODY_PANEL + RIGHT_PANEL + "h1").shouldHave(text("Welcome " + userName));
		$(BODY_PANEL + RIGHT_PANEL + "p").shouldHave(text("Your account was created successfully. You are now logged in."));
		System.out.println("Customer is logged in - seeing the logIn view");
		
		$$(BODY_PANEL + LEFT_PANEL + "li").findBy(text("Log Out")).click();
		System.out.println("User logged out after successfully being registered");
	}
	
	@Step("Click on the Registration button")
	public RegistrationPage clickRegisterButton() throws Exception {
		$("#customerForm input[type='submit']").click();
		
		if ($(RIGHT_PANEL + REGISTRATION_FORM + "span[id='customer.username.errors']").isDisplayed()) {
			throw new Exception("The userName is taken");
		}
		
		return this;
	}
	
}
