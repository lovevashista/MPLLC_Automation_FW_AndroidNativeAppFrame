package MobileProgrammingLLC.Tests;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import MobileProgrammingLLC.PageLibraries.APIDemos_HomePage;
import MobileProgrammingLLC.PageLibraries.APIDemos_PopUpMenu;
import MobileProgrammingLLC.PageLibraries.APIDemos_Views;
import MobileProgrammingLLC.Resources.Base;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


public class APIDemos_MakingPopUps {
	AndroidDriver<AndroidElement> driver;
	Logger log = LogManager.getLogger(APIDemos_MakingPopUps.class.getName());
	Properties locators = new Properties();
	Properties config = new Properties();
	Properties data = new Properties();
	APIDemos_HomePage hp;
	APIDemos_Views v;
	APIDemos_PopUpMenu pm;
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
		pm = new APIDemos_PopUpMenu(driver);
		log.info("Configurations Successfully Initialized.");
	}
	
	@Test
	public void tapOnViewsOption() {
		b.tapOn(hp.getViewsOption(), driver);
	}
	
	@Test(dependsOnMethods= {"tapOnViewsOption"})
	public void tapOnPopUpMenu() {
		b.scrollTo(locators.getProperty("PopUpMenu"), driver);
		b.tapOn(v.getPopUpMenuOption(), driver);
	}
	
	@Test(dependsOnMethods= {"tapOnPopUpMenu"})
	public void makeAPopUp() {
		b.tapOn(pm.getPopUpMenuBtn(), driver);
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
		b.stopAppiumServer();
	}
}
