///////////////////////////////////////////////////////////////////////////////////////////////////
// Form model selenium binding is a form model implementation for Selenium.
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import ru.d_shap.formmodel.binding.FormBinder;
import ru.d_shap.formmodel.binding.FormBindingException;
import ru.d_shap.formmodel.definition.ElementDefinition;
import ru.d_shap.formmodel.definition.FormDefinition;
import ru.d_shap.formmodel.definition.FormDefinitions;
import ru.d_shap.formmodel.definition.FormReferenceDefinition;

/**
 * Selenium form binder.
 *
 * @author Dmitry Shapovalov
 */
public final class SeleniumFormBinder extends FormBinder<SeleniumForm, SeleniumElement, SeleniumFormReference, Element> {

    private final WebDriver _webDriver;

    private Document _document;

    /**
     * Create new object.
     *
     * @param formDefinitions form definitions.
     * @param webDriver       web driver.
     */
    public SeleniumFormBinder(final FormDefinitions formDefinitions, final WebDriver webDriver) {
        super(formDefinitions);
        _webDriver = webDriver;
    }

    @Override
    protected SeleniumForm createBindedFormInstance(final FormDefinition formDefinition) {
        createDom(formDefinition);
        return new SeleniumForm(formDefinition);
    }

    private void createDom(final FormDefinition formDefinition) {
        _webDriver.switchTo().defaultContent();
        String frame = formDefinition.getAdditionalAttribute("frame");
        if (frame != null) {
            String[] frameParts = frame.split("\\.");
            for (String framePart : frameParts) {
                try {
                    int frameIndex = Integer.parseInt(framePart);
                    _webDriver.switchTo().frame(frameIndex);
                } catch (NumberFormatException ex) {
                    _webDriver.switchTo().frame(framePart);
                }
            }
        }
        String html = _webDriver.getPageSource();
        _document = Jsoup.parse(html);
    }

    @Override
    protected List<Element> createBindingDataInstances(final SeleniumElement parentElement, final String lookup) {
        Elements elements;
        if (parentElement == null) {
            elements = _document.select(lookup);
        } else {
            Element initElement = parentElement.getBindingData();
            elements = initElement.select(lookup);
        }
        return elements;
    }

    @Override
    protected SeleniumElement createBindedElementInstance(final ElementDefinition elementDefinition, final Element bindingData) {
        return new SeleniumElement(_webDriver, elementDefinition, bindingData);
    }

    @Override
    protected SeleniumFormReference createBindedFormReferenceInstance(final FormReferenceDefinition formReferenceDefinition) {
        return new SeleniumFormReference(formReferenceDefinition);
    }

    /**
     * Bind current html with the form definition.
     *
     * @param formId the form ID.
     * @return Binded form.
     */
    public SeleniumForm bind(final String formId) {
        List<FormBindingException> holder = new ArrayList<>();
        try {
            WebDriverWait webDriverWait = new WebDriverWait(_webDriver, 5, 200);
            return webDriverWait.until(new ExpectedConditionImpl(formId, holder));
        } catch (TimeoutException ex) {
            if (holder.isEmpty()) {
                throw ex;
            }
        }
        throw holder.get(0);
    }

    /**
     * Expected condition for form loading.
     *
     * @author Dmitry Shapovalov
     */
    private final class ExpectedConditionImpl implements ExpectedCondition<SeleniumForm> {

        private final String _formId;

        private final List<FormBindingException> _holder;

        ExpectedConditionImpl(final String formId, final List<FormBindingException> holder) {
            super();
            _formId = formId;
            _holder = holder;
        }

        @Override
        public SeleniumForm apply(final WebDriver webDriver) {
            try {
                return doBind(_formId);
            } catch (FormBindingException ex) {
                _holder.clear();
                _holder.add(ex);
                return null;
            }
        }

    }

}
