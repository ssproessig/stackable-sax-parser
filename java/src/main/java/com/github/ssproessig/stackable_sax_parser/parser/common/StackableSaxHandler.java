package com.github.ssproessig.stackable_sax_parser.parser.common;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class StackableSaxHandler extends BaseHandler {

  /**
   * Initializes the stackable SAX parser.
   *
   * <p>Basically forwards every supported SAX event to the top-most matching handler from the
   * stack.
   *
   * @param aContext shared context that contains the handler stack
   * @param aHandler root element handler to start with
   */
  public StackableSaxHandler(StackableContext aContext, BaseHandler aHandler) {
    super(aContext, null);
    aContext.pushHandler(aHandler);
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {
    context.handlerFor(localName).startElement(uri, localName, qName, attributes);
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    context.handlerFor(localName).endElement(uri, localName, qName);
  }
}
