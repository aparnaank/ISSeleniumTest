package org.ank.IStest;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
//import org.openqa.selenium.firefox.FirefoxDriver;
import  org.openqa.selenium.chrome.ChromeDriver;
import org.ank.IStest.Utils.*;


/**
 * Created by aparna on 11/29/17.
 */
public class IsLoginTestCase {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
//

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {

    System.setProperty("webdriver.chrome.driver",BrowserPath.CHROME_PATH);
//    driver = new FirefoxDriver();
    driver =new ChromeDriver();
//    driver = new HtmlUnitDriver(BrowserVersion.CHROME, true);
    baseUrl = Utils.BASE_URL;
    driver.manage().timeouts().implicitlyWait(Utils.WAIT_TIME, TimeUnit.SECONDS);
    }


    @Test (priority = 0, description = "Invalid password")
    public void testInvalidPasswdLogin() throws Exception {
        driver.get(baseUrl + "/carbon/admin/login.jsp");
        driver.findElement(By.id("txtUserName")).clear();
        driver.findElement(By.id("txtUserName")).sendKeys(Utils.USER_NAME);
        driver.findElement(By.id("txtPassword")).clear();
        driver.findElement(By.id("txtPassword")).sendKeys("qazx");
        driver.findElement(By.cssSelector("input.button")).click();
        Thread.sleep(2000);
        String invalidLoginErrMsg = driver.findElement(By.cssSelector("#messagebox-warning > p")).getText();
        Assert.assertEquals(invalidLoginErrMsg, Messages.EXPECT_INVALID_LOGIN_ERROR);
        driver.findElement(By.cssSelector("button[type=\"button\"]")).click();

    }


    @Test (priority = 1, description = "Invalid username")
    public void testInvalidUsernameLogin() throws Exception {

        driver.get(baseUrl + "/carbon/admin/login.jsp");
        driver.findElement(By.id("txtUserName")).clear();
        driver.findElement(By.id("txtUserName")).sendKeys("qwert");
        driver.findElement(By.id("txtPassword")).clear();
        driver.findElement(By.id("txtPassword")).sendKeys(Utils.PASSWORD);
        driver.findElement(By.cssSelector("input.button")).click();
        Thread.sleep(2000);
        String invalidLoginErrMsg = driver.findElement(By.cssSelector("#messagebox-warning > p")).getText();
        Assert.assertEquals(invalidLoginErrMsg, Messages.EXPECT_INVALID_LOGIN_ERROR);
        driver.findElement(By.cssSelector("button[type=\"button\"]")).click();
    }


    @Test (priority = 2, description = "Invalid username & password")
    public void testInvalidBothLogin() throws Exception {

        driver.get(baseUrl + "/carbon/admin/login.jsp");
        driver.findElement(By.id("txtUserName")).clear();
        driver.findElement(By.id("txtUserName")).sendKeys("aaaaa");
        driver.findElement(By.id("txtPassword")).clear();
        driver.findElement(By.id("txtPassword")).sendKeys("xxxxx");
        driver.findElement(By.cssSelector("input.button")).click();
        Thread.sleep(2000);
        String invalidLoginErrMsg = driver.findElement(By.cssSelector("#messagebox-warning > p")).getText();
        Assert.assertEquals(invalidLoginErrMsg, Messages.EXPECT_INVALID_LOGIN_ERROR);
        driver.findElement(By.cssSelector("button[type=\"button\"]")).click();
    }


    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}
