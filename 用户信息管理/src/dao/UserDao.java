package dao;

import domain.User;

import java.util.List;
import java.util.Map;

/*
用户操作的Dao
 */
public interface UserDao {
    public List<User> findAll();
    public void deleteFromId(int id);
    public void addUser(User user);
    public void updateUser(User user);
    public User findUserByUsernameAndPassword(String username,String password);
    public User findById(int id);
    public int findTotalCount(Map<String, String[]> condition);
    public List findByPage(int start, int rows, Map<String, String[]> condition);

}
