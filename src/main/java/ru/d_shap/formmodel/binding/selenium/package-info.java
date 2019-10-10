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
/**
 * <p>
 * Form model Selenium binding is a form model binding implementation for Selenium WebDriver.
 * </p>
 * <p>
 * This is the Page Object Design Pattern implementation. Page Object is a Design Pattern which has
 * become popular in test automation for enhancing test maintenance and reducing code duplication.
 * A Page Object serves as an interface to the source page of the application. The application uses the
 * Page Object whenever it needs to interact with the UI of that source page. The benefit is that if the
 * UI changes for the source page, the application themselves don’t need to change, only the Page Object
 * needs to change. Subsequently all changes to support that new UI are located in one place.
 * </p>
 * <p>
 * But, unlike the Page Object Design Pattern, form model is not an object-oriented class. It is the XML
 * definition of the page elements. This definition is binded with the source page and the result of
 * this binding is the binded form. Binded elements can be obtained from this binded form and the
 * application uses this binded elements to interact with the UI of the source page.
 * </p>
 * <p>
 * For example, suppose the following source page:
 * </p>
 * <pre>
 * &lt;html&gt;
 *     &lt;head&gt;
 *         &lt;title&gt;Login page&lt;/title&gt;
 *     &lt;/head&gt;
 *     &lt;body&gt;
 *         &lt;form action="http://example.com"&gt;
 *             &lt;div&gt;
 *                 &lt;span&gt;User name:&lt;/span&gt;
 *                 &lt;input name="username" type="text"/&gt;
 *             &lt;/div&gt;
 *             &lt;div&gt;
 *                 &lt;span&gt;Password:&lt;/span&gt;
 *                 &lt;input name="password" type="password"/&gt;
 *             &lt;/div&gt;
 *             &lt;div&gt;
 *                 &lt;input type="submit" value="Login"/&gt;
 *             &lt;/div&gt;
 *         &lt;/form&gt;
 *     &lt;/body&gt;
 * &lt;/html&gt;
 * </pre>
 * <p>
 * This is a simple login form with inputs for user name and password and submit button.
 * </p>
 * <p>
 * The model for this source page is the following:
 * </p>
 * <pre>
 * &lt;?xml version="1.0"?&gt;
 * &lt;ns1:form id="login" xmlns:ns1="http://d-shap.ru/schema/form-model/1.0"&gt;
 *     &lt;ns1:element id="username" lookup="input[name='username']"/&gt;
 *     &lt;ns1:element id="userpass" lookup="input[name='password']"/&gt;
 *     &lt;ns1:element id="submit" lookup="input[type='submit']"/&gt;
 * &lt;/ns1:form&gt;
 * </pre>
 * <p>
 * The following code implements the binding and interaction with the binded form:
 * </p>
 * <pre>{@code
 * // Load form definitions
 * FormDefinitions formDefinitions = new FormDefinitions();
 * File file = new File("file with the form definition");
 * FormDefinitionsLoader formDefinitionsLoader = new FormXmlDefinitionsFileLoader(file);
 * formDefinitionsLoader.load(formDefinitions);
 * SeleniumFormBinder formBinder = new SeleniumFormBinder(formDefinitions);
 *
 * // Create WebDriver
 * WebDriver webDriver = ...
 *
 * // Bind the source page
 * String url = ...
 * webDriver.get(url);
 * Document document = formBinder.bind(webDriver, "login");
 *
 * // Use the binded form to login
 * formBinder.getBindedElementWithId(document, "username").sendKeys("user");
 * formBinder.getBindedElementWithId(document, "userpass").sendKeys("password");
 * formBinder.getBindedElementWithId(document, "submit").click();
 *
 * // Quit WebDriver
 * webDriver.quit();
 * }</pre>
 */
package ru.d_shap.formmodel.binding.selenium;
