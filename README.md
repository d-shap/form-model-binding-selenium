# Form model selenium binding
Form model selenium binding is a form model binding implementation for Selenium.

This is the Page Object Design Pattern implementation.
Page Object is a Design Pattern which has become popular in test automation for enhancing test maintenance and reducing code duplication.
A Page Object serves as an interface to the source page of the application.
The application uses the Page Object whenever it needs to interact with the UI of that source page.
The benefit is that if the UI changes for the source page, the application themselves don’t need to change, only the Page Object needs to change.
Subsequently all changes to support that new UI are located in one place.

But, unlike the Page Object Design Pattern, form model is not an object-oriented class.
It is the XML definition of the page elements.
This definition is binded with the source page and the result of this binding is the binded form.
Binded elements can be obtained from this binded form and the application uses this binded elements to interact with the UI of the source page.

For example, suppose the following source page:
```
<html>
    <head>
        <title>Login page</title>
    </head>
    <body>
        <form action="http://example.com">
            <div>
                <span>User name:</span>
                <input name="username" type="text"/>
            </div>
            <div>
                <span>Password:</span>
                <input name="password" type="password"/>
            </div>
            <div>
                <input type="submit" value="Login"/>
            </div>
        </form>
    </body>
</html>
```

This is a simple login form with inputs for user name and password and submit button.

The model for this source page is the following:
```
<?xml version="1.0"?>
<ns1:form id="login" xmlns:ns1="http://d-shap.ru/schema/form-model/1.0">
    <ns1:element id="username" lookup="input[name='username']"/>
    <ns1:element id="userpass" lookup="input[name='password']"/>
    <ns1:element id="submit" lookup="input[type='submit']"/>
</ns1:form>
```

The following code implements the binding and interaction with the binded form:
```
// Load form definitions
FormDefinitions formDefinitions = new FormDefinitions();
File file = new File("file with the form definition");
FormDefinitionsLoader formDefinitionsLoader = new FormXmlDefinitionsFileLoader(file);
formDefinitionsLoader.load(formDefinitions);
SeleniumFormBinder formBinder = new SeleniumFormBinder(formDefinitions);

// Create WebDriver
WebDriver webDriver = ...

// Bind the source page
String url = ...
webDriver.get(url);
Document document = seleniumFormBinder.bind(webDriver, "login");

// Use the binded form to login
SeleniumBindedElement bindedElement;
bindedElement = formBinder.getBindedElementWithId(document, "username");
bindedElement.sendKeys("user");
bindedElement = formBinder.getBindedElementWithId(document, "userpass");
bindedElement.sendKeys("password");
bindedElement = formBinder.getBindedElementWithId(document, "submit");
bindedElement.click();

// Quit WebDriver
webDriver.quit();
```

# Latest release
* **&lt;groupId&gt;**: ru.d-shap
* **&lt;artifactId&gt;**: form-model-binding-selenium
* **&lt;version&gt;**: -.-.-

# Donation
If you find my code useful, you can [bye me a coffee](https://www.paypal.me/dshapovalov)
