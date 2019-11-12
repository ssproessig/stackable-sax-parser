package com.github.ssproessig.stackable_sax_parser;

import com.github.ssproessig.stackable_sax_parser.parser.Context;
import com.github.ssproessig.stackable_sax_parser.parser.common.StackableParser;
import com.github.ssproessig.stackable_sax_parser.parser.example.RootElementHandler;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.xml.sax.SAXException;

@Slf4j
class Application {

  public static void main(String[] args) {
    val fileName = "example/example.xml";

    try {
      val context = new Context();
      StackableParser.parse(fileName, RootElementHandler.class, context);

      log.info("Context information parsed:");
      context.getLoggedEvents().forEach(log::info);
    } catch (SAXException e) {
      log.error("Error parsing XML: {}", ExceptionUtils.getStackTrace(e));
    } catch (IOException e) {
      log.error("Error handling '{}': {}", fileName, ExceptionUtils.getStackTrace(e));
    }
  }
}
