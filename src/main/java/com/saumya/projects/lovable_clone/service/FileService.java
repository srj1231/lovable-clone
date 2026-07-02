package com.saumya.projects.lovable_clone.service;

import com.saumya.projects.lovable_clone.dto.files.FileContentResponse;
import com.saumya.projects.lovable_clone.dto.files.FileNode;

import java.util.List;

public interface FileService {
    List<FileNode> getFileTree(Long projectId, Long userId);

    FileContentResponse getFile(Long projectId, String path, Long userId);
}
