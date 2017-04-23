package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class print {
	public static WebDriver driver;
	
	@Test
	public void launch() {
		// TODO Auto-generated method stub

	System.out.println("welcome");
	
	System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver.exe");
	//System.setProperty("webdriver.chrome.driver", "C:/Users/PRK/Desktop/SeleniumArtifacts/POM/POM/Drivers/chromedriver.exe");
	
	driver = new ChromeDriver();	
	
	driver.get("http://www.google.com");
	}
	

}
