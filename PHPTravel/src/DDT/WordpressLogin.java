package DDT;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.junit.Assert;

public class WordpressLogin 
{
	
	WebDriver driver;
		
	@Test(dataProvider="wordpressData")
	public void loginToWordpress(String username, String password) throws InterruptedException
	{
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Azim\\Desktop\\Selinium2\\chromedriver.exe");
		
		driver = new ChromeDriver();
		
		driver.get("http://demosite.center/wordpress/wp-login.php");
		
		driver.findElement(By.id("user_login")).sendKeys(username);
		
		driver.findElement(By.id("user_pass")).sendKeys(password);
		
		driver.findElement(By.xpath("//*[@id=\"wp-submit\"]")).click();
		
		Thread.sleep(5000);
		
		System.out.println(driver.getTitle());
		
		Assert.assertTrue("User is not able to login - Invalid Credentials",driver.getTitle().contains("Dashboard"));
		
		System.out.println("Page Title verified - User is able to login Successfully");
		
		driver.quit();
	}
	
	
	
	
	
	@DataProvider(name="wordpressData")
	public Object[][] passData()
	{
		
		Object[][] data = new Object[3][2];
		
		data[0][0]="admin1";
		
		data[0][1]="demo1";
		
		
		data[1][0]="admin";
		
		data[1][1]="demo123";
		
		
		data[2][0]="admin2";
		
		data[2][1]="demo1234";
		
		return data;
	}

}
