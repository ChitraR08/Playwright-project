package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BrokenBarrierException;

import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.WaitForSelectorState;

public class Upload_Download_Test {

	@Test
	public void FileDownload() throws Exception {
		try(Playwright play = Playwright.create()){
			Browser brow = play.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			BrowserContext bc = brow.newContext(new Browser.NewContextOptions().setAcceptDownloads(true));
			Page pg = bc.newPage();
			pg.navigate("https://rahulshettyacademy.com/upload-download-test/");
			System.out.println("Page Title : "+ pg.title());
			Download down = pg.waitForDownload(() -> {
				pg.click("#downloadButton");
			});
			String fname = down.suggestedFilename();
			System.out.println("Downloading : " +fname);
			
			Path downloadpath = Paths.get("C:\\Users\\Lenovo\\git\\Playwright_Automation1\\playwright\\Download\\download.xlsx");
			down.saveAs(downloadpath);
			
			//Validation
			if(Files.exists(downloadpath) && Files.size(downloadpath) > 0) {
				System.out.println("Download Successfully....!");
			} else {
				System.out.println("Download Failed!!");
				Thread.sleep(1000);
			}
			
			
			//File Upload
			
			pg.setInputFiles("input[type='file']", Paths.get("C:\\Users\\Lenovo\\Downloads\\dwnld.xlsx"));
			pg.waitForTimeout(2000);
			Locator msg =  pg.locator(":text-is('Updated Excel Data Successfully.')");
			msg.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000));
			
			String str = msg.textContent();
			assert str.contains("Updated Excel Data Successfully."): "Message is mismatch";
			System.out.println(str);
			pg.waitForTimeout(1000);
		
			pg.click("//input[@id='fileinput']");
			pg.waitForTimeout(5000);
			
			//Validation
			brow.close();
		}
	}
}
