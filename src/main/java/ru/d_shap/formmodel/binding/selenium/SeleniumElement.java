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

import ru.d_shap.formmodel.binding.BindedElement;
import ru.d_shap.formmodel.definition.ElementDefinition;

/**
 * Selenium element.
 *
 * @author Dmitry Shapovalov
 */
public final class SeleniumElement extends BindedElement<SeleniumElement, SeleniumFormReference, Element> {

    private final WebDriver _webDriver;

    private final String _cssSelector;

    SeleniumElement(final WebDriver webDriver, final ElementDefinition elementDefinition, final Element element) {
        super(elementDefinition, element);
        _webDriver = webDriver;
        _cssSelector = element.cssSelector();
    }

    /**
     * Get web driver.
     *
     * @return web driver.
     */
    public WebDriver getWebDriver() {
        return _webDriver;
    }

    /**
     * Get web element.
     *
     * @return web element.
     */
    public WebElement getWebElement() {
        return _webDriver.findElement(By.cssSelector(_cssSelector));
    }

    /**
     * Click on the current web element.
     */
    public void click() {
        getWebElement().click();
    }

    /**
     * Submit the form with the current web element.
     */
    public void submit() {
        getWebElement().submit();
    }

    /**
     * Send keys to the current web element.
     *
     * @param charSequence the keys.
     */
    public void sendKeys(final CharSequence charSequence) {
        getWebElement().sendKeys(charSequence);
    }

    /**
     * Clear the current web element.
     */
    public void clear() {
        getWebElement().clear();
    }

}
