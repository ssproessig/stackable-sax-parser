package com.github.ssproessig.stackable_sax_parser.parser;

import com.github.ssproessig.stackable_sax_parser.parser.common.StackableContext;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Context extends StackableContext {
  private List<String> loggedEvents = new ArrayList<>();

  public void logEvent(String anEvent) {
    loggedEvents.add(anEvent);
  }
}
