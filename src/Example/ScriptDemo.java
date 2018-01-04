package Example;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class ScriptDemo {
	
	public WebDriver chromeDriver;
	
	@BeforeTest
	public void beforeTestsetUp(){
		
		System.setProperty("webdriver.chrome.driver", "/Driver/chromedriver");
		chromeDriver = new ChromeDriver();
	}
	
	@Test(priority=1,description="Verify Login")
	public void loginTest(){
		
		WebElement  userName;
		WebElement  passWord;
		WebElement     login;
		
		chromeDriver.get("https://na59.salesforce.com");
		userName = chromeDriver.findElement(By.cssSelector("#username"));
		passWord = chromeDriver.findElement(By.cssSelector("#password"));
		login    = chromeDriver.findElement(By.cssSelector("#Login"));
		userName.sendKeys("jagrelot@orgdemo.com");
		passWord.sendKeys("qWaG131o");
		login.click();
	
	}
	
	@Test(priority=2, description="Create Accounts in Salesforce")
	public void createAccounts(){
		
		WebElement    newBtn;
		WebElement  acctName;
		WebElement      save;
		int 	   count = 0;
		WebDriverWait wait = new WebDriverWait(chromeDriver, 20);
		ArrayList<String> acctsToCreate = new ArrayList<String>();
		acctsToCreate.add("Test Account TestNG - 1 - Jenkins EC2-ANT");
		acctsToCreate.add("Test Account TestNG - 2 - Jenkins EC2 ANT");
	
		for (String acct : acctsToCreate ) {
				
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#Account_Tab > a"))).click();
			newBtn = chromeDriver.findElement(By.cssSelector("[name='new']"));
			newBtn.click();
			acctName = chromeDriver.findElement(By.cssSelector("#acc2"));
			acctName.sendKeys(acct);
			save = chromeDriver.findElement(By.cssSelector("td.pbButton > input:first-child"));
			save.click();
			count++;
			System.out.println(acct + " was created Count:" + count);
		}
		
		Assert.assertEquals(count, 2, "Error: The number of accounts created does not match");

	}
}
