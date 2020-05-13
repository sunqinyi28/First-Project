package service.Impl;

import dao.Impl.UserDaoImpl;
import dao.UserDao;
import domain.PageBean;
import domain.User;
import service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao dao=new UserDaoImpl();
    @Override
    public List<User> findAll() {
        //调用Dao完成查询
        return dao.findAll();
    }

    @Override
    public void delete(int id) {
        dao.deleteFromId(id);
    }

    @Override
    public void insert(User user) {
        dao.addUser(user);
    }

    @Override
    public void update(User user) {
        dao.updateUser(user);
    }

    @Override
    public User login(User user) {
        return dao.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public User findUserById(String id) {
        return dao.findById(Integer.parseInt(id));
    }

    @Override
    public void delSeletedUser(String[] ids) {
        for (String id : ids) {
            dao.deleteFromId(Integer.parseInt(id));
        }
    }

    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {
        PageBean<User> pb=new PageBean<User>();
        int currentPage=Integer.parseInt(_currentPage);
        int rows=Integer.parseInt(_rows);
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        int totalCount=dao.findTotalCount(condition);
        pb.setTotalCount(totalCount);
        int start=(currentPage-1)*rows;
        List list=dao.findByPage(start,rows,condition);
        pb.setList(list);
        int totalPage=(totalCount % rows)==0 ? totalCount/rows:(totalCount/rows+1);
        pb.setTotalPage(totalPage);
        return pb;
    }
}
