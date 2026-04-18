package org.utility;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.cucumber.java.Scenario;
import io.cucumber.java.Status;

public class ExtentReport extends UtilityClass {
	static ExtentReports report;
	public static ExtentTest test;

	public static void startReport() {

		ExtentSparkReporter html = new ExtentSparkReporter(
				System.getProperty("user.dir") + "\\src\\test\\resources\\Reports\\ExtentReport\\report.html");
		html.config().setDocumentTitle("Cucumber Extent Report");
		html.config().setReportName("Extent Report");
		html.config().setTheme(Theme.DARK);

		report = new ExtentReports();
		report.attachReporter(html);

		report.setSystemInfo("BrowserName", UtilityClass.getValueFromPropertFile("browser"));
		report.setSystemInfo("OS", "Windows");
		

	}

	public static void createTestLog(Scenario sc) {
		
//		String name = sc.getName();
		Status status = sc.getStatus();
		TakesScreenshot tk = (TakesScreenshot) driver;
		String scr = tk.getScreenshotAs(OutputType.BASE64);

		switch (status) {
		case PASSED:
			test=	test.pass("Test Case Passed").addScreenCaptureFromBase64String(scr);
			break;
		case FAILED:
			test=test.fail("Test Case Failed").addScreenCaptureFromBase64String(scr);
			break;

		default:
			test=test.skip("Test Case Skipped").addScreenCaptureFromBase64String(scr);
			break;
		}
	}

	public static void endReport() {
		report.flush();
	}

	public static void createTest(Scenario sc) {
		test = report.createTest(sc.getName());
	}

	
	public static void attachScreenshot(String base64Image) {
		test.addScreenCaptureFromBase64String(base64Image);
	}
	
}
