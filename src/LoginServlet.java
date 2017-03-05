import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by zheng on 2017/2/17.
 */
public class LoginServlet extends javax.servlet.http.HttpServlet {
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
        String uri = "jdbc:mysql://localhost:3306/monitordb";
        String selectsql = "select username,password from user where username=? and password=?";

        try
        {
            conn = DriverManager.getConnection(uri, "root", "@617475james@");
            sql = conn.prepareStatement(selectsql);

            if(username!=null&&password!=null)
            {
                sql.setString(1,username);
                sql.setString(2,password);
                rs = sql.executeQuery();
                boolean bool = rs.next();
                if(bool == true)
                {
                    msg = "success";
                }else
                {
                    msg = "failed";
                }
            }else
            {
                msg = "failed";
            }

            conn.close();
        }
        catch (SQLException e)
        {
            System.out.print(e);
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
