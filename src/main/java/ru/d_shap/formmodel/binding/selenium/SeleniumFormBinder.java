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

/**
 * Selenium form binder.
 *
 * @author Dmitry Shapovalov
 */
public final class SeleniumFormBinder {

    /**
     * Create new object.
     */
    public SeleniumFormBinder() {
        super();
    }

    //    private void createDom(final FormDefinition formDefinition) {
    //        _webDriver.switchTo().defaultContent();
    //        String frame = formDefinition.getAdditionalAttribute("frame");
    //        if (frame != null) {
    //            String[] frameParts = frame.split("\\.");
    //            for (String framePart : frameParts) {
    //                try {
    //                    int frameIndex = Integer.parseInt(framePart);
    //                    _webDriver.switchTo().frame(frameIndex);
    //                } catch (NumberFormatException ex) {
    //                    _webDriver.switchTo().frame(framePart);
    //                }
    //            }
    //        }
    //        String html = _webDriver.getPageSource();
    //        _document = Jsoup.parse(html);
    //    }

    //    public SeleniumForm bind(final String formId) {
    //        List<FormBindingException> holder = new ArrayList<>();
    //        try {
    //            WebDriverWait webDriverWait = new WebDriverWait(_webDriver, 5, 200);
    //            return webDriverWait.until(new ExpectedConditionImpl(formId, holder));
    //        } catch (TimeoutException ex) {
    //            if (holder.isEmpty()) {
    //                throw ex;
    //            }
    //        }
    //        throw holder.get(0);
    //    }

    //    private final class ExpectedConditionImpl implements ExpectedCondition<SeleniumForm> {
    //
    //        private final String _formId;
    //
    //        private final List<FormBindingException> _holder;
    //
    //        ExpectedConditionImpl(final String formId, final List<FormBindingException> holder) {
    //            super();
    //            _formId = formId;
    //            _holder = holder;
    //        }
    //
    //        @Override
    //        public SeleniumForm apply(final WebDriver webDriver) {
    //            try {
    //                return doBind(_formId);
    //            } catch (FormBindingException ex) {
    //                _holder.clear();
    //                _holder.add(ex);
    //                return null;
    //            }
    //        }
    //
    //    }

}
