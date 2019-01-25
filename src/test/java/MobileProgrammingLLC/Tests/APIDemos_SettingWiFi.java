package MobileProgrammingLLC.Tests;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import MobileProgrammingLLC.PageLibraries.APIDemos_HomePage;
import MobileProgrammingLLC.PageLibraries.APIDemos_Preference;
import MobileProgrammingLLC.PageLibraries.APIDemos_PreferenceDependencies;
import MobileProgrammingLLC.Resources.Base;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


public class APIDemos_SettingWiFi {
	AndroidDriver<AndroidElement> driver;
	Logger log = LogManager.getLogger(APIDemos_SettingWiFi.class.getName());
	Properties locators = new Properties();
	Properties config = new Properties();
	Properties data = new Properties();
	APIDemos_HomePage hp;
	APIDemos_Preference p;
	APIDemos_PreferenceDependencies pd;
	Base b = new Base();
	
	@BeforeClass
	public  void initConfigs() {
		log.info("Initializing Configurations...");
		locators = b.loadLocators();
		config = b.loadConfig();
		data = b.loadData();
		driver = b.createAppiumServerConnection(config.getProperty("ip"),config.getProperty("port"), config.getProperty("DeviceType"));
		hp = new APIDemos_HomePage(driver);
		p = new APIDemos_Preference(driver);
		pd = new APIDemos_PreferenceDependencies(driver);
		log.info("Configurations Successfully Initialized.");
	}
	
	@Test
	public void clickOnPreferenceOption() {
		b.tapOn(hp.getPreferenceLink(), driver);
	}
	
	@Test(dependsOnMethods= {"clickOnPreferenceOption"})
	public void clickOnPreferenceDependenciesOption() {
		b.tapOn(p.getPrefDependenciesLnk(), driver);
	}
	
	@Test(dependsOnMethods= {"clickOnPreferenceDependenciesOption"})
	public void settingWifiFunctionality() {
		b.tapOn(pd.getWifiChkBox(), driver);
		b.tapOn(pd.getwifiSettingsLnk(), driver);
		pd.getwifiPasswordTF().sendKeys(data.getProperty("Password"));
		b.tapOn(pd.getWifiPopUpOKBtn(), driver);
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
		b.stopAppiumServer();
	}
}
