package fp.Dataframe;
import fp.Dataframe.*;
import java.util.List;
import java.time.LocalDate;
public class TestDataFrame {

	public static void main(String[] args) {
		DataFrame d = DataFrame.parse("src/fp/Dataframe/personas.csv", 
				List.of("Id","Nombre","Apellidos","Altura","Fecha_Nacimiento")); 
		
		        //TEST 1
				System.out.println(d);
				
				//TEST 2
				System.out.println("---PRIMERAS DIEZ FILAS----");
				System.out.println(d.head(10));
				System.out.println("---ULTIMAS DIEZ FILAS----");
				System.out.println(d.tail(10));
				System.out.println("---PRIMERAS CINCO FILAS----");
				System.out.println(d.head());
				System.out.println("---ULTIMAS CINCO FILAS----");
				System.out.println(d.tail());
				
				//TEST 3
				int n=5;
				int m=20;
				System.out.println("---FILAS ENTRE LA NUM "+n+ " Y LA NUM " +m+ " ---");
				System.out.println(d.slice(n, m));
				
				//TEST 4
				String columnaelim="Nombre";
				System.out.println("--- NUEVO DATAFRAME SIN LA COLUMNA: "+columnaelim+ " ---");
				DataFrame d1= d.removeColum(columnaelim);
				System.out.println(d1);
				
				//TEST 5
				//Obteniendo la celda para la columna Nombre, para la fila cuyo valor Id es 73
				System.out.println("---BUSCAR CELDA ESPECIFICA---");
				System.out.println(d.cell("73", "Id", "Nombre"));
				//Filtrando filas con fecha posterior al 1/1/1998
				System.out.println("---FILTRADO POR FECHA POSTERIOR A---");
				System.out.println(d.filter(lista -> DataFrame.parse(lista.get(4), LocalDate.class).isAfter(LocalDate.of(1998, 1, 1))));
				//Filtrando filas con fecha anterior al 1/1/1992
				System.out.println("---FILTRADO POR FECHA ANTERIOR A---");
				System.out.println(d.filter(lista -> DataFrame.parse(lista.get(4), LocalDate.class).isBefore(LocalDate.of(1992, 1, 1))));
				//Creando una columna con el Usuario de Instagram
				System.out.println("---AÑADIR COLUMNA CALCULADA---");
				System.out.println(d.addCalculatedColum("Usuario", lista -> "@"+lista.get(1)+lista.get(0)));
				
				//TEST 6
				//Filtrando por apellidos posteriores a la R
				System.out.println("---FILTRADO POR APELLIDO POSTERIOR A---");
				System.out.println(d.filter(lista -> {
				    String apellido = lista.get(2);
				    return apellido.compareToIgnoreCase("R") >= 0;}));
				
				
		       
				
			
	}

}
