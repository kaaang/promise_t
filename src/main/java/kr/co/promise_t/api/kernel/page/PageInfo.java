package kr.co.promise_t.api.kernel.page;

import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

@AllArgsConstructor
public class PageInfo {
    private final Page<?> pageInfo;

    public Map<String, Object> toMap() {
        return Map.of(
                "size", pageInfo.getSize(),
                "totalElements", pageInfo.getTotalElements(),
                "totalPages", pageInfo.getTotalPages(),
                "page", pageInfo.getNumber() + 1,
                "isFirst", pageInfo.isFirst(),
                "isLast", pageInfo.isLast(),
                "hasNext", pageInfo.hasNext(),
                "hasPrevious", pageInfo.hasPrevious());
    }
}
