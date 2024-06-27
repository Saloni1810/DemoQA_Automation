package com.kiwiqa.pages;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OutputPage {
	
	WebDriver driver;
	
	By fieldsLocator = By.xpath("//tbody//td//following-sibling::td");
	
	public OutputPage(WebDriver driver) {
		this.driver = driver;
	}
	public List<WebElement> getFormFields() {
        return driver.findElements(fieldsLocator);
    }
	
	public void verify() throws IOException {
		 // Verifying the details
        String filePath = "C:\\Users\\HP\\Documents\\verify.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        List<WebElement> fields = getFormFields();
        for (WebElement field : fields) {
            line = reader.readLine();
            if (line.equals(field.getText())) {
                System.out.println(line + " Correct");
            }
        }
        reader.close();
	}

}
