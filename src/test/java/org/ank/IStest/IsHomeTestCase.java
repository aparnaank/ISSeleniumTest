package org.ank.IStest;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
//import org.openqa.selenium.firefox.FirefoxDriver;
import  org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


public class IsHomeTestCase {

  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {

      System.setProperty("webdriver.chrome.driver", Util.CHROME_PATH);
//    driver = new FirefoxDriver();
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


  @Test(dependsOnMethods = "testISLogin",priority = 1)
  public void verifyHomepage(){

      String headerTitle = driver.findElement(By.xpath("//div[@id='middle']/h2")).getText();
      Assert.assertEquals(headerTitle,Util.EXPECT_TITLE);
  }

  @Test (dependsOnMethods = "testISLogin",priority = 2)
  public void testSpCreation() throws Exception {

      String spPostFix = RandomStringUtils.randomNumeric(4);
      driver.findElement(By.xpath("(//a[contains(text(),'Add')])[4]")).click();
      driver.findElement(By.id("spName")).clear();
      driver.findElement(By.id("spName")).sendKeys("TestSP"+ spPostFix);
      driver.findElement(By.cssSelector("input.button")).click();
      Thread.sleep(2000);
      driver.findElement(By.linkText("Inbound Authentication Configuration")).click();
      Thread.sleep(2000);
      driver.findElement(By.linkText("SAML2 Web SSO Configuration")).click();
      Thread.sleep(2000);
      driver.findElement(By.id("saml_link")).click();
      driver.findElement(By.id("issuer")).clear();
      driver.findElement(By.id("issuer")).sendKeys(Util.ISSUER);
      driver.findElement(By.id("assertionConsumerURLTxt")).clear();
      driver.findElement(By.id("assertionConsumerURLTxt")).sendKeys(Util.ASSERTION_CONSUMER_URL);
      driver.findElement(By.id("addAssertionConsumerURLBtn")).click();
      driver.findElement(By.cssSelector("button[type=\"button\"]")).click();
      driver.findElement(By.name("enableResponseSignature")).click();
      driver.findElement(By.name("enableSingleLogout")).click();
      driver.findElement(By.cssSelector("input.button")).click();
      driver.findElement(By.cssSelector("button[type=\"button\"]")).click();
      driver.findElement(By.cssSelector("input[type=\"button\"]")).click();

      //Verifying the created SP
      String createdSP = driver.findElement(By.xpath("//table[@id='ServiceProviders']/tbody/tr/td")).getText();
      String expectedSpName = "TestSP" + spPostFix;
      Assert.assertEquals(createdSP,expectedSpName);

      driver.findElement(By.cssSelector("img")).click();

    }


    @Test (dependsOnMethods = "testISLogin",priority = 3)
    public void testIdpCreation() throws Exception {

        String idpPostFix = RandomStringUtils.randomNumeric(4);
        String idpName = "GoogleIDP"+ idpPostFix;

        driver.findElement(By.xpath("(//a[contains(text(),'Add')])[5]")).click();
        driver.findElement(By.id("idPName")).clear();
        driver.findElement(By.id("idPName")).sendKeys(idpName);
        driver.findElement(By.id("idpDisplayName")).clear();
        driver.findElement(By.id("idpDisplayName")).sendKeys(idpName);
        driver.findElement(By.id("idPDescription")).clear();
        driver.findElement(By.id("idPDescription")).sendKeys(idpName);
        driver.findElement(By.linkText("Federated Authenticators")).click();
        driver.findElement(By.linkText("Google Configuration")).click();
        driver.findElement(By.id("GoogleOIDCAuthenticator_Enabled")).click();
        driver.findElement(By.id("cust_auth_prop_GoogleOIDCAuthenticator#ClientId")).clear();
        driver.findElement(By.id("cust_auth_prop_GoogleOIDCAuthenticator#ClientId")).sendKeys(Util.GOOGLE_CLIENT_ID);
        driver.findElement(By.id("cust_auth_prop_GoogleOIDCAuthenticator#ClientSecret")).clear();
        driver.findElement(By.id("cust_auth_prop_GoogleOIDCAuthenticator#ClientSecret")).sendKeys(Util.GOOGLE_CLIENT_SECRET);
        driver.findElement(By.id("cust_auth_prop_GoogleOIDCAuthenticator#callbackUrl")).clear();
        driver.findElement(By.id("cust_auth_prop_GoogleOIDCAuthenticator#callbackUrl")).sendKeys(Util.CALL_BACK_URL);
        driver.findElement(By.cssSelector("div.buttonRow > input[type=\"button\"]")).click();

        driver.findElement(By.cssSelector("img")).click();
    }

    @Test (dependsOnMethods = "testISLogin",priority = 4)
    public void testSPDelete() throws Exception {

        driver.findElement(By.xpath("(//a[contains(text(),'List')])[4]")).click();
        driver.findElement(By.linkText("Delete")).click();
        Thread.sleep(4000);
        driver.findElement(By.cssSelector("button[type=\"button\"]")).click();
        String emptySpList = driver.findElement(By.cssSelector("i")).getText();
        assertEquals(emptySpList,Util.EXPECT_EMPTY_SP_LIST);

        driver.findElement(By.cssSelector("img")).click();
    }

    @Test (dependsOnMethods = "testISLogin",priority = 5)
    public void testIdpDelete() throws Exception {

        driver.findElement(By.xpath("(//a[contains(text(),'List')])[5]")).click();
        driver.findElement(By.linkText("Delete")).click();
        driver.findElement(By.cssSelector("button[type=\"button\"]")).click();
        Thread.sleep(2000);
        String emptyIdpList = driver.findElement(By.cssSelector("i")).getText();
        assertEquals(emptyIdpList,Util.EXPECT_EMPTY_IDP_LIST);

        driver.findElement(By.cssSelector("img")).click();
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
