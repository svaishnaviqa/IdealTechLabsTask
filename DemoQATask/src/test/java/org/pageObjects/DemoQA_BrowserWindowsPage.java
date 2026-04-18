package org.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.utility.UtilityClass;

public class DemoQA_BrowserWindowsPage extends UtilityClass {

    @FindBy(xpath = "//div[contains(@class,'element-group')]"
                  + "//div[contains(@class,'header-text') and text()='Alerts, Frame & Windows']")
    private WebElement alertsFrameWindowsGroup;

    @FindBy(xpath = "//span[text()='Browser Windows']")
    private WebElement browserWindowsMenuItem;

    @FindBy(id = "tabButton")
    private WebElement newTabButton;

    @FindBy(id = "windowButton")
    private WebElement newWindowButton;

    @FindBy(id = "sampleHeading")
    private WebElement sampleHeading;

    public static final By ALERTS_GROUP_HEADER  = By.xpath(
            "//div[contains(@class,'element-group')]"
          + "//div[contains(@class,'header-text') and text()='Alerts, Frame & Windows']");

    public static final By BROWSER_WINDOWS_ITEM = By.xpath("//span[text()='Browser Windows']");
    public static final By NEW_TAB_BUTTON_LOC   = By.id("tabButton");
    public static final By SAMPLE_HEADING_LOC   = By.id("sampleHeading");

    public DemoQA_BrowserWindowsPage() {
        PageFactory.initElements(driver, this);
    }

    public void clickAlertsFrameWindowsGroup() {
        waitForElementVisible(ALERTS_GROUP_HEADER);
        clickButton(alertsFrameWindowsGroup);
    }

    public void clickBrowserWindowsMenuItem() {
        waitForElementVisible(BROWSER_WINDOWS_ITEM);
        clickButton(browserWindowsMenuItem);
    }

    public void clickNewTabButton() {
        waitForElementVisible(NEW_TAB_BUTTON_LOC);
        clickButton(newTabButton);
    }

    public String getSampleHeadingText() {
        waitForElementVisible(SAMPLE_HEADING_LOC);
        return getText(sampleHeading);
    }

    public String getCurrentPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentPageUrl() {
        return driver.getCurrentUrl();
    }
}