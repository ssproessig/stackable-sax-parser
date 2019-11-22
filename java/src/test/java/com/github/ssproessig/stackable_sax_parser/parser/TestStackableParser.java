package com.github.ssproessig.stackable_sax_parser.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.github.ssproessig.stackable_sax_parser.parser.common.StackableParser;
import com.github.ssproessig.stackable_sax_parser.parser.example.RootElementHandler;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class TestStackableParser {
  @Test
  void testParsingExampleXmlShallSucceed() throws SAXException, IOException {
    val context = new Context();
    StackableParser.parse("../example/example.xml", RootElementHandler.class, context);

    val loggedEvents = context.getLoggedEvents();
    assertThat(loggedEvents.size(), is(equalTo(8)));

    List<String> expectedEvents =
        Arrays.asList(
            "new 'a-element' seen with a-attribute-1=enum1",
            "...has name 'name1' in language 'en-US'.",
            "new 'b-element' seen with b-attribute-1=2",
            "...has name 'name5' in language 'en-US'.",
            "...has name 'name7' in language 'de'.",
            "new 'b-element' seen with b-attribute-1=2",
            "...has name 'long content' in language 'en-US'.",
            "...has name 'name7' in language 'no'.");

    assertThat(loggedEvents, equalTo(expectedEvents));
  }
}
