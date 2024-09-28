package org.sid.ContextUser.mappers;

import org.mapstruct.Mapper;
import org.sid.ContextUser.entities.User;
import org.sid.ContextUser.dtos.ReadUserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
     ReadUserDTO readUserDTOToUser(User user);
}
