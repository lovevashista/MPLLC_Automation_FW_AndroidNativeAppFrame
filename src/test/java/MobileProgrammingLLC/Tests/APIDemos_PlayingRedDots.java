package MobileProgrammingLLC.Tests;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import MobileProgrammingLLC.PageLibraries.APIDemos_DragAndDrop;
import MobileProgrammingLLC.PageLibraries.APIDemos_HomePage;
import MobileProgrammingLLC.PageLibraries.APIDemos_Views;
import MobileProgrammingLLC.Resources.Base;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


public class APIDemos_PlayingRedDots {
	AndroidDriver<AndroidElement> driver;
	Logger log = LogManager.getLogger(APIDemos_PlayingRedDots.class.getName());
	Properties locators = new Properties();
	Properties config = new Properties();
	Properties data = new Properties();
	APIDemos_HomePage hp;
	APIDemos_Views v;
	APIDemos_DragAndDrop dd;
	Base b = new Base();
	
	@BeforeClass
	public  void initConfigs() {
		log.info("Initializing Configurations...");
		locators = b.loadLocators();
		config = b.loadConfig();
		data = b.loadData();
		driver = b.createAppiumServerConnection(config.getProperty("ip"),config.getProperty("port"), config.getProperty("DeviceType"));
		hp = new APIDemos_HomePage(driver);
		v = new APIDemos_Views(driver);
		dd = new APIDemos_DragAndDrop(driver);
		log.info("Configurations Successfully Initialized.");
	}
	
	@Test
	public void tapOnViewsOption() {
		b.tapOn(hp.getViewsOption(), driver);
	}
	
	@Test(dependsOnMethods= {"tapOnViewsOption"})
	public void tapOnDragAndDropOption() {
		b.tapOn(v.getDragAndDropOption(), driver);
	}
	
	@Test(dependsOnMethods= {"tapOnDragAndDropOption"})
	public void validatingRedDotsMovement() {
		b.longPressOn(dd.getDot1(), driver);
		b.moveTo(dd.getDot3(), driver, 200, 200);
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
		b.stopAppiumServer();
	}
}
