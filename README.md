# Task Tracker CLI

![Java](https://img.shields.io/badge/Java-17+-blue)
![License](https://img.shields.io/badge/License-MIT-green)

Um gerenciador de tarefas via linha de comando (CLI) que permite criar, atualizar e acompanhar suas tarefas diárias.

## 📦 Funcionalidades

- ✅ Adicionar novas tarefas
- ✏️ Atualizar descrições de tarefas existentes
- 🗑️ Remover tarefas
- 🔄 Alterar status das tarefas (todo, inprogress, done)
- 📋 Listar tarefas com diferentes filtros
- 💾 Salvar automaticamente em arquivo JSON

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Java 17+
- **Armazenamento**: JSON (sem bibliotecas externas)
- **Gerenciamento de Dependências**: Maven

## ⚙️ Como Usar

### Pré-requisitos

- JDK 17 ou superior instalado
- Git (para clonar o repositório)

### Instalação

1. Clone o repositório:
```bash
git clone https://github.com/Berllock/Task-Tracker-CLi.git
```

2. Navegue até o diretório do projeto:
```bash
cd Task-Tracker-CLi
```

3. Compile o projeto:
```bash
javac -d bin src/*.java
```

### Comandos Disponíveis

| Comando                | Descrição                                      | Exemplo                           |
|------------------------|-----------------------------------------------|-----------------------------------|
| `add <descrição>`      | Adiciona nova tarefa                          | `add "Estudar Java"`             |
| `update <id> <desc>`   | Atualiza descrição da tarefa                  | `update 123 "Estudar Java 8"`    |
| `delete <id>`          | Remove uma tarefa                             | `delete 123`                     |
| `status <id> <status>` | Altera status (todo/inprogress/done)          | `status 123 inprogress`          |
| `list`                 | Lista todas as tarefas                        | `list`                           |
| `list-todo`            | Lista tarefas não iniciadas                   | `list-todo`                      |
| `list-inprogress`      | Lista tarefas em progresso                    | `list-inprogress`                |
| `list-done`            | Lista tarefas concluídas                      | `list-done`                      |
| `help`                 | Mostra esta ajuda                             | `help`                           |

### Executando

```bash
java -cp bin Main <comando> [argumentos]
```

## 📁 Estrutura do Projeto

```
task-tracker-cli/
├── src/
│   ├── Main.java            # Ponto de entrada e interface CLI
│   ├── TaskManager.java     # Lógica principal de gerenciamento
│   ├── TaskProperties.java  # Modelo de dados das tarefas
│   └── FileManager.java     # Manipulação do arquivo JSON
└── tasks.json               # Arquivo de armazenamento (gerado automaticamente)
```

## 📝 Formatos

### Estrutura do JSON
```json
[
  {
    "id": "uuid-string",
    "description": "Texto da tarefa",
    "status": "todo/inprogress/done",
    "createdAt": "YYYY-MM-DD",
    "updatedAt": "YYYY-MM-DD"
  }
]
```

## 📄 Licença

Distribuído sob a licença MIT. Veja `LICENSE` para mais informações.

## ✉️ Contato

Breno Souza - [Linkedin](https://www.linkedin.com/in/breno-berllock/) - brenosouzaemp@gmail.com

Link do Projeto: [https://github.com/Berllock/Task-Tracker-CLi.git](https://github.com/Berllock/Task-Tracker-CLi.git)
