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

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

/**
 * The set of Selenium frames or iframes.
 *
 * @author Dmitry Shapovalov
 */
final class SeleniumFrames {

    private final List<SeleniumFrame> _seleniumFrames;

    SeleniumFrames() {
        super();
        _seleniumFrames = new ArrayList<>();
    }

    SeleniumFrames(final String frame) {
        super();
        _seleniumFrames = new ArrayList<>();
        addSeleniumFrame(frame);
    }

    SeleniumFrames(final SeleniumFrames seleniumFrames, final String frame) {
        super();
        _seleniumFrames = new ArrayList<>(seleniumFrames._seleniumFrames);
        addSeleniumFrame(frame);
    }

    private void addSeleniumFrame(final String frame) {
        if (frame != null) {
            try {
                int index = Integer.parseInt(frame);
                _seleniumFrames.add(new SeleniumFrame.IndexedSeleniumFrame(index));
            } catch (NumberFormatException ex) {
                _seleniumFrames.add(new SeleniumFrame.NamedSeleniumFrame(frame));
            }
        }
    }

    void switchTo(final WebDriver webDriver) {
        webDriver.switchTo().defaultContent();
        for (SeleniumFrame seleniumFrame : _seleniumFrames) {
            seleniumFrame.switchTo(webDriver);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append('/');
        boolean first = true;
        for (SeleniumFrame seleniumFrame : _seleniumFrames) {
            if (first) {
                first = false;
            } else {
                result.append('/');
            }
            result.append(seleniumFrame);
        }
        return result.toString();
    }

}
