package MobileProgrammingLLC.PageLibraries;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class APIDemos_Views {
	public APIDemos_Views(AndroidDriver<AndroidElement> driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(uiAutomator="text(\"Expandable Lists\")") AndroidElement ExpandableListsOption;
	public AndroidElement getExpandableListsOption() {
		return ExpandableListsOption;
	}
	
	@AndroidFindBy(xpath="//android.widget.TextView[@text='Drag and Drop']") AndroidElement DragAndDropOption;
	public AndroidElement getDragAndDropOption() {
		return DragAndDropOption;
	}
	
	@AndroidFindBy(uiAutomator="text(\"Popup Menu\")") AndroidElement PopUpMenuOption;
	public AndroidElement getPopUpMenuOption() {
		return PopUpMenuOption;
	}
}
