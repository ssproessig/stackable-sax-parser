package com.github.ssproessig.stackable_sax_parser.parser;

import java.io.IOException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.xerces.parsers.AbstractSAXParser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@UtilityClass
@Slf4j
public class ExampleParser {
  private AbstractSAXParser getSecureSAXParser() throws SAXException {
    // create a parser that disables XML entity processing
    // basically try to work around OWASP Top 10-2017 A4-XML External Entities (XXE) with that
    val saxParser = new org.apache.xerces.parsers.SAXParser();

    // disallow DOCTYPE declaration
    saxParser.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    // do not include external general entities
    saxParser.setFeature("http://xml.org/sax/features/external-general-entities", false);
    // do not include external parameter entities or the external DTD subset
    saxParser.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
    // do not include external DTDs
    saxParser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

    return saxParser;
  }

  public static Context parse(String aFileName) throws SAXException, IOException {
    val context = new Context();
    val saxParser = getSecureSAXParser();
    saxParser.setContentHandler(new RootHandler(context));

    saxParser.parse(new InputSource(aFileName));

    return context;
  }
}
