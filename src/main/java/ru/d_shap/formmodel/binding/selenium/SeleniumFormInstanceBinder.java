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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ru.d_shap.formmodel.binding.FormInstanceBinder;
import ru.d_shap.formmodel.binding.html.HtmlBindedAttribute;
import ru.d_shap.formmodel.binding.html.HtmlBindedElement;
import ru.d_shap.formmodel.binding.html.HtmlBindedForm;
import ru.d_shap.formmodel.binding.html.HtmlBindingSource;
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

    private final Map<String, HtmlBindingSource> _htmlBindingSources;

    /**
     * Create new object.
     */
    public SeleniumFormInstanceBinder() {
        super();
        _htmlFormInstanceBinder = new HtmlFormInstanceBinder();
        _htmlBindingSources = new HashMap<>();
    }

    @Override
    public void preBind(final BindingSource bindingSource, final FormDefinition formDefinition) {
        _htmlFormInstanceBinder.preBind(bindingSource, formDefinition);
        _htmlBindingSources.clear();
    }

    @Override
    public void postBind(final BindingSource bindingSource, final FormDefinition formDefinition, final Document document) {
        _htmlFormInstanceBinder.postBind(bindingSource, formDefinition, document);
        _htmlBindingSources.clear();
    }

    @Override
    public BindedForm bindFormDefinition(final BindingSource bindingSource, final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final FormDefinition formDefinition) {
        WebDriver webDriver = ((SeleniumBindingSource) bindingSource).getWebDriver();
        SeleniumFrames seleniumFrames = getSeleniumFrames(lastBindedForm, formDefinition);
        HtmlBindingSource htmlBindingSource = getHtmlBindingSource(webDriver, seleniumFrames);
        BindedForm bindedForm = _htmlFormInstanceBinder.bindFormDefinition(htmlBindingSource, lastBindedForm, lastBindedElement, parentElement, formDefinition);
        if (bindedForm instanceof HtmlBindedForm) {
            return new SeleniumBindedFormImpl(webDriver, seleniumFrames, (HtmlBindedForm) bindedForm);
        } else {
            return null;
        }
    }

    private SeleniumFrames getSeleniumFrames(final BindedForm lastBindedForm, final FormDefinition formDefinition) {
        SeleniumFrames parentSeleniumFrames;
        if (lastBindedForm == null) {
            parentSeleniumFrames = null;
        } else {
            parentSeleniumFrames = ((SeleniumBindedForm) lastBindedForm).getSeleniumFrames();
        }
        String frame = formDefinition.getOtherAttributeValue("frame");
        // Проверка на абсолютное значение, разбиение на части
        return new SeleniumFrames(parentSeleniumFrames, frame);
    }

    private HtmlBindingSource getHtmlBindingSource(final WebDriver webDriver, final SeleniumFrames seleniumFrames) {
        String htmlBindingSourceKey = seleniumFrames.toString();
        HtmlBindingSource htmlBindingSource = _htmlBindingSources.get(htmlBindingSourceKey);
        if (htmlBindingSource == null) {
            seleniumFrames.switchTo(webDriver);
            String html = webDriver.getPageSource();
            String baseUrl = (String) ((JavascriptExecutor) webDriver).executeScript("return document.location.href");
            org.jsoup.nodes.Document document = Jsoup.parse(html, baseUrl);
            htmlBindingSource = new HtmlBindingSourceImpl(document);
            _htmlBindingSources.put(htmlBindingSourceKey, htmlBindingSource);
        }
        return htmlBindingSource;
    }

    @Override
    public List<BindedElement> bindElementDefinition(final BindingSource bindingSource, final BindedForm lastBindedForm, final BindedElement lastBindedElement, final Element parentElement, final ElementDefinition elementDefinition) {
        WebDriver webDriver = ((SeleniumBindingSource) bindingSource).getWebDriver();
        SeleniumFrames seleniumFrames = ((SeleniumBindedForm) lastBindedForm).getSeleniumFrames();
        List<BindedElement> bindedElements = _htmlFormInstanceBinder.bindElementDefinition(bindingSource, lastBindedForm, lastBindedElement, parentElement, elementDefinition);
        List<BindedElement> seleniumBindedElements = new ArrayList<>();
        if (bindedElements != null) {
            for (BindedElement bindedElement : bindedElements) {
                if (bindedElement instanceof HtmlBindedElement) {
                    SeleniumBindedElement seleniumBindedElement = new SeleniumBindedElementImpl(webDriver, seleniumFrames, (HtmlBindedElement) bindedElement);
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
