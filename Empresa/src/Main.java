import java.io.IOException;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        // Inicializa com logger e locale pt_BR
        Enterprise empresa = new Enterprise(new ConsoleLogger(), new Locale("pt", "BR"));

        // Teste 1: Adição
        System.out.println("=== Teste de Adição ===");
        try {
            empresa.addEmp("Thiago Silva", "Desenvolvedor", 5000.0);
            empresa.addEmp("Ana Costa", "Gerente", 6000.0);
            empresa.addEmp("Pedro Almeida", "Analista", 4500.0);
            empresa.addEmp("123", "Testador", 3000.0); // Deve falhar
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }

        // Teste 2: Listagem
        System.out.println("\n=== Teste de Listagem ===");
        empresa.showEmps();

        // Teste 3: Busca
        System.out.println("\n=== Teste de Busca ===");
        empresa.searchEmployee(2);
        empresa.searchEmployee(5);

        // Teste 4: Alteração de Cargo
        System.out.println("\n=== Teste de Alteração de Cargo ===");
        try {
            empresa.changeRole(1, "Engenheiro de Software");
            empresa.changeRole(10, "Diretor");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }

        // Teste 5: Atualização de Salário
        System.out.println("\n=== Teste de Atualização de Salário ===");
        try {
            empresa.updateWage(2, 7000.0);
            empresa.updateWage(3, -1000.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }

        // Teste 6: Deleção
        System.out.println("\n=== Teste de Deleção ===");
        try {
            boolean deleted = empresa.deleteEmp(1);
            System.out.println("Deleção bem-sucedida? " + deleted);
            empresa.deleteEmp(-1);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }

        // Teste 7: Listagem Final
        System.out.println("\n=== Listagem Final ===");
        empresa.showEmps();

        // Teste 8: Persistência
        System.out.println("\n=== Teste de Persistência ===");
        try {
            empresa.saveToFile("employees.dat");
            Enterprise empresa2 = new Enterprise(new ConsoleLogger(), new Locale("pt", "BR"));
            empresa2.loadFromFile("employees.dat");
            empresa2.showEmps();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro de persistência: " + e.getMessage());
        }
    }
}