package com.saumya.projects.lovable_clone.service.impl;

import com.saumya.projects.lovable_clone.dto.files.FileContentResponse;
import com.saumya.projects.lovable_clone.dto.files.FileNode;
import com.saumya.projects.lovable_clone.service.FileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public List<FileNode> getFileTree(Long projectId) {
        return List.of();
    }

    @Override
    public FileContentResponse getFile(Long projectId, String path) {
        return null;
    }
}
