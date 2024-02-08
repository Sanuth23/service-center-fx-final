package bo.custom.impl;

import bo.custom.CustomerBo;
import bo.custom.UserBo;
import dao.DaoFactory;
import dao.custom.CustomerDao;
import dao.custom.UserDao;
import dao.util.DaoType;
import dto.CustomerDto;
import dto.UserDto;
import entity.Customer;
import entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBoImpl implements UserBo {
    private UserDao userDao = DaoFactory.getInstance().getDao(DaoType.USER);

    @Override
    public boolean saveUser(UserDto dto) throws SQLException, ClassNotFoundException {
        return userDao.save(new User(
                dto.getUserId(),
                dto.getName(),
                dto.getContactNumber(),
                dto.getJobRole(),
                dto.getUsername(),
                dto.getPassword()
        ));
    }

    @Override
    public boolean updateUser(UserDto dto) throws SQLException, ClassNotFoundException {
        return userDao.update(new User(
                dto.getUserId(),
                dto.getName(),
                dto.getContactNumber(),
                dto.getJobRole(),
                dto.getUsername(),
                dto.getPassword()
        ));
    }

    @Override
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
        return userDao.delete(id);
    }

    @Override
    public List<UserDto> allUsers() throws SQLException, ClassNotFoundException {
        List<User> entityList = userDao.getAll();
        List<UserDto> list = new ArrayList<>();
        for (User user:entityList) {
            list.add(new UserDto(
                    user.getUserId(),
                    user.getName(),
                    user.getContactNumber(),
                    user.getJobRole(),
                    user.getUsername(),
                    user.getPassword()
            ));
        }
        return list;
    }

    @Override
    public UserDto getUser(String id) throws SQLException, ClassNotFoundException {
        User user = userDao.getUser(id);

        return (new UserDto(
                user.getUserId(),
                user.getName(),
                user.getContactNumber(),
                user.getJobRole(),
                user.getUsername(),
                user.getPassword()
        ));
    }
}
