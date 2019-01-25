package MobileProgrammingLLC.PageLibraries;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class APIDemos_PreferenceDependencies {
	public APIDemos_PreferenceDependencies(AndroidDriver<AndroidElement> driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(className="android.widget.CheckBox") AndroidElement wifiChkBox;
	public AndroidElement getWifiChkBox() {
		return wifiChkBox;
	}
	
	@AndroidFindBy(xpath="//android.widget.TextView[@text='WiFi settings']") AndroidElement wifiSettingsLnk;
	public AndroidElement getwifiSettingsLnk() {
		return wifiSettingsLnk;
	}
	
	@AndroidFindBy(className="android.widget.EditText") AndroidElement wifiPasswordTF;
	public AndroidElement getwifiPasswordTF() {
		return wifiPasswordTF;
	}
	
	@AndroidFindBy(id="android:id/button1") AndroidElement wifiPopUpOKBtn;
	public AndroidElement getWifiPopUpOKBtn() {
		return wifiPopUpOKBtn;
	}
}
