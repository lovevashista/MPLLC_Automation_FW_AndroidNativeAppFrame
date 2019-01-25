package MobileProgrammingLLC.Tests;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import MobileProgrammingLLC.PageLibraries.APIDemos_ELCustomAdapter;
import MobileProgrammingLLC.PageLibraries.APIDemos_ExpandableLists;
import MobileProgrammingLLC.PageLibraries.APIDemos_HomePage;
import MobileProgrammingLLC.PageLibraries.APIDemos_Views;
import MobileProgrammingLLC.Resources.Base;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


public class APIDemos_RetrivingDogsInfo {
	AndroidDriver<AndroidElement> driver;
	Logger log = LogManager.getLogger(APIDemos_RetrivingDogsInfo.class.getName());
	Properties locators = new Properties();
	Properties config = new Properties();
	Properties data = new Properties();
	APIDemos_HomePage hp;
	APIDemos_Views v;
	APIDemos_ExpandableLists el;
	APIDemos_ELCustomAdapter ca;
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
		el = new APIDemos_ExpandableLists(driver);
		ca = new APIDemos_ELCustomAdapter(driver);
		log.info("Configurations Successfully Initialized.");
	}
	
	@Test
	public void navigateToCustomAdapterPage() {
		b.tapOn(hp.getViewsOption(), driver);
		b.tapOn(v.getExpandableListsOption(), driver);
	}
	
	@Test(dependsOnMethods= {"navigateToCustomAdapterPage"})
	public void retrieveDogNamesInformation() {
		b.tapOn(el.getCustomAdapterOption(), driver);
		b.longPressOn(ca.getDogNamesOption(), driver);
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
		b.stopAppiumServer();
	}
}
