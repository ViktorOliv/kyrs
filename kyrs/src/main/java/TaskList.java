import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Task> findTasksByTitle(String title) {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getTitle().contains(title)) {
                result.add(task);
            }
        }
        return result;
    }

    public boolean hasTimeConflict(Task newTask) {
        for (Task task : tasks) {
            if (task.getStartTime().before(newTask.getEndTime()) && task.getEndTime().after(newTask.getStartTime())) {
                return true;
            }
        }
        return false;
    }
}
