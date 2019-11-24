package com.github.ssproessig.stackable_sax_parser.parser.common;

import com.github.ssproessig.stackable_sax_parser.parser.Context;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.xerces.parsers.AbstractSAXParser;
import org.apache.xerces.util.XMLCatalogResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

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

  private static void setXSDValidation(AbstractSAXParser saxParser)
      throws SAXNotRecognizedException, SAXNotSupportedException {
    saxParser.setFeature("http://xml.org/sax/features/validation", true);
    saxParser.setFeature("http://xml.org/sax/features/namespaces", true);
    saxParser.setFeature("http://apache.org/xml/features/validation/schema", true);
    saxParser.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
  }

  private static void setXMLCatalogResolver(AbstractSAXParser saxParser, String[] catalogs)
      throws SAXNotRecognizedException, SAXNotSupportedException {
    val resolver = new XMLCatalogResolver(catalogs, true);
    saxParser.setProperty("http://apache.org/xml/properties/internal/entity-resolver", resolver);
  }

  public static void parse(
      String aFileName,
      String anXmlCatalog,
      Class<? extends BaseHandler<? extends StackableContext>> aRootHandlerClass,
      Context aContext)
      throws SAXException, IOException {

    BaseHandler<? extends StackableContext> rootHandler;

    try {
      rootHandler = aRootHandlerClass.getConstructor(Context.class).newInstance(aContext);
    } catch (InstantiationException
        | IllegalAccessException
        | InvocationTargetException
        | NoSuchMethodException e) {
      throw new SAXException("Unable to create passed root element handler: " + aRootHandlerClass);
    }

    val saxParser = getSecureSAXParser();
    setXMLCatalogResolver(saxParser, new String[] {anXmlCatalog});
    setXSDValidation(saxParser);
    saxParser.setContentHandler(new StackableSaxHandler(aContext, rootHandler));
    saxParser.setErrorHandler(new ErrorCollector());

    saxParser.parse(new InputSource(aFileName));
  }
}
