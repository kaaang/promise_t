package kr.co.promise_t.api.kernel.util.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public interface Storage {
    void upload(String path, InputStream stream) throws IOException;

    boolean exists(String path);

    URI getUri(String path);
}
