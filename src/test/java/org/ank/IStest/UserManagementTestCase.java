package org.ank.IStest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

/**
 * Created by aparna on 12/12/17.
 */
public class UserManagementTestCase {
    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {

        System.setProperty("webdriver.chrome.driver", Util.CHROME_PATH);
        driver =new ChromeDriver();
        baseUrl = Util.BASE_URL;
        driver.manage().timeouts().implicitlyWait(Util.WAIT_TIME, TimeUnit.SECONDS);
    }

    @Test (priority = 0)
    public void testISLogin() throws Exception {
        driver.get(baseUrl + "/carbon/admin/login.jsp");
        driver.findElement(By.id("txtPassword")).clear();
        driver.findElement(By.id("txtPassword")).sendKeys(Util.USER_NAME);
        driver.findElement(By.id("txtUserName")).clear();
        driver.findElement(By.id("txtUserName")).sendKeys(Util.PASSWORD);
        driver.findElement(By.cssSelector("input.button")).click();

        Thread.sleep(2000);
    }

    @Test (dependsOnMethods = "testISLogin",priority = 1)
    public void testRoleAdd() throws Exception {
        driver.findElement(By.linkText("Add")).click();
        driver.findElement(By.linkText("Add New Role")).click();
        driver.findElement(By.name("roleName")).clear();
        driver.findElement(By.name("roleName")).sendKeys(Util.NEWROLENAME);
        driver.findElement(By.cssSelector("input.button")).click();
        driver.findElement(By.cssSelector("#ygtvcheck2 > div.ygtvspacer")).click();
        driver.findElement(By.cssSelector("input.button")).click();
        driver.findElement(By.cssSelector("td.buttonRow > input.button")).click();

        String EXPECT_CONF_MSG = "Role PRIMARY/"+Util.NEWROLENAME+" is added successfully.";
        String ACTUAL_CONF_MSG = driver.findElement(By.cssSelector("#messagebox-info > p")).getText();
        System.out.println("MSG 1===============================" + ACTUAL_CONF_MSG);

        driver.findElement(By.cssSelector("button[type=\"button\"]")).click();

        String createdRole = driver.findElement(By.xpath("//table[@id='roleTable']/tbody/tr[4]/td")).getText();
        System.out.println("MSG 2===============================" + createdRole);
        assertEquals(createdRole,Util.NEWROLENAME);
    }

    @Test (dependsOnMethods = "testISLogin",priority = 2)
    public void testUserAdd() throws Exception {
        driver.findElement(By.linkText("Add")).click();
        driver.findElement(By.linkText("Add New User")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys(Util.NEWUSER);
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(Util.NEWUSER);
        driver.findElement(By.id("password-repeat")).clear();
        driver.findElement(By.id("password-repeat")).sendKeys(Util.NEWUSER);
        driver.findElement(By.cssSelector("input.button")).click();
        // ERROR: Caught exception [Error: Dom locators are not implemented yet!]
        driver.findElement(By.cssSelector("td.buttonRow > input.button")).click();

        String EXPECT_CONF_MSG = "User PRIMARY/"+Util.NEWUSER+" is added successfully.";
        String ACTUAL_CONF_MSG = driver.findElement(By.cssSelector("#messagebox-info > p")).getText();
        assertEquals(ACTUAL_CONF_MSG,EXPECT_CONF_MSG);
        driver.findElement(By.cssSelector("button[type=\"button\"]")).click();

        String createdUser = driver.findElement(By.xpath("//table[@id='userTable']/tbody/tr[4]/td")).getText();
        System.out.println("MSG ==============================="+createdUser);
        assertEquals(createdUser, Util.NEWUSER);
        driver.findElement(By.linkText("List")).click();
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
