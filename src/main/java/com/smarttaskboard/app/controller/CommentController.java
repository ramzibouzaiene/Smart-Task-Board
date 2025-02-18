package com.smarttaskboard.app.controller;

import com.smarttaskboard.app.dto.CommentDto;
import com.smarttaskboard.app.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        commentService.updateComment(id, commentDto);
        return ResponseEntity.ok("Comment Updated Successfully");
    }

    @PostMapping
    public ResponseEntity<String> addNewComment(@RequestBody CommentDto commentDto) {
        commentService.addNewComment(commentDto);
        return ResponseEntity.ok("Comment Added Successfully");
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<List<CommentDto>> getCommentsByTask(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentsByTask(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Comment Deleted Successfully");
    }

}
