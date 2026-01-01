package me.venomts.pages;

import com.microsoft.playwright.*;
import me.venomts.BrowserSettings;
import org.junit.jupiter.api.*;

import static org.junit.Assert.assertTrue;

public class DeckBuilderPageTest
{
    private static final String PageURL = "https://ygoprodeck.com/deckbuilder/";

    private static Playwright _playwright;
    private static Browser _browser;
    private BrowserContext _context;
    private Page _page;
    private DeckBuilderPage _deckBuilderPage;

    @BeforeAll
    static void LaunchBrowser()
    {
        _playwright = Playwright.create();
        _browser = _playwright.chromium().launch(BrowserSettings.LaunchOptions);
    }

    @AfterAll
    static void CloseBrowser()
    {
        _playwright.close();
    }

    @BeforeEach
    void CreateContextAndPage()
    {
        _context = _browser.newContext();
        _page = _context.newPage();
        _page.navigate(PageURL);
        _deckBuilderPage = new DeckBuilderPage(_page);
    }

    @AfterEach
    void CloseContext()
    {
        _context.close();
    }

    @Test
    void ImportYDKFileTest()
    {
        _deckBuilderPage.ImportYDKFile();
        _deckBuilderPage.AssertImportYDKFile();
    }

    @Test
    void ExportYDKFileTest()
    {
        _deckBuilderPage.ImportYDKFile();
        _deckBuilderPage.ExportYDKFile();
        assertTrue(_deckBuilderPage.IsExportMatchImportYDKFile());
    }

    @Test
    void ClearDeckTest()
    {
        _deckBuilderPage.ImportYDKFile();
        _deckBuilderPage.ClearDeck();
        _deckBuilderPage.AssertDeckCleared();
    }

    @Test
    void SimulateStartHandGoingFirstTest()
    {
        _deckBuilderPage.ImportYDKFile();
        _deckBuilderPage.SimulateStartHand(false);
        _deckBuilderPage.AssertSimulateStartHand(5);
    }

    @Test
    void SimulateStartHandGoingSecondTest()
    {
        _deckBuilderPage.ImportYDKFile();
        _deckBuilderPage.SimulateStartHand(true);
        _deckBuilderPage.AssertSimulateStartHand(6);
    }

    @Test
    void RandomizeDeckTest()
    {
        _deckBuilderPage.RandomizeDeck();

        int mainDeckCardCount = _deckBuilderPage.GetMainDeckCardsCount();
        assertTrue(mainDeckCardCount >= 40 && mainDeckCardCount <= 60);
    }

    @Test
    void SearchForCardsTest()
    {
        _deckBuilderPage.SearchForCards("Snatchy");
        _deckBuilderPage.AssertSearchForCards();
    }

    @Test
    void ResetFilterTest()
    {
        _deckBuilderPage.SearchForCards("Snatchy");
        _deckBuilderPage.AssertSearchForCards();
        _deckBuilderPage.ResetFilter();
        _deckBuilderPage.AssertFilterReset();
    }

    @Test
    void DragToMainDeckTest()
    {
        _deckBuilderPage.AddToMainDeck("Cupsy");
        _deckBuilderPage.AssertCardDraggedToMainDeck();
    }

    @Test
    void DragToExtraDeckTest()
    {
        _deckBuilderPage.AddToExtraDeck("Snatchy");
        _deckBuilderPage.AssertCardDraggedToExtraDeck();
    }

    @Test
    void DragToSideDeckTest()
    {
        _deckBuilderPage.AddToSideDeck("Yummyusment");
        _deckBuilderPage.AssertCardDraggedToSideDeck();
    }

    /*

    @Test
    void testImportButton() {
        _deckBuilderPage.assertImportButtonIsVisible();
        _deckBuilderPage.assertImportButtonIsEnabled();
    }

    @Test
    void testImportActualDeckFile() {
        String filePath = "src/test/resources/ImportDeck.ydk";
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
    String filePath = "src/test/resources/ImportDeck.ydk";
    _deckBuilderPage.importDeckFromFile(filePath);
    _deckBuilderPage.testExportToYdkFileOption();
    System.out.println("Export to .ydk file test completed");
    File file = new File(Paths.get("myFunnyTest.ydk").toUri());
    assertTrue(file.exists());
}

@Test
void testExportToYDKeURL() {
    String filePath = "src/test/resources/ImportDeck.ydk";
    _deckBuilderPage.importDeckFromFile(filePath);
    _deckBuilderPage.testExportToYDKeURLClick();
    System.out.println("YDKe URL export test completed");
}

@Test
void testExportToDeckList() {
    String filePath = "src/test/resources/ImportDeck.ydk";
    _deckBuilderPage.importDeckFromFile(filePath);
    _deckBuilderPage.testExportToDeckList();
    System.out.println("Deck List export test completed");
}
@Test
void testExportShareableLink() {
        String filePath = "src/test/resources/ImportDeck.ydk";
        _deckBuilderPage.importDeckFromFile(filePath);
        _deckBuilderPage.testExportShareableLink();
        System.out.println("Shareable link export test completed");
    }
    @Test
    void testExportToScreenshot() {
        String filePath = "src/test/resources/ImportDeck.ydk";
        _deckBuilderPage.importDeckFromFile(filePath);
        _deckBuilderPage.testExportToScreenshot();
        System.out.println("To screenshot export test completed");
        File file = new File(Paths.get("ydk-decklist.png").toUri());
        assertTrue(file.exists());
    }

    @Test
    void testSimulateStartHand() {
       String filePath = "src/test/resources/ImportDeck.ydk";
        _deckBuilderPage.importDeckFromFile(filePath);
        _deckBuilderPage.simulateStartHand();
        _deckBuilderPage.assertStartHandDrawn();
        System.out.println("Success!");

    }
    @Test
    void testFilterByName()
    {
        String cardName="asg";
        _deckBuilderPage.testFilterByName(cardName);
        _deckBuilderPage.assertFilterChangesCount();
    }
    @Test
    void testClear()
    {
        String filePath = "src/test/resources/ImportDeck.ydk";
        _deckBuilderPage.importDeckFromFile(filePath);
        _deckBuilderPage.testClearButton();
        _deckBuilderPage.assertClearButton();
    }
    @Test
    void testRandomizeButton()
    {
        _deckBuilderPage.testRandomize();
        _deckBuilderPage.assertRandomizeButton();
    }
    @Test
    void testDragToMainDeck()
    {
        _deckBuilderPage.testDragToMainDeck();
        _deckBuilderPage.assertDragToMain();
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
*/
}