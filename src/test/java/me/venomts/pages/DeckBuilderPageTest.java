package me.venomts.pages;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class DeckBuilderPageTest
{
    private Playwright _playwright;
    private Browser _browser;
    private Page _page;
    private DeckBuilderPage _deckBuilderPage;

    private static final String PageURL = "https://ygoprodeck.com/deckbuilder/";

    @BeforeEach
    void setUp()
    {
        _playwright = Playwright.create();
        _browser = _playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(500)
        );
        _page = _browser.newPage();

        _page.navigate(PageURL);
        _page.waitForURL(PageURL);

        _deckBuilderPage = new DeckBuilderPage(_page);
    }

    @Test
    void testImportButton() {
        _deckBuilderPage.assertImportButtonIsVisible();
        _deckBuilderPage.assertImportButtonIsEnabled();
    }

    @Test
    void testImportActualDeckFile() {
        String filePath = "src/test/resources/ydk-decklist.ydk";
        _deckBuilderPage.importDeckFromFile(filePath);
        _deckBuilderPage.assertDeckImportedSuccessfully();
        System.out.println("Deck file imported!");
    }

    @Test
    void testURLImportWorks() {
        System.out.println("Testing URL import...");
        _deckBuilderPage.assertURLImportWorks();
        System.out.println("URL import test passed");
}
@Test
void testExportToYdkFile() {
    String filePath = "src/test/resources/ydk-decklist.ydk";
    _deckBuilderPage.importDeckFromFile(filePath);
    _deckBuilderPage.testExportToYdkFileOption();
    System.out.println("Export to .ydk file test completed");
    File file = new File(Paths.get("myFunnyTest.ydk").toUri());
    assertTrue(file.exists());
}

@Test
void testExportToYDKeURL() {
    String filePath = "src/test/resources/ydk-decklist.ydk";
    _deckBuilderPage.importDeckFromFile(filePath);
    _deckBuilderPage.testExportToYDKeURLClick();
    System.out.println("YDKe URL export test completed");
}

@Test
void testExportToDeckList() {
    String filePath = "src/test/resources/ydk-decklist.ydk";
    _deckBuilderPage.importDeckFromFile(filePath);
    _deckBuilderPage.testExportToDeckList();
    System.out.println("Deck List export test completed");
}
@Test
void testExportShareableLink() {
        String filePath = "src/test/resources/ydk-decklist.ydk";
        _deckBuilderPage.importDeckFromFile(filePath);
        _deckBuilderPage.testExportShareableLink();
        System.out.println("Shareable link export test completed");
    }
    @Test
    void testExportToScreenshot() {
        String filePath = "src/test/resources/ydk-decklist.ydk";
        _deckBuilderPage.importDeckFromFile(filePath);
        _deckBuilderPage.testExportToScreenshot();
        System.out.println("To screenshot export test completed");
        File file = new File(Paths.get("ydk-decklist.png").toUri());
        assertTrue(file.exists());
    }

    @Test
    void testSimulateStartHand() {
       /* String filePath = "src/test/resources/ydk-decklist.ydk";

        _deckBuilderPage.importDeckFromFile(filePath);*/
        _deckBuilderPage.simulateStartHand();

    }








    @AfterEach
    void tearDown() {
        try {
            if (_page != null) {
                _page.close();
            }
            if (_browser != null) {
                _browser.close();
            }
            if (_playwright != null) {
                _playwright.close();
            }
        } catch (Exception e) {
            System.out.println("Error during cleanup: " + e.getMessage());
        }
    }

}