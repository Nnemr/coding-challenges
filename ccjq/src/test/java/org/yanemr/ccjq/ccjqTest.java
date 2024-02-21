package org.yanemr.ccjq;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CcjqTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "{}",
            "11",
            "1.1",
            "[]",
            "[1,2]",
            "[\"test\"]",
            "{\"key\":\"value\"}",
            " { \"key\" : \"value\" }",
            "{\"key\":\"value\",\"k1\":\"v2\"}",
            "{\"key\":1}",
            "{ \"key\":    .11}",
            "{ \"key\": 0.11}",
            "{ \"key\": 11.11}",
            "[{\"key\":\"value\"}, 11, \"a\"]"
    })
    public void testJsonIsValid(String json) {
        Ccjq ccjq = new Ccjq();
        assertTrue(ccjq.parse(json));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "}",
            "{{",
            "[",
            ".1 1",
            "11 1",
            "[a]",
            "{\"key\":\"a\"]",
            "{\"key\":\"a\",}",
            "{\"key\":}",
            "[1,]",
            "{1}",
    })
    public void testInvalidJson_throwsIllegalArgumentException(String input) {
        Ccjq ccjq = new Ccjq();
        assertThrows(IllegalArgumentException.class, () -> ccjq.parse(input));
    }

}
