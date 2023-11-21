import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class Main {

    /*
    Main test runner class where test ng test cases are defined.
     */
    static WebDriver driver;
    DynamicTablePage tablePage;

    @BeforeTest  //This will setup/initialize the webdriver and launch the application w.r.t test configuration file. It will also store the driver instance into local driver object
    public void init() throws IOException {
        Initializer initializer = new Initializer();
        driver = initializer.getDriver();
    }

    @Test()  //this test case will click table data button to expand the input box
    public void clickTableDataButton() throws IOException {
        tablePage = new DynamicTablePage(driver);
        tablePage.clickTableData();
    }

    @Test(dependsOnMethods = "clickTableDataButton") //this will clear the input box and insert new json value in it, then click refresh table button
    public void populateWithJson() {
        tablePage.populateJson();
    }

    @Test(dependsOnMethods = "populateWithJson")  //final test case to assert the table values w.r.t json value
    public void tableAssertions() throws IOException {
        tablePage.assertTable();
    }

}
