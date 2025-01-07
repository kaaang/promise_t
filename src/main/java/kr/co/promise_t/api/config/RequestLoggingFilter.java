package kr.co.promise_t.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerMapping;

public class RequestLoggingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest) {
            var logData = new HashMap<>();

            var uri = httpRequest.getRequestURI();
            if (uri.equals("/users/signin") || uri.startsWith("/users/signup")) {
                chain.doFilter(request, response);
                return;
            }

            logData.put("method", httpRequest.getMethod());
            logData.put("uri", uri);

            // Path Variables
            @SuppressWarnings("unchecked")
            var pathVariables =
                    (Map<String, String>)
                            httpRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if (Objects.nonNull(pathVariables) && !pathVariables.isEmpty()) {
                logData.put("pathVariables", pathVariables);
            }

            // Request Parameters
            var parameterMap = httpRequest.getParameterMap();
            if (!parameterMap.isEmpty()) {
                var formattedParams = new HashMap<>();
                parameterMap.forEach((key, values) -> formattedParams.put(key, String.join(",", values)));
                logData.put("queryParams", formattedParams);
            }

            // Request Body
            var wrappedRequest = new CachedBodyHttpServletRequest(httpRequest);
            var requestBody = wrappedRequest.getBody();
            if (!requestBody.isEmpty()) {
                logData.put("body", objectMapper.readValue(requestBody, Object.class));
            }

            logger.info(
                    "Request Log:\n{}",
                    objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(logData));

            chain.doFilter(wrappedRequest, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
