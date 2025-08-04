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

        if (tasks.stream().anyMatch(t -> t.getDescription().equalsIgnoreCase(description.trim()))) {
            System.out.println("Task already exists");
            return;
        }

        String id = UUID.randomUUID().toString();
        TaskProperties task = new TaskProperties(id, description, "todo", today, today);
        tasks.add(task);
        FileManager.saveTasks(tasks);
        System.out.println("Task add successfully");
    }

    public void updateTask(String id, String description) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("Error: Task ID cannot be empty!");
            return;
        }

        if (description == null || description.trim().isEmpty()) {
            System.out.println("Error: Description cannot be empty!");
            return;
        }

        String cleanId = id.trim().replace("\"", "");
        System.out.println("Debug: Searching for ID -> " + cleanId); // Log de depuração

        TaskProperties task = findTaskById(cleanId);

        if (task != null) {
            System.out.println("Debug: Found task -> " + task); // Log de depuração
            task.setDescription(description.trim());
            task.setUpdatedAt(LocalDate.now());
            FileManager.saveTasks(tasks);
            System.out.println("Task updated successfully!");
        } else {
            System.out.println("Error: Task with ID '" + cleanId + "' not found. Available IDs:");
            tasks.forEach(t -> System.out.println("- " + t.getId()));
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

        // Limpa o ID (remove aspas se houver)
        String cleanId = id.trim().replace("\"", "");

        return tasks.stream()
                .filter(Objects::nonNull) // Filtra tarefas nulas
                .filter(task -> task.getId() != null) // Filtra IDs nulos
                .filter(task -> task.getId().equals(cleanId)) // Comparação exata
                .findFirst()
                .orElse(null);
    }
}
