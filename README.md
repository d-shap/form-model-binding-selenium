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

# XML definition
Namespace: ```http://d-shap.ru/schema/form-model/1.0```

## form
The top-level element. Defines the form.

Attributes:
* ```group``` - the form's group, optional
* ```id``` - the form's ID, mandatory

Attributes ```group``` and ```id``` identify the form and should be unique.

Child elements:
* ```element```
* ```single-element```
* ```form-reference```

## element
Element is a form part, that make sence for the application.

Attributes:
* ```id``` - the element's ID, optional
* ```lookup``` - the element's lookup string, used by the binding extension, mandatory
* ```type``` - the element's type, optional

Valid values for the ```type``` attribute depend on the parent element.
Possible values are:
* ```required``` - there should be exactly one element.
* ```required+``` - there should be at least one element.
* ```optional``` - there could be one element or no element at all.
* ```optional+``` - there could be more then one element or no element at all.
* ```prohibited``` - there should not be any element.

Child elements:
* ```attribute```
* ```element```
* ```single-element```
* ```form-reference```

## attribute
The element's attribute.

Attributes:
* ```id``` - the attribute's ID, optional
* ```lookup``` - the attribute's lookup string, used by the binding extension, mandatory
* ```type``` - the attribute's type, optional

Valid values for the ```type``` attribute depend on the parent element.
Possible values are:
* ```required``` - there should be exactly one element.
* ```optional``` - there could be one element or no element at all.
* ```prohibited``` - there should not be any element.

## single-element
Single element is a container for other elements.
Only one child element should present (but child element could be ```optional+```).

Attributes:
* ```id``` - the single element's ID, optional
* ```type``` - the single element's type, optional

Valid values for the ```type``` attribute depend on the parent element.
Possible values are:
* ```required``` - there should be exactly one element.
* ```optional``` - there could be one element or no element at all.
* ```prohibited``` - there should not be any element.

Child elements:
* ```element```
* ```single-element```

## form-reference
The reference to another form definition.
The elements of the referenced form are included in the current form as child elements of the ```form-reference``` element.

Attributes:
* ```group``` - the form's group, optional
* ```id``` - the form's ID, mandatory

# JSoup selectors
JSoup selectors are used in lookup attributes of the form definition.

[https://jsoup.org/cookbook/extracting-data/selector-syntax](https://jsoup.org/cookbook/extracting-data/selector-syntax)

## Selector overview
* `tagname`: find elements by tag, e.g. `a`
* `ns|tag`: find elements by tag in a namespace, e.g. `fb|name` finds `<fb:name>` elements
* `#id`: find elements by ID, e.g. `#logo`
* `.class`: find elements by class name, e.g. `.masthead`
* `[attribute]`: elements with attribute, e.g. `[href]`
* `[^attr]`: elements with an attribute name prefix, e.g. `[^data-]` finds elements with HTML5 dataset attributes
* `[attr=value]`: elements with attribute value, e.g. `[width=500]` (also quotable, like `[data-name='launch sequence']`)
* `[attr^=value]`, `[attr$=value]`, `[attr*=value]`: elements with attributes that start with, end with, or contain the value, e.g. `[href*=/path/]`
* `[attr~=regex]`: elements with attribute values that match the regular expression; e.g. `img[src~=(?i)\.(png|jpe?g)]`
* `*`: all elements, e.g. `*`

## Selector combinations
* `el#id`: elements with ID, e.g. `div#logo`
* `el.class`: elements with class, e.g. `div.masthead`
* `el[attr]`: elements with attribute, e.g. `a[href]`
* Any combination, e.g. `a[href].highlight`
* `ancestor child`: child elements that descend from ancestor, e.g. `.body p` finds `p` elements anywhere under a block with class `body`
* `parent > child`: child elements that descend directly from parent, e.g. `div.content > p` finds `p` elements; and `body > *` finds the direct children of the `body` tag
* `siblingA + siblingB`: finds sibling B element immediately preceded by sibling A, e.g. `div.head + div`
* `siblingA ~ siblingX`: finds sibling X element preceded by sibling A, e.g. `h1 ~ p`
* `el, el, el`: group multiple selectors, find unique elements that match any of the selectors; e.g. `div.masthead, div.logo`

## Pseudo selectors
* `:lt(n)`: find elements whose sibling index (i.e. its position in the DOM tree relative to its parent) is less than `n`; e.g. `td:lt(3)`
* `:gt(n)`: find elements whose sibling index is greater than `n`; e.g. `div p:gt(2)`
* `:eq(n)`: find elements whose sibling index is equal to `n`; e.g. form `input:eq(1)`
* `:has(selector)`: find elements that contain elements matching the selector; e.g. `div:has(p)`
* `:not(selector)`: find elements that do not match the selector; e.g. `div:not(.logo)`
* `:contains(text)`: find elements that contain the given text. The search is case-insensitive; e.g. `p:contains(jsoup)`
* `:containsOwn(text)`: find elements that directly contain the given text
* `:matches(regex)`: find elements whose text matches the specified regular expression; e.g. `div:matches((?i)login)`
* `:matchesOwn(regex)`: find elements whose own text matches the specified regular expression
* Note that the above indexed pseudo-selectors are 0-based, that is, the first element is at index 0, the second at 1, etc

# Web-scraping
Form model selenium binding also can be used to extract data from the source HTML pages.
But it is rather slow tool because additional processes are created (Internet Browser process, Selenium WebDriver process) and additional communication between this processes is needed.
```
// Load form definitions
FormDefinitions formDefinitions = new FormDefinitions();
File file = new File("file with the form definition");
FormDefinitionsLoader formDefinitionsLoader = new FormXmlDefinitionsFileLoader(file);
formDefinitionsLoader.load(formDefinitions);
SeleniumFormBinder formBinder = new SeleniumFormBinder(formDefinitions);

// Create WebDriver
System.setProperty("webdriver.chrome.driver", "path/to/chrome/webdriver");
ChromeOptions chromeOptions = new ChromeOptions();
chromeOptions.setHeadless(true);
WebDriver webDriver = new ChromeDriver(chromeOptions);

// Bind the HTML
String url = "some url";
webDriver.get(url);
Document document = formBinder.bind(webDriver, "p-extractor");

// Get the binded elements and text
List<Element> elements = formBinder.getElementsWithId(document, "p-element");
List<HtmlBindedElement> bindedElements = formBinder.getBindedElements(elements);
for (HtmlBindedElement bindedElement: bindedElements) {
    bindedElement.getText();
}

// Quit WebDriver
webDriver.quit();
```

# Frames and child windows
Sometimes frames and child windows are used.
In this case every frame and every child window should be bounded separately.
```
WebDriver webDriver = ...
webDriver.get(url);
webDriver.switchTo().frame("nameOrId");

Document document = formBinder.bind(webDriver, "form-id");

webDriver.quit();
```

# Latest release
* **&lt;groupId&gt;**: ru.d-shap
* **&lt;artifactId&gt;**: form-model-binding-selenium
* **&lt;version&gt;**: 1.0.0

# Donation
If you find my code useful, you can [bye me a coffee](https://www.paypal.me/dshapovalov)
