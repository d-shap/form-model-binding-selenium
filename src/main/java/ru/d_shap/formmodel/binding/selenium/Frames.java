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
 * The set of HTML frames or iframes.
 *
 * @author Dmitry Shapovalov
 */
final class Frames {

    private final WebDriver _webDriver;

    private final List<Frame> _frames;

    Frames(final WebDriver webDriver, final String frame) {
        super();
        _webDriver = webDriver;
        _frames = new ArrayList<>();
        addFrame(frame);
    }

    Frames(final Frames frames, final String frame) {
        super();
        _webDriver = frames._webDriver;
        _frames = new ArrayList<>(frames._frames);
        addFrame(frame);
    }

    private void addFrame(final String frame) {
        if (frame != null) {
            try {
                int index = Integer.parseInt(frame);
                _frames.add(new Frame.IndexedFrame(_webDriver, index));
            } catch (NumberFormatException ex) {
                _frames.add(new Frame.NamedFrame(_webDriver, frame));
            }
        }
    }

    void switchTo() {
        _webDriver.switchTo().defaultContent();
        for (Frame frame : _frames) {
            frame.switchTo();
        }
    }

}
