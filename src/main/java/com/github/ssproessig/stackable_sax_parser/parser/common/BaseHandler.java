package com.github.ssproessig.stackable_sax_parser.parser.common;

import org.xml.sax.helpers.DefaultHandler;

/** Base of any stackable SAX handler. Can be queried if it handles an element. */
public class BaseHandler<C> extends DefaultHandler {
  protected final C context;
  protected final String rootElementTag;

  public BaseHandler(C aContext, String aRootElementTag) {
    context = aContext;
    rootElementTag = aRootElementTag;
  }

  public boolean handles(String localName) {
    return rootElementTag.equals(localName);
  }
}
