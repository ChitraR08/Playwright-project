package test;

import com.microsoft.playwright.*;

import test.Locators_Test.ElementUtils;

public class PractisePage {

	
    public static void main(String[] args) throws InterruptedException {

        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
            );

            Page page = browser.newPage();
            page.navigate("https://rahulshettyacademy.com/AutomationPractice/");

            // Example fields to highlight

            Locator nameField = page.locator("#name");
            Locator alertButton = page.locator("#alertbtn");
            Locator radioOption = page.locator("input[value='radio2']");
            Locator dropdown = page.locator("#dropdown-class-example");
            Locator checkbox1 = page.locator("#checkBoxOption1");

            // highlight before interacting
            ElementUtils.highlightElement(page, nameField);
            nameField.fill("Playwright User");

            ElementUtils.highlightElement(page, radioOption);
            radioOption.click();

            ElementUtils.highlightElement(page, checkbox1);
            checkbox1.click();

            ElementUtils.highlightElement(page, dropdown);
            dropdown.selectOption("Option2");

            ElementUtils.highlightElement(page, alertButton);
            alertButton.click();

            // handle alert
           // System.out.println("Alert Text: " + page.onDialog(dialog -> {System.out.println(dialog.message()); dialog.accept(); }));
            Thread.sleep(1000);
            browser.close();
        }
    }
}