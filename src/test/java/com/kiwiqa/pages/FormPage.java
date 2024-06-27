package com.kiwiqa.pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class FormPage {

    WebDriver driver;

    // Locators
    By firstNameLocator = By.id("firstName");
    By lastNameLocator = By.id("lastName");
    By emailLocator = By.id("userEmail");
    By genderLocator = By.xpath("//label[text()='Female']");
    By numberLocator = By.id("userNumber");
    By dateOfBirthLocator = By.id("dateOfBirthInput");
    By monthLocator = By.xpath("(//div[contains(@class,'datepicker__month')])[2]/select");
    By yearLocator = By.xpath("//div[contains(@class,'datepicker__year')]/select");
    By dateLocator = By.xpath("(//div[contains(@class,'datepicker__week')])[4]/div[contains(text(), '23')]");
    By subjectLocator = By.xpath("//div[contains(@class,'auto-complete__value')]");
    By inputLocator = By.id("subjectsInput");
    By hobbiesLocator = By.xpath("//label[contains(text(), 'Reading')]");
    By picLocator = By.id("uploadPicture");
    By addressLocator = By.id("currentAddress");
    By stateLocator = By.id("state");
    By stateNameLocator = By.id("react-select-3-input");
    By cityLocator = By.id("city");
    By cityNameLocator = By.id("react-select-4-input");
    By submitLocator = By.id("submit");
    

    // Constructor
    public FormPage(WebDriver driver) {
        this.driver = driver;
    }
    public Map<String, String> readFromExcel(FileInputStream file) throws IOException {
    	
        
	    Workbook workbook = new XSSFWorkbook(file);
	    Sheet sheet = workbook.getSheet("Sheet1");
	    Map<String, String> dataMap = new LinkedHashMap<>();
	    
	    //Getting all values together
	    Iterator<Row> rowIterator = sheet.iterator();
	    DataFormatter dataFormatter = new DataFormatter();
	    while (rowIterator.hasNext()) {
	        Row row = rowIterator.next();
	        Cell keyCell = row.getCell(0);
	        Cell valueCell = row.getCell(1);
                String key = dataFormatter.formatCellValue(keyCell);
                String value = dataFormatter.formatCellValue(valueCell);
                // Only put non-empty keys and values into the map
                if (!key.isBlank()  ) {
                	if(key.equals("Hobbies") || key.equals("Subjects")) {
                		int colIndex = 2;
                		for(Cell cell :row) {
                			if (cell.getCellType() == CellType.BLANK) {
                                // Exit the loop if an empty cell is encountered
                                break;
                            }
                			Cell hobbyCell = row.getCell(colIndex);
                			value = value + " " + dataFormatter.formatCellValue(hobbyCell);
                			colIndex++;
                		}
                	}
                    dataMap.put(key, value);
                }
        workbook.close();
	    }
	    System.out.println("MAp data" + dataMap);
	    return dataMap;

	    
	    }
	    
	    
    
    
    // Methods to interact with elements
    public void enterFirstName(String firstName) {
        WebElement firstNameElement = waitForElement(firstNameLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", firstNameElement);
        firstNameElement.click();
        firstNameElement.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        WebElement lastNameElement = driver.findElement(lastNameLocator);
        lastNameElement.click();
        lastNameElement.sendKeys(lastName);
    }

    public void enterEmail(String email) {
        WebElement emailElement = driver.findElement(emailLocator);
        emailElement.click();
        emailElement.sendKeys(email);
    }

    public void selectGender(String gender) {
        WebElement genderElement = driver.findElement(By.xpath("//label[text()='"+ gender+ "']"));
        genderElement.click();
    }

    public void enterPhoneNumber(String number) {
        WebElement numberElement = driver.findElement(numberLocator);
        numberElement.click();
        numberElement.sendKeys(number);
    }

    public void selectDateOfBirth(String month, String year, String date) {
        driver.findElement(dateOfBirthLocator).click();
        driver.findElement(monthLocator).click();//option[text()='"+March']
        driver.findElement(By.xpath("//option[text()='" + month + "']")).click();
        
        driver.findElement(yearLocator).click();
        driver.findElement(By.xpath("//option[text()='" + year +"']")).click();
        driver.findElement(By.xpath("//div[text()='" + date +"']")).click();
    }

    public void enterSubjects(String sub) {
        WebElement subjectElement = driver.findElement(subjectLocator);
        subjectElement.click();
        WebElement inputElement = driver.findElement(inputLocator);
        String[] subjects = sub.split(" ");
        for (String subject : subjects) {
            inputElement.sendKeys(subject);
            inputElement.sendKeys(Keys.ENTER);
        }
    }

    public void selectHobbies(String hobbies ) {
    	String[] hobby = hobbies.split(" ");
    	for(String hob : hobby) {
    		WebElement hobbiesElement = driver.findElement(By.xpath("//label[contains(text(), '"+ hob +"')]"));
            hobbiesElement.click();
    	}
        
    }

    public void uploadPicture(String filePath) {
        WebElement picElement = driver.findElement(picLocator);
        picElement.sendKeys(filePath);
    }

    public void enterAddress(String address) {
        WebElement addressElement = driver.findElement(addressLocator);
        addressElement.click();
        addressElement.sendKeys(address);
    }

    public void selectState(String state) {
        WebElement stateElement = driver.findElement(stateLocator);
        stateElement.click();
        WebElement stateNameElement = driver.findElement(stateNameLocator);

        stateNameElement.sendKeys(state);
        stateNameElement.sendKeys(Keys.ENTER);
    }   
     public void selectCity(String city) throws InterruptedException {
        WebElement cityElement = driver.findElement(cityLocator);
        cityElement.click();
        WebElement cityNameElement = driver.findElement(cityNameLocator);
        cityNameElement.sendKeys(city);
        cityNameElement.sendKeys(Keys.ENTER);
    }

     public void submitForm() {
         WebElement submitElement = driver.findElement(submitLocator);
         submitElement.click();
     }

    

    // Helper method to wait for an element
    private WebElement waitForElement(By locator) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(java.net.SocketException.class);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}

