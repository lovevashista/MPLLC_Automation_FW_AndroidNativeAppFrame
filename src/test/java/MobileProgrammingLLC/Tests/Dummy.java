package MobileProgrammingLLC.Tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class Dummy {
	public static void main(String[] args) throws MalformedURLException {
		DesiredCapabilities dc = new DesiredCapabilities();
		
		dc.setCapability(MobileCapabilityType.DEVICE_NAME, "LoveEmulator");
		dc.setCapability(MobileCapabilityType.APP, "/Users/love.vashista/Documents/Eclipse_Workspace/FrameWorks/MPLLC_AndroidNativeAppFrame/ApiDemos-debug.apk");
		
		AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), dc);
	}
}
