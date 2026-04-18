package org.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.utility.UtilityClass;

public class DemoQA_FramesPage extends UtilityClass {

    @FindBy(id = "sampleHeading")
    private WebElement sampleHeading;

    public static final By FRAME_TOP_LOC    = By.id("frame1");
    public static final By FRAME_BOTTOM_LOC = By.id("frame2");
    public static final By SAMPLE_HDG_LOC   = By.id("sampleHeading");

    public static final By FRAMES_MENU_ITEM = By.xpath("//span[text()='Frames']");

    public DemoQA_FramesPage() {
        PageFactory.initElements(driver, this);
    }

    public void clickFramesMenuItem() {
        waitForElementVisible(FRAMES_MENU_ITEM);
        clickButton(driver.findElement(FRAMES_MENU_ITEM));
    }

    public void switchToTopFrame() {
        waitForElementVisible(FRAME_TOP_LOC);
        driver.switchTo().frame(driver.findElement(FRAME_TOP_LOC));
    }

    public void switchToBottomFrame() {
        waitForElementVisible(FRAME_BOTTOM_LOC);
        driver.switchTo().frame(driver.findElement(FRAME_BOTTOM_LOC));
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public String getFrameHeadingText() {
        PageFactory.initElements(driver, this);
        waitForElementVisible(SAMPLE_HDG_LOC);
        return getText(sampleHeading);
    }
}