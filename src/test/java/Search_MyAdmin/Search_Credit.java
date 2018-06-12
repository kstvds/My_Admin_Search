package Search_MyAdmin;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import ObjectRepository.HomePage;
import ObjectRepository.LoginPage;
import ObjectRepository.NewAccoBooking;
import ObjectRepository.Operations;
import Utility.Configuration;
import lib.DriverAndObjectDetails;
import lib.ExcelDataConfig;
import lib.ExtentManager;
import lib.Takescreenshot;
import lib.DriverAndObjectDetails.DriverName;

/* #######  Test for accommodation Search for Credit user #########
######   Scenario Logs In, Searches for a specified hotel   ##### */

public class Search_Credit {
	public WebDriver driverqa;
	String errorpath;
	ExcelDataConfig excel;
	ExtentTest test;
	Configuration Config = new Configuration();
	Takescreenshot obj = new Takescreenshot();
	ExtentReports rep = ExtentManager.getInstance();
	LoginPage login = new LoginPage();
	HomePage home = new HomePage();
	NewAccoBooking acco = new NewAccoBooking();
	Operations opo = new Operations();
	Logger logger = Logger.getLogger("Search_Credit");

	/* ####### Passing browser as parameters in test ######### **/

	@Test
	@Parameters({ "browsername" })
	public void SearchCredit(String browsername) throws Exception {
		test = rep.startTest("Credit Search");
		excel = new ExcelDataConfig(Config.getExcelPathBook());
		PropertyConfigurator.configure("Log4j.properties");
		logger.info("Test Case Started");
		if (browsername.equalsIgnoreCase("CH")) {
			driverqa = new DriverAndObjectDetails(DriverName.CH).CreateDriver();
		} else if (browsername.equalsIgnoreCase("IE")) {
			driverqa = new DriverAndObjectDetails(DriverName.IE).CreateDriver();
		} else {
			driverqa = new DriverAndObjectDetails(DriverName.FF).CreateDriver();
		}
		WebDriverWait wait = new WebDriverWait(driverqa, 50);
		Actions action = new Actions(driverqa);

		/* ####### Login functionality ######### **/

		try {
			logger.info("Browser Opened");
			String URL = excel.getData(0, 1, 5) + "/_myadmin";
			driverqa.get(URL);
			logger.info("Test Case Started");
			test.log(LogStatus.INFO, "Starting Login");
			WebElement username = driverqa.findElement(LoginPage.uname);
			username.clear();
			username.sendKeys(excel.getData(0, 1, 1));
			WebElement password = driverqa.findElement(LoginPage.pwd);
			password.clear();
			password.sendKeys(excel.getData(0, 1, 2));
			driverqa.findElement(LoginPage.submit).click();
			Thread.sleep(1000);
			String expectedtitle = "DOTWconnect.com::DOTWconnect.com: My Admin";
			String atualtitle = driverqa.getTitle();
			Assert.assertEquals(atualtitle, expectedtitle);
			test.log(LogStatus.INFO, "Ending Login");
			test.log(LogStatus.PASS, "PASSED Login");
			logger.info("Login Successful");
			wait.until(ExpectedConditions.visibilityOfElementLocated(HomePage.operation));
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Search/Accommodation_Search_Credit/Log-In.jpg");

		} catch (Throwable e) {
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Search/Error/Accommodation_Search_Credit/Log-In.jpg");
			errorpath = Config.SnapShotPath() + "/Search/Error/Accommodation_Search_Credit/Log-In.jpg";
			test.log(LogStatus.FAIL, "Login");
			logger.info(e.getMessage());
			test.log(LogStatus.FAIL, e.getMessage());
			rep.endTest(test);
			rep.flush();
			Assert.assertTrue(false, e.getMessage());

		}
		logger.info("Searching Customer");

		/* ####### Searching for specific credit customer ######### **/

		try {
			test.log(LogStatus.INFO, "Navigating to customer search page");
			logger.info("Navigating to customer search page");
			driverqa.findElement(HomePage.operation).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Operations.newBooking));
			driverqa.findElement(Operations.newBooking).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Operations.AccomBook));
			driverqa.findElement(Operations.AccomBook).click();
			Thread.sleep(2000);
			String searchcustatualtitle = driverqa.getTitle();
			String searchcustexpectedtitle = "DOTWconnect.com::New Accommodation Booking";
			Assert.assertEquals(searchcustatualtitle, searchcustexpectedtitle);
			logger.info("Navigated to customer search page");
			test.log(LogStatus.PASS, "Navigated to customer search page");
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Search/Accommodation_Search_Credit/Customer-Search.jpg");

		} catch (Throwable e) {
			logger.info(e.getMessage());
			test.log(LogStatus.FAIL, e.getMessage());
			rep.endTest(test);
			rep.flush();
			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Search/Error/Accommodation_Search_Credit/Customer-Search.jpg");
			errorpath = Config.SnapShotPath() + "/Search/Error/Accommodation_Search_Credit/Customer-Search.jpg";
			test.log(LogStatus.FAIL, "Navigation to customer search page");
			Assert.assertTrue(false, e.getMessage());

		}
		logger.info("Selecting Customer");
		test.log(LogStatus.INFO, "Selecting Customer");

		/* ####### Selecting the specified credit customer ######### **/

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(Operations.company));
			driverqa.findElement(Operations.company).sendKeys(excel.getData(0, 5, 1));
			action.sendKeys(Keys.ARROW_DOWN).build().perform();
			action.sendKeys(Keys.ENTER).build().perform();
			Thread.sleep(4000);
			action.sendKeys(Keys.ENTER).build().perform();
			Thread.sleep(2000);

			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Search/Accommodation_Search_Credit/Customer-list.jpg");
			wait.until(ExpectedConditions.visibilityOfElementLocated(Operations.chooseCustbook));
			driverqa.findElement(Operations.chooseCustbook).click();
			Thread.sleep(1000);
			String searchpageactualtitle = driverqa.getTitle();
			String searchpageexpectedtitle = "DOTWconnect.com::";
			Assert.assertEquals(searchpageactualtitle, searchpageexpectedtitle);
			logger.info("Customer Selected");
			test.log(LogStatus.PASS, "Customer Selected");

		} catch (Throwable e) {
			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Search/Error/Accommodation_Search_Credit/Customer-list.jpg");
			errorpath = Config.SnapShotPath() + "/Search/Error/Accommodation_Search_Credit/Customer-list.jpg";
			test.log(LogStatus.FAIL, "Customer Selection");
			logger.info(e.getMessage());
			test.log(LogStatus.FAIL, e.getMessage());
			rep.endTest(test);
			rep.flush();
			Assert.assertTrue(false, e.getMessage());

		}
		logger.info("Applying search Filters");
		logger.info("Starting HotelSearch Credit");

		/* ####### Searching Hotel for specified date ######### **/

		try {
			test.log(LogStatus.INFO, "Starting HotelSearch");
			wait.until(ExpectedConditions.visibilityOfElementLocated(NewAccoBooking.AccomUnit));
			driverqa.findElement(NewAccoBooking.AccomUnit).sendKeys(excel.getData(0, 9, 1));
			Thread.sleep(4000);
			action.sendKeys(Keys.ARROW_DOWN).build().perform();
			action.sendKeys(Keys.ENTER).build().perform();
			driverqa.findElement(NewAccoBooking.inDate).clear();
			driverqa.findElement(NewAccoBooking.inDate).sendKeys(excel.getData(0, 15, 1));
			driverqa.findElement(NewAccoBooking.outDate).clear();
			driverqa.findElement(NewAccoBooking.outDate).sendKeys(excel.getData(0, 15, 2));
			String expected = excel.getData(0, 9, 1);
			Thread.sleep(2000);
			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Search/Accommodation_Search_Credit/Search-Hotel-filters.jpg");
			wait.until(ExpectedConditions.visibilityOfElementLocated(NewAccoBooking.bookChannel));
			driverqa.findElement(NewAccoBooking.bookChannel).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(NewAccoBooking.thirdPartyChannel));
			driverqa.findElement(NewAccoBooking.thirdPartyChannel).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(NewAccoBooking.thirdParty));
			driverqa.findElement(NewAccoBooking.thirdParty).click();
			Thread.sleep(2000);
			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Search/Accommodation_Search_Credit/Booking-Channel-filters.jpg");

			driverqa.findElement(NewAccoBooking.searchButton).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(NewAccoBooking.thirdPartyresultHotel));
			String result = driverqa.findElement(NewAccoBooking.thirdPartyresultHotel).getText();
			System.out.println(result);
			System.out.println(expected);
			Thread.sleep(2000);
			obj.Takesnap(driverqa, Config.SnapShotPath() + "/Search/Accommodation_Search_Credit/Search-Result.jpg");
			Assert.assertTrue(result.contains(expected));
			test.log(LogStatus.INFO, "Ending HotelSearch Credit");
			test.log(LogStatus.PASS, "PASSED HotelSearch Credit");
			logger.info("Hotel Search Complete Credit");
		} catch (Throwable e) {
			test.log(LogStatus.FAIL, "Hotel Search Credit");
			obj.Takesnap(driverqa,
					Config.SnapShotPath() + "/Search/Error/Accommodation_Search_Credit/Search-Result.jpg");
			errorpath = Config.SnapShotPath() + "/Search/Error/Accommodation_Search_Credit/Search-Result.jpg";
			logger.info(e.getMessage());
			test.log(LogStatus.FAIL, e.getMessage());
			rep.endTest(test);
			rep.flush();
			Assert.assertTrue(false, e.getMessage());
		}
	}

	/* ####### Generating the Failure Reports and Screenshots ######### **/

	@AfterMethod
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {

			test.log(LogStatus.FAIL, test.addScreenCapture(errorpath));
			test.log(LogStatus.FAIL, result.getThrowable());
		}
		rep.endTest(test);
	}

	/* ####### Ending Tests ######### **/

	@AfterTest
	public void afterTest() {

		rep.endTest(test);
		rep.flush();
		driverqa.close();
	}
}
