package com.github.ssproessig.stackable_sax_parser.parser.example;

import com.github.ssproessig.stackable_sax_parser.parser.Context;
import com.github.ssproessig.stackable_sax_parser.parser.common.BaseHandler;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.Attributes;

@Slf4j
public class NameElementHandler extends BaseHandler<Context> {

  private String languageSeen;
  private String nameSeen;

  public NameElementHandler(Context context) {
    super(context, "name");
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    log.info("startElement: {}", qName);

    nameSeen = attributes.getValue("name");
    if (nameSeen == null) nameSeen = "";

    languageSeen = attributes.getValue("language");
  }

  @Override
  public void characters(char[] ch, int start, int length) {
    String s = new String(ch, start, length);
    nameSeen += s.trim();
  }

  @Override
  public void endElement(String uri, String localName, String qName) {
    context.logEvent(String.format("...has name '%s' in language '%s'.", nameSeen, languageSeen));
  }
}
