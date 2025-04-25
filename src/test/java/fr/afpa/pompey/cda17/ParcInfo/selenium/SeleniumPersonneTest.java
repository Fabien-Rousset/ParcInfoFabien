//package fr.afpa.pompey.cda17.ParcInfo.selenium;
//
//import org.junit.jupiter.api.*;
//import org.openqa.selenium.Alert;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxOptions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.util.Assert;
//
//import java.time.Duration;
//import java.util.List;
//import java.util.Objects;
//
///**
// * Selenium tests for verifying the structure and functionality of the Personnes page.
// * Validates UI components including table structure, action buttons, and creation flow.
// */
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//public class SeleniumPersonneTest {
//    // Configuration constants
//    private static final String PERSONNES_URL = "http://localhost:8081/personnes";
//    private static final String CREATE_BUTTON_TEXT = "Ajouter une nouvelle personne";
//    private static final String CREATE_PAGE_URL = "http://localhost:8081/personnes/create";
//    private static final String EDIT_ICON_HTML = "<span class=\"material-symbols-outlined\">edit</span>";
//    private static final String DELETE_ICON_HTML = "<span class=\"material-symbols-outlined\">delete</span>";
//    private static final int EXPECTED_HEADER_COUNT = 5;
//    private static final int EXPECTED_ROW_CELL_COUNT = 6;
//    private static final int EXPECTED_ACTION_BUTTONS = 2;
//
//    private WebDriver driver;
//
//    /**
//     * Initializes Firefox WebDriver before each test.
//     * Configures browser options and sets up a fresh session.
//     */
//    @BeforeEach
//    public void setup() {
//        FirefoxOptions options = new FirefoxOptions();
//        // Uncomment for headless execution: options.addArguments("--headless");
//        driver = new FirefoxDriver(options);
//    }
//
//    /**
//     * Ensures proper cleanup by terminating the browser session after each test.
//     */
//    @AfterEach
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//
//    /**
//     * Validates core structure of the Personnes page:
//     * - Main section styling
//     * - Creation button existence and functionality
//     * - Table structure including headers and data rows
//     * - Action buttons (edit/delete) appearance and markup
//     */
//    @Test
//    public void testPersonnesPageStructureAndActions() {
//        driver.get(PERSONNES_URL);
//
//        // Verify main content section
//        WebElement mainSection = driver.findElement(By.tagName("main"));
//        String mainClasses = mainSection.getAttribute("class");
//        Assertions.assertTrue(mainClasses.contains("personnes"),
//                "Main section should have 'personnes' styling class");
//
//        // Test creation button properties
//        WebElement createButton = mainSection.findElement(By.tagName("a"));
//        Assertions.assertEquals(CREATE_PAGE_URL, createButton.getAttribute("href"),
//                "Create button should point to personne creation page");
//        Assertions.assertEquals(CREATE_BUTTON_TEXT, createButton.getText(),
//                "Create button should display correct text");
//
//        // Validate table structure
//        WebElement dataTable = driver.findElement(By.tagName("table"));
//        WebElement tableBody = dataTable.findElement(By.tagName("tbody"));
//        List<WebElement> tableRows = tableBody.findElements(By.tagName("tr"));
//
//        // Verify header count
//        List<WebElement> headerCells = dataTable.findElements(By.tagName("th"));
//        Assertions.assertEquals(EXPECTED_HEADER_COUNT, headerCells.size(),
//                "Table should contain " + EXPECTED_HEADER_COUNT + " column headers");
//
//        // Verify row cell count (assumes at least one data row exists)
//        WebElement firstDataRow = tableRows.getFirst();
//        List<WebElement> rowCells = firstDataRow.findElements(By.tagName("td"));
//        Assertions.assertEquals(EXPECTED_ROW_CELL_COUNT, rowCells.size(),
//                "Data rows should contain " + EXPECTED_ROW_CELL_COUNT + " cells");
//
//        // Validate action buttons in last cell
//        WebElement actionCell = rowCells.getLast();
//        List<WebElement> actionButtons = actionCell.findElements(By.tagName("a"));
//        Assertions.assertEquals(EXPECTED_ACTION_BUTTONS, actionButtons.size(),
//                "Action cell should contain " + EXPECTED_ACTION_BUTTONS + " buttons");
//
//        // Verify edit button properties
//        WebElement editButton = actionButtons.getFirst();
//        Assertions.assertEquals("warn", editButton.getAttribute("class"),
//                "Edit button should have 'warn' styling class");
//        Assertions.assertEquals(EDIT_ICON_HTML,
//                Objects.requireNonNull(editButton.getAttribute("innerHTML")).trim(),
//                "Edit button should contain correct icon markup");
//
//        // Verify delete button properties
//        WebElement deleteButton = actionButtons.getLast();
//        Assertions.assertEquals("danger", deleteButton.getAttribute("class"),
//                "Delete button should have 'danger' styling class");
//        Assertions.assertEquals(DELETE_ICON_HTML,
//                Objects.requireNonNull(deleteButton.getAttribute("innerHTML")).trim(),
//                "Delete button should contain correct icon markup");
//    }
//
//    @Test
//    public void testPersonnesCreation(){
//        final String PRENOM = "Alain";
//        final String NOM = "Chabat";
//        final String ADRESSE = "5 rue des Nuls";
//        final String TEL = "0384215487";
//
//        driver.get(PERSONNES_URL);
//
//        WebElement mainSection = driver.findElement(By.tagName("main"));
//        WebElement createButton = mainSection.findElement(By.tagName("a"));
//        createButton.click();
//        Assertions.assertEquals(CREATE_PAGE_URL, driver.getCurrentUrl());
//
//        driver.findElement(By.id("prenomInput")).sendKeys(PRENOM);
//        driver.findElement(By.id("nomInput")).sendKeys(NOM);
//        driver.findElement(By.id("adresseInput")).sendKeys(ADRESSE);
//        driver.findElement(By.id("telephoneInput")).sendKeys(TEL);
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dateNaissanceInput"))).sendKeys("1958-11-24");
//
//        driver.findElement(By.tagName("button")).click();
//
//        WebElement row = driver.findElement(By.cssSelector("table tbody " +
//                "tr:last-child"));
//
//        List<WebElement> cells = row.findElements(By.tagName("td"));
//        Assertions.assertEquals(cells.size(), EXPECTED_ROW_CELL_COUNT);
//        Assertions.assertEquals(PRENOM, cells.get(0).getText());
//        Assertions.assertEquals(NOM, cells.get(1).getText());
//        Assertions.assertEquals(ADRESSE, cells.get(2).getText());
//        Assertions.assertEquals(TEL, cells.get(3).getText());
//        Assertions.assertEquals("24/11/1958", cells.get(4).getText());
//
//        row.findElement(By.cssSelector("[href='#']")).click();
//        Alert alert = driver.switchTo().alert();
//        alert.accept();
//    }
//}