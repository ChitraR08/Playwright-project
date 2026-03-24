package test;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

public class Locators_Test{
	
	Playwright playwr;
	Browser brwsr;
	BrowserType bt;
	Page pg;
	final String url = "https://rahulshettyacademy.com/AutomationPractice/";
	
	public static class ElementUtils { 
	public static void highlightElement(Page page, Locator locator) {
	        try {
	            // Wait until the element is visible in DOM
	            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

	            // Scroll element into view
	            locator.scrollIntoViewIfNeeded();

	            // Get the ElementHandle from Locator
	            ElementHandle elementHandle = locator.elementHandle();
	            if (elementHandle != null) {

	                // Apply highlight with blink effect
	                page.evaluate("""
	                    (element) => {
	                        const originalBorder = element.style.border;
	                        const originalBG = element.style.backgroundColor;

	                        // Flash 3 times
	                        for (let i = 0; i < 3; i++) {
	                            setTimeout(() => {
	                                element.style.border = "3px solid red";
	                                element.style.backgroundColor = "yellow";
	                            }, i * 300);

	                            setTimeout(() => {
	                                element.style.border = originalBorder;
	                                element.style.backgroundColor = originalBG;
	                            }, i * 300 + 150);
	                        }
	                    }
	                """, elementHandle);
	            } else {
	                System.out.println("ElementHandle is null. Cannot highlight.");
	            }

	        } catch (Exception e) {
	            System.out.println("Highlight failed safely: " + e.getMessage());
	        }
	}
	    }
		@BeforeMethod
	public void LaunchBrowser() {
		playwr = Playwright.create();
		ArrayList<String> args = new ArrayList<>();
		args.add("--start-maximized");
		
		//bt = playwr.chromium();
		brwsr = playwr.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false).setArgs(args));
		BrowserContext bct = brwsr.newContext(new Browser.NewContextOptions().setViewportSize(null));
		pg = bct.newPage();
		pg.navigate(url);
		System.out.println(pg.title());
	}
	
	@Test
	public void FormData() throws Exception {
		
		//Radio Button
		
		Locator r1 = pg.locator("xpath = //input[@value='radio3']");
		//r1.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
		ElementUtils.highlightElement(pg, r1);
		r1.click();
		PlaywrightAssertions.assertThat(pg.locator("xpath = //input[@value='radio3']")).isChecked();
		
		
		//Checkbox
		Locator chek1 = pg.locator("xpath = //input[@id='checkBoxOption2']");
		ElementUtils.highlightElement(pg, chek1);
		chek1.click();
		 Locator chek2 = pg.locator("xpath = //input[@id='checkBoxOption3']");
		 chek2.click();
		ElementUtils.highlightElement(pg, chek2);
		PlaywrightAssertions.assertThat(pg.locator("xpath = //input[@id='checkBoxOption3']")).isChecked();
	
		//Suggestion Class
		pg.locator("#autocomplete").type("US");
		pg.waitForSelector("(//div[@id='ui-id-9'])");
		pg.keyboard().press("ArrowDown");
		pg.keyboard().press("ArrowDown");
		pg.keyboard().press("ArrowDown");
		pg.keyboard().press("ArrowDown");
		pg.keyboard().press("Enter");
		
		
		//Dropdown selection
		pg.locator("#dropdown-class-example").selectOption("option2");
		//pg.locator("#dropdown-class-example").selectOption(new P.seleoptionOptions().setValue("option2"));
		Thread.sleep(2000);
		pg.selectOption("select", new SelectOption().setIndex(3));
		Thread.sleep(2000);

		//Switch window example
		
		/*
		 * pg.locator("#openwindow").click(); Thread.sleep(2000); BrowserContext bc2 =
		 * brwsr.newContext(); Page pg2 = bc2.newPage(); Thread.sleep(1000);
		 * 
		 * pg2.navigate("https://www.qaclickacademy.com/"); Thread.sleep(1000);
		 * 
		 * System.out.println("Title for new window : "+ pg2.title());
		 * Thread.sleep(1000);
		 */		 		
		//Switch Tab Example
		/*
		 * pg.locator("#opentab").click(); Thread.sleep(2000); BrowserContext bc3 =
		 * brwsr.newContext(); Page pg3 = bc3.newPage();
		 * pg3.navigate("https://www.qaclickacademy.com/");
		 * java.util.List<BrowserContext> contexts = brwsr.contexts();
		 * for(BrowserContext bc4 : contexts) { Page cp = bc4.pages().get(0);
		 * System.out.println("Title of Page :" +cp.title()); } Thread.sleep(2000);
		 */
		
		//Alert Example
		Locator lc = pg.locator("[name='enter-name']");
		lc.fill("Chitra");
		Thread.sleep(2000);
		String str = lc.inputValue();
		Assert.assertFalse(str.isEmpty(), "The Input field should not be empty");
		System.out.println(str);
	//	pg.locator("//input[@id='alertbtn']").click();
		Thread.sleep(1000);
		pg.onDialog(Dialog -> {
			String s = Dialog.message();
			System.out.println("Message : "+Dialog.message());
		//	Assert.assertEquals(s, "Hello Chitra, share this practice page and share your knowledge");
			Dialog.accept();
		});
		
		Locator alert = pg.locator("//input[@id='alertbtn']");
		alert.click();
		Thread.sleep(2000);
		
		//Confirm Alert Example
		
		  Locator lc2 = pg.locator("[name='enter-name']"); lc2.fill("rami");
		  
		  pg.onceDialog(Dialog -> { String s2 = Dialog.message();
		  System.out.println("Message : "+Dialog.message()); //
		 // Dialog.accept();
		  Assert.assertEquals("Hello rami, Are you sure you want to confirm?", Dialog.message());
		  });
			//assertThat(pg.locator("#confirmbtn")).isVisible();
			
		  pg.locator("#confirmbtn").click();
		 
		
		//WebTable
		// --------- Count number of rows ------------------
		Locator table = pg.locator("table.table-display");
		Locator row = table.locator("tr");
		int rowcount = row.count();
		System.out.println(rowcount);
		
		//------------- Extracting TD Data from Table ------
		List<String> allData = row.allInnerTexts();

		// Option B: Iterate for specific cell data
		for (int i = 1; i < row.count(); i++) { // Starting from 1 to skip header
		    String cellValue = row.nth(i).locator("td").nth(1).innerText();
		    System.out.println("Column 2 Value: " + cellValue);
		}
		
		
				//------------- Extracting TR Data from Table ------
		 Locator rows = pg.locator("table[name='courses'] tbody tr").nth(3); // 4th row

         String instructor = rows.locator("td").nth(0).textContent();
         String course = rows.locator("td").nth(1).textContent();
         String price = rows.locator("td").nth(2).textContent();

         System.out.println("Instructor: " + instructor);
         System.out.println("Course: " + course);
         System.out.println("Price: " + price);
         
         
      //Hide / Show Textbox 
         
         Locator textBox = pg.locator("#displayed-text");
         textBox.fill("Playwright Java");

         // Click Hide button
         pg.locator("#hide-textbox").click();
         Thread.sleep(1000);

         // Check if textbox is hidden
         System.out.println("Textbox visible after Hide: " + textBox.isVisible());

         // Click Show button
         pg.locator("#show-textbox").click();
         Thread.sleep(1000);

         // Check if textbox is visible
         System.out.println("Textbox visible after Show: " + textBox.isVisible());

         
         //Web Table Fixed Header
         
         Locator amounts = pg.locator(".tableFixHead td:nth-child(4)");

         int sum = 0;

         for (int i = 0; i < amounts.count(); i++) {
             String value = amounts.nth(i).textContent().trim();
             sum = sum + Integer.parseInt(value);
         }

         System.out.println("Calculated Sum: " + sum);

         // Get displayed total amount
         String totalText = pg.locator(".totalAmount").textContent();
         int displayedTotal = Integer.parseInt(totalText.replaceAll("[^0-9]", ""));

         System.out.println("Displayed Total: " + displayedTotal);

         if (sum == displayedTotal) {
             System.out.println("Validation Passed");
         } else {
             System.out.println("Validation Failed");
		}	
         
        //Mouse Hover Example
         pg.locator("(//button[normalize-space()='Mouse Hover'])[1]").hover();
         Thread.sleep(2000);
         Locator test = pg.locator("//a[normalize-space()='Reload']");
         System.out.println(test.allInnerTexts());
         
         Thread.sleep(2000);
	}
	@AfterMethod
	public void TearDown() {
		brwsr.close();
	}
}