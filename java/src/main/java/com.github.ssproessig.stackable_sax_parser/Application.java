package com.github.ssproessig.stackable_sax_parser;

import com.github.ssproessig.stackable_sax_parser.parser.ExampleParser;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.xml.sax.SAXException;

@Slf4j
class Application {

  public static void main(String[] args) {
    log.info("Hello, World");

    val fileName = "example/example.xml";

    try {
      val context = ExampleParser.parse(fileName);
      log.info("XML parsed: {}", context);
    } catch (SAXException e) {
      log.error("Error parsing XML: {}", ExceptionUtils.getStackTrace(e));
    } catch (IOException e) {
      log.error("Error handling '{}': {}", fileName, ExceptionUtils.getStackTrace(e));
    }
  }
}
