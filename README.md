# Form model Selenium binding
Form model Selenium binding is a form model binding implementation for Selenium WebDriver.

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
Document document = formBinder.bind(webDriver, "login");

// Use the binded form to login
formBinder.getBindedElementWithId(document, "username").sendKeys("user");
formBinder.getBindedElementWithId(document, "userpass").sendKeys("password");
formBinder.getBindedElementWithId(document, "submit").click();

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

A selector is a chain of simple selectors, separated by combinators.
Selectors are case insensitive (including against elements, attributes, and attribute values).

## Selectors
|Pattern|Matches|Example|
|-------|-------|-------|
|`*`|any element|`*`|
|`tag`|elements with the given tag name|`div`|
|`*\|E`|elements of type *E* in any namespace *ns*|`*\|name` finds *&lt;fb:name&gt;* elements|
|`ns\|E`|elements of type *E* in the namespace *ns*|`fb\|name` finds *&lt;fb:name&gt;* elements|
|`#id`|elements with attribute ID of *id*|`div#wrap`, `#logo`|
|`.class`|elements with a class name of *class*|`div.left`, `.result`|
|`[attr]`|elements with an attribute named *attr* (with any value)|`a[href]`, `[title]`|
|`[^attrPrefix]`|elements with an attribute name starting with *attrPrefix*|`[^data-]`, `div[^data-]`|
|`[attr=val]`|elements with an attribute named *attr*, and value equal to *val*|`img[width=500]`, `a[rel=nofollow]`|
|`[attr="val"]`|elements with an attribute named *attr*, and value equal to *val*|`span[hello="Cleveland"][goodbye="Columbus"]`, `a[rel="nofollow"]`|
|`[attr^=valPrefix]`|elements with an attribute named *attr*, and value starting with *valPrefix*|`a[href^=http:]`|
|`[attr$=valSuffix]`|elements with an attribute named *attr*, and value ending with *valSuffix*|`img[src$=.png]`|
|`[attr*=valContaining]`|elements with an attribute named *attr*, and value containing *valContaining*|`a[href*=/search/]`|
|`[attr~=regex]`|elements with an attribute named *attr*, and value matching the regular expression|`mg[src~=(?i)\\.(png\|jpe?g)]`|
||the above may be combined in any order|`div.header[title]`|

## Combinators
|Pattern|Matches|Example|
|-------|-------|-------|
|`E F`|an *F* element descended from an *E* element|`div a`, `.logo h1`|
|`E > F`|an *F* direct child of *E*|`ol > li`|
|`E + F`|an *F* element immediately preceded by sibling *E*|`li + li`, `div.head + div`|
|`E ~ F`|an *F* element preceded by sibling *E*|`h1 ~ p`|
|`E, F, G`|all matching elements *E*, *F*, or *G*|`a[href], div, h3`|

## Pseudo selectors
|Pattern|Matches|Example|
|-------|-------|-------|
|`:lt(n)`|elements whose sibling index is less than `n`|`td:lt(3)` finds the first 3 cells of each row|
|`:gt(n)`|elements whose sibling index is greater than `n`|`td:gt(1)` finds cells after skipping the first two|
|`:eq(n)`|elements whose sibling index is equal to `n`|`td:eq(0)` finds the first cell of each row|
|`:has(selector)`|elements that contains at least one element matching the `selector`|`div:has(p)` finds *div* elements that contain *p* elements|
|`:not(selector)`|elements that do not match the selector|`div:not(.logo)` finds all *div* elements that do not have the *logo* class, `div:not(:has(div))` finds *div* elements that do not contain *div* elements|
|`:contains(text)`|elements that contains the specified text|`p:contains(jsoup)` finds *p* elements containing the text *jsoup*|
|`:matches(regex)`|elements whose text matches the specified regular expression|`td:matches(\\d+)` finds table cells containing digits, `div:matches((?i)login)` finds *div* elements containing the *login* text, case insensitively|
|`:containsOwn(text)`|elements that directly contain the specified text|`p:containsOwn(jsoup)` finds *p* elements with own text *jsoup*|
|`:matchesOwn(regex)`|elements whose own text matches the specified regular expression|`td:matchesOwn(\\d+)` finds table cells directly containing digits, `div:matchesOwn((?i)login)` finds *div* containing the *login* text, case insensitively|
|`:containsData(data)`|elements that contains the specified data (the contents of script and style elements, and comment nodes (etc) are considered data nodes, not text nodes)|`script:contains(jsoup)` finds *script* elements containing the data *jsoup*|
||the above may be combined in any order and with other selectors|`.light:contains(name):eq(0)`|
* The above indexed pseudo-selectors are 0-based, that is, the first element is at index 0, the second at 1, etc
* The above text pseudo-selectors are case insensitive

## Structural pseudo selectors
|Pattern|Matches|Example|
|-------|-------|-------|
|`:root`|the element that is the root of the document or the last selected element|`:root`|
|`:nth-child(an+b)`|elements that have `an+b-1` siblings before it in the document tree, for any positive integer or zero value of `n`, and has a parent element|`tr:nth-child(2n+1)` finds every odd row of a table, `:nth-child(10n-1)` the 9th, 19th, 29th, etc, element, `li:nth-child(5)` the 5th *li*|
|`:nth-last-child(an+b)`|elements that have `an+b-1` siblings after it in the document tree, for any positive integer or zero value of `n`, and has a parent element|`tr:nth-last-child(-n+2)` the last two rows of a table|
|`:nth-of-type(an+b)`|elements that have `an+b-1` siblings with the same expanded element name before it in the document tree, for any zero or positive integer value of `n`, and has a parent element|`img:nth-of-type(2n+1)`|
|`:nth-last-of-type(an+b)`|elements that have `an+b-1` siblings with the same expanded element name after it in the document tree, for any zero or positive integer value of `n`, and has a parent element|`img:nth-last-of-type(2n+1)`|
|`:first-child`|elements that are the first child of some other element|`div > p:first-child`|
|`:last-child`|elements that are the last child of some other element|`ol > li:last-child`|
|`:first-of-type`|elements that are the first sibling of its type in the list of children of its parent element|`dl dt:first-of-type`|
|`:last-of-type`|elements that are the last sibling of its type in the list of children of its parent element|`tr > td:last-of-type`|
|`:only-child`|elements that have a parent element and whose parent element hasve no other element children||
|`:only-of-type`|an element that has a parent element and whose parent element has no other element children with the same expanded element name||
|`:empty`|elements that have no children at all||

# Web-scraping
Form model Selenium binding also can be used to extract data from the source HTML pages.
But it is rather slow because additional processes are created (Internet Browser process, Selenium WebDriver process) and additional communication between this processes is needed.
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
List<SeleniumBindedElement> bindedElements = formBinder.getBindedElements(elements);
for (SeleniumBindedElement bindedElement: bindedElements) {
    bindedElement.getText();
}

// Quit WebDriver
webDriver.quit();
```

If the source HTML page does not use AJAX-requests or JavaScript DOM manipulations, then form model HTML binding can be used to extract data from the source HTML page.
```
// Load form definitions
FormDefinitions formDefinitions = new FormDefinitions();
File file = new File("file with the form definition");
FormDefinitionsLoader formDefinitionsLoader = new FormXmlDefinitionsFileLoader(file);
formDefinitionsLoader.load(formDefinitions);
HtmlFormBinder formBinder = new HtmlFormBinder(formDefinitions);

// Bind the HTML
String url = "some url";
Document document = formBinder.bindUrl(url, "p-extractor");

// Get the binded elements and text
List<Element> elements = formBinder.getElementsWithId(document, "p-element");
List<HtmlBindedElement> bindedElements = formBinder.getBindedElements(elements);
for (HtmlBindedElement bindedElement: bindedElements) {
    bindedElement.getText();
}
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

# HOW TO
[HOW TO examples](HOWTO.md)

# Latest release
Form model library:
* **&lt;groupId&gt;**: ru.d-shap
* **&lt;artifactId&gt;**: form-model
* **&lt;version&gt;**: 1.0

Form model Selenium binding:
* **&lt;groupId&gt;**: ru.d-shap
* **&lt;artifactId&gt;**: form-model-binding-selenium
* **&lt;version&gt;**: 1.0.0

# Donation
If you find my code useful, you can [bye me a coffee](https://www.paypal.me/dshapovalov)
