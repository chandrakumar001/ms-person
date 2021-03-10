package com.chandrakumar.ms.api.person.validation;

import java.util.Optional;

public class PageRequestValidator {

    public static final String THE_PAGE_MUST_NOT_BE_ZERO_OR_NEGATIVE = "The Page Must not be zero or negative";
    public static final String THE_SIZE_MUST_NOT_BE_ZERO_OR_NEGATIVE = "The Size Must not be zero or negative";

    private PageRequestValidator() {
        throw new IllegalStateException("PageRequestValidator class");
    }

    public static Optional<String> validateRequest(final Integer page,
                                                   final Integer size) {
        if (page == null && size == null) {
            return Optional.empty();
        }
        if (null != size && 0 >= size) {
            return Optional.of(THE_SIZE_MUST_NOT_BE_ZERO_OR_NEGATIVE);
        }
        if (null != page && 0 >= page) {
            return Optional.of(THE_PAGE_MUST_NOT_BE_ZERO_OR_NEGATIVE);
        }
        return Optional.empty();
    }
}
