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

import ru.d_shap.formmodel.binding.BindedForm;
import ru.d_shap.formmodel.definition.FormDefinition;

/**
 * Selenium form.
 *
 * @author Dmitry Shapovalov
 */
public final class SeleniumForm extends BindedForm<SeleniumElement, SeleniumFormReference, Element> {

    SeleniumForm(final FormDefinition formDefinition) {
        super(formDefinition);
    }

}
