package org.ank.IStest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import java.util.concurrent.TimeUnit;

import org.ank.IStest.Utils.*;

/**
 * Created by aparna on 12/12/17.
 */
public class UserManagementTestCase {
    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {

        System.setProperty("webdriver.chrome.driver", BrowserPath.CHROME_PATH);
        driver =new ChromeDriver();
        baseUrl = Utils.BASE_URL;
        driver.manage().timeouts().implicitlyWait(Utils.WAIT_TIME, TimeUnit.SECONDS);
    }

    @Test (priority = 0)
    public void testISLogin() throws Exception {
        driver.get(baseUrl + "/carbon/admin/login.jsp");
        driver.findElement(By.id("txtPassword")).clear();
        driver.findElement(By.id("txtPassword")).sendKeys(Utils.USER_NAME);
        driver.findElement(By.id("txtUserName")).clear();
        driver.findElement(By.id("txtUserName")).sendKeys(Utils.PASSWORD);
        driver.findElement(By.cssSelector("input.button")).click();

        Thread.sleep(2000);
    }

    @Test (dependsOnMethods = "testISLogin",priority = 1)
    public void testRoleAdd() throws Exception {
        driver.findElement(By.linkText("Add")).click();
        driver.findElement(By.linkText("Add New Role")).click();
        driver.findElement(By.name("roleName")).clear();
        driver.findElement(By.name("roleName")).sendKeys(Utils.NEWROLENAME);
        driver.findElement(By.cssSelector("input.button")).click();
        driver.findElement(By.cssSelector("#ygtvcheck2 > div.ygtvspacer")).click();
        driver.findElement(By.cssSelector("input.button")).click();
        driver.findElement(By.cssSelector("td.buttonRow > input.button")).click();

        String EXPECT_CONF_MSG = "Role PRIMARY/"+ Utils.NEWROLENAME+" is added successfully.";
        String ACTUAL_CONF_MSG = driver.findElement(By.cssSelector("#messagebox-info > p")).getText();

        driver.findElement(By.cssSelector("button[type=\"button\"]")).click();

        String createdRole = driver.findElement(By.xpath("//table[@id='roleTable']/tbody/tr[3]/td")).getText();
        assertEquals(createdRole, Utils.NEWROLENAME);
    }

    @Test (dependsOnMethods = "testISLogin",priority = 2)
    public void testUserAdd() throws Exception {
        driver.findElement(By.linkText("Add")).click();
        driver.findElement(By.linkText("Add New User")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys(Utils.NEWUSER);
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(Utils.NEWUSER);
        driver.findElement(By.id("password-repeat")).clear();
        driver.findElement(By.id("password-repeat")).sendKeys(Utils.NEWUSER);
        driver.findElement(By.cssSelector("input.button")).click();
        // ERROR: Caught exception [Error: Dom locators are not implemented yet!]
        driver.findElement(By.cssSelector("td.buttonRow > input.button")).click();

        String EXPECT_CONF_MSG = "User PRIMARY/"+ Utils.NEWUSER+" is added successfully.";
        String ACTUAL_CONF_MSG = driver.findElement(By.cssSelector("#messagebox-info > p")).getText();
        assertEquals(ACTUAL_CONF_MSG,EXPECT_CONF_MSG);
        driver.findElement(By.cssSelector("button[type=\"button\"]")).click();

        String createdUser = driver.findElement(By.xpath("//table[@id='userTable']/tbody/tr[2]/td")).getText();
        assertEquals(createdUser, Utils.NEWUSER);
        driver.findElement(By.linkText("List")).click();

    }

    public void userLogin() throws Exception {
        driver.findElement(By.cssSelector("img")).click();
        driver.findElement(By.linkText("Sign-out")).click();

        driver.findElement(By.id("txtUserName")).clear();
        driver.findElement(By.id("txtUserName")).sendKeys(Utils.NEWUSER);
        driver.findElement(By.id("txtPassword")).clear();
        driver.findElement(By.id("txtPassword")).sendKeys(Utils.NEWUSER);
        driver.findElement(By.cssSelector("input.button")).click();

        String isTitle = driver.getTitle();
        assertEquals(isTitle,Messages.EXPECT_IAM_TITLE );

        driver.findElement(By.linkText("Sign-out")).click();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
    
    private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
    
}
