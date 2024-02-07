package Stepdefinations;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.TestUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import static util.TestUtil.EXPLICIT_WAIT;

public class UItest {

    public static WebDriver driver;
    public static Properties prop;
    public static WebDriverWait wait;
    String url;

    @io.cucumber.java.en.Given("user setup tests")
    public void Initialize(){
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream("src/main/java/configs/config.properties");
            prop.load(ip);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @io.cucumber.java.en.And("user opens a web browser")
    public void userOpensAWebBrowser() throws IOException {
        String browserName = prop.getProperty("browser");
        if (browserName.equals("chrome")) {
            driver = new ChromeDriver();
        }
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT,TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
        driver.manage().deleteAllCookies();
        url =prop.getProperty("url");
    }


    @io.cucumber.java.en.And("Navigate to stackoverflow page")
    public void navigateToStackoverflowPage() {
        driver.get(url);
        String title = driver.getTitle();
        Assert.assertEquals("Stack Overflow - Where Developers Learn, Share, & Build Careers",title);
    }


    @io.cucumber.java.en.When("User clicks on Questions")
    public void userClicksOnQuestions() throws Exception{
        try {
            driver.findElement(By.xpath("//a[@aria-controls='left-sidebar']")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Questions']")));
            driver.findElement(By.xpath("//span[text()='Questions']")).click();
        }
        catch (NoSuchElementException e){
            System.out.println(e.getStackTrace());
        }
    }


    @io.cucumber.java.en.And("User clicks on Users")
    public void userClicksOnUsers() {
        driver.findElement(By.xpath("//span[text()='Users']")).click();
    }


    @io.cucumber.java.en.And("User clicks on Editors")
    public void userClicksOnEditors() throws Exception{
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Users who edited at least 5 posts']")));
            driver.findElement(By.xpath("//a[@title='Users who edited at least 5 posts']")).click();
        }
        catch (NoSuchElementException e){
            System.out.println(e.getStackTrace());
        }
    }


    @io.cucumber.java.en.Then("User click on page {int}")
    public void userClickOnPage(int pageNumber) throws NotFoundException,NoSuchElementException{
        int defaultPage = 1;
       int defaultPageNumber=1;
        List<WebElement> isPageNumberPresnt = null;
        if (pageNumber==defaultPage){
            String defaultPageText=driver.findElement(By.xpath("//div[@class='s-pagination--item is-selected']")).getText();
            defaultPageNumber = Integer.parseInt(defaultPageText);
            isPageNumberPresnt=driver.findElements(By.xpath("//div[@class='s-pagination--item is-selected']"));
        }
        else {
            isPageNumberPresnt = driver.findElements(By.xpath("//a[text()='"+pageNumber+"']"));

        }
        try {
            if(!isPageNumberPresnt.isEmpty())
                {
                    if (pageNumber==defaultPageNumber){
                        System.out.println("You are navigated to page "+pageNumber);
                        driver.findElement(By.xpath("//div[@class='s-pagination--item is-selected']")).click();
                    }
                    else {
                        System.out.println("You are navigated to page "+pageNumber);
                        driver.findElement(By.xpath("//a[text()='" + pageNumber + "']")).click();
                    }
            }
            else
            {
                System.out.println("Page not found");
                driver.quit();
            }
        }
        catch (NoSuchElementException e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }


    @io.cucumber.java.en.And("User should get maximum number of edits per user")
    public void userShouldGetMaximumNumberOfEditsPerUser() throws Exception {
        try {
            List<WebElement> numberOfEdits = driver.findElements(By.xpath("//div[@class='user-tags']"));
            List<WebElement> names, locations;
            int[] allEdits = new int[numberOfEdits.size()];
            for (int i = 0; i < numberOfEdits.size() - 1; i++) {
                String onlyDigit = numberOfEdits.get(i).getText().replaceAll("[^\\d.]", "");
                int dig = Integer.parseInt(onlyDigit);
                allEdits[i] = dig;
            }
            int maxEdits = Arrays.stream(allEdits).max().getAsInt();
            System.out.println("Max edits per user are " + maxEdits);

            names = driver.findElements(By.xpath("//div[contains(@class,'user-tags') and normalize-space(text())='" + maxEdits + " " + "edits']//..//div[@class='user-details']//a"));
            locations = driver.findElements(By.xpath("//div[contains(@class,'user-tags') and normalize-space(text())='" + maxEdits + " " + "edits']//..//div[@class='user-details']//span[@class='user-location']"));

            for (int i = 0; i < names.size(); i++) {
                System.out.println( "UserName is " + names.get(i).getText() + " max edits are " + maxEdits + " and Location is " + locations.get(i).getText());
            }
        }
        catch (Exception e){
            System.out.println(e.getStackTrace());
        }
    }

    @io.cucumber.java.en.And("User close the browser")
    public void userCloseTheBrowser() {
        driver.quit();
    }

}
