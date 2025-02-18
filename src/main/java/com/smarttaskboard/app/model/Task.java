package com.smarttaskboard.app.model;

import com.smarttaskboard.app.enums.PriorityStatus;
import com.smarttaskboard.app.enums.TaskStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    private LocalDate dueDate;
    @Enumerated(EnumType.STRING)
    private PriorityStatus priority;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
     @ManyToMany
     @JoinTable(name = "task_users",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
     private Set<User> assignedUsers = new HashSet<>();
     @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<Comment> comments = new ArrayList<>();

}
