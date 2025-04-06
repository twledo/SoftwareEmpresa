public class Employee {
    private int ID;
    private String nameEmployee;
    private String load; //emprego
    private double wage; //salario

    public Employee(int ID, String nameEmployee, String load, double wage) {
        this.ID = ID;
        this.nameEmployee = nameEmployee;
        this.load = load;
        this.wage = wage;
    }

    @Override
    public String toString() {
        return "ID: " + ID + " | Nome: " + nameEmployee + " | Cargo: " + load + " | Salário: R$" + wage;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNameEmployee() {
        return nameEmployee;
    }

    public void setNameEmployee(String nameEmployee) {
        this.nameEmployee = nameEmployee;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }
}