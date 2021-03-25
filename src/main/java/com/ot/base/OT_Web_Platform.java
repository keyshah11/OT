package com.ot.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.devicefarm.DeviceFarmClient;
import software.amazon.awssdk.services.devicefarm.model.CreateTestGridUrlRequest;
import software.amazon.awssdk.services.devicefarm.model.CreateTestGridUrlResponse;

public class OT_Web_Platform {

	public static WebDriver driver;

	protected static Logger logger;

	public static Properties prop;

	private ChromeOptions chromeOptions = new ChromeOptions();

	private ChromeOptions chromeOptionsservice = new ChromeOptions();

	private FirefoxOptions firefoxOptions = new FirefoxOptions();

	public OT_Web_Platform() {

		// Initializing the prop variable to read the files from config.properties
		try {

			prop = new Properties();
			FileInputStream ip = new FileInputStream("./src/main/java/com/ot/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeClass(groups = { "always" })
	@Parameters("browser")
	public void createDriver(String browser) throws MalformedURLException {
//		if (prop.getProperty("browserName").equalsIgnoreCase("chrome")) {
		if (browser.equalsIgnoreCase("chrome")) {
//			System.setProperty("webdriver.chrome.silentoutput", "true");
			System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
//			System.setProperty("webdriver.chrome.driver", prop.getProperty("chromeDriverPath"));
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
//			driver = new ChromeDriver(chromeOptions);	
		}
//		} else if (prop.getProperty("browserName").equalsIgnoreCase("firefox")) {
		else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", prop.getProperty("mozillaDriverPath"));

			firefoxOptions.addArguments("--disable-notifications");
//			WebDriverManager.firefoxdriver().setup();
//			WebDriver driver = new FirefoxDriver();

			driver = new FirefoxDriver(firefoxOptions);
		} else if (prop.getProperty("browserName").equalsIgnoreCase("ie")) {

			System.setProperty("webdriver.ie.driver", prop.getProperty("IEDriverPath"));

			driver = new InternetExplorerDriver();

		} else if (prop.getProperty("browserName").equalsIgnoreCase("chrome-cap")) {

			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			LoggingPreferences logginPreferences = new LoggingPreferences();
			logginPreferences.enable(LogType.BROWSER, Level.SEVERE);
			capabilities.setCapability(CapabilityType.LOGGING_PREFS, logginPreferences);
			System.setProperty("webdriver.chrome.driver", prop.getProperty("chromeDriverPath"));

			driver = new ChromeDriver(capabilities);

		} else if (prop.getProperty("browserName").equalsIgnoreCase("Headless-Chrome")) {

			System.out.println("Test Starts Running In Headless Chrome Browser.");
			System.setProperty("webdriver.chrome.driver", prop.getProperty("chromeDriverPath"));
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("--window-size=1920,1080");
//			    chromeOptions.addArguments("--disable-gpu");
//			    chromeOptions.addArguments("--disable-extensions");
//			    chromeOptions.setExperimentalOption("useAutomationExtension", false);
//			    chromeOptions.addArguments("--proxy-server='direct://'");
//			    chromeOptions.addArguments("--proxy-bypass-list=*");
			chromeOptions.addArguments("--start-maximized");
			driver = new ChromeDriver(chromeOptions);

		}

		else if (prop.getProperty("browserName").equals("Headless-Firefox")) {

			System.out.println("Test Starts Running In Headless Firefox Browser.");
			System.setProperty("webdriver.gecko.driver", prop.getProperty("mozillaDriverPath"));
			FirefoxOptions firefoxoptions = new FirefoxOptions();
			firefoxoptions.addArguments("--headless");
			firefoxoptions.addArguments("--window-size=1920,1080");
			firefoxoptions.addArguments("--start-maximized");
			driver = new FirefoxDriver(firefoxoptions);
		}

		else if (prop.getProperty("browserName").equals("awsfirefox")) {
//			private static RemoteWebDriver driver;
			String myProjectARN = "arn:aws:devicefarm:us-west-2:097294584695:testgrid-project:d40954ff-b94f-4b6c-9707-6ed5fdbce1fc";
			DeviceFarmClient client = DeviceFarmClient.builder().region(Region.US_WEST_2).build();
			CreateTestGridUrlRequest request = CreateTestGridUrlRequest.builder().expiresInSeconds(300)
					.projectArn(myProjectARN).build();
			CreateTestGridUrlResponse response = client.createTestGridUrl(request);
			URL testGridUrl = new URL(response.url());
			// You can now pass this URL into RemoteWebDriver.
			DesiredCapabilities desired_capabilities = new DesiredCapabilities();
			desired_capabilities.setCapability("browserName", "firefox");
			desired_capabilities.setCapability("browserVersion", "latest");
			desired_capabilities.setCapability("platform", "windows");
			driver = new RemoteWebDriver(testGridUrl, DesiredCapabilities.firefox());
		}

		else {
			System.out.println("No Matching browser found for  " + prop.getProperty("browserName"));
			tearDown();
		}

		driver.manage().window().maximize();

		driver = getTargetDriver();
		driver.get(prop.getProperty("URL"));
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public WebDriver getTargetDriver() {
		if (driver == null) {
			String message = "Driver is null, cannot continue. Application has probably crashed or Driver creation failed!";
			System.out.println(message);
		}
		PageFactory.initElements(driver, this);
		return driver;
	}

	public void tearDown() {
		WebDriver driver = getTargetDriver();
		driver.close();
		driver.quit();
	}

	public void loadPropertiyFile(String fileName) {
		String configFile;

		if (fileName.equalsIgnoreCase("config"))
			configFile = "./src/main/java/com/ot/config/" + fileName + ".properties";
		else
			configFile = "./src/main/java/com/ot/config/" + fileName + ".properties";

		prop = new Properties(System.getProperties());

		try {
			prop.load(new FileReader(configFile));
		} catch (IOException e) {
			System.out.println("exception occured during load prop file " + e);
		}
		System.setProperties(prop);
		System.out.println("prop file loaded successfully");
	}

}
