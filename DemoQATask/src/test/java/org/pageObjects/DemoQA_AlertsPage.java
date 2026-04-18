package org.pageObjects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.utility.UtilityClass;

import java.time.Duration;

public class DemoQA_AlertsPage extends UtilityClass {

    
    @FindBy(id = "alertButton")
    private WebElement alertButton;

    @FindBy(id = "timerAlertButton")
    private WebElement timerAlertButton;

    @FindBy(id = "confirmButton")
    private WebElement confirmButton;

    @FindBy(id = "promtButton")
    private WebElement promptButton;


    @FindBy(id = "confirmResult")
    private WebElement confirmResult;

    @FindBy(id = "promptResult")
    private WebElement promptResult;

    public static final By ALERT_BUTTON_LOC   = By.id("alertButton");
    public static final By CONFIRM_BUTTON_LOC = By.id("confirmButton");
    public static final By PROMPT_BUTTON_LOC  = By.id("promtButton");   // note: DemoQA typo
    public static final By CONFIRM_RESULT_LOC = By.id("confirmResult");
    public static final By PROMPT_RESULT_LOC  = By.id("promptResult");


    public static final By ALERTS_MENU_ITEM = By.xpath("//span[text()='Alerts']");


    public DemoQA_AlertsPage() {
        PageFactory.initElements(driver, this);
    }

    public void clickAlertsMenuItem() {
        waitForElementVisible(ALERTS_MENU_ITEM);
        clickButton(driver.findElement(ALERTS_MENU_ITEM));
    }

    public void clickAlertButtonAndAccept() {
        waitForElementClickable(ALERT_BUTTON_LOC);
        clickButton(alertButton);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }

    public String clickConfirmAndAccept() {
        waitForElementClickable(CONFIRM_BUTTON_LOC);
        clickButton(confirmButton);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert confirm = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = confirm.getText();
        confirm.accept();
        return alertText;
    }

    public void clickPromptTypeAndAccept(String input) {
        waitForElementClickable(PROMPT_BUTTON_LOC);
        clickButton(promptButton);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert prompt = wait.until(ExpectedConditions.alertIsPresent());
        prompt.sendKeys(input);
        prompt.accept();
    }

    public String getConfirmResultText() {
        waitForElementVisible(CONFIRM_RESULT_LOC);
        return getText(confirmResult);
    }

    public String getPromptResultText() {
        waitForElementVisible(PROMPT_RESULT_LOC);
        return getText(promptResult);
    }
}