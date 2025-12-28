package me.venomts.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.nio.file.Paths;
import com.microsoft.playwright.FileChooser;

import com.microsoft.playwright.options.AriaRole;

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
    }
    public void clickImportButton() {
        _importButton.click();
    }
    public void clickExportButton()
    {
        _exportButton.click();
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
        _page.waitForTimeout(300);
        Locator toYDK=_page.getByText("To .ydk Deck File");
        toYDK.click();
        System.out.println(toYDK.textContent());
        _page.waitForTimeout(10000);

        System.out.println("Export to .ydk file clicked");
    }
    public void testExportToYDKeURLClick() {
        System.out.println("Testing Export to YDKe URL...");
        clickExportButton();
        _page.waitForTimeout(100);
        _page.evaluate("Array.from(document.querySelectorAll('.dropdown-item')).find(el => el.textContent.includes(' To YDKe URL in Clipboard ')).click()");
        System.out.println("YDKe URL export clicked");
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
    public void assertExportToYdkFileWorks() {

        clickExportButton();
        _page.waitForTimeout(200);

        assertThat(_exportYDKDeckFile).isVisible();
        assertThat(_exportYDKDeckFile).isEnabled();

        System.out.println("Export to .ydk file option available");
    }

    public void assertExportToYDKeURLOptionExists() {
        clickExportButton();
        _page.waitForTimeout(200);

        assertThat(_exportYDKeURLClipboard).isVisible();

        System.out.println("YDKe URL export option exists");
    }




}
