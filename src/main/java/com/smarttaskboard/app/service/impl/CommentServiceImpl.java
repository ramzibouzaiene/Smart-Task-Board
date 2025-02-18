package com.smarttaskboard.app.service.impl;

import com.smarttaskboard.app.dto.CommentDto;
import com.smarttaskboard.app.exception.ResourceNotFoundException;
import com.smarttaskboard.app.mapper.CommentMapper;
import com.smarttaskboard.app.model.Comment;
import com.smarttaskboard.app.model.Task;
import com.smarttaskboard.app.model.User;
import com.smarttaskboard.app.repository.CommentRepository;
import com.smarttaskboard.app.repository.TaskRepository;
import com.smarttaskboard.app.repository.UserRepository;
import com.smarttaskboard.app.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public CommentDto getCommentById(Long id) {
        Optional<Comment> commentById = commentRepository.findById(id);
        return commentById.map(commentMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Comment with the id" + id + " not found"));
    }

    @Override
    public void updateComment(Long id, CommentDto commentDto) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found"));

        commentMapper.updateCommentFromDto(commentDto, existingComment);
        Task task = taskRepository.findById(commentDto.getTask().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + commentDto.getTask().getId() + " not found"));
        User user = userRepository.findById(commentDto.getAuthor().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + commentDto.getAuthor().getId() + " not found"));
        existingComment.setTask(task);
        existingComment.setAuthor(user);
        commentRepository.save(existingComment);
        log.info("Comment with id {} updated", id);
    }

    @Override
    public void addNewComment(CommentDto commentDto) {
        Task task = taskRepository.findById(commentDto.getTask().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + commentDto.getTask().getId() + " not found"));
        User user = userRepository.findById(commentDto.getAuthor().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + commentDto.getAuthor().getId() + " not found"));
        Comment newComment = commentMapper.toEntity(commentDto);
        newComment.setTask(task);
        newComment.setAuthor(user);
        Comment savedComment = commentRepository.save(newComment);
        log.info("New comment created with id {}", savedComment.getId());
        commentMapper.toDto(savedComment);
    }

    @Override
    public List<CommentDto> getCommentsByTask(Long taskId) {
        List<Comment> comments = commentRepository.findByTaskId(taskId);
        log.info("Get comments for the task {}", taskId);
        return commentMapper.toDtoList(comments);
    }

    @Override
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            log.error("Comment with id {} not found", id);
            throw new ResourceNotFoundException("Comment with id " + id + " not found");
        }
        commentRepository.deleteById(id);
        log.info("Deleted comment with id {}", id);
    }
}
