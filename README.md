# A secure, schema-aware and stackable SAX parser
![](https://github.com/ssproessig/stackable-sax-parser/workflows/Java%20CI/badge.svg)


## Mission
Find a simple approach to parse non-trivial XML files, with

- [x] possibility to split handlers into separate classes, e.g. one per XML element
- [x] possibility to reuse parsers for XML elements that can occur as children of other elements multiple times
- [ ] make the parser XSD-aware, so that potential errors in the XML are already handled before parsing
- [ ] ensure that injections can not happen via strings parsed from XML 

## Problems in Detail

### Stackable SAX parser
SAX parsers tend to contain many condition clauses in their `startElement`/`endElement` handlers when trying to parse a non-trivial example.

This also makes them hard to maintain and extend. Hence we want to

- split the parser into different SAX handlers and
- want to reuse handlers if an XML element is a child in many other elements 

### Schema-Aware
Ensure that parsing the XML file can validate against given XSDs.

As complex XML dialects may come with several XSDs, make it possible to pass an XML catalog into the parser for lookup and validation.

### Secure
Parsing data via XML into a system may lead to several security problems, e.g.

- [OWASP Top 10-2017](https://www.owasp.org/index.php/Top_10-2017_Top_10): A4:2017-XML External Entities (XXE)
- OWASP Top 10-2017: A7:2017-Cross-Site Scripting (XSS)

## Complete Example
taken from [example/example.xml](example/example.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<root-element xmlns="urn:example-v1" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="urn:example-v1" version="1">
    <a-element a-attribute-1="enum1">
        <name language="en-US" name="name1"/>
    </a-element>
    <b-element b-attribute-1="2">
        <name language="en-US" name="name5"/>
        <name language="de" name="name7"/>
    </b-element>
    <b-element b-attribute-1="2">
        <name language="en-US" name="<script>alert('XSS is possible');</script>"/>
        <name language="no">name7</name>
    </b-element>
</root-element>
```

basically we want

- one SAX parser bootstrapping
- three handler classes (for `a-element`, `b-element` and `name`)
- reuse the `name` handler
- ensure that no XML External Entity processing happened
- the XSS attempt in the 2nd `<b-element/>` was filtered and reported

## Usage
### Invocation
Example taken from `Application.java` 

```java
class Application {
  public static void main(String[] args) {
    val fileName = "example/example.xml";

    try {
      // TODO: prepare XML catalog and use it
      // TODO: report validation errors OR injection attempts
      val context = new Context();
      StackableParser.parse(fileName, RootElementHandler.class, context);
      log.info("XML parsed: {}", context);
    } catch (SAXException e) {
      log.error("Error parsing XML: {}", ExceptionUtils.getStackTrace(e));
    } catch (IOException e) {
      log.error("Error handling '{}': {}", fileName, ExceptionUtils.getStackTrace(e));
    }
  }
}
```

basically
- create _your context_ with the fields you need, extending `StackableContext`
- define a _root element handler_, extending `BaseHandler`
- invoke `StackableParser.parse(<fileName>, <rootElementHandler.class>, <contextInstance>)`

In your _root element handler_
- parse the attributes of the root element AND
- define and instantiate your nested stackable handler - and `context.pushHandler()` it onto the stack
- make your handlers make use of the context (e.g. storing information OR using an object - hello, in-memory TinkerGraph! - from it)
