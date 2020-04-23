import org.junit.Test;
import play.test.WithBrowser;

import static org.junit.Assert.assertTrue;

public class IntegrationTest extends WithBrowser{
    @Test
    public void test() {
        browser.goTo("http://localhost:" + play.api.test.Helpers.testServerPort());
        assertTrue(browser.pageSource().contains("Работает"));
    }
}
