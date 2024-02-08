package bo.custom;

import bo.SuperBo;
import dto.CustomerDto;
import dto.UserDto;

import java.sql.SQLException;
import java.util.List;

public interface UserBo extends SuperBo {
    boolean saveUser(UserDto dto) throws SQLException, ClassNotFoundException;
    boolean updateUser(UserDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteUser(String id) throws SQLException, ClassNotFoundException;
    List<UserDto> allUsers() throws SQLException, ClassNotFoundException;
    UserDto getUser(String id)throws SQLException, ClassNotFoundException;
}
