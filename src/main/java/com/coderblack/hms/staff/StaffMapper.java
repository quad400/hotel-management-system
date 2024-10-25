package com.coderblack.hms.staff;


import com.coderblack.hms.staff.request.UpdateStaffAdminPassRequest;
import com.coderblack.hms.staff.request.UpdateStaffRequest;
import com.coderblack.hms.user.User;
import com.coderblack.hms.user.UserResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StaffMapper {

    StaffMapper INSTANCE = Mappers.getMapper(StaffMapper.class);

    void toStaff(UpdateStaffAdminPassRequest request, @MappingTarget Staff staff);

    void toStaffUpdate(UpdateStaffRequest request, @MappingTarget Staff staff);

    @Mapping(source="user", target="user")
    StaffResponse toStaffResponse(Staff staff);

    UserResponse toUserResponse(User user);
}
