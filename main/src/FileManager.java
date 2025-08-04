import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class FileManager {
    private static final String FILE_NAME = "tasks.json";

    public static void saveTasks(List<TaskProperties> tasks) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[\n");

        for (int i = 0; i < tasks.size(); i++) {
            TaskProperties task = tasks.get(i);
            jsonBuilder.append("  {\n");
            jsonBuilder.append("    \"id\":\"").append(task.getId()).append("\",\n");
            jsonBuilder.append("    \"description\":\"").append(task.getDescription()).append("\",\n");
            jsonBuilder.append("    \"status\":\"").append(task.getStatus()).append("\",\n");
            jsonBuilder.append("    \"createdAt\":\"").append(task.getCreatedAt()).append("\",\n");
            jsonBuilder.append("    \"updatedAt\":\"").append(task.getUpdatedAt()).append("\"\n");
            jsonBuilder.append("  }");

            if (i < tasks.size() - 1) {
                jsonBuilder.append(",");
            }
            jsonBuilder.append("\n");
        }

        jsonBuilder.append("]");

        try (FileWriter file = new FileWriter(FILE_NAME)) {
            file.write(jsonBuilder.toString());
        } catch (IOException e) {
            System.err.println("Error while writing to file: " + e.getMessage());
        }
    }

    public static List<TaskProperties> loadTasks() {
        List<TaskProperties> tasks = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            StringBuilder content = new StringBuilder();
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine());
            }

            String jsonString = content.toString().trim();
            if (jsonString.isEmpty() || jsonString.equals("[]")) {
                return tasks;
            }

            jsonString = jsonString.substring(1, jsonString.length() - 1);
            String[] jsonObjects = jsonString.split("\\},\\s*\\{");

            for (String obj : jsonObjects) {
                if (!obj.startsWith("{")) obj = "{" + obj;
                if (!obj.endsWith("}")) obj = obj + "}";

                Map<String, String> taskMap = parseJsonObject(obj);

                TaskProperties task = new TaskProperties(
                        taskMap.getOrDefault("id", UUID.randomUUID().toString()), // Garante ID
                        taskMap.get("description"),
                        taskMap.get("status"),
                        LocalDate.parse(taskMap.get("createdAt")),
                        LocalDate.parse(taskMap.get("updatedAt"))
                );

                tasks.add(task);

                tasks.add(task);
            }

        } catch (Exception e) {
            System.err.println("Error while reading file: " + e.getMessage());
        }

        return tasks;
    }

    private static Map<String, String> parseJsonObject(String jsonObject) {
        Map<String, String> map = new HashMap<>();
        jsonObject = jsonObject.substring(1, jsonObject.length() - 1);

        String[] pairs = jsonObject.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":", 2);
            String key = keyValue[0].trim().replace("\"", "");
            String value = keyValue[1].trim();

            if(value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
                value = unescapeJson(value);
            }
            map.put(key, value);
        }
        return map;
    }

    private static String escapeJson(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("\\\"", "\"")
                .replace("\\\\", "\\")
                .replace("\\b", "\b")
                .replace("\\f", "\f")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t");
    }

    private static String unescapeJson(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

}
