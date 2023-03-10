package bankproject.feature.manager;

import bankproject.WebHook;
import bankproject.page_object_model.AddCustomerPage;
import bankproject.page_object_model.CustomerLoginPage;
import bankproject.page_object_model.ManagerPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Feature("Adding Customer Tests")
@Test(description = "Adding Customer Tests")
public class AddingCustomerTest extends WebHook {
    AddCustomerPage addCustomerPage;

    String errorMessage = "Please fill out this field.";

    @BeforeMethod
    public void classSetUp() {
        ManagerPage managerPage = new ManagerPage(driver);
        managerPage.goTo();
        addCustomerPage = managerPage.goToAddCustomerPage();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Manager adds customer with valid customer information")
    public void managerAddsCustomerWithValidCustomerInformation() {
        String firstName = "W33";
        String lastName = "Haa";
        String postCode = "123";
        addCustomerPage.addCustomer(firstName, lastName, postCode);
        addCustomerPage.VerifyAlertCustomerIsAddedAndCloseTheAlert();

        CustomerLoginPage customerLoginPage = new CustomerLoginPage(driver);
        customerLoginPage.goTo();
        String newCustomerName = firstName + " " + lastName;
        customerLoginPage.login(newCustomerName).verifyTheCustomerIsLoggedIn(newCustomerName);
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Manager is unable to add customer with the empty first name")
    public void managerIsUnableToAddCustomerWithTheEmptyFirstName() {
        String firstName = "";
        String lastName = "Haa";
        String postCode = "123";
        addCustomerPage.addCustomer(firstName, lastName, postCode);
        addCustomerPage.verifyFirstNameValidationMessage(errorMessage);
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Manager is unable to add customer with the empty last name")
    public void managerIsUnableToAddCustomerWithTheEmptyLastName() {
        String firstName = "W33";
        String lastName = "";
        String postCode = "123";
        addCustomerPage.addCustomer(firstName, lastName, postCode);
        addCustomerPage.verifyLastNameValidationMessage(errorMessage);
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Manager is unable to add customer with the empty post code")
    @Flaky
    public void managerIsUnableToAddCustomerWithTheEmptyPostCode() {
        String firstName = "W33";
        String lastName = "Haa";
        String postCode = "";
        addCustomerPage.addCustomer(firstName, lastName, postCode);
        addCustomerPage.verifyPostCodeValidationMessage(errorMessage);
    }
}
