package com.github.ssproessig.stackable_sax_parser.parser;

import org.xml.sax.helpers.DefaultHandler;

public class RootHandler extends DefaultHandler {
  private Context context;

  public RootHandler(Context aContext) {
    context = aContext;
  }
}
