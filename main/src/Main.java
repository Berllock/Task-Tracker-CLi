public class Main {
    public static void main(String[] args) {



        if (args.length == 0) {
            printHelp();
            return;
        }

        TaskManager taskManager = new TaskManager();
        String command = args[0].toLowerCase();

        try {
            switch (command) {
                case "add":
                    if (args.length < 2) {
                        System.err.println("Descrição da tarefa é obrigatória!");
                        return;
                    }
                    taskManager.addTask(args[1]);
                    break;

                case "update":
                    if (args.length < 3) {
                        System.err.println("ID e nova descrição são obrigatórios!");
                        return;
                    }
                    taskManager.updateTask(args[1], args[2]);
                    break;

                case "delete":
                    if (args.length < 2) {
                        System.err.println("ID da tarefa é obrigatório!");
                        return;
                    }
                    taskManager.deleteTask(args[1]);
                    break;

                case "status":
                    if (args.length < 3) {
                        System.err.println("ID e novo status são obrigatórios!");
                        return;
                    }
                    taskManager.changeTaskStatus(args[1], args[2]);
                    break;

                case "list":
                    taskManager.listAllTasks();
                    break;

                case "list-done":
                    taskManager.listTasksByStatus("done");
                    break;

                case "list-todo":
                    taskManager.listTasksByStatus("todo");
                    break;

                case "list-inprogress":
                    taskManager.listTasksByStatus("inprogress");
                    break;

                case "help":
                    printHelp();
                    break;

                default:
                    System.err.println("Comando inválido!");
                    printHelp();
            }
        } catch (Exception e) {
            System.err.println("Ocorreu um erro: " + e.getMessage());
        }
    }

    private static void printHelp() {
        System.out.println("Uso: java Main <comando> [argumentos]");
        System.out.println("\nComandos disponíveis:");
        System.out.println("  add <descrição>          - Adiciona uma nova tarefa");
        System.out.println("  update <id> <descrição>  - Atualiza a descrição de uma tarefa");
        System.out.println("  delete <id>             - Remove uma tarefa");
        System.out.println("  status <id> <status>     - Altera o status de uma tarefa (todo, inprogress, done)");
        System.out.println("  list                     - Lista todas as tarefas");
        System.out.println("  list-done                - Lista tarefas concluídas");
        System.out.println("  list-todo                - Lista tarefas a fazer");
        System.out.println("  list-inprogress          - Lista tarefas em progresso");
        System.out.println("  help                     - Mostra esta ajuda");
    }
}