package com.gmail.at.sichyuriyy.onlinestore.persistance.dao;

import com.gmail.at.sichyuriyy.onlinestore.domain.Role;
import com.gmail.at.sichyuriyy.onlinestore.domain.User;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.H2Db;
import com.gmail.at.sichyuriyy.onlinestore.TestData;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc.JdbcUserDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.util.ScriptRunner;
import com.gmail.at.sichyuriyy.onlinestore.util.ResourcesUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Yuriy on 4/3/2017.
 */
public class UserDaoTest {

    private UserDao userDao;
    private TestData testData;
    private ConnectionManager connectionManager;

    @Before
    public void setUp() throws Exception {
        connectionManager = H2Db.initWithTx();
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("database.sql"));
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("test_data.sql"));
        userDao = new JdbcUserDao(connectionManager);
        testData = new TestData();
    }

    @After
    public void tearDown() throws Exception {
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("clear_database.sql"));
    }


    @Test
    public void addRoles() {
        userDao.addRoles(1L, Collections.singletonList(Role.ADMINISTRATOR));

        List<Role> actual = userDao.findRoles(1L);
        List<Role> expected = Arrays.asList(Role.CUSTOMER, Role.ADMINISTRATOR);

        assertEquals(expected, actual);
    }

    @Test
    public void addRole() {
        userDao.addRole(1L, Role.ADMINISTRATOR);

        List<Role> actual = userDao.findRoles(1L);
        List<Role> expected = Arrays.asList(Role.CUSTOMER, Role.ADMINISTRATOR);

        assertEquals(expected, actual);
    }

    @Test
    public void deleteRoles() throws Exception {
        userDao.deleteRoles(1L);

        List<Role> actual = userDao.findRoles(1L);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void updateRoles() throws Exception {
        userDao.updateRoles(1L, Collections.singletonList(Role.ADMINISTRATOR));

        List<Role> actual = userDao.findRoles(1L);
        List<Role> expected = Collections.singletonList(Role.ADMINISTRATOR);

        assertEquals(expected, actual);
    }

    @Test
    public void findRoles() {
        List<Role> actual = userDao.findRoles(1L);
        List<Role> expected = Collections.singletonList(Role.CUSTOMER);
        assertEquals(expected, actual);

    }

    @Test
    public void findByName() throws Exception {
        User actual = userDao.findByName("user1");
        User expected = testData.getUser(1L);
        assertWeakEquals(expected, actual);
    }

    @Test
    public void findPart() throws Exception {
        List<User> actual = userDao.findPart(1, 1);
        List<User> expected = Collections.singletonList(testData.getUser(2L));
        assertWeakEquals(expected.get(0), actual.get(0));
        assertEquals(expected.size(), actual.size());
    }

    @Test
    public void findByBlackList() {
        List<User> actual = userDao.findByBlackList(true, 1, 0);
        List<User> expected = Collections.singletonList(testData.getUser(2L));
        assertWeakEquals(expected.get(0), actual.get(0));
        assertEquals(expected.size(), actual.size());
    }

    @Test
    public void findById() {
        User expected = testData.getUser(1L);
        User actual = userDao.findById(1L);
        assertWeakEquals(expected, actual);
    }

    @Test
    public void create() {
        User expected = new User(null, "new_log", "new_pswd", false);
        Long id = userDao.create(expected);
        expected.setId(id);

        User actual = userDao.findById(id);

        assertWeakEquals(expected, actual);
    }

    @Test
    public void update() {
        User expected = testData.getUser(1L);
        expected.setBlackList(true);
        expected.setLogin("updated_log");
        expected.setPassword("updated_pswd");
        
        userDao.update(expected);

        User actual = userDao.findById(1L);

        assertWeakEquals(expected, actual);
    }

    @Test
    public void remove() {
        assertNotNull(userDao.findById(4L));
        userDao.delete(4L);
        assertNull(userDao.findById(4L));
    }

    @Test
    public void findAll() throws Exception {
        List<User> expected = Arrays.asList(
                testData.getUser(1L),
                testData.getUser(2L),
                testData.getUser(3L),
                testData.getUser(4L));
        List<User> actual = userDao.findAll();

        assertWeakEquals(expected.get(0), actual.get(0));
        assertWeakEquals(expected.get(1), actual.get(1));
        assertWeakEquals(expected.get(2), actual.get(2));
        assertWeakEquals(expected.get(3), actual.get(3));
    }

    private void assertWeakEquals(User expected, User actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getBlackList(), actual.getBlackList());
        assertEquals(expected.getLogin(), actual.getLogin());
        assertEquals(expected.getPassword(), actual.getPassword());
    }

}