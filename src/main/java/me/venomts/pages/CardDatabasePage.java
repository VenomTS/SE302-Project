package me.venomts.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import me.venomts.enums.*;


import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CardDatabasePage
{
    private final Page _page;

    private final Locator _name;
    private final Locator _attribute;
    private final Locator _race;
    private final Locator _attack;
    private final Locator _defense;
    private final Locator _level;
    private final Locator _link;
    private final Locator _scale;
    private final Locator _language;
    private final Locator _sortBy;
    private final Locator _sortDirection;
    private final Locator _resetFiltersButton;
    private final Locator _cardsFound;
    private final Locator _limit;

    private final String _cardsFoundInit;

    private static final String DefaultURL = "https://ygoprodeck.com/card-database/?num=24&offset=0";
    private int _currentLimit = 24;


    public CardDatabasePage(Page page)
    {
        _page = page;
        Locator advancedFiltersButton = page.locator(".btn.btn-primary.filterlimit");
        _name = page.locator("#filter-dq");
        _attribute = page.locator("#filter-attribute");
        _race = page.locator("#filter-race");
        _attack = page.locator("#atkLabel");
        _defense = page.locator("#defLabel");
        _level = page.locator("#levelLabel");
        _link = page.locator("#linkLabel");
        _scale = page.locator("#scaleLabel");
        _language = page.locator("#filter-language");
        _sortBy = page.locator("#filter-sort");
        _sortDirection = page.locator("#filter-sort-direction");
        _resetFiltersButton = page.locator("#resetFilters");
        _cardsFound = page.locator(".api-paging-text").first();
        _limit = page.locator("#filter-limit");

        _cardsFoundInit = _cardsFound.textContent();
        advancedFiltersButton.click();
    }

    public void ApplyFilter(String name, Attribute attribute, Type type, int attack, int defense, int level, int link, int scale, Language language, SortBy sortBy, boolean isAscending)
    {
        _name.fill(name);
        String attributeString = attribute.toString().replace("__", " ").replace("_", "-");
        _attribute.selectOption(attribute == Attribute.Select__Attribute ? attributeString : attributeString.toUpperCase());
        _race.selectOption(type.toString().replace("__", " ").replace("_", "-"));
        _attack.fill(String.valueOf(attack));
        _defense.fill(String.valueOf(defense));
        _level.fill(String.valueOf(level));
        _link.fill(String.valueOf(link));
        _scale.fill(String.valueOf(scale));
        _language.selectOption(language.toString());
        _sortBy.selectOption(sortBy.toString().replace("__", " "));
        _sortDirection.selectOption(isAscending ? "ASC" : "DESC");
    }

    public void SetLimit(FilterLimit limit)
    {
        _limit.selectOption(limit.toString().replace("__", " "));
        switch(limit)
        {
            case Limit__24:
                _currentLimit = 24;
                break;
            case Limit__30:
                _currentLimit = 30;
                break;
            case Limit__50:
                _currentLimit = 50;
                break;
            default:
                _currentLimit = 100;
                break;
        }
    }

    public void ResetFilters()
    {
        _resetFiltersButton.click();
    }

    // We cannot use numbers as assertions because numbers may change due to addition of new cards
    public boolean IsFilterApplied()
    {
        String foundCards = _cardsFound.textContent();
        assertThat(_page).not().hasURL(DefaultURL);
        return !foundCards.equals(_cardsFoundInit);
    }

    public void AssertLimitSet()
    {
        Pattern pattern = Pattern.compile("(num=" + _currentLimit + ")");
        assertThat(_page).hasURL(pattern);
    }

    public boolean IsFilterReset()
    {
        String foundCards = _cardsFound.textContent();
        assertThat(_page).hasURL(DefaultURL);
        return foundCards.equals(_cardsFoundInit);
    }
}
