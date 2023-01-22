package com.chc.retail.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class ApiResponseHandlerTest {
    @Test
    void givenApiResponseTestWhenInitialization() {
        LocalDateTime timestamp = LocalDateTime.of(1, 1, 1, 1, 1);
        ApiResponseHandler actualApiResponseHandler = new ApiResponseHandler(timestamp, "Not all who wander are lost",
                new ArrayList<>());
        ArrayList<String> stringList = new ArrayList<>();
        actualApiResponseHandler.setErrors(stringList);
        actualApiResponseHandler.setMessage("Not all who wander are lost");
        LocalDateTime ofResult = LocalDateTime.of(1, 1, 1, 1, 1);
        actualApiResponseHandler.setTimestamp(ofResult);
        assertSame(stringList, actualApiResponseHandler.getErrors());
        assertEquals("Not all who wander are lost", actualApiResponseHandler.getMessage());
        assertSame(ofResult, actualApiResponseHandler.getTimestamp());
    }
}

