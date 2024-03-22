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
		List<Integer> numeros= new ArrayList<>(List.of(4,5,7,10,14,19));
		System.out.println("---TEST diferencias---");
		testDiferencias(numeros);
		//Test cadenaMasLarga
		List<String> caracteres= new ArrayList<>(List.of("Ordenador","Raton","Teclado","Pantallas"));
		System.out.println("---TEST cadenaMasLarga---");
		testCadenaMasLarga(caracteres);
		System.out.println("-----Aqui empieza la defensa---");
		//Test P2
		testP2(5,2,1);
		testP2(2,2,5);
		//Test C2
		testC2(6,4);
		testC2(3,4);
		//Test C2
		testS2(6,4);
		testS2(3,4);
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
	public static void testDiferencias(List<Integer> numeros){
		System.out.println("las diferencias son: "+ Funciones.diferencias(numeros));
	}
	public static void testCadenaMasLarga(List<String> caracteres) {
		System.out.println("La cadena o cadenas mas largas son: "+ Funciones.cadenaMasLarga(caracteres));
		
	}	
	//AQUI EMPIEZA LA DEFENSA
	
	public static void testP2 ( int n, int k, int i) {
		try {
			Double resultado= Funciones.P2(n,k,i);
			System.out.println("El resultado es:"+ resultado);
			}catch(IllegalArgumentException e) {
				System.out.println("Error: "+ e.getMessage());
			}
	}
	public static void testC2(int n,int k ){
		try {
			Double numcom= Funciones.C2(n, k);
			System.out.println("El resultado es: "+ numcom);
		}catch(IllegalArgumentException e){
			System.out.println("Error: "+ e.getMessage());
		}
	}
	public static void testS2(int n, int k) {
		try {
		Double numS= Funciones.S2(n, k);
		System.out.println("El resultado es:"+ numS);
		}catch(IllegalArgumentException e) {
			System.out.println("Error: "+ e.getMessage());
		}
	}
	
		
}


