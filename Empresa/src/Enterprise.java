import java.util.ArrayList;

public class Enterprise {

    public Enterprise() {
        this.employeeArrayList = new ArrayList<>();
    }

    private final ArrayList<Employee> employeeArrayList;

    private void log(String type, String mensagem) {
        String cor = switch (type.toLowerCase()) {
            case "error" -> "\u001B[31m"; // Vermelho
            case "ok" -> "\u001B[32m"; // Verde
            case "info" -> "\u001B[34m"; // Azul
            case "array" -> "\u001B[33m"; // Amarelo
            default -> "\u001B[0m"; // Padrão
        };
        System.out.println(cor + "[" + type.toUpperCase() + "] " + mensagem + "\u001B[0m");
    }

    public Employee findById(int id) {
        if (id < 0) {
            log("error", "Impossível achar ID negativo");
            return null;
        }

        for (Employee newEmp : employeeArrayList) {
            if (newEmp.getID() == id) {
                return newEmp;
            }
        }
        return null;
    }

    public void addEmp(int id, String nameEmployee, String load, int wage) {
        if (findById(id) != null) {
            log("error", "Usuário com ID: " + id + " já existente. Adicione com o ID: " + (employeeArrayList.size() + 1));
            return;
        }

        // Validações
        if (id < 0) {
            log("error", "ID inválido.");
            return;
        }

        if (nameEmployee == null || nameEmployee.trim().isEmpty()) {
            log("error", "Nome do funcionário inválido.");
            return;
        }

        if (load == null || load.trim().isEmpty()) {
            log("error", "Cargo do funcionário inválido.");
            return;
        }

        if (wage <= 0) {
            log("error", "Salário deve ser maior que zero.");
            return;
        }

        Employee newEmp = new Employee(id, nameEmployee.trim(), load.trim(), wage);
        employeeArrayList.add(newEmp);
        log("ok", "Funcionario " + nameEmployee.trim() + " adicionado ao ID: " + id);
    }

    public void changeLoad(int id, String newLoad) {
        Employee newEmp = findById(id);

        if (id < 0) {
            log("error", "ID inválido.");
            return;
        }

        if (newLoad == null || newLoad.trim().isEmpty()) {
            log("error", "Cargo do funcionário inválido.");
            return;
        }

        if (newEmp != null) {
            newEmp.setLoad(newLoad);
            log("info", "Cargo alterado para: " + newEmp.getLoad());
            return;
        }
        log("error", "Funcionário com ID " + id + " não encontrado.");
    }

    public void deleteEmp(int id) {
        Employee newEmp = findById(id);

        if (id < 0) {
            log("error", "ID inválido.");
            return;
        }

        if (newEmp != null) {
            employeeArrayList.remove(newEmp);
            log("info", "Usuário " + newEmp.getID() + " deletado");
            return;
        }
        log("error", "Funcionário com ID " + id + " não encontrado.");
    }

    public void searchEmployee(int id) {
        Employee newEmp = findById(id);
        if (newEmp != null) {
            log("info", "Funcionário encontrado: " + newEmp);
            return;
        }
        log("error", "Funcionário com ID " + id + " não encontrado.");
    }

    public void showEmps() {
        log("array", "-=- Lista de funcionários -=-");
        if (employeeArrayList.isEmpty()) {
            log("error", "Nenhum funcionário encontrado na array");
        } else {
            for (Employee newEmp : employeeArrayList) {
                System.out.println("\u001B[33m" + newEmp + "\u001B[0m"); // Amarelo
            }
        }
    }

    public void updateWage(int id, double newUpdate) {
        Employee newEmp = findById(id);
        if (newUpdate <= 0) {
            log("error", "Salário deve ser maior que zero.");
            return;
        }

        if (newEmp != null) {
            newEmp.setWage(newUpdate);
            log("info", "Salário atualizado para " + newEmp.getWage());
            return;
        }
        log("error", "Funcionário com ID " + id + " não encontrado.");
    }
}
