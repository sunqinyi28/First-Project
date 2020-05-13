package dao.Impl;

import dao.UserDao;
import domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<User> findAll() {
        //使用JDBC操作数据库
        //定义sql
        String sql="select * from user";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public void deleteFromId(int id) {
        String sql="delete from user where id=?";
        template.update(sql,id);
    }

    @Override
    public void addUser(User user) {
        String sql="INSERT INTO USER (name,gender,age,address,qq,email) VALUES (?,?,?,?,?,?)";
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail());
    }

    @Override
    public void updateUser(User user) {
        String sql="UPDATE USER SET NAME=?,gender=?,age=?,address=?,qq=?,email=? WHERE id=?";
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail(),user.getId());
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
       try{
        String sql="select * from user where username=? and password=?";
        User user=template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username,password);
        return user;
       }catch (Exception e){
           e.printStackTrace();
           return null;
       }
    }

    @Override
    public User findById(int id) {
        String sql="select * from user where id=?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),id);
    }

    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        String sql="select count(*) from user where 1 = 1 ";
        StringBuilder sb=new StringBuilder(sql);
        //遍历map
        Set<String> kaySet = condition.keySet();
        List<Object> params=new ArrayList<Object>();
        for (String key : kaySet) {
            if("currentPage".equals(key)||"rows".equals(key)){
                continue;
            }
            String value = condition.get(key)[0];
            if (value!=null&&!"".equals(value)){
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//加条件的值
            }
        }
        return template.queryForObject(sb.toString(),Integer.class,params.toArray());
    }

    @Override
    public List findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql="select * from user where 1 = 1 ";
        StringBuilder sb=new StringBuilder(sql);
        //遍历map
        Set<String> kaySet = condition.keySet();
        List<Object> params=new ArrayList<Object>();
        for (String key : kaySet) {
            if("currentPage".equals(key)||"rows".equals(key)){
                continue;
            }
            String value = condition.get(key)[0];
            if (value!=null&&!"".equals(value)){
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//加条件的值
            }
        }
        sb.append(" limit ?,? ");
        params.add(start);
        params.add(rows);
        return template.query(sb.toString(),new BeanPropertyRowMapper<User>(User.class),params.toArray());
    }

}
