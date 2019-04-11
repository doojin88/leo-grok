package kr.leo.grok.leogrok;

import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;
import io.krakens.grok.api.exception.GrokException;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class GrokTest {

    @Test
    public void test() throws GrokException {
        /* Create a new grokCompiler instance */
        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerDefaultPatterns();

        /* Grok pattern to compile, here httpd logs */
        final Grok grok = grokCompiler.compile("%{COMBINEDAPACHELOG}");

        /* Line of log to match */
        String log = "112.169.19.192 - - [06/Mar/2013:01:36:30 +0900] \"GET / HTTP/1.1\" 200 44346 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.152 Safari/537.22\"";

        Match gm = grok.match(log);

        /* Get the map with matches */
        final Map<String, Object> capture = gm.capture();

        assertEquals("{request=/, MONTH=Mar, agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.152 Safari/537.22, COMMONAPACHELOG=112.169.19.192 - - [06/Mar/2013:01:36:30 +0900] \"GET / HTTP/1.1\" 200 44346, auth=-, HOUR=01, ident=-, verb=GET, TIME=01:36:30, INT=+0900, referrer=-, YEAR=2013, response=200, bytes=44346, clientip=112.169.19.192, COMBINEDAPACHELOG=112.169.19.192 - - [06/Mar/2013:01:36:30 +0900] \"GET / HTTP/1.1\" 200 44346 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.152 Safari/537.22\", MINUTE=36, SECOND=30, httpversion=1.1, rawrequest=null, MONTHDAY=06, timestamp=06/Mar/2013:01:36:30 +0900}", capture.toString());

        assertEquals("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.152 Safari/537.22", capture.get("agent"));
    }
}
