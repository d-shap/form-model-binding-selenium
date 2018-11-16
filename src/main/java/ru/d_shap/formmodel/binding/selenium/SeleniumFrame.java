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
 * The Selenium frame or iframe.
 *
 * @author Dmitry Shapovalov
 */
abstract class SeleniumFrame {

    SeleniumFrame() {
        super();
    }

    abstract void switchTo(WebDriver webDriver);

    /**
     * The Selenium frame or iframe, identified by index.
     *
     * @author Dmitry Shapovalov
     */
    static final class IndexedSeleniumFrame extends SeleniumFrame {

        private final int _index;

        IndexedSeleniumFrame(final int index) {
            super();
            _index = index;
        }

        @Override
        void switchTo(final WebDriver webDriver) {
            webDriver.switchTo().frame(_index);
        }

        @Override
        public String toString() {
            return String.valueOf(_index);
        }

    }

    /**
     * The Selenium frame or iframe, identified by name.
     *
     * @author Dmitry Shapovalov
     */
    static final class NamedSeleniumFrame extends SeleniumFrame {

        private final String _name;

        NamedSeleniumFrame(final String name) {
            super();
            _name = name;
        }

        @Override
        void switchTo(final WebDriver webDriver) {
            webDriver.switchTo().frame(_name);
        }

        @Override
        public String toString() {
            return _name;
        }

    }

}
