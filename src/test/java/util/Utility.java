package util;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.ScreenshotOptions;

public class Utility {

	public static byte[] CaptureScreenshot(Page pg) {
		
		SimpleDateFormat custom = new SimpleDateFormat("dd_MM_yy_HH_mm_ss");
		
		Date d = new Date();
		String newdate = custom.format(d);
		
		byte[] arr = pg.screenshot(new ScreenshotOptions().setPath(Paths.get("C:\\Users\\Lenovo\\git\\Playwright_Automation1\\playwright\\Screenshots\\"+newdate+".png")));
		return arr;
	}
}
