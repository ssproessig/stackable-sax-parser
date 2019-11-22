package com.github.ssproessig.stackable_sax_parser.parser.example;

import com.github.ssproessig.stackable_sax_parser.parser.Context;
import com.github.ssproessig.stackable_sax_parser.parser.common.BaseHandler;

public class MetaInfoHandler extends BaseHandler<Context> {

  public MetaInfoHandler(Context aContext) {
    super(aContext, XmlNamespaces.META_INFO, "meta-info");
  }

  public boolean handles(String uri, String localName) {
    return XmlNamespaces.META_INFO.equals(uri);
  }
}
