import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by zheng on 2017/2/17.
 */
public class RegisterServlet extends javax.servlet.http.HttpServlet {
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
        String selectsql = "select username,password from user where username=?";
        try
        {
            conn = DriverManager.getConnection(uri, "root", "@617475james@");
            sql = conn.prepareStatement(selectsql);

            if(username!=null) {
                sql.setString(1, username);
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
       // out.flush();
      //  out.close();
        System.out.println(temp);
      //  Connection conn0;
      //  PrintWriter out0 = response.getWriter();
      //  PreparedStatement sql0;
        if(temp.equals("right"))
        {
            msg = "success";
            System.out.println(temp);
            try
            {
               // String uri0 = "jdbc:mysql://localhost:8080/monitordb";
                String insertSql = "insert into user values(?,?)";
                conn = DriverManager.getConnection(uri, "root", "@617475james@");
                sql = conn.prepareStatement(insertSql);
                sql.setString(1,username);
                sql.setString(2,password);
                int status = sql.executeUpdate();
                if(status!=0)
                {
                    System.out.print("添加数据成功！");
                }else
                {
                    System.out.print("添加数据失败");
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
