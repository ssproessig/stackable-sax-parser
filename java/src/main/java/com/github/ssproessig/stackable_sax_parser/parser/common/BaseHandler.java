package com.github.ssproessig.stackable_sax_parser.parser.common;

import org.xml.sax.helpers.DefaultHandler;

/** Base of any stackable SAX handler. Can be queried if it handles an element. */
public class BaseHandler extends DefaultHandler {
  protected final StackableContext context;
  protected final String rootElementTag;

  public BaseHandler(StackableContext aContext, String aRootElementTag) {
    context = aContext;
    rootElementTag = aRootElementTag;
  }

  public boolean handles(String localName) {
    return rootElementTag.equals(localName);
  }
}
