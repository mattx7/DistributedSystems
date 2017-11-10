package vsp.api_client.utility;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HTTPBasicAuthTest {

    /**
     * Tests {@link HTTPBasicAuth#getAuthHeaderInBase64()}.
     */
    @Test
    public void test() throws Exception {
        // prepare objects
        HTTPBasicAuth auth = new HTTPBasicAuth("name", "pass");
        // check method to test
        final String authHeaderInBase64 = auth.getAuthHeaderInBase64();
        // post-conditions
        Assert.assertEquals(authHeaderInBase64, "bmFtZTpwYXNz"); // expected from https://www.base64encode.org/
    }

}
