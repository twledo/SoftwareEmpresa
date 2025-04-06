public class Main {
    public static void main(String[] args) {
        Enterprise enterprise = getEnterprise();

        // Atualização de salário
        enterprise.updateWage(2, 4500); // válido
        enterprise.updateWage(3, -500); // inválido
        enterprise.updateWage(999, 3000); // ID inexistente

        // Alteração de cargo
        enterprise.changeLoad(1, "Arquiteta de Software");
        enterprise.changeLoad(2, ""); // inválido
        enterprise.changeLoad(999, "Novo Cargo"); // ID inexistente

        // Exclusão de funcionários
        enterprise.deleteEmp(2); // existente
        enterprise.deleteEmp(999); // inexistente
        enterprise.deleteEmp(-1); // ID inválido

        // Mostra todos os funcionários restantes
        enterprise.showEmps();
    }

    private static Enterprise getEnterprise() {
        Enterprise enterprise = new Enterprise();

        // Testes de adição de funcionários
        enterprise.addEmp(1, "Alice", "Engenheira", 5000);
        enterprise.addEmp(2, "Bob", "Analista", 4200);
        enterprise.addEmp(3, "Carol", "Gerente", 6800);

        // Tentativa de adicionar com ID duplicado
        enterprise.addEmp(1, "Duplicado", "Teste", 1000);

        // Testes com dados inválidos
        enterprise.addEmp(-5, "ErroIDNegativo", "Dev", 3000);
        enterprise.addEmp(4, "", "VazioNome", 3000);
        enterprise.addEmp(5, "SemCargo", "", 3000);
        enterprise.addEmp(6, "SalarioZero", "Dev", 0);

        // Busca de funcionário
        enterprise.searchEmployee(1); // Deve encontrar
        enterprise.searchEmployee(999); // Não existe
        return enterprise;
    }
}
