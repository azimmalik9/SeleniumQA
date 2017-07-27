package DDT;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import lib.ExcelDataConfig;

public class PhpTravelLogin 
{
	
	WebDriver driver;
	static File src;
	static XSSFWorkbook wb;
	static XSSFSheet sheet1; 
	
	@BeforeClass
	public void setup()
	{
	System.setProperty("webdriver.chrome.driver", "C:\\Users\\Azim\\Desktop\\Selinium2\\chromedriver.exe");
	
	driver = new ChromeDriver();
	driver.get("http://www.phptravels.net/");
	}
	
	@Test(dataProvider="websiteLogin")
	public void loginToWordpress(String username, String password)  
	{						
		
		driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/ul/li[2]/a")).click();
		 
		driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/ul/li[2]/ul/li[1]/a")).click();								
		
		 WebDriverWait wait = new WebDriverWait(driver, 10);

		 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"loginfrm\"]/div[4]/button")));
		 
		driver.findElement(By.name("username")).sendKeys(username);
		
		driver.findElement(By.name("password")).sendKeys(password);
		
		driver.findElement(By.xpath("//*[@id=\"loginfrm\"]/div[4]/button")).click();				
	

		 
		  wait.until(ExpectedConditions.titleContains("My Account"));
		 
		 
		
		Assert.assertTrue("User is not able to login - Invalid Credentials",driver.getTitle().contains("My Account"));
		
		System.out.println("Page Title verified - User is able to login successfully");
		
		driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
		System.out.println(driver.getTitle());
	}
	
	
	
	@AfterMethod
	public void tearDown(ITestResult result) throws Exception
	{
		ExcelDataConfig config = new ExcelDataConfig("C:\\Azim\\ExcelData\\TestData.xlsx");		
		
		int rows = config.getRowCount(0);
		
		src = new File("C:\\Azim\\ExcelData\\TestData.xlsx");
		
		FileInputStream fis = new FileInputStream(src);
		
		wb = new XSSFWorkbook(fis);
		
		sheet1 = wb.getSheet("Input Data");
		String output;
		try
		{	   	    
			for(int i=0;i<rows;i++)
			{
				switch (result.getStatus())
				{
				case ITestResult.SUCCESS:
			 output = "Pass";
					
			config.setData(0, i, 0, output);
					
					break;
					
					
				case ITestResult.FAILURE:
					 output = "Fail";
					config.setData(0, i, 0, output);
					sheet1.getRow(i).createCell(2).setCellValue(config.setData(0, i, 0, output));
					
					break;
					
					
				case ITestResult.SKIP:
					
					sheet1.getRow(i).createCell(2).setCellValue("Skipped");
					
					break;
					
					
				default:
					
					break;
				}
				
				FileOutputStream fout = new FileOutputStream(src);   	        
				
				wb.write(fout);
				}
		}
		
		catch(Exception e)
		{
			System.out.println("\nLog Message::@AfterMethod: Exception caught");
			
			e.printStackTrace();
		}
		
		wb.close();
		
		driver.quit();
		
	}
			
	
	
	
	@DataProvider(name="websiteLogin")
	public Object[][] passData()
	{
		// Save the test data file in a separate folder loads the data faster during testing.
		ExcelDataConfig config = new ExcelDataConfig("C:\\Azim\\ExcelData\\TestData.xlsx");		
		
		int rows = config.getRowCount(0);
		
		Object[][] data=new Object[rows][2];
		
		for(int i=0;i<rows;i++)
		{
			data[i][0]=config.getData(0, i, 0);
			data[i][1]=config.getData(0, i, 1);									
			
		}				
		
		return data;
		
	}
	
	
	
	

}
