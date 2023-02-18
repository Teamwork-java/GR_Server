package md.gr_server.exception;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class ValidationErrorResponse {
    private final List<Violation> violations;
}
