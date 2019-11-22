package com.github.ssproessig.stackable_sax_parser.parser.common;

import org.xml.sax.helpers.DefaultHandler;

/** Base of any stackable SAX handler. Can be queried if it handles an element. */
public class BaseHandler<C> extends DefaultHandler {
  protected final C context;
  protected final String xmlNamespace;
  protected final String rootElementTag;

  public BaseHandler(C aContext, String anXmlNamespace, String aRootElementTag) {
    context = aContext;
    xmlNamespace = anXmlNamespace;
    rootElementTag = aRootElementTag;
  }

  public boolean handles(String uri, String localName) {
    return xmlNamespace.equals(uri) && rootElementTag.equals(localName);
  }
}
