package com.exam_portal.question_service.controller;

import com.exam_portal.question_service.model.Question;
import com.exam_portal.question_service.service.QuestionService;
import com.examportal.common.dto.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@PreAuthorize("hasRole('ADMIN')") // All endpoints require admin
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // Create a new question
    @PostMapping
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTO questionDTO) {
        return ResponseEntity.ok(questionService.createQuestion(questionDTO));
    }

    // Get all questions
    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    // Get a question by ID
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(questionService.getQuestionById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update a question by ID
    @PutMapping("/{id}")
    public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable Long id, @RequestBody QuestionDTO questionDTO) {
        try {
            return ResponseEntity.ok(questionService.updateQuestion(id, questionDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a question by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        try {
            questionService.deleteQuestion(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Import questions in bulk
    @PostMapping("/import")
    public ResponseEntity<Void> importQuestions(@RequestBody List<QuestionDTO> questions) {
        questionService.importQuestions(questions);
        return ResponseEntity.ok().build();
    }

    // Export questions in bulk
    @GetMapping("/export")
    public ResponseEntity<List<QuestionDTO>> exportQuestions() {
        return ResponseEntity.ok(questionService.exportQuestions());
    }
}