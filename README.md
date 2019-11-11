# A stackable SAX parser

## Problem
SAX parsers tend to contain many condition clauses in their `startElement`/`endElement` handlers when trying to parse a non-trivial example. 

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
