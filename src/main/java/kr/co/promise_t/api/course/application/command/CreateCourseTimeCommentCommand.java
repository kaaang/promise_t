package kr.co.promise_t.api.course.application.command;

import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import kr.co.promise_t.api.course.application.command.model.CreateCourseTimeCommentCommandModel;
import kr.co.promise_t.api.course.application.exception.CourseTimeNotFoundException;
import kr.co.promise_t.api.course.application.exception.CourseTimeReservationNotFoundException;
import kr.co.promise_t.api.kernel.command.Command;
import kr.co.promise_t.api.kernel.exception.FailAttachmentUploadException;
import kr.co.promise_t.api.kernel.util.storage.Storage;
import kr.co.promise_t.core.course.CourseTimeReservationAttachment;
import kr.co.promise_t.core.course.CourseTimeReservationComment;
import kr.co.promise_t.core.course.repository.write.CourseTimeWriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateCourseTimeCommentCommand
        implements Command<CreateCourseTimeCommentCommandModel> {
    private final CourseTimeWriteRepository courseTimeWriteRepository;
    private final Storage storage;

    @Override
    @Transactional
    public void execute(CreateCourseTimeCommentCommandModel model) {
        var courseTime =
                courseTimeWriteRepository
                        .findWithReservationByIdAndReservationId(
                                model.getCourseTimeId(), model.getReservationId())
                        .orElseThrow(() -> new CourseTimeNotFoundException("수업 일정을 찾을 수 없습니다."));

        var reservation =
                courseTime.getReservations().stream()
                        .findFirst()
                        .orElseThrow(() -> new CourseTimeReservationNotFoundException("수업 예약 내역을 찾을 수 없습니다."));

        var comment =
                CourseTimeReservationComment.create(
                        UUID.randomUUID(), reservation, model.getUserid(), model.getContents());
        reservation.addComment(comment);

        // TODO : 추후 커멘드로 분리
        model
                .getFiles()
                .forEach(
                        file -> {
                            var attachmentId = UUID.randomUUID();
                            var path =
                                    this.createPtah(
                                            attachmentId,
                                            reservation.getId(),
                                            Objects.requireNonNull(file.getOriginalFilename()));
                            this.uploadFile(file, path);

                            var attachment = CourseTimeReservationAttachment.create(attachmentId, path, comment);
                            comment.addAttachment(attachment);
                        });

        courseTimeWriteRepository.save(courseTime);
    }

    private void uploadFile(@Nonnull MultipartFile file, @Nonnull String path) {
        try {
            storage.upload(path, file.getInputStream());
        } catch (IOException e) {
            log.error("=== Failed to upload image === ", e);
            throw new FailAttachmentUploadException("파일 업로드 실패");
        }
    }

    private String createPtah(
            @Nonnull UUID attachmentId, @Nonnull UUID reservationId, @Nonnull String filename) {
        return String.format(
                "%s/%s.%s",
                reservationId, attachmentId, FilenameUtils.getExtension(filename).toLowerCase());
    }
}
