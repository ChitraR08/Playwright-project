package test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class Parallel_Execution {

	@Test
	public void testgoogle() {
		try(Playwright play = Playwright.create()){
			Browser b = play.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			Page pg = b.newPage();
			pg.navigate("https://www.google.com/");
			System.out.println("Google Thread : "+ Thread.currentThread().getId());
			b.close();
		}
	}
	
	@Test
	public void testamazon() {
		try(Playwright p = Playwright.create()){
			Browser brws = p.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
			Page page = brws.newPage();
			page.navigate("https://www.amazon.com/");
			System.out.println("Amazon Thread : "+ Thread.currentThread().getId());
			brws.close();
		}
	}
	
		
}
