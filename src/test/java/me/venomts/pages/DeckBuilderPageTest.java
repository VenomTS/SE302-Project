package me.venomts.pages;

import com.microsoft.playwright.*;
import me.venomts.BrowserSettings;
import org.junit.jupiter.api.*;

import static org.junit.Assert.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
        _browser = _playwright.firefox().launch(BrowserSettings.LaunchOptions);
    }

    @AfterAll
    static void CloseBrowser()
    {
        _playwright.close();
    }

    @BeforeEach
    void CreateContextAndPage()
    {
        _context = _browser.newContext(new Browser.NewContextOptions().setViewportSize(2560, 1440));
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
    @DisplayName("Import a Deck - Smoke Test")
    @Order(1)
    void ImportYDKFileTest()
    {
        _deckBuilderPage.ImportYDKFile();
        _deckBuilderPage.AssertImportYDKFile();
        _page.waitForTimeout(BrowserSettings.AfterTestDelay);
    }

    @Test
    @DisplayName("Export a Deck - Smoke Test")
    @Order(2)
    void ExportYDKFileTest()
    {
        _deckBuilderPage.ImportYDKFile();
        _deckBuilderPage.ExportYDKFile();
        assertTrue(_deckBuilderPage.IsExportMatchImportYDKFile());
        _page.waitForTimeout(BrowserSettings.AfterTestDelay);
    }

    @Test
    @DisplayName("Clear a Deck")
    @Order(3)
    void ClearDeckTest()
    {
        _deckBuilderPage.ImportYDKFile();
        _deckBuilderPage.ClearDeck();
        _deckBuilderPage.AssertDeckCleared();
        _page.waitForTimeout(BrowserSettings.AfterTestDelay);
    }

    @Test
    @DisplayName("Simulate Starting Hand")
    @Order(4)
    void SimulateStartHandGoingFirstTest()
    {
        _deckBuilderPage.ImportYDKFile();
        _deckBuilderPage.SimulateStartHand(false);
        _deckBuilderPage.AssertSimulateStartHand(5);
        _page.waitForTimeout(BrowserSettings.AfterTestDelay);
    }

    @Test
    @DisplayName("Simulate Starting Hand (Going Second)")
    @Order(5)
    void SimulateStartHandGoingSecondTest()
    {
        _deckBuilderPage.ImportYDKFile();
        _deckBuilderPage.SimulateStartHand(true);
        _deckBuilderPage.AssertSimulateStartHand(6);
        _page.waitForTimeout(BrowserSettings.AfterTestDelay);
    }

    @Test
    @DisplayName("Create a random deck")
    @Order(6)
    void RandomizeDeckTest()
    {
        _deckBuilderPage.RandomizeDeck();

        int mainDeckCardCount = _deckBuilderPage.GetMainDeckCardsCount();
        assertTrue(mainDeckCardCount >= 40 && mainDeckCardCount <= 60);
        _page.waitForTimeout(BrowserSettings.AfterTestDelay);
    }

    @Test
    @DisplayName("Search for cards (DeckBuilder)")
    @Order(7)
    void SearchForCardsTest()
    {
        _deckBuilderPage.SearchForCards("Snatchy");
        _deckBuilderPage.AssertSearchForCards();
        _page.waitForTimeout(BrowserSettings.AfterTestDelay);
    }

    @Test
    @DisplayName("Reset Card Filters (DeckBuilder)")
    @Order(8)
    void ResetFilterTest()
    {
        _deckBuilderPage.SearchForCards("Snatchy");
        _deckBuilderPage.AssertSearchForCards();
        _deckBuilderPage.ResetFilter();
        _deckBuilderPage.AssertFilterReset();
        _page.waitForTimeout(BrowserSettings.AfterTestDelay);
    }

    @Test
    @DisplayName("Add Card to Main Deck by Dragging")
    @Order(9)
    void DragToMainDeckTest()
    {
        _deckBuilderPage.AddToMainDeck("Cupsy");
        _deckBuilderPage.AssertCardDraggedToMainDeck();
        _page.waitForTimeout(BrowserSettings.AfterTestDelay);
    }

    @Test
    @DisplayName("Add Card to Extra Deck by Dragging")
    @Order(10)
    void DragToExtraDeckTest()
    {
        _deckBuilderPage.AddToExtraDeck("Snatchy");
        _deckBuilderPage.AssertCardDraggedToExtraDeck();
        _page.waitForTimeout(BrowserSettings.AfterTestDelay);
    }

    @Test
    @DisplayName("Add Card to Side Deck by Dragging")
    @Order(11)
    void DragToSideDeckTest()
    {
        _deckBuilderPage.AddToSideDeck("Yummyusment");
        _deckBuilderPage.AssertCardDraggedToSideDeck();
        _page.waitForTimeout(BrowserSettings.AfterTestDelay);
    }
}