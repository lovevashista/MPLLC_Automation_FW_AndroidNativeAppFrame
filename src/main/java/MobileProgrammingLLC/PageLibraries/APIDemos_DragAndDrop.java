package MobileProgrammingLLC.PageLibraries;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class APIDemos_DragAndDrop {
	public APIDemos_DragAndDrop(AndroidDriver<AndroidElement> driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(xpath="//android.view.View[@resource-id='io.appium.android.apis:id/drag_dot_1']") AndroidElement Dot1;
	public AndroidElement getDot1() {
		return Dot1;
	}
	
	@AndroidFindBy(xpath="//android.view.View[@resource-id='io.appium.android.apis:id/drag_dot_3']") AndroidElement Dot3;
	public AndroidElement getDot3() {
		return Dot3;
	}
}
