///////////////////////////////////////////////////////////////////////////////////////////////////
// Form model Selenium binding is a form model binding implementation for Selenium WebDriver.
// Copyright (C) 2018 Dmitry Shapovalov.
//
// This file is part of form model Selenium binding.
//
// Form model Selenium binding is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Form model Selenium binding is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.
///////////////////////////////////////////////////////////////////////////////////////////////////
package ru.d_shap.fm.formmodel.binding.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ru.d_shap.fm.formmodel.binding.html.HtmlBindedElement;

/**
 * The Selenium binded element.
 *
 * @author Dmitry Shapovalov
 */
public interface SeleniumBindedElement extends HtmlBindedElement {

    /**
     * Get the web driver.
     *
     * @return the web driver.
     */
    WebDriver getWebDriver();

    /**
     * Get the web element.
     *
     * @return the web element.
     */
    WebElement getWebElement();

    /**
     * Click on the web element.
     */
    void click();

    /**
     * Clear the web element.
     */
    void clear();

    /**
     * Send keys to the web element.
     *
     * @param charSequence the keys.
     */
    void sendKeys(CharSequence charSequence);

    /**
     * Submit the form with the web element.
     */
    void submit();

}
