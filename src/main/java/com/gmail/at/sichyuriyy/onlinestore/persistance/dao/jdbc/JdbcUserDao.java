package com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc;

import com.gmail.at.sichyuriyy.onlinestore.entity.Role;
import com.gmail.at.sichyuriyy.onlinestore.entity.User;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.JdbcTemplate;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.UserDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.mapper.UserMapper;
import com.gmail.at.sichyuriyy.onlinestore.persistance.transaction.Transaction;

import java.util.List;

/**
 * Created by Yuriy on 3/30/2017.
 */
public class JdbcUserDao implements UserDao {

    private static final String INSERT_USER = "INSERT INTO `user`" +
            "(`login`, `password`, `black_list`) VALUES(?, ?, ?)";

    private static final String SELECT_USER_BY_ID = "SELECT * FROM `user` WHERE `id`=?";

    private static final String UPDATE_USER = "UPDATE `user` " +
            "SET `login`=?, `password`=?, `black_list`=? WHERE `id`=?";

    private static final String DELETE_USER = "DELETE FROM `user` WHERE `id`=?";

    private static final String SELECT_ALL_USERS = "SELECT * FROM `user` ORDER BY `id` ASC";

    private static final String INSERT_USER_ROlE = "INSERT INTO `user_role` " +
            "(`user_id`, `role_id`) VALUES(?, ?)";

    private static final String DELETE_ROLES = "DELETE FROM `user_role` WHERE `user_id`=?";

    private static final String SELECT_ROLES_BY_USER_ID = "SELECT `role`.name FROM `role` " +
            "INNER JOIN `user_role` ON (`role`.id=`user_role`.role_id AND `user_role`.user_id=?)";

    private static final String SELECT_USER_BY_LOGIN = "SELECT * FROM `user` WHERE `login`=?";

    private static final String SELECT_USER_BY_LOGIN_PASSWORD  = "SELECT * FROM `user` WHERE `login`=? " +
            "AND `password`=?";

    private static final String SELECT_USER_PART = "SELECT * FROM `user` ORDER BY `id` ASC LIMIT ? OFFSET ? ";

    private static final String SELECT_USER_PART_BY_BLACK_LIST = "SELECT * FROM `user` WHERE `black_list`=? " +
            "ORDER BY `id` ASC LIMIT ? OFFSET ?";

    private ConnectionManager cm;
    private JdbcTemplate jdbcTemplate;

    public JdbcUserDao(ConnectionManager cm) {
        this.cm = cm;
        jdbcTemplate = new JdbcTemplate(cm);
    }

    @Override
    public Long create(User user) {
        final Long[] id = new Long[1];
        Transaction.tx(cm, () -> {
            id[0] = jdbcTemplate.insert(INSERT_USER, user.getLogin(), user.getPassword(), user.getBlackList());
            addRoles(user.getId(), user.getRoles());
        });
        return id[0];
    }

    @Override
    public User findById(Long id) {
        final User[] user = new User[1];
        Transaction.tx(cm, () -> {
            user[0] = jdbcTemplate.queryObject(SELECT_USER_BY_ID,
                    new UserMapper(), id);
            if (user[0] != null)
                user[0].setRoles(findRoles(id));
        });

        return user[0];
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(UPDATE_USER, user.getLogin(), user.getPassword(), user.getBlackList(), user.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_USER, id);
    }

    @Override
    public List<User> findAll() {
        final Object[] users = new Object[1];
        Transaction.tx(cm, () -> {
            List<User> userList = jdbcTemplate.queryObjects(SELECT_ALL_USERS,
                    new UserMapper());
            userList.forEach((u) -> u.setRoles(findRoles(u.getId())));
            users[0] = userList;
        });
        return (List<User>) users[0];
    }

    @Override
    public void addRoles(Long userId, List<Role> roles) {
        Transaction.tx(cm, () -> {
            for (Role role: roles) {
                addRole(userId, role);
            }
        });
    }

    @Override
    public void addRole(Long userId, Role role) {
        jdbcTemplate.insert(INSERT_USER_ROlE, userId, role.ordinal() + 1);
    }

    @Override
    public void deleteRoles(Long userId) {
        jdbcTemplate.update(DELETE_ROLES, userId);
    }

    @Override
    public void updateRoles(Long userId, List<Role> roles) {
        Transaction.tx(cm, () -> {
            deleteRoles(userId);
            addRoles(userId, roles);
        });
    }

    @Override
    public List<Role> findRoles(Long userId) {
        return jdbcTemplate.queryObjects(SELECT_ROLES_BY_USER_ID,
                (rs) -> Role.valueOf(rs.getString("role.name")),
                userId);
    }

    @Override
    public User findByName(String username) {
        User[] user = new User[1];
        Transaction.tx(cm, () -> {
            user[0] = jdbcTemplate.queryObject(SELECT_USER_BY_LOGIN,
                    new UserMapper(), username);
            if (user[0] != null) {
                user[0].setRoles(findRoles(user[0].getId()));
            }
        });
        return user[0];
    }

    @Override
    public User findByLoginPassword(String login, String password) {
        User[] user = new User[1];
        Transaction.tx(cm, () -> {
            user[0] = jdbcTemplate.queryObject(SELECT_USER_BY_LOGIN_PASSWORD,
                    new UserMapper(), login, password);
            if (user[0] != null) {
                user[0].setRoles(findRoles(user[0].getId()));
            }
        });
        return user[0];
    }

    @Override
    public List<User> findPart(int limit, int offset) {
        final Object[] users = new Object[1];
        Transaction.tx(cm, () -> {
            List<User> userList = jdbcTemplate.queryObjects(SELECT_USER_PART,
                    new UserMapper(), limit, offset);
            userList.forEach((u) -> u.setRoles(findRoles(u.getId())));
            users[0] = userList;
        });
        return (List<User>) users[0];
    }

    @Override
    public List<User> findByBlackList(Boolean blackList, int limit, int offset) {
        final Object[] users = new Object[1];
        Transaction.tx(cm, () -> {
            List<User> userList = jdbcTemplate.queryObjects(SELECT_USER_PART_BY_BLACK_LIST,
                    new UserMapper(), blackList, limit, offset);
            userList.forEach((u) -> u.setRoles(findRoles(u.getId())));
            users[0] = userList;
        });
        return (List<User>) users[0];
    }
}
