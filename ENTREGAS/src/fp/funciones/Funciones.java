package fp.funciones;

import us.lsi.tools.Preconditions
;  
import java.util.List;

import fp.tipos.Fecha;

import java.util.ArrayList;


public class Funciones {
	public static boolean esPrimo(Integer n) {
		Preconditions.checkArgument(n>=1, "El numero es nulo");
		boolean res = true;
		for(Integer i= 2;i <= Math.sqrt(n);i++) {
			if(n%i == 0) {
				res = false;
				break;
			}
		}
		return res;
	}
	//Calculo de factorial
		public static Double factorial(Integer n) {
			if (n == 0 || n == 1) {
	            return 1.;
	        } else {
	            Double factorial = 1.;
	            for (int i = 2; i <= n; i++) {
	                factorial *= i;
	            }
	            return factorial; 
	        }
		}
		
		public static Double numeroCombinatorio(Integer n, Integer k) {
			Preconditions.checkArgument(n>=k, String.format("El valor n (%d) ha de ser mayor que k (%d)", n,k));
			Double x = Funciones.factorial(n);
			Double y = Funciones.factorial(k)*(Funciones.factorial(n-k));
			Double res = x/y;
			return res;
		}
		
		public static Double calculoNumeroS(Integer n, Integer k) {
			Preconditions.checkArgument(n>=k,String.format("El valor n (%d) ha de ser mayor que k (%d)",n,k));
			Double primOP = 1/(Funciones.factorial(k));
			Double sumatorio =0. ;
			for(Integer i=0;i<=k;i++) {
				Double x = Math.pow(-1, i);
				Double y = Funciones.numeroCombinatorio(k, i);
				Double z = Math.pow((k-i), n);
				sumatorio += x * y * z;
				}
			return primOP*sumatorio;
		}
		
		public static List<Integer> diferencias(List<Integer> numeros){
			List<Integer> diferencias= new ArrayList<>();
			
			if (numeros == null || numeros.size() <= 1) {
	            return diferencias;
	        }
			
			for(Integer i = 1;i < numeros.size();i++) {
				Integer diferencia = numeros.get(i) - numeros.get(i-1);
				diferencias.add(diferencia);
			}
			return diferencias;
		}
		
		public static List<String> cadenaMasLarga(List<String> caracteres) {
			 List<String> masLargas = new ArrayList<>();
		        int longitudMasLarga = 0;
		        for (String cadena : caracteres) {
		            int longitudCadena = cadena.length();
		            if (longitudCadena > longitudMasLarga) {
		                masLargas.clear(); 
		                masLargas.add(cadena);
		                longitudMasLarga = longitudCadena;
		            } else if (longitudCadena == longitudMasLarga) {
		                masLargas.add(cadena); 
		            }
		        }
		        return masLargas;
		}

	}	
