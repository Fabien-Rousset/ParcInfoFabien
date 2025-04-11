package fr.afpa.pompey.cda17.ParcInfo.selenium;

import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.List;

/**
 * Selenium integration tests for critical elements on the application's index page.
 * Uses Firefox browser for testing. Ensures core UI components behave as expected.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumIndexTest {
    // Configuration constants
    private static final String APP_URL = "http://localhost:8081/";
    private static final String EXPECTED_TITLE = "Accueil - Parcinfo";
    private static final String CONTACT_EMAIL = "mailto:contact@parcinfo.fr";
    private static final int EXPECTED_MENU_LINKS = 4;
    private static final int EXPECTED_VALID_MENU_LINKS = 2;

    private WebDriver driver;

    /**
     * Initializes Firefox WebDriver before each test.
     * Configures browser options (e.g., potential headless mode, preferences).
     * SpringBootTest annotation ensures the app context is loaded on defined port 8081.
     */
    @BeforeEach
    public void setup() {
        FirefoxOptions options = new FirefoxOptions();
        // Example: options.addArguments("--headless"); // For CI/CD environments
        driver = new FirefoxDriver(options);
    }

    /**
     * Terminates WebDriver session after each test.
     * Ensures no lingering processes consume resources.
     */
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();  // Properly closes all windows and ends session
        }
    }

    /**
     * Validates core index page elements:
     * - Page title correctness
     * - Footer contact link text and functionality
     * - Header menu link count and validity
     */
    @Test
    public void testIndexPageCriticalElements() {
        driver.get(APP_URL);

        // Validate page title matches expected
        String actualTitle = driver.getTitle();
        Assertions.assertEquals(EXPECTED_TITLE, actualTitle,
                "Index page title should match '" + EXPECTED_TITLE + "'");

        // Test footer contact link
        WebElement contactLink = driver.findElement(By.cssSelector("footer ul li a"));
        Assertions.assertEquals("Contact", contactLink.getText(),
                "Footer contact link should display 'Contact' text");

        String hrefValue = contactLink.getAttribute("href");
        Assertions.assertNotNull(hrefValue, "Contact link href should not be null");
        Assertions.assertTrue(hrefValue.contains(CONTACT_EMAIL),
                "Contact link href should direct to '" + CONTACT_EMAIL + "'");

        // Validate header menu structure
        List<WebElement> menuLinks = driver.findElements(By.cssSelector("header ul li a"));
        List<WebElement> validMenuLinks = driver.findElements(By.cssSelector("header ul li a[href]"));

        Assertions.assertEquals(EXPECTED_MENU_LINKS, menuLinks.size(),
                "Header should contain " + EXPECTED_MENU_LINKS + " menu items");
        Assertions.assertEquals(EXPECTED_VALID_MENU_LINKS, validMenuLinks.size(),
                "Header should contain " + EXPECTED_VALID_MENU_LINKS + " menu items with valid hrefs");
    }
}