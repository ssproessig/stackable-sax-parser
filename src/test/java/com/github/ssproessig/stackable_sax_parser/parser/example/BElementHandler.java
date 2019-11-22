package com.github.ssproessig.stackable_sax_parser.parser.example;

import com.github.ssproessig.stackable_sax_parser.parser.Context;
import com.github.ssproessig.stackable_sax_parser.parser.common.BaseHandler;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.Attributes;

@Slf4j
public class BElementHandler extends BaseHandler<Context> {

  public BElementHandler(Context aContext) {
    super(aContext, "b-element");
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    log.info("startElement: {}", qName);

    context.logEvent(
        "new 'b-element' seen with b-attribute-1=" + attributes.getValue("b-attribute-1"));

    context.pushHandler(new NameElementHandler(context));
  }
}
