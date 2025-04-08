import java.io.Serializable;

public class Employee implements Serializable {
    private final int ID;
    private final String nameEmployee;
    private String role; // Renomeado de load
    private double wage;

    public Employee(int ID, String nameEmployee, String role, double wage) {
        this.ID = ID;
        this.nameEmployee = nameEmployee;
        this.role = role;
        this.wage = wage;
    }

    public int getID() {
        return ID;
    }

    public String getNameEmployee() {
        return nameEmployee;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s | Cargo: %s | Salário: R$%.2f", ID, nameEmployee, role, wage);
    }
}