/*
 * Copyright 2021 DSHARE. All rights Reserved.
 * DSHARE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package kr.co.promise_t.api.kernel.presentation.http.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpApiResponse<T> {
    private T data;
    private Map<String, Object> meta;

    public HttpApiResponse(T data) {
        this.data = data;
    }

    public HttpApiResponse(T data, Map<String, Object> meta) {
        this.data = data;
        this.meta = meta;
    }

    public static <T> HttpApiResponse<T> of(T data, Map<String, Object> meta) {
        return new HttpApiResponse<>(data, meta);
    }

    public static <T> HttpApiResponse<T> of(T data) {
        return new HttpApiResponse<>(data, null);
    }
}
