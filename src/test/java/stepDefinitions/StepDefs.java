package stepDefinitions;



import java.io.File;
import java.io.IOException;

import java.util.Date;


import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefs {
	

	public WebDriver driver;
	public Properties props;

	
	
	@Before()
	public void browserInitialize() throws IOException {
	
	   
		System.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver.exe");
		/*Map<String, Object> chromePreferences = new Hashtable<String, Object>();
		 Below two chrome preference settings will disable popup dialog when download file.
		chromePreferences.put("profile.default_content_settings.popups", 0);
		chromePreferences.put("download.prompt_for_download", "false");
		
		 Set file save to directory. 
		chromePreferences.put("download.default_directory", "C:\\WorkSpace");

		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("prefs", chromePreferences);

		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);*/
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		

	}
	
	

	public void takeScreenShot(){
        // fileName of the screenshot
        Date d=new Date();
        String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
        // store screenshot in that file
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
               FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//Fail_Screenshot//"+screenshotFile));
        } catch (IOException e) {
               // TODO Auto-generated catcsh block
               e.printStackTrace();
        }
        //put screenshot file in reports
       
        
        
 }
	//login to the application
	
	@Given("^User is on Home Page$")
	public void user_is_on_Home_Page() throws Throwable {
		driver.get("https://accounts.zoho.com/signin?servicename=AaaServer&serviceurl=%2Fu%2Fh%23home");
	   
	}

	@When("^User Navigate to LogIn Page$")
	public void user_Navigate_to_LogIn_Page() throws Throwable {
		
		
		//driver.findElement(By.xpath("//*[text()='LOGIN']")).click();
		
		
	}

	@When("^User enters UserName and Password$")
	public void user_enters_UserName_and_Password() throws Throwable {
		driver.findElement(By.id("lid")).sendKeys("kaushikmohit54@gmail.com");
		
		driver.findElement(By.id("pwd")).sendKeys("Faltuzoho@123");
	    
	}

	@Then("^Message displayed Login Successfully$")
	public void message_displayed_Login_Successfully() throws Throwable {
		//span[contains(@class,'eight')]
		
		driver.findElement(By.xpath("//text()[.='Sign In']/ancestor::div[1]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[@class='five']")).click();
		driver.findElement(By.xpath("//span[text()='CRM']")).click();
		
		//for switching a window
		for(String handle:driver.getWindowHandles() ) {
			driver.switchTo().window(handle);
			
		}
		
		
		
        driver.findElement(By.xpath("//img[@id='topdivuserphoto_3609886000000182021']")).click();
        Thread.sleep(2000);
		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
		
		WebDriverWait wait=new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@src='https://www.zoho.com/crm/lp/images/green-tick.gif']")));
		boolean val=driver.findElement(By.xpath("//img[@src='https://www.zoho.com/crm/lp/images/green-tick.gif']")).isDisplayed();
		Assert.assertTrue(val);	
		
		
		
	}

	@When("^User LogOut from the Application$")
	public void user_LogOut_from_the_Application() throws Throwable {
		driver.findElement(By.xpath("//img[@id='topdivuserphoto_3609886000000182021']")).click();
		
		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
	}

	@Then("^Message displayed LogOut Successfully$")
	public void message_displayed_LogOut_Successfully() throws Throwable {
	
	    
	}




	
		@After()
	public void embedScreenshot(Scenario scenario) throws IOException {
		if (scenario.isFailed()) {
			try {
				final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
				scenario.embed(screenshot, "image/png");
			} catch (WebDriverException wde) {
				System.err.println(wde.getMessage());
			} catch (ClassCastException cce) {
				cce.printStackTrace();
			}
		}
				driver.quit();
	}

}