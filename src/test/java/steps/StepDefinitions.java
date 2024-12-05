package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.FoodPage;
import utils.TestSql;

import java.util.concurrent.TimeUnit;

@Epic("Тестирование управления продуктами")
@Feature("Управление продуктами на странице")
public class StepDefinitions {
    private WebDriver driver;
    private FoodPage foodPage;
    private TestSql testSql;

    @Before
    @Step("Подготовка тестового окружения")
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
    @Step("Переход на страницу с продуктами")
    public void iNavigateToTheFoodPage() {
        driver.get("http://localhost:8080/food");
    }

    @When("I click the {string} button")
    @Step("Нажатие кнопки '{buttonName}'")
    public void iClickTheButton(String buttonName) {
        if (buttonName.equals("Add")) {
            foodPage.ClickAdd();
        }
    }

    @When("I enter the name {string}")
    @Step("Ввод имени: {name}")
    public void iEnterTheName(String name) {
        foodPage.inputName(name);
    }

    @When("I select type {string}")
    @Step("Выбор типа: {type}")
    public void iSelectType(String type) {
        foodPage.Type(type);
    }

    @When("I mark the item as exotic")
    @Step("Пометка продукта как экзотического")
    public void iMarkTheItemAsExotic() {
        foodPage.setExot();
    }

    @When("I save the item")
    @Step("Сохранение продукта")
    public void iSaveTheItem() {
        foodPage.SaveItem();
    }

    @Then("the item {string} with type {string} and exotic {string} should be present in the list")
    @Step("Проверка, что продукт '{name}' с типом '{type}' и признаком экзотичности '{exotic}' есть в списке")
    public void theItemShouldBePresentInTheList(String name, String type, String exotic) {
        foodPage.checkNewItem(name, type, exotic);
    }

    @Then("the database should contain the item {string} with type {string} and exotic {string}")
    @Step("Проверка, что база данных содержит продукт '{name}' с типом '{type}' и экзотичностью '{exotic}'")
    public void theDatabaseShouldContainTheItem(String name, String type, String exotic) {
        testSql.searchItemByName(name, type, exotic);
    }

    @Then("the item {string} should not have duplicates in the list")
    @Step("Проверка, что продукт '{name}' не имеет дубликатов в списке")
    public void theItemShouldNotHaveDuplicatesInTheList(String name) {
        foodPage.checkNewSameItem(name);
    }

    @Then("the database should not contain a single entry for {string} with type {string}")
    @Step("Проверка, что база данных содержит единственную запись для '{name}' с типом '{type}'")
    public void theDatabaseShouldContainASingleEntryFor(String name, String type) {
        testSql.getCountOfFood(name, type);
    }

    @Given("I clean up unwanted food items")
    @Step("Очистка ненужных продуктов")
    public void iCleanUpUnwantedFoodItems() {
        testSql.deleteUnwantedFoods();
        foodPage.ClearAll();
    }

    @After
    @Step("Завершение тестового окружения")
    public void tearDown() {
        driver.quit();
    }
}
