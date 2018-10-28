///////////////////////////////////////////////////////////////////////////////////////////////////
// Form model selenium binding is a form model binding implementation for Selenium.
// Copyright (C) 2018 Dmitry Shapovalov.
//
// This file is part of form model selenium binding.
//
// Form model selenium binding is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Form model selenium binding is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.
///////////////////////////////////////////////////////////////////////////////////////////////////
package ru.d_shap.formmodel.binding.selenium;

import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ru.d_shap.formmodel.binding.html.HtmlBindedElement;

/**
 * The Selenium binded element implementation.
 *
 * @author Dmitry Shapovalov
 */
final class SeleniumBindedElementImpl implements SeleniumBindedElement {

    private final WebDriver _webDriver;

    private final HtmlBindedElement _htmlBindedElement;

    private final String _cssSelector;

    SeleniumBindedElementImpl(final WebDriver webDriver, final HtmlBindedElement htmlBindedElement) {
        super();
        _webDriver = webDriver;
        _htmlBindedElement = htmlBindedElement;
        _cssSelector = _htmlBindedElement.getElement().cssSelector();
    }

    @Override
    public Element getElement() {
        return _htmlBindedElement.getElement();
    }

    @Override
    public String getOwnText() {
        return _htmlBindedElement.getOwnText();
    }

    @Override
    public String getText() {
        return _htmlBindedElement.getText();
    }

    @Override
    public String getAttributeValue(final String attributeName) {
        return _htmlBindedElement.getAttributeValue(attributeName);
    }

    @Override
    public String getAbsoluteAttributeValue(final String attributeName) {
        return _htmlBindedElement.getAbsoluteAttributeValue(attributeName);
    }

    @Override
    public WebDriver getWebDriver() {
        return _webDriver;
    }

    @Override
    public String getCssSelector() {
        return _cssSelector;
    }

    @Override
    public WebElement getWebElement() {
        return _webDriver.findElement(By.cssSelector(_cssSelector));
    }

    @Override
    public void click() {
        getWebElement().click();
    }

    @Override
    public void clear() {
        getWebElement().clear();
    }

    @Override
    public void sendKeys(final CharSequence charSequence) {
        getWebElement().sendKeys(charSequence);
    }

    @Override
    public void submit() {
        getWebElement().submit();
    }

}
