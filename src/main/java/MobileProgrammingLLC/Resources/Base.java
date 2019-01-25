package MobileProgrammingLLC.Resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class Base {
	AppiumServiceBuilder builder;
	AppiumDriverLocalService service;
	DesiredCapabilities dc;
	AndroidDriver<AndroidElement> driver;
	String homeDir = System.getProperty("user.home");
	
	Properties configProp = new Properties();
	FileInputStream configFis = null;
	File configFile = new File("src/main/java/MobileProgrammingLLC/Resources");
	File configSrc = new File(configFile, "config.properties");
	Properties locProp = new Properties();
	FileInputStream locFis = null;
	File locFile = new File("src/main/java/MobileProgrammingLLC/Resources"); 
	File locSrc = new File(locFile, "locators.properties");
	Properties dataProp = new Properties();
	FileInputStream dataFis = null;
	File dataFile = new File("src/main/java/MobileProgrammingLLC/Resources");
	File dataSrc = new File(dataFile, "data.properties");
	
	Logger log = LogManager.getLogger(Base.class.getName());
	
	AndroidTouchAction t;
	TapOptions tele;
	LongPressOptions lpele;
	PointOption<?> ptOp;

	public void startAppiumServer(String ip, int port) {
		log.debug("Starting Appium Server...");
		try {
			builder = new AppiumServiceBuilder();
			builder.withIPAddress(ip);
			builder.usingPort(port);
			service = AppiumDriverLocalService.buildService(builder);
			service.start();
			Thread.sleep(7000L);
			log.info("Server successfully started.");
		}catch(InterruptedException e) {
			log.error("Sleep Interrupted.");
		}
	}
	
	public void startEmulator() {
		log.debug("Starting Emulator...");
		String[] command = new String[] {homeDir + "/Library/Android/sdk/emulator/emulator","-avd",configProp.getProperty("DeviceName")};
		try {
			Process process = new ProcessBuilder(command).start();
			process.waitFor(15000,TimeUnit.MILLISECONDS);
			log.info("Emulator successfully started.");
		}catch(Exception e) {
			log.error("Emulator Starting failed.");
		}
	}
	
	public void setCapabilities(String deviceType) {
		log.debug("Setting Capabilities...");
		try {
			dc = new DesiredCapabilities();
			if(deviceType.equalsIgnoreCase("emulator")) {
				startEmulator();
				dc.setCapability(MobileCapabilityType.DEVICE_NAME, configProp.getProperty("DeviceName"));
			}else if(deviceType.equalsIgnoreCase("real")) {
				dc.setCapability(MobileCapabilityType.DEVICE_NAME, configProp.getProperty("DeviceName"));
			}
			//dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
			log.info("Capabilities Successfully set.");
		}catch(Exception e) {
			log.error("Setting capabilities failed.");
		}
	}
	
	public void invokingNativeApp() {
		log.debug("Invoking the Native Application...");
		try {
			File src = new File("ApiDemos-debug.apk");
			dc.setCapability(MobileCapabilityType.APP, src.getAbsolutePath());
			log.info("Application successfully invoked.");
		}catch(Exception e) {
			log.error("Application invoking failed.");
		}
	}
	
	public AndroidDriver<AndroidElement> createAppiumServerConnection(String ip, String port, String deviceType) {
		int p = Integer.parseInt(port);
		killAppiumServer();
		startAppiumServer(ip, p);
		setCapabilities(deviceType);
		invokingNativeApp();
		log.debug("Creating connection with Appium Server...");
		try {
			driver = new AndroidDriver<AndroidElement>(new URL("http://"+ip+":"+port+"/wd/hub"), dc);
			log.info("Connection successfully created.");
		} catch (MalformedURLException e) {
			log.error("Connection with server failed.");
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return driver;
	}
	
	public  void captureScreen(String testCase, AndroidDriver<AndroidElement> driver) {
		File s = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			Date d = new Date();
			File f = new File("screens");
			File src = new File(f,testCase+"_"+d.toString()+"_"+"ss.png");
			FileUtils.copyFile(s, src);
		} catch (IOException e) {
			log.error("Capturing Screenshot failed.");
		}
	}
	
	public void stopAppiumServer() {
		log.debug("Stopping Appium Server...");
		try {
			service.stop();
			log.info("Server successfully stopped.");
		}catch(Exception e) {
			log.error("Stopping Appium Server failed.");
		}
	}
	
	public void killAppiumServer() {
		try {
			String[] command = new String[] {homeDir + "/Library/Android/sdk/platform-tools/adb","kill-server"};
			Process process = new ProcessBuilder(command).start();
			process.waitFor(2000,TimeUnit.MILLISECONDS);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public  Properties loadData() {
		log.debug("Loading data...");
		try {
			dataFis = new FileInputStream(dataSrc.getAbsolutePath());
			dataProp.load(dataFis);
		} catch (FileNotFoundException e) {
			log.error("Data file not found");
		}catch (IOException e) {
			log.error("Reading Data file failed.");
		}
		log.info("Data successfully loaded.");
		return dataProp;
	}
	
	public  Properties loadLocators() {
		log.debug("Loading locators...");
		try {
			locFis = new FileInputStream(locSrc.getAbsolutePath());
			locProp.load(locFis);
		} catch (FileNotFoundException  e) {
			log.error("Locator file not found");
		} catch (IOException e1) {
			log.error("Reading Locator file failed.");
		}
		log.info("Locators successfully loaded.");
		return locProp;
	}
	
	public  Properties loadConfig() {
		log.debug("Loading config file...");
		try {
			configFis = new FileInputStream(configSrc.getAbsolutePath());
			configProp.load(configFis);
		} catch (FileNotFoundException e) {
			log.error("ERROR: Config file not found");
		} catch (IOException e) {
			log.error("ERROR: Reading Config file failed.");
		}
		log.info("Config file successfully loaded.");
		return configProp;
	}
	
	public  void waitFor(By ele) {
		try {
			WebDriverWait w = new WebDriverWait(driver, 20);
			w.until(ExpectedConditions.presenceOfElementLocated(ele));
		}catch(Exception e) {
			log.error("Element having locator '"+ele+"'"+" could not be located.");
		}
	}
	
	public  void waitForSometime() {
		try {
			log.warn("Sleep feature used. Liberal usage not advised.");
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			log.error("Waiting Interrupted.");
		}
	}
	
	public  void waitForLongtime() {
		try {
			log.warn("Long Sleep feature used (only for exceptional scenarios). Liberal usage strictly discouraged.");
			Thread.sleep(30000L);
		} catch (InterruptedException e) {
			log.error("Waiting Interrupted.");
		}
	}
	
	public void tapOn(AndroidElement element, AndroidDriver<AndroidElement> driver) {
		log.debug("Attempting to tap on element '"+element + "'...");
		
		try {
			t = new AndroidTouchAction(driver);
			tele = TapOptions.tapOptions().withElement(ElementOption.element(element));
			t.tap(tele).perform();
		}catch(Exception e) {
			log.error("Tapping on the element failed.");
		}
		log.info("Successfully tapped on '" + element + "'");
	}
	
	public void longPressOn(AndroidElement element, AndroidDriver<AndroidElement> driver) {
		log.debug("Attempting to long press on element '"+element + "'...");
		try {
			t = new AndroidTouchAction(driver);
			lpele = LongPressOptions.longPressOptions().withElement(ElementOption.element(element));
			t.longPress(lpele).perform();
		}catch(Exception e) {
			log.error("Long Pressing the element failed.");
		}
		log.info("Successfully long pressed on '"+ element + "'.");
	}
	
	public void moveTo(AndroidElement element, AndroidDriver<AndroidElement> driver, int xOffset, int yOffset) {
		log.debug("Attempting to move the element to '"+element + "'...");
		try {
			int x = element.getLocation().getX() + xOffset;
			int y = element.getLocation().getY() + yOffset;
			ptOp = PointOption.point(x, y);
			t.moveTo(ptOp).release().perform();
		}catch(Exception e) {
			log.error("Moving to the element failed.");
		}
		log.info("Successfully moved the element to '"+ element + "'.");
	}
	
	public void scrollTo(String uiAutomator, AndroidDriver<AndroidElement> driver) {
		log.debug("Attempting to perform scroll on the page...");
		try {
			driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("+uiAutomator+")"));
		}catch(Exception e) {
			log.error("Page scrolling failed.");
		}
		log.info("Scrolling successful.");
	}
	
	public void getCurrentAppActivity(AndroidDriver<AndroidElement> driver) {
		log.debug("Fetching app current activity...");
		try {
			log.info("CURRENT APP ACTIVITY: " + driver.currentActivity());
		}catch(Exception e) {
			log.error("Fetching current activity failed!");
		}
	}
	
	public void getAppContext(AndroidDriver<AndroidElement> driver) {
		log.debug("Fetching app context...");
		try {
			log.info("APP CONTEXT: " + driver.getContext());
		}catch(Exception e) {
			log.error("Fetching app context failed!");
		}
	}
	
	public void getDeviceOrientation(AndroidDriver<AndroidElement> driver) {
		log.debug("Getting Device Orientation...");
		try {
			log.info("DEVICE ORIENTATION: " + driver.getOrientation());
		}catch(Exception e) {
			log.error("Getting device orientation failed!");
		}
	}
	
	public void hideKeypad(AndroidDriver<AndroidElement> driver) {
		log.debug("Hiding Device Keypad...");
		try {
			driver.hideKeyboard();
		}catch(Exception e) {
			log.error("Hiding Keyboard unsuccessful.");
		}
		log.info("Keypad hidden successfully.");
	}
	
	public void navigateBackOnDevice(AndroidDriver<AndroidElement> driver) {
		log.debug("Navigating on the previous activity...");
		try {
			driver.pressKey(new KeyEvent(AndroidKey.BACK));
		}catch(Exception e) {
			log.error("Navigating back unsuccessful!");
		}
		log.info("Back Key pressed successfully.");
	}
	
	public void navigateOnHomeScreen(AndroidDriver<AndroidElement> driver) {
		log.debug("Navigating on the home screen...");
		try {
			driver.pressKey(new KeyEvent(AndroidKey.HOME));
		}catch(Exception e) {
			log.error("Navigation on home screen unsuccessful!");
		}
		log.info("Home Key pressed successfully.");
	}
	
	public void compareContent(AndroidElement ele, String expected) {
		try {
			log.debug("Attempting to compare content returned by \"" + ele + "\" from \"" + expected + "\" ...");
			Assert.assertEquals(ele.getText(), expected);
		}catch(Exception e) {
			log.error("Comparison Failed! Expected was '"+expected+"' but actual is '"+ele.getText()+"'");
		}
		log.info("Comparison Successful!");
	}
	
	public void enterContentInto(AndroidElement ele, String content) {
		try {
			log.debug("Attempting to send \""+ content + "\" to element \"" + ele + "\"...");
			ele.sendKeys(content);
		}catch(Exception e) {
			log.error("Entering content into element '"+ele+"' failed.");
		}
		log.info("Content \"" + content + "\" entered successfully into element \"" + ele + "\"");
	}
	
	public void isDisplayedOnPage(AndroidElement ele) {
		log.debug("Attempting to locate " + ele + " on the page...");
		try {
			if(ele.isDisplayed()) {
				log.info(ele + " is displayed on the page successfully.");
			}
		}catch(Exception e){
			log.error("Presence of " + ele + " on the page failed.");
		}
	}
}
