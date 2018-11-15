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

import org.openqa.selenium.WebDriver;

/**
 * The HTML frame or iframe.
 *
 * @author Dmitry Shapovalov
 */
abstract class Frame {

    private final WebDriver _webDriver;

    private Frame(final WebDriver webDriver) {
        super();
        _webDriver = webDriver;
    }

    WebDriver getWebDriver() {
        return _webDriver;
    }

    abstract void switchTo();

    /**
     * The HTML frame or iframe, identified by index.
     *
     * @author Dmitry Shapovalov
     */
    static final class IndexedFrame extends Frame {

        private final int _index;

        private IndexedFrame(final WebDriver webDriver, final int index) {
            super(webDriver);
            _index = index;
        }

        @Override
        void switchTo() {
            getWebDriver().switchTo().frame(_index);
        }

    }

    /**
     * The HTML frame or iframe, identified by name.
     *
     * @author Dmitry Shapovalov
     */
    static final class NamedFrame extends Frame {

        private final String _name;

        private NamedFrame(final WebDriver webDriver, final String name) {
            super(webDriver);
            _name = name;
        }

        @Override
        void switchTo() {
            getWebDriver().switchTo().frame(_name);
        }

    }

}
