package kr.co.promise_t.api.kernel.util.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import kr.co.promise_t.api.kernel.exception.StorageFileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Service
@RequiredArgsConstructor
public class S3SyncStorage implements Storage {
    private static final Duration SIGNATURE_DURATION = Duration.ofSeconds(60 * 5);

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${storage.s3.bucket}")
    private String bucket;

    @Override
    public void upload(String path, InputStream inputStream) throws IOException {
        var putObjectRequest = PutObjectRequest.builder().bucket(bucket).key(path).build();

        var body = RequestBody.fromInputStream(inputStream, inputStream.available());
        s3Client.putObject(putObjectRequest, body);
    }

    @Override
    public URI getUri(String path) {
        if (!exists(path)) {
            throw new StorageFileNotFoundException("파일을 찾을 수 없습니다.");
        }

        var getObjectRequest = GetObjectRequest.builder().bucket(bucket).key(path).build();

        var presignRequest =
                GetObjectPresignRequest.builder()
                        .signatureDuration(SIGNATURE_DURATION)
                        .getObjectRequest(getObjectRequest)
                        .build();

        var presignedRequest = s3Presigner.presignGetObject(presignRequest);

        try {
            return presignedRequest.url().toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException("URI SyntaxException", e);
        }
    }

    @Override
    public boolean exists(String path) {
        try {
            var headObjectRequest = HeadObjectRequest.builder().bucket(bucket).key(path).build();

            s3Client.headObject(headObjectRequest);
            return true;
        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                return false;
            }

            throw e;
        }
    }
}
