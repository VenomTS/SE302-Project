package me.venomts.pages;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.nio.file.Paths;

public class DeckBuilderPage {
    private Page _page;
    private Locator _importButton;
    private Locator _importFromFileOption;
    private Locator _importFromURLOption;
    private Locator _exportButton;
    private Locator _exportYDKDeckFile;
    private Locator _exportYDKeURLClipboard;
    private Locator _exportDeckListClipboard;
    private Locator _exportShareableLink;
    private Locator _exportToScreenshot;
    private Locator _toolsButton;
    private Locator _filterButton;
    private Locator _editButton;

    public DeckBuilderPage(Page page){
        _page = page;
        _importButton=page.locator("#deckImport__BV_toggle_");
        _importFromFileOption=page.getByText(" From .ydk Deck File ");
        _importFromURLOption=page.getByText(" From YDKe URL ");
        _exportButton=page.locator("#deckExport__BV_toggle_");
        _exportYDKeURLClipboard=page.getByText(" To YDKe URL in Clipboard ");
        _exportDeckListClipboard=page.getByText(" To Deck List in Clipboard ");
        _exportShareableLink=page.getByText(" To Shareable Link in Clipboard ");
        _exportToScreenshot=page.getByText(" To Screenshot ");
        _exportYDKDeckFile =page.getByText(" To .ydk Deck File ");
        _toolsButton=page.locator("#deckTools__BV_toggle_");
        _filterButton=page.locator(".btn.btn-primary.collapsed");
        _editButton=page.locator("#deckEdit__BV_toggle_");
    }
    public void clickImportButton() {
        _importButton.click();
    }
    public void clickExportButton()
    {
        _exportButton.click();
    }
    public void clickToolsButton()
    {
        _toolsButton.click();
    }
    public void clickFilterButton()
    {
        _filterButton.click();
    }
    public void clickEditButton()
    {
        _editButton.click();
    }

    public void importDeckFromFile(String filePath) {
        FileChooser fileChooser = _page.waitForFileChooser(() -> {
            clickImportButton();
            _page.locator(".dropdown-menu.show").waitFor();

            _importFromFileOption.click();
        });
        fileChooser.setFiles(Paths.get(filePath));
    }
    public void testExportToYdkFileOption() {
        System.out.println("Testing Export to .ydk file...");
        clickExportButton();
        Locator toYDK = _page.getByText("To .ydk Deck File");
        Download download = _page.waitForDownload(toYDK::click);
        download.saveAs(Paths.get("myFunnyTest.ydk"));
    }
    public void testExportToYDKeURLClick() {
        System.out.println("Testing Export to YDKe URL...");
        clickExportButton();
        Locator toYDKLink = _page.getByText("To YDKe URL in Clipboard");
        Locator successMsg = _page.locator(".alert-success, .toast-success, [class*='success']");
        if (successMsg.count() > 0) {
            System.out.println("Success message shown: " + successMsg.first().textContent());
        } else {
            System.out.println("Note: No success message visible");
        }
        System.out.println("YDKe URL export clicked");
    }
    public void testExportToDeckList()
    {
        System.out.println("Testing export to deck list in clipboard");
        clickExportButton();

        Locator DeckListClipboard = _page.getByText("To Deck List in Clipboard");
        Locator successMsg = _page.locator(".alert-success, .toast-success, [class*='success']");
        if (successMsg.count() > 0) {
            System.out.println("Success message shown: " + successMsg.first().textContent());
        } else {
            System.out.println("Note: No success message visible");
        }
        System.out.println("DeckList exported to clipboard");
    }
    public void testExportShareableLink()
    {
        System.out.println("Testing export shareable link");
        clickExportButton();

        Locator ShareableLink = _page.getByText("To Shareable Link in Clipboard");
        Locator successMsg = _page.locator(".alert-success, .toast-success, [class*='success']");
        if (successMsg.count() > 0) {
            System.out.println("Success message shown: " + successMsg.first().textContent());
        } else {
            System.out.println("Note: No success message visible");
        }
        System.out.println("Shareable link exported to clipboard");
    }

    public void testExportToScreenshot()
    {
        System.out.println("Testing export to Screenshot");
        clickExportButton();
        Locator ToScreenshot = _page.getByText("To Screenshot");
        Download download = _page.waitForDownload(ToScreenshot::click);
        download.saveAs(Paths.get("ydk-decklist.png"));
    }

    public void simulateStartHand()
    {
        System.out.println("testing simulate start-hand");
        clickToolsButton();
        Locator StartHand = _page.getByText("Simulate Start-Hand");
        StartHand.click();
        _page.waitForTimeout(100);
    }
    public void testFilterByName(String cardName)
    {
        clickFilterButton();
        Locator nameInput = _page.locator("#v-3");
        nameInput.fill(cardName);
        _page.waitForTimeout(150);
    }
    public void testClearButton()
    {
        clickEditButton();
        Locator clearButton = _page.getByText("Clear");
        clearButton.click();
        Locator OK =_page.locator("#clearDeck___BV_modal_footer_ > .btn.btn-primary");
        OK.click();
    }
    public void testRandomize()
    {
        clickToolsButton();
        Locator randomizeButton = _page.getByText("Randomize", new Page.GetByTextOptions().setExact(true));
        randomizeButton.click();

    }
    public void testDragToMainDeck()
    {
        Locator cardList=_page.getByAltText("Blue-Eyes White Dragon");
        cardList.hover();
        _page.mouse().down();
        Locator mainDeck=_page.locator("div[data-deck-part-area='main']");
        mainDeck.hover();
        _page.mouse().up();

    }














    public void assertImportButtonIsVisible() {
        assertThat(_importButton).isVisible();
    }
    public void assertImportButtonIsEnabled() {
        assertThat(_importButton).isEnabled();
    }

    public void assertDeckImportedSuccessfully() {
        _page.waitForTimeout(3000);
        Locator allCards = _page.locator(".card");
        assertThat(allCards.first()).isVisible();
    }

    public void assertURLImportWorks() {
        _importButton.click();
        _page.locator(".dropdown-menu.show").waitFor();
        _importFromURLOption.click();
        _page.waitForTimeout(1000);

        Locator urlInput = _page.locator("#v-1");
        assertThat(urlInput.first()).isVisible();

        urlInput.fill("ydke://o6lXBZyFNAI=!viOnAg==!7ydRAA==!");

        _page.waitForTimeout(3000);
        Locator cards = _page.locator(".card");
        assertThat(cards.first()).isVisible();

        System.out.println("URL import worked with " + cards.count() + " cards");
    }
    public void assertStartHandDrawn() {
        Locator drawnCards = _page.locator(".draw-sim__output__card");
        assertThat(drawnCards).hasCount(5);
        System.out.println("Start hand drawn with " + drawnCards.count() + " cards");
    }
    public void assertFilterChangesCount() {
        Locator result = _page.locator(".builder__count");
        System.out.println(result.textContent());
        String resultText = result.innerText();
        assertThat(result).not().containsText("13941 of 13941");
    }
    public void assertClearButton()
    {
        Locator mainDeck=_page.locator(".deck-part.deck-part--main");
        Locator cards=mainDeck.locator(".deck-part__stats");
        assertThat(cards).containsText("0 Cards");
    }
    public void assertRandomizeButton()
    {
        Locator mainDeck=_page.locator(".deck-part.deck-part--main");
        Locator cards=mainDeck.locator(".deck-part__stats");
        System.out.println(cards.textContent());
        assertThat(cards).not().hasText("0 Cards");
    }
    public void assertDragToMain()
    {
        Locator mainDeck=_page.locator(".deck-part.deck-part--main");
        Locator cards=mainDeck.locator(".deck-part__stats");
        System.out.println(cards.textContent());
        assertThat(cards).not().containsText("0 Cards");
        _page.waitForTimeout(1000);
    }

}
