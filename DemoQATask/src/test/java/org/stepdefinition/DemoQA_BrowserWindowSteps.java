package org.stepdefinition;

import static org.junit.Assert.*;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.pageObjects.DemoQA_AlertsPage;
import org.pageObjects.DemoQA_BrowserWindowsPage;
import org.pageObjects.DemoQA_FramesPage;
import org.utility.ExtentReport;
import org.utility.UtilityClass;

import io.cucumber.java.en.*;

public class DemoQA_BrowserWindowSteps extends UtilityClass {

	private static final String DEMOQA_LINKS_URL = getValueFromPropertFile("demoqa.links.url");
	private static final String NEW_TAB_EXPECTED_URL = getValueFromPropertFile("demoqa.newtab.expected.url");

	private DemoQA_BrowserWindowsPage browserWindowsPage;
	private DemoQA_AlertsPage alertsPage;
	private DemoQA_FramesPage framesPage;
	private String originalWindowHandle;
	private String newTabHandle;

	@Given("user navigates to DemoQA Browser Windows page")
	public void userNavigatesToDemoQALinksPage() {
		driver.get(DEMOQA_LINKS_URL);
		originalWindowHandle = driver.getWindowHandle();

		browserWindowsPage = new DemoQA_BrowserWindowsPage();
		alertsPage = new DemoQA_AlertsPage();
		framesPage = new DemoQA_FramesPage();

		ExtentReport.test.info("Step 1 – Navigated to: " + DEMOQA_LINKS_URL);
		ExtentReport.test.info("Original window handle: " + originalWindowHandle);
	}

	@Given("User clicks on {string} in the left panel")
	public void userClicksOnGroupInLeftPanel(String groupName) {
		browserWindowsPage.clickAlertsFrameWindowsGroup();
		ExtentReport.test.info("Step 2 – Expanded group: " + groupName);
	}

	@And("User clicks on {string} menu item")
	public void userClicksOnMenuItem(String menuItem) {
		switch (menuItem) {

		case "Alerts":
			alertsPage.clickAlertsMenuItem();
			assertTrue("Expected URL to contain 'alerts'", driver.getCurrentUrl().contains("alerts"));
			ExtentReport.test.pass("Navigated to Alerts page: " + driver.getCurrentUrl());
			break;

		case "Frames":
			framesPage.clickFramesMenuItem();
			assertTrue("Expected URL to contain 'frames'", driver.getCurrentUrl().contains("frames"));
			ExtentReport.test.pass("Navigated to Frames page: " + driver.getCurrentUrl());
			break;

		case "Browser Windows":
			browserWindowsPage.clickBrowserWindowsMenuItem();
			assertTrue("Expected URL to contain 'browser-windows'", driver.getCurrentUrl().contains("browser-windows"));
			ExtentReport.test.pass("Navigated to Browser Windows page: " + driver.getCurrentUrl());
			break;

		default:
			fail("Unknown menu item: " + menuItem);
		}
	}

	@When("User clicks the {string} button to trigger an alert")
	public void userClicksAlertButton(String buttonLabel) {
		alertsPage.clickAlertButtonAndAccept();
		ExtentReport.test.info("Alert triggered and accepted for button: " + buttonLabel);
	}

	@Then("The alert should be accepted successfully")
	public void theAlertShouldBeAcceptedSuccessfully() {
		assertTrue("Driver should be back on the alerts page after accepting alert",
				driver.getCurrentUrl().contains("alerts"));
		ExtentReport.test.pass("Simple JS alert accepted. Driver returned to page.");
	}

	@When("User clicks the button to trigger a confirm box")
	public void userClicksConfirmButton() {
		String alertText = alertsPage.clickConfirmAndAccept();
		ExtentReport.test.info("Confirm box text was: " + alertText);
	}

	@Then("The confirm box should be accepted and result shows {string}")
	public void confirmResultShouldShow(String expectedText) {
		String actual = alertsPage.getConfirmResultText();
		ExtentReport.test.info("Confirm result text: " + actual);
		assertEquals("Confirm result mismatch.\nExpected: " + expectedText + "\nActual  : " + actual, expectedText,
				actual);
		ExtentReport.test.pass("Confirm result verified: " + actual);
	}

	@When("User clicks the button to trigger a prompt and enters {string}")
	public void userClicksPromptAndEnters(String inputText) {
		alertsPage.clickPromptTypeAndAccept(inputText);
		ExtentReport.test.info("Prompt accepted with input: " + inputText);
	}

	@Then("The prompt result should display {string}")
	public void promptResultShouldDisplay(String expectedText) {
		String actual = alertsPage.getPromptResultText();
		ExtentReport.test.info("Prompt result text: " + actual);
		assertEquals("Prompt result mismatch.\nExpected: " + expectedText + "\nActual  : " + actual, expectedText,
				actual);
		ExtentReport.test.pass("Prompt result verified: " + actual);
	}

	@When("User switches to the top frame")
	public void userSwitchesToTopFrame() {
		framesPage.switchToTopFrame();
		ExtentReport.test.info("Switched into top frame (frame1).");
	}

	@Then("The top frame should display the heading {string}")
	public void topFrameShouldDisplayHeading(String expectedHeading) {
		String actual = framesPage.getFrameHeadingText();
		ExtentReport.test.info("Top frame heading: " + actual);
		assertEquals("Top frame heading mismatch.\nExpected: " + expectedHeading + "\nActual  : " + actual,
				expectedHeading, actual);
		ExtentReport.test.pass("Top frame heading verified: " + actual);
	}

	@When("User switches back to the main page")
	public void userSwitchesBackToMainPage() {
		framesPage.switchToDefaultContent();
		ExtentReport.test.info("Switched back to main document (defaultContent).");
	}

	@When("User switches to the bottom frame")
	public void userSwitchesToBottomFrame() {
		framesPage.switchToBottomFrame();
		ExtentReport.test.info("Switched into bottom frame (frame2).");
	}

	@Then("The bottom frame should display the heading {string}")
	public void bottomFrameShouldDisplayHeading(String expectedHeading) {
		String actual = framesPage.getFrameHeadingText();
		ExtentReport.test.info("Bottom frame heading: " + actual);
		assertEquals("Bottom frame heading mismatch.\nExpected: " + expectedHeading + "\nActual  : " + actual,
				expectedHeading, actual);
		ExtentReport.test.pass("Bottom frame heading verified: " + actual);
	}

	@When("User clicks the {string} button")
	public void userClicksTheButton(String buttonLabel) {
		Set<String> handlesBefore = driver.getWindowHandles();

		if (buttonLabel.equalsIgnoreCase("New Tab")) {
			browserWindowsPage.clickNewTabButton();
		}
		ExtentReport.test.info("Clicked button: " + buttonLabel);

		long waitSeconds = Long.parseLong(getValueFromPropertFile("explicit.wait.seconds"));
		new WebDriverWait(driver, Duration.ofSeconds(waitSeconds))
				.until(d -> d.getWindowHandles().size() > handlesBefore.size());

		Set<String> handlesAfter = new HashSet<>(driver.getWindowHandles());
		handlesAfter.removeAll(handlesBefore);
		newTabHandle = handlesAfter.iterator().next();

		assertNotNull("New tab did not open within " + waitSeconds + " seconds.", newTabHandle);
		ExtentReport.test.info("New tab detected. Handle: " + newTabHandle);
	}

	@Then("A new tab should open with the expected URL")
	public void aNewTabShouldOpenWithExpectedURL() {
		driver.switchTo().window(newTabHandle);
		String actualUrl = driver.getCurrentUrl();
		ExtentReport.test.info("New tab URL: " + actualUrl);
		assertEquals("New tab URL mismatch.\nExpected: " + NEW_TAB_EXPECTED_URL + "\nActual  : " + actualUrl,
				NEW_TAB_EXPECTED_URL, actualUrl);
		ExtentReport.test.pass("New tab URL verified: " + actualUrl);
	}

	@And("The new tab should display the heading {string}")
	public void theNewTabShouldDisplayTheHeading(String expectedHeading) {
		String actual = browserWindowsPage.getSampleHeadingText();
		ExtentReport.test.info("New tab heading: " + actual);
		assertEquals("New tab heading mismatch.\nExpected: " + expectedHeading + "\nActual  : " + actual,
				expectedHeading, actual);
		ExtentReport.test.pass("New tab heading verified: " + actual);
	}

	@When("User switches back to the original tab")
	public void userSwitchesBackToOriginalTab() {
		driver.switchTo().window(originalWindowHandle);
		ExtentReport.test.info("Switched back to original tab. Handle: " + originalWindowHandle);
	}

	@Then("The original tab should still display the Browser Windows page")
	public void originalTabShouldDisplayBrowserWindowsPage() {
		String currentUrl = driver.getCurrentUrl();
		ExtentReport.test.info("Original tab URL after switch-back: " + currentUrl);
		assertTrue("Expected original tab URL to contain 'browser-windows' but was: " + currentUrl,
				currentUrl.contains("browser-windows"));
		ExtentReport.test.pass("Original tab still on Browser Windows page: " + currentUrl);
	}
}