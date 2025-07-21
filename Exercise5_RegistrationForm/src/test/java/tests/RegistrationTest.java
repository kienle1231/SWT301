package tests;

import org.junit.jupiter.api.*;
import pages.RegistrationPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Registration Form Test")
public class RegistrationTest extends BaseTest {

    static RegistrationPage regPage;

    @BeforeAll
    static void init() {
        regPage = new RegistrationPage(driver);
    }

    @Test
    @Order(1)
    @DisplayName("Register successfully with valid data")
    void testRegisterSuccess() {
        regPage.navigate();
        regPage.enterFirstName("John");
        regPage.enterLastName("Doe");
        regPage.selectGenderMale();
        regPage.enterMobile("0987654321");
        regPage.uploadFile(System.getProperty("user.dir") + "/src/test/resources/avatar.jpg");
        regPage.enterAddress("123 Automation Lane");
        regPage.submit();

        String popup = regPage.getPopupText();
        assertTrue(popup.contains("Thanks for submitting the form"));
    }
}
