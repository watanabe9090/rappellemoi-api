package dev.ctc.learning.rappellemoiapi.base;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BaseErrorHandler {
    protected Map<String, String> convert(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("cause", ex.getMessage());
        errors.put("timestamp", String.valueOf(Instant.now().toEpochMilli()));
        return errors;
    }
}
