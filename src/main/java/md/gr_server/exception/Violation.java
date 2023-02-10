package md.gr_server.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Violation {
    private final String fieldName;
    private final String message;
}
