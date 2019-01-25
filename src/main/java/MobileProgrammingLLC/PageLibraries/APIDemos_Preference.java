package MobileProgrammingLLC.PageLibraries;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class APIDemos_Preference {
	public APIDemos_Preference(AndroidDriver<AndroidElement> driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(uiAutomator="text(\"3. Preference dependencies\")") AndroidElement PrefDependenciesLnk;
	public AndroidElement getPrefDependenciesLnk() {
		return PrefDependenciesLnk;
	}
}
