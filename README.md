# A stackable SAX parser

## Problem
SAX parsers tend to contain many condition clauses in their `startElement`/`endElement` handlers when trying to parse a non-trivial example.

This also makes them hard to maintain and extend. 

## Mission
Find a simple approach to parse non-trivial XML files, with

- possibility to split handlers into separate classes, e.g. one per XML element
- possibility to reuse parsers for XML elements that can occurs as children of many 

## Example
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
        <name language="en-US" name="name5"/>
        <name language="no" name="name7"/>
    </b-element>
</root-element>
```

basically we want

- one SAX parser bootstrapping
- three handler classes (for `a-element`, `b-element` and `name`)
- reuse the `name` handler 

## Usage
### Invocation
Example taken from `Application.java` 

```java
class Application {
  public static void main(String[] args) {
    val fileName = "example/example.xml";

    try {
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
