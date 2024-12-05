package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class FoodPage {
    private WebDriverWait wait;
    public WebDriver driver;
    public FoodPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @FindBy(xpath = "//button[text()='Добавить']")
    private WebElement bttnAdd;

    @FindBy(className = "form-check-input")
    private WebElement checkExot;

    @FindBy(xpath = "//*[@id=\"name\"]")
    private WebElement nameField;

    @FindBy(xpath = "//*[@id=\"type\"]")
    private WebElement selectType;

    @FindBy(xpath = "//*[@id=\"save\"]")
    private WebElement bttnSave;

    @FindBy(xpath = "//*[@id=\"reset\"]")
    private WebElement bttnReset;

    public void inputName(String name){
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"name\"]")));
        nameField.sendKeys(name);
    }

    public void setExot(){
        checkExot.click();
    }

    public void Type(String type){
        Select select = new Select(selectType);
        select.selectByValue(type);
    }
    public void SaveItem(){
        bttnSave.click();
    }
    public void ClearAll(){
        WebElement clearButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navbarDropdown")));
        clearButton.click();
        bttnReset.click();
    }
    public void ClickAdd(){
        bttnAdd.click();
    }

    public void checkNewItem(String nameItem, String typeItem, String valueExot){
        WebElement firstRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table tbody tr:last-child")));
        String name = firstRow.findElement(By.cssSelector("td:nth-child(2)")).getText();
        String category = firstRow.findElement(By.cssSelector("td:nth-child(3)")).getText();
        String value = firstRow.findElement(By.cssSelector("td:nth-child(4)")).getText();
        assertEquals(nameItem, name);
        assertEquals(typeItem, category);
        assertEquals(valueExot, value);
    }
    public void checkNewSameItem(String fruitName){
        Integer countSameItem = 0;
        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody")));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.tagName("td"));
            if(columns.size() > 0 && columns.get(0).getText().equals(fruitName)){
                Integer id = Integer.parseInt(row.findElement(By.xpath("./th")).getText());
                String name = columns.get(0).getText();
                String type = columns.get(1).getText();
                String exot = columns.get(2).getText();
                System.out.println("id: " + id);
                System.out.println("name: " + name);
                System.out.println("type: " + type);
                System.out.println("exot: " + exot);
                countSameItem++;
            }
        }
        assertNotEquals(countSameItem, null);


    }

}