package org.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.utility.ExtentReport;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/Features",
                 glue="org.stepdefinition",
                 dryRun=false, 
                 stepNotifications=true,
                 monochrome=true,
                 plugin= {"html:src\\test\\resources\\Reports\\HtmlReport\\report.html",
                		 "json:src\\test\\resources\\Reports\\JsonReport\\report.json"}
)
public class TestRunner {
	
	
	@BeforeClass
	public static void startReportGen() {
		ExtentReport.startReport();

	}

	@AfterClass
	public static void closeReportGen() {
		ExtentReport.endReport();

	}

}
