package com.saumya.projects.lovable_clone.mapper;

import com.saumya.projects.lovable_clone.dto.member.MemberResponse;
import com.saumya.projects.lovable_clone.entity.ProjectMember;
import com.saumya.projects.lovable_clone.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMemberMapper {

    @Mapping(source = "id", target = "userId")
    @Mapping(target = "projectRole", constant = "OWNER")
    MemberResponse toMemberResponseFromOwner(User user);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "email", source = "user.email")
    MemberResponse toMemberResponseFromMember(ProjectMember projectMember);
}
