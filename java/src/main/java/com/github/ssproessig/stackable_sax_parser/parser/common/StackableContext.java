package com.github.ssproessig.stackable_sax_parser.parser.common;

import java.util.LinkedList;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.Attributes;

@Slf4j
public class StackableContext {

  /**
   * A "last resort" matching-everything element handler that consumes everything forwarded to it.
   */
  @Slf4j
  private static class MatchingAllElementsHandler extends BaseHandler {

    public MatchingAllElementsHandler() {
      super(null, null);
    }

    public boolean handles(String localName) {
      // this handler will handle all elements - as it's the "last resort"
      return true;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
      log.info("startElement({}, {}, {}, {})", uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
      log.info("endElement({}, {}, {})", uri, localName, qName);
    }
  }

  // stack of all SAX handlers
  protected LinkedList<BaseHandler> handlers = new LinkedList<>();

  /** Initializes the context and put the "all consuming" handler there */
  public StackableContext() {
    handlers.add(new MatchingAllElementsHandler());
  }

  /**
   * Adds a new handler to the stack. Invoked by handlers that want to register child handlers.
   *
   * @param aHandler a handler to put on the stack
   */
  public void pushHandler(BaseHandler aHandler) {
    handlers.push(aHandler);
  }

  /**
   * Pops the top-most handler from the stack.
   *
   * @return the popped handler from the stack
   */
  public BaseHandler popHandler() {
    handlers.pop();
    return handlers.peek();
  }

  /**
   * Finds the next handler from the stack that is able to handle the current element.
   *
   * @param localName local name of the element currently being handled
   * @return the next SAX handler to use
   */
  public BaseHandler handlerFor(String localName) {
    // per default use the top-most handler from the stack
    BaseHandler nextHandler = handlers.peek();

    // ... and until the handler handles the localName, pop the next handler from the stack
    while (nextHandler != null && !nextHandler.handles(localName)) {
      nextHandler = popHandler();
    }

    Objects.requireNonNull(nextHandler, "handler stack went empty - halt");
    return nextHandler;
  }
}
