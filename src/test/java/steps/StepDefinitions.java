package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.FoodPage;
import utils.TestSql;

import java.util.concurrent.TimeUnit;

public class StepDefinitions {
    private WebDriver driver;
    private FoodPage foodPage;
    private TestSql testSql;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/Users/ageev/Downloads/first-web-test/src/test/resources/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("http://localhost:8080/food");
        foodPage = new FoodPage(driver);
        testSql = new TestSql();
    }

    @Given("I navigate to the food page")
    public void iNavigateToTheFoodPage() {
        driver.get("http://localhost:8080/food");
    }

    @When("I click the {string} button")
    public void iClickTheButton(String buttonName) {
        if (buttonName.equals("Add")) {
            foodPage.ClickAdd();
        }
    }

    @When("I enter the name {string}")
    public void iEnterTheName(String name) {
        foodPage.inputName(name);
    }

    @When("I select type {string}")
    public void iSelectType(String type) {
        foodPage.Type(type);
    }

    @When("I mark the item as exotic")
    public void iMarkTheItemAsExotic() {
        foodPage.setExot();
    }

    @When("I save the item")
    public void iSaveTheItem() {
        foodPage.SaveItem();
    }

    @Then("the item {string} with type {string} and exotic {string} should be present in the list")
    public void theItemShouldBePresentInTheList(String name, String type, String exotic) {
        foodPage.checkNewItem(name, type, exotic);
    }

    @Then("the database should contain the item {string} with type {string} and exotic {string}")
    public void theDatabaseShouldContainTheItem(String name, String type, String exotic) {
        testSql.searchItemByName(name, type, exotic);
    }

    @Then("the item {string} should not have duplicates in the list")
    public void theItemShouldNotHaveDuplicatesInTheList(String name) {
        foodPage.checkNewSameItem(name);
    }

    @Then("the database should not contain a single entry for {string} with type {string}")
    public void theDatabaseShouldContainASingleEntryFor(String name, String type) {
        testSql.getCountOfFood(name, type);
    }

    @Given("I clean up unwanted food items")
    public void iCleanUpUnwantedFoodItems() {
        testSql.deleteUnwantedFoods();
        foodPage.ClearAll();
    }


    @After
    public void tearDown() {
        driver.quit();
    }
}
