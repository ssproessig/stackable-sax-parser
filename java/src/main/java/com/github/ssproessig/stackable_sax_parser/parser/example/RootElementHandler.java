package com.github.ssproessig.stackable_sax_parser.parser.example;

import com.github.ssproessig.stackable_sax_parser.parser.common.BaseHandler;
import com.github.ssproessig.stackable_sax_parser.parser.common.StackableContext;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

@Slf4j
public class RootElementHandler extends BaseHandler {
  private static final String EXPECTED_VERSION = "1";

  public RootElementHandler(StackableContext aContext) {
    super(aContext, "root-element");
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {
    log.info("startElement: {}", qName);

    val version = attributes.getValue("version");
    if (version == null || !version.equals(EXPECTED_VERSION)) {
      throw new SAXException(
          String.format(
              "Unsupported example schema used: %s, expected: %s", version, EXPECTED_VERSION));
    }

    context.pushHandler(new BElementHandler(context));
    context.pushHandler(new AElementHandler(context));
  }
}
