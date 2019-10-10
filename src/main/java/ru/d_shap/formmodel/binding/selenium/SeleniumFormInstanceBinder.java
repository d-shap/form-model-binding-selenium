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
package ru.d_shap.formmodel.binding.selenium;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ru.d_shap.formmodel.binding.FormInstanceBinder;
import ru.d_shap.formmodel.binding.html.HtmlBindedAttribute;
import ru.d_shap.formmodel.binding.html.HtmlBindedElement;
import ru.d_shap.formmodel.binding.html.HtmlBindedForm;
import ru.d_shap.formmodel.binding.html.HtmlFormInstanceBinder;
import ru.d_shap.formmodel.binding.model.BindedAttribute;
import ru.d_shap.formmodel.binding.model.BindedElement;
import ru.d_shap.formmodel.binding.model.BindedForm;
import ru.d_shap.formmodel.binding.model.BindingSource;
import ru.d_shap.formmodel.definition.model.AttributeDefinition;
import ru.d_shap.formmodel.definition.model.ElementDefinition;
import ru.d_shap.formmodel.definition.model.FormDefinition;

/**
 * Form instance binder Selenium implementation.
 *
 * @author Dmitry Shapovalov
 */
public final class SeleniumFormInstanceBinder implements FormInstanceBinder {

    private final HtmlFormInstanceBinder _htmlFormInstanceBinder;

    /**
     * Create new object.
     */
    public SeleniumFormInstanceBinder() {
        super();
        _htmlFormInstanceBinder = new HtmlFormInstanceBinder();
    }

    @Override
    public void preBind(final BindingSource bindingSource, final FormDefinition formDefinition) {
        _htmlFormInstanceBinder.preBind(bindingSource, formDefinition);
    }

    @Override
    public void postBind(final BindingSource bindingSource, final FormDefinition formDefinition, final Document document) {
        _htmlFormInstanceBinder.postBind(bindingSource, formDefinition, document);
    }

    @Override
    public BindedForm bindFormDefinition(final BindingSource bindingSource, final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final FormDefinition formDefinition) {
        BindedForm bindedForm = _htmlFormInstanceBinder.bindFormDefinition(bindingSource, lastBindedForm, lastBindedElement, parentElement, formDefinition);
        if (bindedForm instanceof HtmlBindedForm) {
            return new SeleniumBindedFormImpl(((SeleniumBindingSource) bindingSource).getWebDriver(), (HtmlBindedForm) bindedForm);
        } else {
            return null;
        }
    }

    @Override
    public List<BindedElement> bindElementDefinition(final BindingSource bindingSource, final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final ElementDefinition elementDefinition) {
        List<BindedElement> bindedElements = _htmlFormInstanceBinder.bindElementDefinition(bindingSource, lastBindedForm, lastBindedElement, parentElement, elementDefinition);
        List<BindedElement> seleniumBindedElements = new ArrayList<>();
        if (bindedElements != null) {
            for (BindedElement bindedElement : bindedElements) {
                if (bindedElement instanceof HtmlBindedElement) {
                    SeleniumBindedElement seleniumBindedElement = new SeleniumBindedElementImpl(((SeleniumBindingSource) bindingSource).getWebDriver(), (HtmlBindedElement) bindedElement);
                    seleniumBindedElements.add(seleniumBindedElement);
                }
            }
        }
        return seleniumBindedElements;
    }

    @Override
    public BindedAttribute bindAttributeDefinition(final BindingSource bindingSource, final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final AttributeDefinition attributeDefinition) {
        BindedAttribute bindedAttribute = _htmlFormInstanceBinder.bindAttributeDefinition(bindingSource, lastBindedForm, lastBindedElement, parentElement, attributeDefinition);
        if (bindedAttribute instanceof HtmlBindedAttribute) {
            return new SeleniumBindedAttributeImpl((HtmlBindedAttribute) bindedAttribute);
        } else {
            return null;
        }
    }

}
