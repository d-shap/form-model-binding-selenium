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

import ru.d_shap.fm.formmodel.binding.html.HtmlBindedAttribute;

/**
 * The Selenium binded attribute implementation.
 *
 * @author Dmitry Shapovalov
 */
final class SeleniumBindedAttributeImpl implements SeleniumBindedAttribute {

    private final HtmlBindedAttribute _htmlBindedAttribute;

    SeleniumBindedAttributeImpl(final HtmlBindedAttribute htmlBindedAttribute) {
        super();
        _htmlBindedAttribute = htmlBindedAttribute;
    }

    @Override
    public String getName() {
        return _htmlBindedAttribute.getName();
    }

    @Override
    public String getValue() {
        return _htmlBindedAttribute.getValue();
    }

    @Override
    public String getAbsoluteValue() {
        return _htmlBindedAttribute.getAbsoluteValue();
    }

}
