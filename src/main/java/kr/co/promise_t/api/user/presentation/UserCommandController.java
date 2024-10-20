package kr.co.promise_t.api.user.presentation;

import jakarta.validation.Valid;
import kr.co.promise_t.api.user.presentation.request.UserCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserCommandController {
    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
