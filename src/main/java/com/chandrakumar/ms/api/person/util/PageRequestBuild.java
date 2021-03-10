package com.chandrakumar.ms.api.person.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;

public class PageRequestBuild {

    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int DEFAULT_SIZE_NUMBER = 10;
    public static final int DEFAULT_SIZE_NOT_ALLLOWED = 0;
    public static final int DEFAULT_PAGE_NOT_ALLLOWED = 0;

    private PageRequestBuild() {
        throw new IllegalStateException("PageRequestBuild class");
    }

    public static PageRequest getPageRequest(@Nullable final Integer page,
                                             @Nullable final Integer size) {

        if (page == null && size == null) {
            return PageRequest.of(
                    DEFAULT_PAGE_NUMBER,
                    DEFAULT_SIZE_NUMBER
            );
        }

        if (page != null && size != null) {
            return PageRequest.of(page, size);
        }

        if (page != null && DEFAULT_PAGE_NOT_ALLLOWED <= page) {
            return PageRequest.of(
                    page,
                    DEFAULT_SIZE_NUMBER
            );
        }
        if (size != null && DEFAULT_SIZE_NOT_ALLLOWED <= size) {
            return PageRequest.of(
                    DEFAULT_PAGE_NUMBER,
                    size
            );

        }
        return PageRequest.of(
                DEFAULT_PAGE_NUMBER,
                DEFAULT_SIZE_NUMBER
        );
    }
}
