import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class TaskManager {
    private List<TaskProperties> tasks;
    LocalDate today = LocalDate.now();

    public TaskManager() {
        this.tasks = FileManager.loadTasks();
    }

    public void addTask(String description) {

        if (description == null || description.trim().isEmpty()) {
            System.out.println("Description cannot be null or empty");
            return;
        }

        String id = UUID.randomUUID().toString();
        TaskProperties task = new TaskProperties(id, description, "todo", today, today);
        tasks.add(task);
        FileManager.saveTasks(tasks);
        System.out.println("Task add successfully");
    }

    public void updateTask(String id, String newDescription) {
        if (id == null || id.trim().isEmpty()) {
            System.err.println("Error: ID cannot be empty");
            return;
        }

        String cleanId = id.trim();

        TaskProperties taskToUpdate = tasks.stream()
                .filter(task -> task != null)
                .filter(task -> cleanId.equals(task.getId())) // Comparação exata
                .findFirst()
                .orElse(null);

        if (taskToUpdate != null) {
            taskToUpdate.setDescription(newDescription.trim());
            taskToUpdate.setUpdatedAt(LocalDate.now());
            FileManager.saveTasks(tasks);
            System.out.println("Task updated successfully!");
        } else {
            System.err.println("Error: Task not found. Current tasks:");
            tasks.forEach(t -> System.out.println("- ID: " + t.getId() + " | Desc: " + t.getDescription()));
        }
    }

    public void deleteTask(String id) {
        TaskProperties task = findTaskById(id);
        if (task != null) {
            tasks.remove(task);
            FileManager.saveTasks(tasks);
            System.out.println("Task delete successfully");
        } else {
            System.out.println("Task not found");
        }
    }

    public void changeTaskStatus(String id, String status) {
        TaskProperties task = findTaskById(id);
        if (task != null) {
            task.setStatus(status);
            FileManager.saveTasks(tasks);
            System.out.println("Task change successfully");
        } else {
            System.out.println("Task not found");
        }
    }

    public void listAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found");
        } else {
            tasks.forEach(System.out::println);
        }
    }

    public void listTasksByStatus(String status) {
        List<TaskProperties> filteredTasks = tasks.stream()
                .filter(task -> task.getStatus().equals(status))
                .collect(Collectors.toList());

        if(filteredTasks.isEmpty()) {
            System.out.println("No task found with status " + status);
            return;
        }
        filteredTasks.forEach(System.out::println);
    }


    private TaskProperties findTaskById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }

        String cleanId = id.trim().replace("\"", "");

        return tasks.stream()
                .filter(Objects::nonNull) // Filtra tarefas nulas
                .filter(task -> task.getId() != null) // Filtra IDs nulos
                .filter(task -> task.getId().equals(cleanId)) // Comparação exata
                .findFirst()
                .orElse(null);
    }
}
