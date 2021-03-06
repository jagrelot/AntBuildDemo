package Example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;
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
    
	Properties properties = new Properties();
	public static WebDriver chromeDriver;

	@BeforeTest
	public void beforeTestsetUp() throws IOException{
		
		System.setProperty("webdriver.chrome.driver", "/Driver/chromedriver"); 
		chromeDriver = new ChromeDriver();	
		InputStream input = new FileInputStream("/var/lib/jenkins/workspace/ANT Build Demo/config.properties"); 
	    properties.load(input);
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
		userName.sendKeys(properties.getProperty("userName"));
		passWord.sendKeys(properties.getProperty("passWord"));
		login.click();
	
	}
	
	@Test(priority=2)
	public void closeOverlay(){
		WebDriverWait wait = new WebDriverWait(chromeDriver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#tryLexDialogX"))).click();	
	}
	
	@Test(priority=3, description="Create Accounts in Salesforce")
	public void createAccounts(){
		
		WebElement    newBtn;
		WebElement  acctName;
		WebElement      save;
		int 	   count = 0;
		WebDriverWait wait = new WebDriverWait(chromeDriver, 40);
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
	
	@Test(priority=4, description="Update and Verify Named Credentials")
	public static void updateNamedCredentials(){

		WebElement      edit;
		WebElement namedCred;
		WebElement      save;
		String           url;
		WebDriverWait wait = new WebDriverWait(chromeDriver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#setupLink"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#Security_font"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#NamedCredential_font"))).click();
		edit = chromeDriver.findElement(By.cssSelector("td.actionColumn > a:first-child"));
		edit.click();
		namedCred = chromeDriver.findElement(By.id("namedCredential:NamedCredentialForm:thePageBlock:mainBlockSection:EndpointSection:Endpoint"));
		namedCred.clear();
		url = "http://seleniumdriverexample.com";
		namedCred.sendKeys(url);
		Assert.assertEquals(namedCred.getText(), "http://seleniumdriverexample.com", "The named credential for this enviroment is not correct");
		System.out.println("Named Credential was updated to " + url);
		save = chromeDriver.findElement(By.cssSelector("td.pbButton > input:first-child"));
		save.click();		
	}
	
//	@Test(priority=3, description="Sample Script")
//	public void runScript(){
//		WebElement    devConsole;
//		WebDriverWait wait = new WebDriverWait(chromeDriver, 40);
//		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#userNav-arrow"))).click();
//		devConsole = chromeDriver.findElement(By.cssSelector("a.debugLogLink.menuButtonMenuLink"));
//		devConsole.click();
//	    
//    	String salesForceWindow = chromeDriver.getWindowHandle();
//		//String scriptWindow;
//		Set<String> windows = chromeDriver.getWindowHandles();
//		for(String x : windows){
//			if(x.equals(salesForceWindow) == false){
//				chromeDriver.switchTo().window(x);
//				break;
//			}
//		}
//		
//		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#debugMenuEntry"))).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#openExecuteAnonymousWindow-textEl"))).click();
//		WebElement textArea;
//		textArea = chromeDriver.findElement(By.cssSelector("cm-word"));
//		textArea.clear();
//    	textArea.sendKeys("Jose Agrelot");
//	}

}
