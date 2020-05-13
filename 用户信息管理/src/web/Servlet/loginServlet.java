package web.Servlet;

import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import service.Impl.UserServiceImpl;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");
        //获取数据
        String verifycode = request.getParameter("verifycode");
        //验证码校验
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//验证码一次性
        if(!checkcode_server.equalsIgnoreCase(verifycode)) {
            request.setAttribute("login_msg","验证码错误！");
            request.getRequestDispatcher("index.jsp").forward(request,response);
            return;
        }
        Map<String, String[]> map = request.getParameterMap();
        //封装User对象
        User user=new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用Service
        UserService service=new UserServiceImpl();
        User loginUser = service.login(user);
        //判断是否登陆成功
        if(loginUser!=null){
            session.setAttribute("user",loginUser);
            response.sendRedirect(request.getContextPath()+"/Welcome.jsp");
        }else {
            request.setAttribute("login_msg","用户名或密码错误！");
            request.getRequestDispatcher("/index.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
