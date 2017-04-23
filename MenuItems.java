package assinments;
import java.util.List;
//import java.util.Set;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

//filesystem
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
 
public class MenuItems {
      
      
       public static  WebDriver driver;
      
       public static String workingDir = System.getProperty("user.dir");
      
       public static String[] data_array;
      
       public static String[] menu_array;
       
       	private static ExtentReports extent;
       	private static ExtentTest test;
   		private static ExtentHtmlReporter htmlReporter;
   		private static String filePath = workingDir +"\\TestReport.html";
//================================================================================================================================
       
       @BeforeSuite
       public static ExtentReports GetExtent()
       {
    	   if (extent != null)
    		   return extent;  //avoid creating new instance of html file
           extent = new ExtentReports();		
           extent.attachReporter(getHtmlReporter());	
           return extent;
       }
       
       private static ExtentHtmlReporter getHtmlReporter() {
    		
           htmlReporter = new ExtentHtmlReporter(filePath);
   		
   	// make the charts visible on report open
           htmlReporter.config().setChartVisibilityOnOpen(true);
   		
           htmlReporter.config().setDocumentTitle("Automation report");
           htmlReporter.config().setReportName("TA Assignments");
           return htmlReporter;
   	}
       
       @AfterSuite
       public void Cleanup()
       {
    	   
   		//test.log(Status.INFO, "fail check started");
   		
   		extent.flush();
		extent.close();
       }
       
       @Parameters("url")
       @Test 
       public void declare(String param) throws InterruptedException
       {
       test = extent.createTest("Testing TransAmerica Menu Lists");
       System.setProperty("webdriver.chrome.driver", workingDir+"/Drivers/chromedriver.exe");
       driver = new ChromeDriver();
       driver.navigate().to(param);
       Thread.sleep(10000);
       int present =
       driver.findElements(By.xpath("//div[contains(@class, 'ui-dialog-content')]")).size();
       if (present > 0)
       {
       System.out.println("inside the cookies");
       driver.findElement(By.xpath("//div[starts-with(@class, 'ui-dialog-titlebar')]/button")).click();
       }
       }
//================================================================================================================================      
 
       public static String[] excelread(String FindValue) throws Exception{
             
              data_array = null;
             
              FileInputStream file = new FileInputStream(
                new File(workingDir+"/Data/TestData.xlsx"));
              int rownum = 0;
              // Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        // Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);
       
               for (Row row : sheet) {
                   for (Cell cell : row) {
                       if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                           if (cell.getRichStringCellValue().getString().trim().equals(FindValue)) {
                                 rownum = row.getRowNum(); 
                           }
                       }
                   }
               } 
               
               XSSFRow getrw = sheet.getRow(rownum);
              
               int maxCell=  getrw.getLastCellNum();
               data_array = new String[maxCell];
              
               for (int col=0; col<maxCell; col++)
               {
                     System.out.print(getrw.getCell(col).getStringCellValue()+"|| ");
                     data_array[col] = getrw.getCell(col).getStringCellValue();
                     
               }
               return data_array;
       }
      
//================================================================================================================================
      
//================================================================================================================================
       @Test (dependsOnMethods = "declare")
       public void aboutus_link() throws Exception{
              String data = "aboutus";
              driver.findElement(By.xpath("//li[@data-menu = 'about-us']")).click();
              MenuItems.menulist(data);
       }
//================================================================================================================================      
       @Test (dependsOnMethods = "declare")
       public void Search_Link() throws Exception{
              String data = "search";
              driver.findElement(By.xpath("//li[@data-menu = 'search']")).click();
              MenuItems.menulist(data);
       }
//================================================================================================================================
       @Test (dependsOnMethods = "declare")
       public void Product_Link() throws Exception{
              String data = "products";
              driver.findElement(By.xpath("//li[@data-menu = 'products']")).click();
              MenuItems.menulist(data);
       }
 
//================================================================================================================================
       public static void menulist(String data) throws Exception
       {
         
              //driver.findElement(By.xpath("//li[@data-menu = 'search']")).click();
                
              //8888
              //Set<WebElement> elem0 = (Set<WebElement>) driver.findElements(By.xpath("//div[contains(@class,'menucolumn')]/span/a"));
                
              List<WebElement> elem  = driver.findElements(By.xpath("//div[contains(@class,'menucolumn')]/span/a"));
                
              List<WebElement> elem1  = driver.findElements(By.xpath("//ul[contains(@class,'ta-menu')]/li"));
         
        
              //MenuItems.excelread("search");
              //8888
              MenuItems.excelread(data);
              int len = MenuItems.data_array.length;
              String str;
      
              for (int i=0;i<elem.size();i++)
                
              {
                
                     System.out.println(elem.get(i).getText());
                     str = elem.get(i).getText();
                     if( str!= null && !str.isEmpty())
                     {
                           for(int j=0; j<len; j++)
                           {
                                  if (data_array[j].equalsIgnoreCase(str))
                                  {
                                        // System.out.println("Application value "+ str +"  matches with TestData");
                                         test.pass("Application value "+ str +"  matches with TestData");
                                         break;
                                  }
                                  else if (j == len-1){
                                        // System.out.println("Application value "+ str +"  not matches with TestData");
                                         test.fail("Application value "+ str +"  not matches with TestData");
                                  }
                                                             
                           }
                          
                     }
              }
         
        
          
              for (int i=0;i<elem1.size();i++)
                
              {
                
                     str = elem1.get(i).getText();
                     if( str!= null && !str.isEmpty())
                     {
                           for(int j=0; j<len; j++)
                           {
                                         //System.out.println(data_array[(j + 1)]);
                                  if (data_array[j].equalsIgnoreCase(str))
                                  {
                                	  	test.pass("Application value "+ str +"  matches with TestData");
                                         break;
                                  }
                                  else if (j == (len-1)){
                                	  	test.fail("Application value "+ str +"  not matches with TestData");
                                  }
                                                             
                           }
                          
                     }
             
                
              }
         
        
       }
      
//================================================================================================================================
}