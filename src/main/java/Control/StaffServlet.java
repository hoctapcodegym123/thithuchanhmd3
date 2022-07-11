package Control;

import dao.DepartmentDao;
import dao.StaffDao;
import model.Staff;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;


@WebServlet(urlPatterns = "/staff")
public class StaffServlet extends HttpServlet {
    StaffDao staffDao = new StaffDao();
    DepartmentDao departmentDao = new DepartmentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        RequestDispatcher dispatcher = null;
        switch (action) {
            case "create":
                req.setAttribute("department", departmentDao.getAll());
                dispatcher = req.getRequestDispatcher("/create.jsp");
                dispatcher.forward(req, resp);
                break;
            case "search":
                String search = req.getParameter("search");
                req.setAttribute("staffs", staffDao.getAllByName(search));
                dispatcher = req.getRequestDispatcher("/home.jsp");
                dispatcher.forward(req, resp);
                break;
            default:
                req.setAttribute("staffs", staffDao.getAll());
                dispatcher = req.getRequestDispatcher("/home.jsp");
                dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        RequestDispatcher dispatcher = null;
        switch (action) {
            case "create":
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                LocalDate birth = LocalDate.parse(request.getParameter("birth"));
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");
                String email = request.getParameter("email");
                int iddepartment = Integer.parseInt(request.getParameter("class"));

                Staff st = new Staff(id, name, birth, phone, address, email, departmentDao.findById(iddepartment));
                staffDao.create(st);
                resp.sendRedirect("/staff");
                break;
        }
    }
}
