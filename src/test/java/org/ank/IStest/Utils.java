package org.ank.IStest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by aparna on 12/11/17.
 */
public class Utils {

    private WebDriver driver;

    public static final int WAIT_TIME = 30; // Delay time to wait the website
    // launch completely
    public static final String BASE_URL = "https://localhost:9443/";

    // Valid account for login
    public static final String USER_NAME = "admin";
    public static final String PASSWORD = "admin";

    public static final String VALID = "valid";
    public static final String INVALID = "invalid";

    // Expected output
    public static final class Messages{
        public static final String EXPECT_IAM_TITLE = "WSO2 Management Console";
        public static final String EXPECT_TITLE = "WSO2 Identity Server Home";
        public static final String EXPECT_INVALID_LOGIN_ERROR = "Login failed! Please recheck the username and password and try again.";
    }

    /* You can change the Path of FireFox base on your environment here */
    public static final class BrowserPath{
        public static final String FIREFOX_PATH = "src/main/resources/drivers/firefox/qeckodriver";
        public static final String CHROME_PATH = "src/main/resources/drivers/chrome/chromedriver";
    }

    public static final class TravelocitySP{
        public static final String ASSERTION_CONSUMER_URL="http://localhost:8080/travelocity.com/home.jsp";
        public static final String ISSUER = "travelocity.com";
        public static final String EXPECT_EMPTY_SP_LIST = "No Service Providers registered";
    }

    public static final class GoogleIDP {
        public static final String GOOGLE_CLIENT_ID = "34901453018-ckvpj407i7gigvip69961nud9ko28bvq.apps.googleusercontent.com";
        public static final String GOOGLE_CLIENT_SECRET = "clKhaedjxS2sc_xL1tetCHw0";
        public static final String CALL_BACK_URL = "https://localhost:9443/commonauth";
        public static final String EXPECT_EMPTY_IDP_LIST = "No Identity Providers registered";
    }

    public static final String NEWROLENAME = "testRole";
    public static final String NEWUSER = "testUser";

    // You can change the information of your data file here
//    public static final String FILE_PATH = "testData.xls"; // File Path
//    public static final String SHEET_NAME = "Data"; // Sheet name
//    public static final String TABLE_NAME = "testData"; // Name of data table
}
