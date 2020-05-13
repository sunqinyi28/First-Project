package service;

import domain.PageBean;
import domain.User;

import java.util.List;
import java.util.Map;

/*
用户管理业务接口
 */
public interface UserService {
    /*
    查询所有用户信息
     */
    public List<User> findAll();
    public void delete(int id);
    public void insert(User user);
    public void update(User user);
    public User login(User user);

    public User findUserById(String id);

    public void delSeletedUser(String[] ids);

    public PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition);
}
