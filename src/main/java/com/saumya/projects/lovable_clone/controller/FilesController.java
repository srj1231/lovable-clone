package com.saumya.projects.lovable_clone.controller;

import com.saumya.projects.lovable_clone.dto.files.FileContentResponse;
import com.saumya.projects.lovable_clone.dto.files.FileNode;
import com.saumya.projects.lovable_clone.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/files")
public class FilesController {

    private final FileService fileService;

    @GetMapping
    public ResponseEntity<List<FileNode>> getFileTree(@PathVariable Long projectId) {
        Long userId = 1L;
        return ResponseEntity.ok(fileService.getFileTree(projectId, userId));
    }

    @GetMapping("/{*path}")
    public ResponseEntity<FileContentResponse> getFile(@PathVariable Long projectId, @PathVariable String path) {
        Long userId = 1L;
        return ResponseEntity.ok(fileService.getFile(projectId, path, userId));
    }

    // TODO: download as zip
}
