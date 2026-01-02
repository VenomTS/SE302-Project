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
        Page _page = _context.newPage();
        _page.navigate(PageURL);
        _deckBuilderPage = new DeckBuilderPage(_page);
    }

    @AfterEach
    void CloseContext()
    {
        _context.close();
    }

    @Test
    @DisplayName("Smoke Test")
    void ImportYDKFileTest()
    {
        _deckBuilderPage.ImportYDKFile();
        _deckBuilderPage.AssertImportYDKFile();
    }

    @Test
    @DisplayName("Smoke Test")
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
}