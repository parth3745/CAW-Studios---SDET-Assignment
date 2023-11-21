import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class Initializer {
    WebDriver driver;
    Properties prop;
    public Initializer() throws IOException {
//        initializing properties object to read test configuration info
        prop = new Properties();
        prop.load(new FileReader(System.getProperty("user.dir") + "/src/main/resources/Configuration.properties"));

//        setting up web driver(deciding browser) based on configuration file
        if (prop.getProperty("browser").equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        else if (prop.getProperty("browser").equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        else if (prop.getProperty("browser").equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }

//        setting up implicit wait of 3 seconds(this will wait for web elements for 3 seconds before failing test case)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

//        Fetching application url from test configuration file and launching it in browser
        driver.get(prop.getProperty("appUrl"));
        driver.manage().window().maximize();
    }

    //this method will be used to fetch webdriver in main class
    public WebDriver getDriver() {
        return driver;
    }
}
