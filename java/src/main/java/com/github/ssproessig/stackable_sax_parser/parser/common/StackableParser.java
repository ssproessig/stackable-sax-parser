package com.github.ssproessig.stackable_sax_parser.parser.common;

import com.github.ssproessig.stackable_sax_parser.parser.Context;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.xerces.parsers.AbstractSAXParser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@UtilityClass
@Slf4j
public class StackableParser {
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

  public static void parse(
      String aFileName, Class<? extends BaseHandler> aRootHandlerClass, Context aContext)
      throws SAXException, IOException {

    BaseHandler rootHandler;

    try {
      rootHandler = aRootHandlerClass.getConstructor(Context.class).newInstance(aContext);
    } catch (InstantiationException
        | IllegalAccessException
        | InvocationTargetException
        | NoSuchMethodException e) {
      throw new SAXException("Unable to create passed root element handler: " + aRootHandlerClass);
    }

    val saxParser = getSecureSAXParser();
    saxParser.setContentHandler(new StackableSaxHandler(aContext, rootHandler));
    saxParser.parse(new InputSource(aFileName));
  }
}
