package fp.funciones.test;
import java.util.ArrayList;
import java.util.List;
import fp.funciones.Funciones;

public class TestFunciones {
	public static void main(String[] args) {
		
		//Test esPrimo normal
		if(Funciones.esPrimo(3)) {
		    System.out.println("El numero es primo");
		}
		else {System.out.println("El numero no es primo");
		}
		//Test para que cuando salga error, se imprima por pantalla pero permita imprimir las demas funciones (no salga en rojo)
		try {
			boolean primo= Funciones.esPrimo(0);
		if(primo) {
		    System.out.println("El numero es primo");
		}
		else {System.out.println("El numero no es primo");
		}
		}catch(IllegalArgumentException e) {
			System.out.println("Error: "+ e.getMessage());
		}
		//Test numeroCombinatorio normal
		System.out.println("El resultado es: " +Funciones.numeroCombinatorio(6, 4));
		//Test para que cuando salga error, se imprima por pantalla pero permita hacer las demas funciones (no salga en rojo)
		try {
			Double numcom= Funciones.numeroCombinatorio(3, 4);
			System.out.println("El resultado es: "+ numcom);
		}catch(IllegalArgumentException e){
			System.out.println("Error: "+ e.getMessage());
		}
		//Test calculoNumeroS normal
		System.out.println("El resultado es: "+Funciones.calculoNumeroS(6, 4));
		//Test para que cuando salga error, se imprima por pantalla pero permita hacer las demas funciones (no salga en rojo)
		try {
		Double numS= Funciones.calculoNumeroS(3, 4);
		System.out.println("El resultado es :"+ numS);
		}catch(IllegalArgumentException e) {
			System.out.println("Error: "+ e.getMessage());
		}
		//Test diferencias
		List<Integer> numeros= new ArrayList<>();
		numeros.add(4);
		numeros.add(5);
		numeros.add(7);
		numeros.add(10);
		numeros.add(14);
		numeros.add(19);
		System.out.println("las diferencias son: "+ Funciones.diferencias(numeros));
		//Test cadenaMasLarga
		List<String> caracteres= new ArrayList<>();
		caracteres.add("Ordenador");
		caracteres.add("Raton");
		caracteres.add("Teclado");
		caracteres.add("Pantallas");
		System.out.println("La/s cadena/s mas larga/s es/son: "+ Funciones.cadenaMasLarga(caracteres));
		
		
		
	}

}
