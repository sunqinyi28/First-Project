package web.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
/*
登录验证过滤器
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //强制转换
        HttpServletRequest requset=(HttpServletRequest) req;
        //请求路径
        String uri = requset.getRequestURI();
        if(uri.contains("/index.jsp")||uri.contains("/loginServlet")||uri.contains("/css/")||uri.contains("/js/")||uri.contains("/fonts/")||uri.contains("/checkCodeServlet")){//css,js,图片，验证码
            chain.doFilter(req, resp);
        }else {
            Object user = requset.getSession().getAttribute("user");
            if(user!=null){
                chain.doFilter(req, resp);
            }else {
                requset.setAttribute("login_msg","您尚未登录，请登录！");
                requset.getRequestDispatcher("/index.jsp").forward(requset,resp);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
