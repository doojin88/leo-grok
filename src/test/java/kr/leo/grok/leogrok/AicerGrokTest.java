package kr.leo.grok.leogrok;

import org.aicer.grok.dictionary.GrokDictionary;
import org.aicer.grok.util.Grok;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AicerGrokTest {

    private static final void displayResults(final Map<String, String> results) {
        if (results != null) {
            for(Map.Entry<String, String> entry : results.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }
        }
    }

    @Test
    public void test() {
        final String rawDataLine1 = "1234567 - israel.ekpo@massivelogdata.net cc55ZZ35 1789 Hello Grok";
        final String rawDataLine2 = "98AA541 - israel-ekpo@israelekpo.com mmddgg22 8800 Hello Grok";
        final String rawDataLine3 = "55BB778 - ekpo.israel@example.net secret123 4439 Valid Data Stream";

        final String expression = "%{EMAIL:username} %{USERNAME:password} %{INT:yearOfBirth}";

        final GrokDictionary dictionary = new GrokDictionary();

        // Load the built-in dictionaries
        dictionary.addBuiltInDictionaries();

        // Resolve all expressions loaded
        dictionary.bind();

        // Take a look at how many expressions have been loaded
        System.out.println("Dictionary Size: " + dictionary.getDictionarySize());

        Grok compiledPattern = dictionary.compileExpression(expression);

        assertEquals("{username=israel.ekpo@massivelogdata.net, password=cc55ZZ35, yearOfBirth=1789}", compiledPattern.extractNamedGroups(rawDataLine1).toString() );
        assertEquals("{username=israel-ekpo@israelekpo.com, password=mmddgg22, yearOfBirth=8800}", compiledPattern.extractNamedGroups(rawDataLine2).toString() );
        assertEquals("{username=ekpo.israel@example.net, password=secret123, yearOfBirth=4439}", compiledPattern.extractNamedGroups(rawDataLine3).toString() );
    }
}
