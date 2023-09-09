package com.AutoProject.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Scanner;
import java.time.Duration;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import java.util.NoSuchElementException;
import org.monte.media.VideoFormatKeys;
import java.awt.Rectangle;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Keys;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Date;

public class binance1 {
	

    private static WebDriver driver;
    private WebDriverWait wait;
    private String baseURL = "https://www.binance.com/en"; // website URL
    private ScreenRecorder screenRecorder;

    @BeforeClass
    public void setUp() throws Exception {
    	
    	clearConsole();
    	
    	System.setProperty("webdriver.chrome.driver", "My_Path\\chromedriver.exe");
        
        driver = new ChromeDriver();  
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));     
        

     // Configure the video recording using MonteMedia
        File videoFile = new File("C:\\Users\\sneha\\Documents\\Binance1-video\\recorded_video.mp4");
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        screenRecorder = new ScreenRecorder(gc, captureSize,
                new Format(VideoFormatKeys.MediaTypeKey, VideoFormatKeys.MediaType.FILE, VideoFormatKeys.MimeTypeKey, VideoFormatKeys.MIME_AVI),
                new Format(VideoFormatKeys.MediaTypeKey, VideoFormatKeys.MediaType.VIDEO, VideoFormatKeys.EncodingKey, VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        VideoFormatKeys.CompressorNameKey, VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        VideoFormatKeys.DepthKey, 24, VideoFormatKeys.FrameRateKey, Rational.valueOf(15),
                        VideoFormatKeys.QualityKey, 1.0f, VideoFormatKeys.KeyFrameIntervalKey, 15 * 60),
                new Format(VideoFormatKeys.MediaTypeKey, VideoFormatKeys.MediaType.VIDEO, VideoFormatKeys.EncodingKey, "black",
                        VideoFormatKeys.FrameRateKey, Rational.valueOf(30)),
                null, videoFile);
    } 
    
    @Test(priority = 1)
    public void openWebsite() {
        driver.get(baseURL);
        
        handleRewardPointPopUp();
        
        // Accept cookies if the pop-up is present
        handleCookieConsent();
        
        handleRewardPointPopUp();
        
     // Perform the login immediately
        login();
        
        // Start the scheduled tasks after login
        startScheduledTasks();
        
        // Add a wait to ensure that tasks have time to execute
        try {
            Thread.sleep(8 * 60 * 60 * 1000); // Sleep for 8 hours
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    
    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
        
    
    private void handleCookieConsent() {
        // Check if the cookie consent pop-up is present
        WebElement cookiePopup = null;
        try {
            cookiePopup = driver.findElement(By.id("onetrust-policy")); //cookie pop-up element
        } catch (NoSuchElementException e) {
            // If the element is not found, no cookie pop-up is present
        }

        // Handle the cookie consent pop-up if present
        if (cookiePopup != null) {
            // Click on the "Accept" button
            WebElement acceptButton = cookiePopup.findElement(By.xpath("//*[@id=\"onetrust-accept-btn-handler\"]")); //"Accept" button
            acceptButton.click();
        }
    }	
    
    private static void handleRewardPointPopUp() {
		// Find all elements that match the reward point pop-up selector
		  java.util.List<WebElement> rewardPointPopups = driver.findElements(By.cssSelector(".css-y12ctw > div"));

		    // If there are any reward point pop-ups present, handle them
		    if (!rewardPointPopups.isEmpty()) {
		        // Handle each pop-up individually if needed
		        for (WebElement rewardPointPopup : rewardPointPopups) {
		            WebElement closeButton = rewardPointPopup.findElement(By.cssSelector(".css-zdq66x > svg > path"));
		            closeButton.click();
		            try {
		        		Thread.sleep(2000); // wait for 5 seconds
		        	} catch (InterruptedException e) {
		        		e.printStackTrace();
		        	}
		        }
		    }
  } 
    
    public void login() {
    	
    	// Create a scanner to read user input
        Scanner scanner = new Scanner(System.in);
        
        // Click the login button
        WebElement login = driver.findElement(By.xpath("//*[@id=\"header_login\"]"));
    	login.click();
    	try {
    		Thread.sleep(2000); // wait for 2 seconds
    	} catch (InterruptedException e) {
    		e.printStackTrace();
    	}
   	   
   /* 	// Enter username/email
        WebElement username = wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));	 
    	username.click();
    	try {
    		Thread.sleep(2000); // wait for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	
    	System.out.print("Enter User name as Email (or 'exit' to quit): ");
    	String uName = scanner.nextLine();
    	try {
    		Thread.sleep(2000); // wait for 2 seconds
    	} catch (InterruptedException e) {
    		e.printStackTrace();
    	}
    	
    	// Check if the input is "exit" to quit
        if (uName.equalsIgnoreCase("exit")) {
            scanner.close(); // Close the scanner if user wants to exit
            return; // Exit the method
        }

    	username.clear(); // Clear any existing text in the search box
    	username.sendKeys(uName);      
    	username.sendKeys(Keys.ENTER);

        // Submit the user name
    	username.submit();
    	try {
    		Thread.sleep(2000); // wait for 2 seconds
    	} catch (InterruptedException e) {
    		e.printStackTrace();
    	}
    	
    	WebElement NextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"click_login_submit\"]")));
    	JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", NextButton);		
    	try {
    		Thread.sleep(10000); // wait for 10 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    	
    	scanner.nextLine();
    	
    	// If the login is successful, release the latch to allow the execution of executeAfterLogin() method
        if (loginSuccessful()) {

            System.out.println("Login successful");
           
            
        } else {
            System.out.println("Login failed");
        }	 
        
        scanner.close();
  
    }   
    
    public boolean loginSuccessful() {
    	
        System.out.println("good work");

        try {
            WebElement loggedInElement = driver.findElement(By.linkText("Dashboard"));
            return loggedInElement.isDisplayed();
        } catch (NoSuchElementException e) {
            // If the element is not found, the login is not successful
            return false;
        }
    }   
    
    public void startScheduledTasks() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Calendar targetTime = getTargetTime(12,28);
        long initialDelay = targetTime.getTimeInMillis() - System.currentTimeMillis();

        scheduler.schedule(this::executeAfterLogin, initialDelay, TimeUnit.MILLISECONDS);
    }
  
   
 // Helper method to calculate the initial delay until the target time
    public Calendar getTargetTime(int hourOfDay, int minute) {
        Calendar targetTime = Calendar.getInstance();
        targetTime.set(Calendar.HOUR_OF_DAY, 12);
        
        targetTime.set(Calendar.MINUTE, 28);
        targetTime.set(Calendar.SECOND, 0);
        targetTime.set(Calendar.MILLISECOND, 0);

        // If the current time is already past the target time, schedule for the next day
        if (System.currentTimeMillis() > targetTime.getTimeInMillis()) {
            targetTime.add(Calendar.DAY_OF_MONTH, 1);
        }

        return targetTime;
    }	    	  
    
    private void executeAfterLogin() {
        System.out.println("Scheduled task executing at: " + new Date());
        int retryIntervalMinutes = 5; // Interval between retries in minutes

        while (true) {
            try {
                // Perform login and tasks
                searchAndClickElement();               
                startVideoRecording();
                takeScreenshotsAndScroll();
                stopMonteScreenRecorder();
                logout();
                tearDown();
                
                // Exit the loop if tasks are successful
                break;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Task execution failed. Retrying in " + retryIntervalMinutes + " minutes...");

                // Sleep for the specified retry interval before the next attempt
                try {
                    Thread.sleep(retryIntervalMinutes * 60 * 1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public void searchAndClickElement() {
    	// Locate the element to click
        WebElement wallet = driver.findElement(By.xpath("//*[@id=\"header_menu_ba-wallet\"]/div[1]/div"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", wallet);
        try {
            Thread.sleep(1000); // Wait for 2 seconds to ensure the element is clickable
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Click on the element
        wallet.click();
        try {
    		Thread.sleep(1000); // wait for 10 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        WebElement DepWit = driver.findElement(By.xpath("//*[@id=\"header_menu_item_ba-exchangeWallet\"]/div[2]/div[1]/div"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", DepWit);
        try {
            Thread.sleep(500); // Wait for 2 seconds to ensure the element is clickable
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DepWit.click();
        try {
    		Thread.sleep(500); // wait for 1 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }	 

    public void startVideoRecording() throws IOException {
        // Start the video recording
    	screenRecorder.start();
    }
    

    public void takeScreenshotsAndScroll() throws IOException, InterruptedException {
        int pageNumber = 1;
        Actions actions = new Actions(driver);
        
        while (true) {       	            
            	
        	if (pageNumber == 1 ) {
	        		       
	            // Wait for a moment to load the content
	            Thread.sleep(4000);  
	            
	            // Take a screenshot of the current visible part of the page
	            File screenshotFile = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
	            BufferedImage fullScreen = ImageIO.read(screenshotFile);
	            ImageIO.write(fullScreen, "png", new File("C:\\Users\\sneha\\Documents\\Binace1-ScreenShot\\screenshot_page_" + pageNumber + "_top.png"));
	
	            // Wait for a moment to load the content
	            Thread.sleep(2000);  	            	            
	            

	            WebElement SearchMid = driver.findElement(By.id("next-page"));
	        	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);",  SearchMid);
	            try {
	                Thread.sleep(2000); // Wait for 2 seconds to ensure the element is clickable
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            } 
	            // Scroll down to capture the middle part of the first page
	            actions.sendKeys(Keys.PAGE_DOWN).perform();
	            Thread.sleep(2000);	
	            try {
                    Thread.sleep(2000); // Wait for 2 seconds to ensure the element is clickable
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }  
	            
	            // Take a screenshot of the current visible part of the page (middle part of the first page)
	            File screenshotFileMiddle = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
	            BufferedImage fullScreenMiddle = ImageIO.read(screenshotFileMiddle);
	            ImageIO.write(fullScreenMiddle, "png", new File("C:\\Users\\sneha\\Documents\\Binace1-ScreenShot\\screenshot_page_" + pageNumber + "_middle.png"));
	
	            // Wait for a moment to load the content after scrolling
	            Thread.sleep(2000);
		        
	            WebElement Search = driver.findElement(By.id("next-page"));
	        	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",  Search);
	            try {
	                Thread.sleep(2000); // Wait for 2 seconds to ensure the element is clickable
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            } 
	            
	            // Scroll down to capture the bottom part of the first page
	            actions.sendKeys(Keys.END).perform();
	            Thread.sleep(2000);
	
	            // Take a screenshot of the current visible part of the page (bottom part of the first page)
	            File screenshotFileBottom = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
	            BufferedImage fullScreenBottom = ImageIO.read(screenshotFileBottom);
	            ImageIO.write(fullScreenBottom, "png", new File("C:\\Users\\sneha\\Documents\\Binace1-ScreenShot\\screenshot_page_" + pageNumber + "_bottom.png"));
	            
	            Thread.sleep(2000);
	            		            
        	} else {
        		
	        		// Scroll up to the top of the page for the next iteration
	                while (true) {
	                	
	                	
	                    actions.sendKeys(Keys.PAGE_UP).perform();
	                    Thread.sleep(1000);
	                    // Check if the top of the page is reached
	                    JavascriptExecutor jsExecutors = (JavascriptExecutor) driver;
	                    boolean isAtTop = (boolean) jsExecutors.executeScript(
	                            "return (window.pageYOffset === 0);");
	                    if (isAtTop) {
	                        break;
	                    }
	                }  
	                
	                // Scroll up to the top of the page to ensure it starts from the top
	                actions.sendKeys(Keys.HOME).perform();
	                Thread.sleep(4000);     
	                
	                // Take a screenshot of the current visible part of the page
	                File screenshotFile = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
	                BufferedImage fullScreen = ImageIO.read(screenshotFile);
	                ImageIO.write(fullScreen, "png", new File("C:\\Users\\sneha\\Documents\\Binace1-ScreenShot\\screenshot_page_" + pageNumber + "_top.png"));
	
	                // Wait for a moment to load the content
	                Thread.sleep(2000);  
	                
	                // Scroll down to capture the middle part of the first page
	                actions.sendKeys(Keys.PAGE_DOWN).perform();
	                Thread.sleep(2000);
	
	                // Take a screenshot of the current visible part of the page (middle part of the first page)
	                File screenshotFileMiddle = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
	                BufferedImage fullScreenMiddle = ImageIO.read(screenshotFileMiddle);
	                ImageIO.write(fullScreenMiddle, "png", new File("C:\\Users\\sneha\\Documents\\Binace1-ScreenShot\\screenshot_page_" + pageNumber + "_middle.png"));
	
	                // Wait for a moment to load the content after scrolling
	                Thread.sleep(2000);
	
	                // Scroll down to capture the bottom part of the first page
	                actions.sendKeys(Keys.END).perform();
	                Thread.sleep(2000);
	
	                // Take a screenshot of the current visible part of the page (bottom part of the first page)
	                File screenshotFileBottom = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
	                BufferedImage fullScreenBottom = ImageIO.read(screenshotFileBottom);
	                ImageIO.write(fullScreenBottom, "png", new File("C:\\Users\\sneha\\Documents\\Binace1-ScreenShot\\screenshot_page_" + pageNumber + "_bottom.png"));
	                
	                Thread.sleep(2000);                       

        	}            
            
            // Check if pagination element is present
            WebElement paginationElement = driver.findElement(By.xpath("//*[@class='css-1bgd29k']"));

            if (!paginationElement.isDisplayed()) {
                // If pagination element is not displayed, break the loop (reached last page)
                break;
            }

            // Find the element for the next button or page numbers
            try {
                WebElement nextButton = driver.findElement(By.id("next-page"));

                if (!nextButton.isEnabled()) {
                	
                    // If next button is not enabled, break the loop (reached last page)
                    break;
                    
                } else {
                    nextButton.click();
                    try {
                        Thread.sleep(2000); // Wait for 2 seconds to ensure the element is clickable
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }                                                      
                    
                }
                
            } catch (NoSuchElementException e) {
            	
                // If next button is not found, try clicking on individual page numbers one by one
                WebElement pageNumberElement = driver.findElement(By.xpath("//*[@id='page-" + (pageNumber + 1) + "']"));
                
                if (!pageNumberElement.isDisplayed()) {
                	
                    // If pagination element is not displayed, break the loop (reached last page)
                    break;
                    
                } else {
                    pageNumberElement.click();
                    try {
                        Thread.sleep(4000); // Wait for 2 seconds to ensure the element is clickable
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }                                    
                    
                }
            }

            // Wait for a moment to load the content of the next page
            Thread.sleep(1000);

            // Increment the page number for the next iteration
            pageNumber++;
            }
    }

  
    public void stopMonteScreenRecorder() throws IOException {
        // Stop the video recording
        screenRecorder.stop();
    }


    public void logout() {
    	
    	WebElement logoutSearch = driver.findElement(By.id("header_menu_cabinet"));
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", logoutSearch);
        try {
            Thread.sleep(2000); // Wait for 2 seconds to ensure the element is clickable
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click on the logout element
        logoutSearch.click();

        try {
            Thread.sleep(4000); // Wait for 2 seconds to allow the page to load after clicking logout
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    	
     // Locate the logout button and click on it
        WebElement logoutButton = driver.findElement(By.cssSelector(".menu-item.hidden-in-bnc-app"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", logoutButton);
        try {
            Thread.sleep(2000); // Wait for 2 seconds to ensure the element is clickable
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logoutButton.click();

        try {
            Thread.sleep(2000); // Wait for 2 seconds to allow the logout process to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

   public void tearDown() throws InterruptedException {
	   
	   Thread.sleep(2000);

        driver.quit();
    }
   

}
