package fp.funciones.test;
import java.util.ArrayList;
import java.util.List;
import fp.funciones.Funciones;

public class TestFunciones {
	public static void main(String[] args) {
		//Test esPrimo
		System.out.println("---TEST esPrimo---");
		testEsPrimo(0);
		testEsPrimo(3);
		testEsPrimo(4);
		//Test numeroCombinatorio
		System.out.println("---TEST numeroCombinatorio---");
		testNumeroCombinatorio(3,4);
		testNumeroCombinatorio(6,4);
		//Test numeroS
		System.out.println("---TEST numeroS---");
		testNumeroS(3,4);
		testNumeroS(6,4);
		//Test diferencias
		System.out.println("---TEST diferencias---");
		testDiferencias();
		//Test cadenaMasLarga
		System.out.println("---TEST cadenaMasLarga---");
		testCadenaMasLarga();
	}
	
	public static void testEsPrimo(int res) {
		try {
			boolean primo= Funciones.esPrimo(res);
		if(primo) {
		    System.out.println("El numero es primo");
		}
		else {System.out.println("El numero no es primo");
		}
		}catch(IllegalArgumentException e) {
			System.out.println("Error: "+ e.getMessage());
			
		}
	}
	public static void testNumeroCombinatorio(int n,int k ){
		try {
			Double numcom= Funciones.numeroCombinatorio(n, k);
			System.out.println("El resultado es: "+ numcom);
		}catch(IllegalArgumentException e){
			System.out.println("Error: "+ e.getMessage());
		}
	}
	public static void testNumeroS(int n, int k) {
		try {
		Double numS= Funciones.calculoNumeroS(n, k);
		System.out.println("El resultado es:"+ numS);
		}catch(IllegalArgumentException e) {
			System.out.println("Error: "+ e.getMessage());
		}
	}
	public static void testDiferencias(){
		List<Integer> numeros= new ArrayList<>();
		numeros.add(4);
		numeros.add(5);
		numeros.add(7);
		numeros.add(10);
		numeros.add(14);
		numeros.add(19);
		System.out.println("las diferencias son: "+ Funciones.diferencias(numeros));
	}
	public static void testCadenaMasLarga() {
		List<String> caracteres= new ArrayList<>();
		caracteres.add("Ordenador");
		caracteres.add("Raton");
		caracteres.add("Teclado");
		caracteres.add("Pantallas");
		System.out.println("La cadena o cadenas mas largas son: "+ Funciones.cadenaMasLarga(caracteres));
		
	}	
		
}


