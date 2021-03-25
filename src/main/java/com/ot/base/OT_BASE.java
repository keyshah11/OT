package com.ot.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;

public class OT_BASE extends OT_Web_Platform {

	private int implicitTime = 15;
	private long waitTimeInSecs = 20;
	public int i = 1;
	public static WebElement element;
	public static List<WebElement> elements;
	public static String stringelement;
	public static String path = System.getProperty("user.dir");
	protected static DecimalFormat df = new DecimalFormat("0.00");
	ATUTestRecorder recorder;
	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
	Date date = new Date();

	public String readexcel(String excelpath, int row, int col) throws IOException {
		File src = new File(path + excelpath);
		FileInputStream fis;
		fis = new FileInputStream(src);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sh1 = wb.getSheetAt(0);
		String excelvalue = sh1.getRow(row).getCell(col).getStringCellValue();
		System.out.println(excelvalue);
		return excelvalue;
	}

	public void openURL(String URL) {
		WebDriver driver = getTargetDriver();
		driver.get(URL);
	}

	public boolean isDisplayed(By by) {
		boolean flag;

		try {
			driver.findElement(by).isDisplayed();
			flag = true;
		} catch (Exception e) {
			System.out.println("Fail to check isDisplayed : " + by);
			flag = false;
		}
		return flag;
	}

	public WebElement isElementVisible(By locator, String elementName) {
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, waitTimeInSecs);
			element = wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(locator)));
		} catch (Exception e) {

			System.out.println("FAIL: " + elementName + " is not visible which is located at " + locator);
		}
		return element;
	}

	// Click the element
	public WebElement click(By locator, String elementName) {
		WebElement element = null;
		try {
			isElementVisible(locator, elementName);
			element = driver.findElement(locator);
			element.click();
			System.out.println("PASS: sucessfully clicked on " + elementName);
		} catch (Exception e) {
			System.out.println("FAIL: To click on " + elementName + "  which is located at " + locator);

		}
		return element;
	}

	// Sendkeys to the element
	public WebElement sendKeys(By locator, String sendkey, String elementName) {
		WebElement element = null;
		try {
			clear(locator, elementName);
			element = driver.findElement(locator);
			element.sendKeys(sendkey);
			System.out.println("PASS: In " + elementName + " sucessfully entered value = " + sendkey);
		} catch (Exception e) {
			System.out.println("FAIL: To send value on " + elementName + "  which is located at " + locator);
		}
		return element;
	}

	// Clear the element
	public boolean clear(By locator, String elementName) {
		boolean isTextCleared = false;
		WebElement element = null;
		try {
			isElementVisible(locator, elementName);
			element = driver.findElement(locator);
			element.clear();
			isTextCleared = true;
			System.out.println("PASS: Cleared text from element " + elementName);
		} catch (Exception e) {
			System.out.println("FAIL: To clear value on " + elementName + "  which is located at " + locator);
		}
		return isTextCleared;
	}

	// Get text of the element
	public String getText(By locator, String elementName) {
		String element = null;
		try {
			isElementVisible(locator, elementName);
			element = driver.findElement(locator).getText();
			System.out.println("PASS: Element text is " + element);
		} catch (Exception e) {
			System.out.println("FAIL: To find the element text" + elementName + "  which is located at " + locator);
		}
		return element;
	}

	// Get classname of the element
	public String getAttribute(WebElement element, String name) {
		return element.getAttribute(name);
	}

	// Implicitly wait
	public void implicitlyWait() {
		driver.manage().timeouts().implicitlyWait(implicitTime, TimeUnit.SECONDS);
	}

	// Thread.sleep
	public void sleep(int seconds) {
		try {
			int miliseconds = seconds * 1000;
			Thread.sleep(Integer.valueOf(miliseconds));
		} catch (Exception e) {
			System.out.println("Problem Rise During Custom Sleep for the page.....");
		}
	}

	@AfterClass(groups = { "always" })
	public void Browser_Close() throws ATUTestRecorderException {
		System.out.println("");
		System.out.println("+++++++++++++++++++++++++++++++++End Test Case ----"
				+ this.getClass().getName().replace("com.ot.testcases.", "") + "++++++++++++++++++++++++++++++++++++");
//		recorder.stop();
		driver.quit();

	}

	public static void Dropdown_Selection_by_Text(By locator, String text) {
		WebElement element = driver.findElement(locator);
		Select dropdowntext = new Select(element);
		dropdowntext.selectByVisibleText(text);
	}

	public static class SaveScreenshot {
		public static void capture(String testCaseName) {
			// Cast driver object to TakesScreenshot
			TakesScreenshot screenshot = (TakesScreenshot) driver;
			// Get the screenshot as an image File
			File src = screenshot.getScreenshotAs(OutputType.FILE);
			try {
				// Specify the destination where the image will be saved
				File dest = new File(
						path + "\\\\test-output\\\\html\\\\ScreenShot\\\\" + testCaseName + "_" + timestamp() + ".jpg");
//				File dest = new File("./test-output/html/ScreenShot/" + testCaseName + "_" + timestamp() + ".jpg");

				// Copy the screenshot to destination
				FileUtils.copyFile(src, dest);
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
			Reporter.log("<a href=" + path + "\\test-output\\html\\ScreenShot\\" + testCaseName + "_"
					+ SaveScreenshot.timestamp() + ".jpg" + ">ScreenShot - " + testCaseName + "</a>");

//			Reporter.log("<a href=" + "./test-output/html/ScreenShot/" + testCaseName + "_" + timestamp() + ".jpg"
//					+ ">ScreenShot - " + testCaseName + "</a>");
		}

		public static String timestamp() {
			// Timestamp to make each screenshot name unique
			return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		}
	}

	public static String between(String value, String a, String b) {
		// Return a substring between the two strings.
		try {
			int posA = value.indexOf(a);
			if (posA == -1) {
				return "";
			}
			int posB = value.lastIndexOf(b);
			if (posB == -1) {
				return "";
			}
			int adjustedPosA = posA + a.length();
			if (adjustedPosA >= posB) {
				return "";
			}
			return value.substring(adjustedPosA, posB);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return b;
	}

	public static String before(String value, String a) {
		// Return substring containing all characters before a string.
		int posA = value.indexOf(a);
		if (posA == -1) {
			return "";
		}
		return value.substring(0, posA);
	}

	public static String after(String value, String a) {
		// Returns a substring containing all characters after a string.
		int posA = value.lastIndexOf(a);
		if (posA == -1) {
			return "";
		}
		int adjustedPosA = posA + a.length();
		if (adjustedPosA >= value.length()) {
			return "";
		}
		return value.substring(adjustedPosA);
	}

	public void waitsleep(int sec) throws InterruptedException {
		int seconds = 1000 * sec;
		Thread.sleep(seconds);
	}

	public static WebElement expliwait(By by) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));

		return element;
	}

	public static void impliwait() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public static void scrollbrowser(double x, double y) throws InterruptedException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(" + x + "," + y + ")");

	}

	public static void pressescape() throws InterruptedException {

		Actions action = new Actions(driver);
		action.sendKeys(Keys.ESCAPE);

	}

	public static void pagereload() throws InterruptedException {

		driver.navigate().refresh();

	}

	public List<WebElement> getVisibleElementLists(By locator, String elementName) {
		int visibleElementsCount = 0;
		int elementsCount = 0;
		WebDriver driver = getTargetDriver();
		List<WebElement> visibleElementsList = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, waitTimeInSecs);
			visibleElementsList = wait
					.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator)));
			visibleElementsCount = visibleElementsList.size();
			System.out.println(
					"Found visibility for list of elements " + elementName + " with size = " + visibleElementsCount);
		} catch (Exception e) {
			try {
				// if all elements are not visible, then get the list of all Elements by Locator
				System.out.println(
						"All elements not visible for " + elementName + " , getting only visible element list...");
				List<WebElement> lstElements = driver.findElements(locator);
				if (lstElements != null && lstElements.size() > 0) {
					visibleElementsList = new ArrayList<WebElement>();
					elementsCount = lstElements.size();
					for (int i = 0; i <= elementsCount; i++) {
						WebElement element = lstElements.get(i);
						if (element.isDisplayed()) {
							visibleElementsList.add(element);
							visibleElementsCount++;
						}
					}
				} else {
					System.out.println(elementName + " web element list is found with size = " + elementsCount
							+ " , Visible elements count =" + visibleElementsCount);
				}
			} catch (Exception e1) {
				String errorMessage = elementName + " web element list is found with size = " + elementsCount
						+ " , Visible elements count =" + visibleElementsCount;
				System.out.println(errorMessage);
			}
		}
		return visibleElementsList;
	}

	public static List<String> ExtractJSLogs() {

		List<String> errorLogs = new ArrayList<String>();
		LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);

		for (LogEntry entry : logEntries) {

			String Console_Errors = new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage();

			if (Console_Errors.contains("$data.key")) {
				errorLogs.add(Console_Errors);
				System.out.println(Console_Errors);

			}
		}

		return errorLogs;

	}
}
