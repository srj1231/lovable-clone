package com.saumya.projects.lovable_clone.mapper;

import com.saumya.projects.lovable_clone.dto.member.MemberResponse;
import com.saumya.projects.lovable_clone.entity.ProjectMember;
import com.saumya.projects.lovable_clone.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMemberMapper {
    MemberResponse toMemberResponseFromOwner(User user);

    MemberResponse toMemberResponseFromMember(ProjectMember projectMember);
}
