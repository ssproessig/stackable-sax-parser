package com.github.ssproessig.stackable_sax_parser.parser.example;

import com.github.ssproessig.stackable_sax_parser.parser.Context;
import com.github.ssproessig.stackable_sax_parser.parser.common.BaseHandler;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.Attributes;

@Slf4j
public class AElementHandler extends BaseHandler<Context> {

  public AElementHandler(Context aContext) {
    super(aContext, XmlNamespaces.EXAMPLE, "a-element");
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    log.info("startElement: {}", qName);

    context.logEvent(
        "new 'a-element' seen with a-attribute-1=" + attributes.getValue("a-attribute-1"));

    context.pushHandler(new NameElementHandler(context));
  }

  @Override
  public void endElement(String uri, String localName, String qName) {
    log.info("endElementElement: {}", qName);
  }
}
