package com.smarttaskboard.app.service;

import com.smarttaskboard.app.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto getCommentById(Long id);

    void updateComment(Long id, CommentDto commentDto);

    void addNewComment(CommentDto commentDto);
    List<CommentDto> getCommentsByTask(Long taskId);

    void deleteComment(Long id);
}
