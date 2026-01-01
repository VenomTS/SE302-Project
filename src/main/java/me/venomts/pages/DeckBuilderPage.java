package me.venomts.pages;

import com.microsoft.playwright.*;
import me.venomts.FileManagement;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DeckBuilderPage
{
    private final Page _page;

    private final Locator _mainDeckStats;
    private final Locator _extraDeckStats;
    private final Locator _sideDeckStats;
    private final Locator _mainDeckContent;
    private final Locator _extraDeckContent;
    private final Locator _sideDeckContent;

    private final Locator _tools;
    private final Locator _cardsFound;

    private static final String CardsInDeckBase = "0 Cards";
    private String _baseCardsFound;

    public DeckBuilderPage(Page page)
    {
        _page = page;

        _mainDeckStats = _page.locator(".deck-part--main .deck-part__stats");
        _extraDeckStats = _page.locator(".deck-part--extra .deck-part__stats");
        _sideDeckStats = _page.locator(".deck-part--side .deck-part__stats");
        _mainDeckContent = _page.locator(".deck-part--main .deck-part__content");
        _extraDeckContent = _page.locator(".deck-part--extra .deck-part__content");
        _sideDeckContent = _page.locator(".deck-part--side .deck-part__content");
        _tools = _page.locator("#deckTools__BV_toggle_");
        _cardsFound = _page.locator(".builder__count");
    }

    private String GetResource(String resourceName)
    {
        URL fileURL = getClass().getResource(resourceName);
        if(fileURL == null)
            throw new RuntimeException("Could not find the resource " + resourceName);

        return fileURL.getPath();
    }

    private void SetBaseCardsFound()
    {
        assertThat(_cardsFound).not().hasText("Result: 0 of 0 Cards");
        _baseCardsFound = _cardsFound.textContent();
    }

    private Locator GetDraggableCard(String cardName)
    {
        SearchForCards(cardName);
        Locator card = _page.locator(".builder-matches__match").first();
        return card.locator(".card.builder-matches__match__card");
    }

    public void ImportYDKFile()
    {
        Locator importButton = _page.locator("#deckImport__BV_toggle_");
        Locator importFromFileButton = _page.getByText("From .ydk Deck File");
        FileChooser fileChooser = _page.waitForFileChooser(() ->
        {
            importButton.click();
            importFromFileButton.click();
        });

        try
        {
            String filePath = GetResource("/ImportDeck.ydk");
            Path path = Paths.get(filePath);
            fileChooser.setFiles(path);
        }
        catch(RuntimeException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void ExportYDKFile()
    {
        Locator exportButton = _page.locator("#deckExport__BV_toggle_");
        Locator exportToFileButton = _page.getByText("To .ydk Deck File");
        exportButton.click();

        Download download = _page.waitForDownload(exportToFileButton::click);
        download.saveAs(Paths.get("ExportDeck.ydk"));
    }

    public void ClearDeck()
    {
        Locator editButton = _page.locator("#deckEdit__BV_toggle_");
        Locator clearButton = _page.getByText("Clear", new Page.GetByTextOptions().setExact(true));
        editButton.click();
        clearButton.click();

        Locator okButton =_page.locator("#clearDeck___BV_modal_footer_ .btn.btn-primary");
        okButton.click();
    }

    public void SimulateStartHand(boolean isGoingSecond)
    {
        Locator simulateStartHand = _page.getByText("Simulate Start-Hand");
        Locator goingSecondButton = _page.getByText("Going Second", new Page.GetByTextOptions().setExact(true));
        _tools.click();
        simulateStartHand.click();
        if(isGoingSecond)
            goingSecondButton.click();
    }

    public void RandomizeDeck()
    {
        Locator randomizeButton = _page.getByText("Randomize", new Page.GetByTextOptions().setExact(true));
        _tools.click();
        randomizeButton.click();
    }

    public void SearchForCards(String name)
    {
        SetBaseCardsFound();
        Locator filterCardsButton = _page.locator(".btn.btn-primary.collapsed");
        Locator cardNameField = _page.locator("#v-3");

        filterCardsButton.click();
        cardNameField.fill(name);
    }

    public void ResetFilter()
    {
        Locator resetFilterButton = _page.locator(".btn.btn-danger");
        resetFilterButton.click();
    }

    public void AddToMainDeck(String cardName)
    {
        Locator card = GetDraggableCard(cardName);
        _mainDeckContent.scrollIntoViewIfNeeded();
        card.dragTo(_mainDeckContent);
    }

    public void AddToExtraDeck(String cardName)
    {
        Locator card = GetDraggableCard(cardName);
        _extraDeckContent.scrollIntoViewIfNeeded();
        card.dragTo(_extraDeckContent);
    }

    public void AddToSideDeck(String cardName)
    {
        Locator card = GetDraggableCard(cardName);
        _sideDeckContent.scrollIntoViewIfNeeded();
        card.dragTo(_sideDeckContent);
    }

    public boolean IsExportMatchImportYDKFile()
    {
        String originalFilePath = GetResource("/ImportDeck.ydk");
        File originalFile = new File(originalFilePath);
        File exportedFile = Paths.get("ExportDeck.ydk").toFile();

        return FileManagement.AreFilesSame(originalFile, exportedFile);
    }

    public int GetMainDeckCardsCount()
    {
        Locator mainDeckContent = _page.locator(".deck-part--main .deck-part__content");
        Locator cards = mainDeckContent.locator(".card");
        return cards.count();
    }

    public void AssertImportYDKFile()
    {
        assertThat(_mainDeckStats).hasText("40 Cards (29 Monster | 8 Spell | 3 Trap)");
        assertThat(_extraDeckStats).hasText("15 Cards (12 Fusion | 2 Link | 1 XYZ)");
        assertThat(_sideDeckStats).hasText(CardsInDeckBase);
    }

    public void AssertDeckCleared()
    {
        assertThat(_mainDeckStats).hasText(CardsInDeckBase);
        assertThat(_extraDeckStats).hasText(CardsInDeckBase);
        assertThat(_sideDeckStats).hasText(CardsInDeckBase);
    }

    public void AssertSimulateStartHand(int count)
    {
        Locator hand = _page.locator(".draw-sim__output");
        Locator cards = hand.locator(".draw-sim__output__card");

        assertThat(cards).hasCount(count);
    }

    public void AssertSearchForCards()
    {
        assertThat(_cardsFound).not().hasText(_baseCardsFound);
    }

    public void AssertFilterReset()
    {
        assertThat(_cardsFound).hasText(_baseCardsFound);
    }

    public void AssertCardDraggedToMainDeck()
    {
        assertThat(_mainDeckStats).not().hasText(CardsInDeckBase);
    }

    public void AssertCardDraggedToExtraDeck()
    {
        assertThat(_extraDeckStats).not().hasText(CardsInDeckBase);
    }

    public void AssertCardDraggedToSideDeck()
    {
        assertThat(_sideDeckStats).not().hasText(CardsInDeckBase);
    }
}
