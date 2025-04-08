import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Gerencia uma coleção de empregados, permitindo adição, busca, atualização e remoção.
 */
public class Enterprise {
    private final Map<Integer, Employee> employees;
    private final AtomicInteger nextID;
    private final PriorityQueue<Integer> availableIds;
    private final Logger logger;
    private final ResourceBundle messages;

    /**
     * Construtor da classe Enterprise.
     * @param logger Objeto para registro de mensagens
     * @param locale Localização para mensagens (ex.: pt_BR)
     */
    public Enterprise(Logger logger, Locale locale) {
        this.employees = new ConcurrentSkipListMap<>();
        this.nextID = new AtomicInteger(1);
        this.availableIds = new PriorityQueue<>();
        this.logger = logger;
        this.messages = ResourceBundle.getBundle("messages", locale);
    }

    /**
     * Retorna uma visão imutável dos empregados.
     * @return Mapa imutável de ID para Employee
     */
    public Map<Integer, Employee> getEmployees() {
        return Collections.unmodifiableMap(employees);
    }

    /**
     * Busca um empregado por ID.
     * @param id ID do empregado
     * @return Employee ou null se não encontrado
     */
    public Employee findById(int id) {
        if (id < 0) {
            return null;
        }
        return employees.get(id);
    }

    /**
     * Adiciona um novo empregado.
     * @param nameEmployee Nome do empregado
     * @param role Cargo do empregado
     * @param wage Salário do empregado
     * @throws IllegalArgumentException se os parâmetros forem inválidos
     */
    public void addEmp(String nameEmployee, String role, double wage) throws IllegalArgumentException {
        if (nameEmployee == null || nameEmployee.trim().isEmpty()) {
            throw new IllegalArgumentException(messages.getString("invalid.employee.name"));
        }
        if (!nameEmployee.matches("[a-zA-Z\\s]+")) {
            throw new IllegalArgumentException(messages.getString("invalid.employee.name.format"));
        }
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException(messages.getString("invalid.role"));
        }
        if (wage <= 0 || wage > 1_000_000) {
            throw new IllegalArgumentException(messages.getString("invalid.wage"));
        }

        int id = availableIds.isEmpty() ? nextID.getAndIncrement() : availableIds.poll();
        Employee employee = new Employee(id, nameEmployee.trim(), role.trim(), wage);
        employees.put(id, new Employee(id, employee.getNameEmployee(), employee.getRole(), employee.getWage())); // Cópia defensiva
        logger.log(messages.getString("employee.added").replace("{0}", nameEmployee.trim()).replace("{1}", String.valueOf(id)));
    }

    /**
     * Altera o cargo de um empregado.
     * @param id ID do empregado
     * @param role Novo cargo
     * @return true se alterado, false se não encontrado ou inválido
     * @throws IllegalArgumentException se os parâmetros forem inválidos
     */
    public boolean changeRole(int id, String role) throws IllegalArgumentException {
        if (id < 0) {
            logger.error(messages.getString("invalid.id"));
            throw new IllegalArgumentException(messages.getString("invalid.id"));
        }
        if (role == null || role.trim().isEmpty()) {
            logger.error(messages.getString("invalid.role"));
            throw new IllegalArgumentException(messages.getString("invalid.role"));
        }
        Employee employee = findById(id);
        if (employee != null) {
            employee.setRole(role.trim());
            logger.log(messages.getString("role.changed").replace("{0}", String.valueOf(id)).replace("{1}", role.trim()));
            return true;
        }
        logger.error(messages.getString("employee.not.found").replace("{0}", String.valueOf(id)));
        return false;
    }

    /**
     * Remove um empregado.
     * @param id ID do empregado
     * @return true se removido, false se não encontrado ou inválido
     * @throws IllegalArgumentException se o ID for inválido
     */
    public boolean deleteEmp(int id) throws IllegalArgumentException {
        if (id < 0) {
            logger.error(messages.getString("invalid.id"));
            throw new IllegalArgumentException(messages.getString("invalid.id"));
        }
        Employee employee = employees.remove(id);
        if (employee != null) {
            availableIds.offer(id); // Reutiliza o ID
            logger.log(messages.getString("employee.deleted").replace("{0}", String.valueOf(id)));
            return true;
        }
        logger.error(messages.getString("employee.not.found").replace("{0}", String.valueOf(id)));
        return false;
    }

    /**
     * Busca e exibe um empregado por ID.
     * @param id ID do empregado
     * @return true se encontrado, false se não encontrado
     */
    public boolean searchEmployee(int id) {
        if (id < 0) {
            logger.error(messages.getString("invalid.id"));
            return false;
        }
        Employee employee = findById(id);
        if (employee != null) {
            logger.log(messages.getString("employee.found").replace("{0}", employee.toString()));
            return true;
        }
        logger.error(messages.getString("employee.not.found").replace("{0}", String.valueOf(id)));
        return false;
    }

    /**
     * Exibe todos os empregados em formato tabular.
     */
    public void showEmps() {
        logger.log("-=- " + messages.getString("employee.list") + " -=-");
        if (employees.isEmpty()) {
            logger.error(messages.getString("employee.list.empty"));
        } else {
            logger.log(String.format("%-5s | %-20s | %-25s | %-15s", "ID", messages.getString("name"), messages.getString("role"), messages.getString("wage")));
            for (Employee emp : employees.values()) {
                logger.log(String.format("%-5d | %-20s | %-25s | R$%-14.2f", emp.getID(), emp.getNameEmployee(), emp.getRole(), emp.getWage()));
            }
        }
    }

    /**
     * Atualiza o salário de um empregado.
     * @param id ID do empregado
     * @param wage Novo salário
     * @return true se atualizado, false se não encontrado ou inválido
     * @throws IllegalArgumentException se o salário for inválido
     */
    public boolean updateWage(int id, double wage) throws IllegalArgumentException {
        if (wage <= 0 || wage > 1_000_000) {
            logger.error(messages.getString("invalid.wage"));
            throw new IllegalArgumentException(messages.getString("invalid.wage"));
        }
        Employee employee = findById(id);
        if (employee != null) {
            employee.setWage(wage);
            logger.log(messages.getString("wage.updated").replace("{0}", String.valueOf(id)).replace("{1}", String.format("%.2f", wage)));
            return true;
        }
        logger.error(messages.getString("employee.not.found").replace("{0}", String.valueOf(id)));
        return false;
    }

    /**
     * Salva os empregados em um arquivo.
     * @param filename Nome do arquivo
     * @throws IOException se ocorrer um erro de I/O
     */
    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(new HashMap<>(employees));
            logger.log(messages.getString("data.saved").replace("{0}", filename));
        }
    }

    /**
     * Carrega empregados de um arquivo.
     * @param filename Nome do arquivo
     * @throws IOException se ocorrer um erro de I/O
     * @throws ClassNotFoundException se o arquivo contiver dados inválidos
     */
    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Map<Integer, Employee> loaded = (Map<Integer, Employee>) ois.readObject();
            employees.clear();
            employees.putAll(loaded);
            // Atualiza nextID e availableIds
            int maxId = loaded.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
            nextID.set(maxId + 1);
            availableIds.clear();
            logger.log(messages.getString("data.loaded").replace("{0}", filename));
        }
    }
}