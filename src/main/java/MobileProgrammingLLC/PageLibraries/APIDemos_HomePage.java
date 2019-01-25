package MobileProgrammingLLC.PageLibraries;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class APIDemos_HomePage {
	public APIDemos_HomePage(AndroidDriver<AndroidElement> driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(xpath="//android.widget.TextView[@text='Preference']") AndroidElement PreferenceLink;
	public AndroidElement getPreferenceLink() {
		return PreferenceLink;
	}
	
	@AndroidFindBy(uiAutomator="text(\"Views\")") AndroidElement ViewsOption;
	public AndroidElement getViewsOption() {
		return ViewsOption;
	}
}
