package com.github.ssproessig.stackable_sax_parser.parser.example;

import com.github.ssproessig.stackable_sax_parser.parser.common.BaseHandler;
import com.github.ssproessig.stackable_sax_parser.parser.common.StackableContext;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.Attributes;

@Slf4j
public class AElementHandler extends BaseHandler {

  public AElementHandler(StackableContext aContext) {
    super(aContext, "a-element");
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    log.info("startElement: {}", qName);

    context.pushHandler(new NameElementHandler(context));
  }
}
