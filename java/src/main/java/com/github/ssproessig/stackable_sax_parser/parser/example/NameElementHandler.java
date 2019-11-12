package com.github.ssproessig.stackable_sax_parser.parser.example;

import com.github.ssproessig.stackable_sax_parser.parser.common.BaseHandler;
import com.github.ssproessig.stackable_sax_parser.parser.common.StackableContext;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.Attributes;

@Slf4j
public class NameElementHandler extends BaseHandler {

  public NameElementHandler(StackableContext context) {
    super(context, "name");
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    log.info("startElement: {}", qName);
  }
}
