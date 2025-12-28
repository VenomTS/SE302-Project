package me.venomts.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DeckBuilderPage {
    private Page _page;
    private Locator _importButton;
    private Locator _importFromFileOption;
    private Locator _importFromURLOption;

    public DeckBuilderPage(Page page){
        _page = page;
        _importButton=page.locator("#deckImport__BV_toggle_");
        _importFromFileOption=page.getByText(" From .ydk Deck File ");
        _importFromURLOption=page.getByText(" From YDKe URL ");



    }
    public void clickImportButton() {
        _importButton.click();
    }
    public void clickImportFromFile() {
        _importButton.click();
        _importFromFileOption.click();
    }
    public void clickImportFromURL() {
        _importButton.click();
        _importFromURLOption.click();
    }


    public void assertImportButtonIsVisible() {
        assertThat(_importButton).isVisible();
    }

    public void assertImportButtonIsEnabled() {
        assertThat(_importButton).isEnabled();
    }

    public void assertDropdownOpensWithOptions() {
        _importButton.click();
        _page.waitForTimeout(500);

        assertThat(_importFromFileOption).isVisible();
        assertThat(_importFromURLOption).isVisible();

        System.out.println("Dropdown opens with both options");
    }

    public void assertImportFromFileOptionClickable() {
        _importButton.click();
        _page.waitForTimeout(500);

        _importFromFileOption.click();

        _page.waitForTimeout(1000);
        Locator errors = _page.locator(".error, .alert-danger");
        assertThat(errors).isHidden();

        System.out.println("Import from File option clicked without errors");
    }

    public void assertImportFromURLOptionClickable() {
        _importButton.click();
        _page.waitForTimeout(500);
        _importFromURLOption.click();
        _page.waitForTimeout(1000);

        Locator errors = _page.locator(".error, .alert-danger");
        assertThat(errors).isHidden();

        System.out.println("Import from URL option clicked without errors");
    }
}