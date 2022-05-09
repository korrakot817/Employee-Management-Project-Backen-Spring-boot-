package com.Project.Backend.mapper;

import com.Project.Backend.entity.User;
import com.Project.Backend.model.MRegisterResponse;
import com.Project.Backend.model.MUserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface UserMapper {

    MRegisterResponse toMRegisterResponse(User user);

    MUserProfile toUserProfile(User user);


}
