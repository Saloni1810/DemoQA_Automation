package com.kiwiqa.pages;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;

public class FormTestNG {
	
	WebDriver driver;
	FormPage formPage;
	OutputPage outputPage;
	FileInputStream file;
	Map<String, String> dataMap = new LinkedHashMap<>();
	
	@BeforeClass
	  public void setUp() throws FileNotFoundException {
		
		driver = new ChromeDriver();
		formPage = new FormPage(driver);
		outputPage = new OutputPage(driver);
		driver.manage().window().maximize();
		driver.get("https://demoqa.com/automation-practice-form");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		file = new FileInputStream( System.getProperty("user.dir") + "\\Form_Data.xlsx");
	  }
	
	@BeforeMethod
	  public void beforeMethod() {
		formPage = new FormPage(driver);
		outputPage = new OutputPage(driver);
	  }
	
	  @Test
	  public void readExcelData() throws IOException, InterruptedException {
		
		  
	  dataMap = formPage.readFromExcel(file);  
  	  formPage.enterFirstName(dataMap.get("First Name"));
      formPage.enterLastName(dataMap.get("Last Name"));
      formPage.enterEmail(dataMap.get("Email"));
      formPage.selectGender(dataMap.get("Gender"));
      formPage.enterPhoneNumber(dataMap.get("Mobile"));
      formPage.selectDateOfBirth(dataMap.get("Month"),dataMap.get("Year"),dataMap.get("Date"));
      formPage.enterSubjects(dataMap.get("Subjects"));
      formPage.selectHobbies(dataMap.get("Hobbies"));
      formPage.uploadPicture("C:\\Users\\HP\\Downloads\\" + dataMap.get("Upload file") +"");
      formPage.enterAddress(dataMap.get("Address"));
      formPage.selectState(dataMap.get("State"));
      formPage.selectCity(dataMap.get("City"));
      
      
      formPage.submitForm();

      
      System.out.println("Done!!");
      outputPage.verify();
	  }
	  
	 
	
	  
  @AfterMethod
  public void afterMethod() {
	  System.out.println("Donee");
  }

  

  @AfterClass
  public void afterClass() {
	  driver.quit();
  }

}
