package MobileProgrammingLLC.PageLibraries;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class APIDemos_ELCustomAdapter {
	public APIDemos_ELCustomAdapter(AndroidDriver<AndroidElement> driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(xpath="//*[@text='Dog Names']") AndroidElement DogNamesOption;
	public AndroidElement getDogNamesOption() {
		return DogNamesOption;
	}
}
