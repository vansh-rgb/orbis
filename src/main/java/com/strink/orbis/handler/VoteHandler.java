package com.strink.orbis.handler;

import com.strink.orbis.dto.ServerResponseDto;
import com.strink.orbis.dto.VoteDto;
import com.strink.orbis.model.User;
import com.strink.orbis.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VoteHandler {

    private final VoteService voteService;

    @PostMapping("/{postId}/vote")
    public ResponseEntity<?> upvote(@PathVariable String postId, @RequestBody VoteDto voteDto, @AuthenticationPrincipal User user) {
        try {
            return new ResponseEntity<>(voteService.handleVote(postId, voteDto, user.getId()), HttpStatus.ACCEPTED);

        } catch (Exception e) {
            return new ResponseEntity<>(new ServerResponseDto(HttpStatus.BAD_REQUEST, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

}
