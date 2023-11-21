import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//this class contains page objects and test methods, there is only one page object class because only one page needs to be tested here.
//JSONReader class is used as parent class to read the json test-data.
public class DynamicTablePage extends JsonReader {

    WebDriver driver;
    JsonReader reader;

    //class constructor which takes webdriver from main class and assigns it to the local webdriver of this class
    public DynamicTablePage(WebDriver driver) throws IOException {
        super();
        this.driver = driver;

        //initializing page objects of this page
        PageFactory.initElements(driver, this);
        reader = new JsonReader();
    }

    public void clickTableData() {
        //clicking the table data button
        tableDataButton.click();
    }

    public void populateJson() {

        jsonDataTextArea.clear(); //clears any pre-existing json value in input box
        jsonDataTextArea.sendKeys(reader.jsonString); //inserts the json value from TestData.json file in the input box
        refreshTableButton.click(); //clicks the refresh table button
    }

    //method to verify that data is filled correctly in the dynamic html table
    public void assertTable() throws IOException {
        List<String> tableHeaders = new ArrayList<>();

        //Fetching the list of table headers (name, age, gender in this scenario)
        for (WebElement h: headers) {
            tableHeaders.add(h.getText());
        }

        int expectedRows = entries.size(); //fetching number of table rows based on json data
        int expectedCols = entries.get(0).size(); //fetching number of table columns based on json data
        int actualRows = tableRows.size(); //fetching number of rows on actual webpage
        int actualCols = headers.size(); //fetching number of columns on actual webpage
        Assert.assertTrue(expectedRows == (actualRows - 1) && expectedCols == actualCols); //asserting that rows and columns match with json data. I used actualRows minus 1 to remove the table header row

        /* logic to verify that each value. It iterates through each table cell and matches it with the values from the json test data
        to ensure that it matches */
        for (int i = 0; i < actualRows; i++) {
            if (i > 0) /* this if condition is to skip table header row as it doesn't contain actual values*/ {
                for (int j = 0; j < actualCols; j++) {
                    Assert.assertTrue(tableRows.get(i).findElements(By.tagName("td")).get(j).getText().equalsIgnoreCase(entries.get(i-1).get(tableHeaders.get(j))));
                }
            }
        }
    }

    //Page objects to recognize web elements on the page
    @FindBy(tagName = "summary")
    WebElement tableDataButton;

    @FindBy(id = "jsondata")
    WebElement jsonDataTextArea;

    @FindBy(id = "refreshtable")
    WebElement refreshTableButton;

    @FindAll(@FindBy(tagName = "tr"))
    List<WebElement> tableRows;

    @FindBy(tagName = "th")
    List<WebElement> headers;


}
