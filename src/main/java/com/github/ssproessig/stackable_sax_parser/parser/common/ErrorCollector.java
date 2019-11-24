package com.github.ssproessig.stackable_sax_parser.parser.common;

import lombok.extern.slf4j.Slf4j;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

@Slf4j
public class ErrorCollector implements ErrorHandler {
  @Override
  public void warning(SAXParseException exception) {
    log.warn(exception.toString());
  }

  @Override
  public void error(SAXParseException exception) {
    log.error(exception.toString());
  }

  @Override
  public void fatalError(SAXParseException exception) {
    log.error(exception.toString());
  }
}
