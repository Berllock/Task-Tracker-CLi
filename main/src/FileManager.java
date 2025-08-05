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
            String content = scanner.useDelimiter("\\Z").next().trim();

            if (content.isEmpty() || content.equals("[]")) {
                return tasks;
            }

            content = content.substring(1, content.length() - 1).trim();
            String[] jsonObjects = content.split("(?<=\\})\\s*,\\s*(?=\\{)");

            for (String jsonObj : jsonObjects) {
                try {
                    if (!jsonObj.startsWith("{")) jsonObj = "{" + jsonObj;
                    if (!jsonObj.endsWith("}")) jsonObj = jsonObj + "}";

                    Map<String, String> taskMap = parseJsonObject(jsonObj);

                    String id = taskMap.get("id");
                    if (id == null) {
                        System.err.println("Warning: Found task with null ID, skipping");
                        continue;
                    }

                    TaskProperties task = new TaskProperties(
                            id,
                            taskMap.get("description"),
                            taskMap.get("status"),
                            LocalDate.parse(taskMap.get("createdAt")),
                            LocalDate.parse(taskMap.get("updatedAt"))
                    );

                    tasks.add(task);
                } catch (Exception e) {
                    System.err.println("Error parsing task: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return tasks;
    }

    private static Map<String, String> parseJsonObject(String jsonObject) {
        Map<String, String> map = new HashMap<>();

        try {
            jsonObject = jsonObject.substring(1, jsonObject.length() - 1).trim();
            String[] pairs = jsonObject.split("\\s*,\\s*");

            for (String pair : pairs) {
                String[] keyValue = pair.split("\\s*:\\s*", 2);
                if (keyValue.length == 2) {
                    String key = keyValue[0].replace("\"", "").trim();
                    String value = keyValue[1].trim();

                    if (value.startsWith("\"") && value.endsWith("\"")) {
                        value = value.substring(1, value.length() - 1);
                    }

                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing JSON object: " + e.getMessage());
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
