package fp.banco;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Comparator;
import java.time.MonthDay;
public class ExamenEntrega3 {

   public static List<Empleado> empleadosEntreDiaMes (Banco banco, String ini, String fin){
		 String[] iniParts = ini.split("/");
	     String[] finParts = fin.split("/");

	     if (iniParts.length != 2 || finParts.length != 2) {
	    	 throw new IllegalArgumentException("Formato incorrecto. Debe ser DD/MM.");
	     }

	     int iniDia = Integer.parseInt(iniParts[0]);
	     int iniMes = Integer.parseInt(iniParts[1]);
	     int finDia = Integer.parseInt(finParts[0]);
	     int finMes = Integer.parseInt(finParts[1]);

	     if (iniDia < 1 || iniDia > 31 || finDia < 1 || finDia > 31) {
	         throw new IllegalArgumentException("El día debe estar entre 1 y 31.");
	     }
	     if (iniMes < 1 || iniMes > 12 || finMes < 1 || finMes > 12) {
	         throw new IllegalArgumentException("El mes debe estar entre 1 y 12.");
	     }
	     MonthDay iniDate = MonthDay.of(iniMes, iniDia);
	     MonthDay finDate = MonthDay.of(finMes, finDia);

	     if (iniDate.isAfter(finDate)) {
	    	 throw new IllegalArgumentException("La fecha de inicio debe ser menor o igual que la fecha de fin.");
	     }
	        
	     return banco.empleados().todos().stream()
	                .filter(empleado -> {
	                    LocalDateTime birthDate = empleado.persona().fechaDeNacimiento();
	                    MonthDay birthMonthDay = MonthDay.from(birthDate);
	                    return (birthMonthDay.equals(iniDate) || birthMonthDay.equals(finDate) || 
	                            (birthMonthDay.isAfter(iniDate) && birthMonthDay.isBefore(finDate)));
	                })
	                .collect(Collectors.toList());
	}

    public static Map<Character, List<Empleado>> empleadosConLetraDni(Banco banco, Set<Character> letras) {
        // Verificar si todos los parámetros están informados
        if (banco == null || letras == null) {
            throw new IllegalArgumentException("Todos los parámetros deben estar informados");
        }

        // Verificar si el conjunto de letras solo contiene caracteres alfabéticos
        if (letras.stream().anyMatch(letra -> !Character.isAlphabetic(letra))) {
            throw new IllegalArgumentException("El conjunto de letras solo debe contener caracteres alfabéticos");
        }

        // Mapear empleados por letra del DNI
        Map<Character, List<Empleado>> empleadosPorLetra = new HashMap<>();
        for (char letra : letras) {
            List<Empleado> empleadosConLetra = banco.empleados().todos().stream()
                    .filter(empleado -> empleado.dni().charAt(8) == letra)
                    .collect(Collectors.toList());
            empleadosPorLetra.put(letra, empleadosConLetra);
        }

        return empleadosPorLetra;
    }
    
    public static Double clientesEdadMedia(Banco banco, Integer m) {
        // Verificar si todos los parámetros están informados
        if (banco == null || m == null) {
            throw new IllegalArgumentException("Todos los parámetros deben estar informados");
        }

        // Verificar si m es positivo
        if (m <= 0) {
            throw new IllegalArgumentException("m debe ser un valor positivo");
        }

        // Calcular la edad media de los clientes mayores o iguales a m
        long totalClientes = banco.personas().todos().stream()
                .filter(persona -> persona.edad() >= m)
                .count();

        if (totalClientes == 0) {
            return 0.0;
        }

        long sumaEdades = banco.personas().todos().stream()
                .filter(persona -> persona.edad() >= m)
                .mapToLong(persona -> {
                    LocalDate ahora = LocalDate.now();
                    return ahora.getYear() - persona.fechaDeNacimiento().getYear();
                })
                .sum();

        return (double) sumaEdades / totalClientes;
    }
    public static Set<String> clientesConMenosPrestamos(Banco banco, int n) {
        // Verificar si todos los parámetros están informados
        if (banco == null || n <= 0) {
            throw new IllegalArgumentException("Todos los parámetros deben estar informados y n debe ser mayor que 0");
        }

        // Obtener los DNI de los clientes con menos prestamos
        Set<String> clientes = banco.personas().todos().stream()
                .filter(persona -> banco.prestamosDeCliente(persona.dni()).size() > 0)
                .sorted(Comparator.comparingInt(persona -> banco.prestamosDeCliente(persona.dni()).size()))
                .limit(n)
                .map(persona -> persona.dni())
                .collect(Collectors.toSet());

        return clientes;
    }
    
    
    public static void main(String[] args) {
        Banco banco = Banco.of();
        
        // Prueba del método empleadosEntreDiaMes
        System.out.println("Empleados nacidos entre el 01/01 y el 31/01:");//devuelve el dni del empleado y su fecha de contratacion 
        System.out.println(ExamenEntrega3.empleadosEntreDiaMes(banco, "01/01", "31/01")); //(su fecha de nacimiento SÏ que esta en el rango)
        System.out.println();

        // Prueba del método empleadosConLetraDni
        Set<Character> letras = Set.of('A', 'B', 'C');
        System.out.println("Empleados cuyo DNI comienza con A, B o C:");
        System.out.println(ExamenEntrega3.empleadosConLetraDni(banco, letras));
        System.out.println();

        // Prueba del método clientesEdadMedia
        System.out.println("Edad media de los clientes mayores o iguales a 30 años:");
        System.out.println(ExamenEntrega3.clientesEdadMedia(banco, 30));
        System.out.println();

        // Prueba del método clientesConMenosPrestamos
        System.out.println("DNI de los 3 clientes con menos préstamos:");
        System.out.println(ExamenEntrega3.clientesConMenosPrestamos(banco, 3));
    }
}
