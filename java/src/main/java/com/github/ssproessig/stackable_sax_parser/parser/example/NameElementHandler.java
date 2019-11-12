package com.github.ssproessig.stackable_sax_parser.parser.example;

import com.github.ssproessig.stackable_sax_parser.parser.Context;
import com.github.ssproessig.stackable_sax_parser.parser.common.BaseHandler;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.Attributes;

@Slf4j
public class NameElementHandler extends BaseHandler<Context> {

  public NameElementHandler(Context context) {
    super(context, "name");
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    log.info("startElement: {}", qName);

    context.logEvent(
        String.format(
            "...has name '%s' in language '%s'.",
            attributes.getValue("name"), attributes.getValue("language")));
  }
}
