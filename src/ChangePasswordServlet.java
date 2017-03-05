import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by zheng on 2017/2/18.
 */
@WebServlet(name = "ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {

        Connection conn;
        PreparedStatement sql;
        ResultSet rs;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            System.out.print(e);
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String msg = null;
        String temp=null;
        String uri = "jdbc:mysql://localhost:3306/monitordb";
        String selectsql = "select username,password from user where username=? and password=?";
        try
        {
            conn = DriverManager.getConnection(uri, "root", "@617475james@");
            sql = conn.prepareStatement(selectsql);

            if(username!=null&&password!=null) {
                sql.setString(1, username);
                sql.setString(2, password);
                rs = sql.executeQuery();
                boolean bool = rs.next();
                if(bool==true)
                {
                    temp="wrong";
                }
                else {
                    temp = "right";
                }
            }
            conn.close();
        }
        catch (SQLException e)
        {
            System.out.print(e);
        }

        System.out.println(temp);
        if(temp.equals("right"))
        {
            msg = "success";
            System.out.println(temp);
            try
            {
                String changeSql = "update user set username=? where password=?";
                conn = DriverManager.getConnection(uri, "root", "@617475james@");
                sql = conn.prepareStatement(changeSql);
                sql.setString(1,username);
                sql.setString(2,password);
                int status = sql.executeUpdate();
                if(status!=0)
                {
                    System.out.println("添加数据成功！");
                }else
                {
                    System.out.println("添加数据失败");
                }
                conn.close();
            }
            catch (SQLException e)
            {
                System.out.print(e);
            }
        }else
        {
            msg = "failed";
        }
        out.print(msg);
        out.flush();
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
    {
        doGet(request, response);
    }
}
