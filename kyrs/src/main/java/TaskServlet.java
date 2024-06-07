import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.text.SimpleDateFormat;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {
    private TaskList taskList = new TaskList();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            for (Task task : taskList.getTasks()) {
                out.println("<li>" + task.getTitle() + " - " + task.getDescription() + " - " +
                            task.getStartTime() + " to " + task.getEndTime() + " - " + 
                            task.getType().getName() + "</li>");
            }
        } finally {
            out.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String type = request.getParameter("type");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        try {
            Date start = formatter.parse(startTime);
            Date end = formatter.parse(endTime);
            TaskType taskType = new TaskType(type);
            Task newTask = new Task(title, description, start, end, taskType);

            if (taskList.hasTimeConflict(newTask)) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("Time conflict detected");
            } else {
                taskList.addTask(newTask);
                doGet(request, response);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid date format");
        }
    }
}
