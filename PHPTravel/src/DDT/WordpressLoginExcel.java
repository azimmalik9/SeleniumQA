package DDT;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import lib.ExcelDataConfig;

import org.junit.Assert;

public class WordpressLoginExcel 
{
	
	WebDriver driver;
	
	@BeforeMethod
	public void setup()
	{
	System.setProperty("webdriver.chrome.driver", "C:\\Users\\Azim\\Desktop\\Selinium2\\chromedriver.exe");
	
	driver = new ChromeDriver();
	}
	
	@Test(dataProvider="wordpressData")
	public void loginToWordpress(String username, String password) throws Exception
	{						
		driver.get("http://demosite.center/wordpress/wp-login.php");
		
		driver.findElement(By.id("user_login")).sendKeys(username);
		
		driver.findElement(By.id("user_pass")).sendKeys(password);
		
		driver.findElement(By.xpath("//*[@id=\"wp-submit\"]")).click();
		
		Thread.sleep(5000);
		
		System.out.println(driver.getTitle());
		
		Assert.assertTrue("User is not able to login - Invalid Credentials",driver.getTitle().contains("Dashboard"));
		
		System.out.println("Page Title verified - User is able to login Successfully");
				
	}
	
	
	@AfterMethod
	public void tearDown()
	{
		
		driver.quit();
	}
	
	
	@DataProvider(name="wordpressData")
	public Object[][] passData()
	{
		
		ExcelDataConfig config = new ExcelDataConfig("C:\\ToolsQA\\LearnAutomation\\TestData\\inputData.xlsx");
		
		int rows = config.getRowCount(0);
		
		Object[][] data = new Object[rows][3];
		
		for(int i=0;i<rows;i++)
		{
			
			data[i][0]=config.getData(0, i, 0);
			data[i][1]=config.getData(0, i, 1);
			
			
		}
		
		
		return data;
	}

}
