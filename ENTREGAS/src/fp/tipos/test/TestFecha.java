package fp.tipos.test;
import fp.tipos.Fecha;

public class TestFecha {

	public static void main(String[] args) {
		Fecha fecha= Fecha.of(2023, 11, 25);
		Fecha fecha1= Fecha.of(2023, 11, 30);
		//Test metodo of
		System.out.println("---TEST metodo of---");
		System.out.println(fecha);
		//Test metodo parse
		System.out.println("---TEST metodo parse---");
		System.out.println(Fecha.parse("2023-11-25"));
		//Test nombreMes
		System.out.println("---TEST nombreMes---");
		testNombreMes(fecha);
		//Test sumarDias
		System.out.println("---TEST sumarDias---");
		testSumarDias(fecha,5);
		//Test restarDias
		System.out.println("---TEST restarDias---");
		testRestarDias(fecha,5);
		//Test diferenciaEnDias
		System.out.println("---TEST diferenciaEnDias---");
		testDiferenciaEnDias(fecha,fecha1);
		//Test esAñoBisiesto
		System.out.println("---TEST esAñoBisiesto---");
		testEsAñoBisiesto(2024);
		testEsAñoBisiesto(2023);
		System.out.println("---TEST diasEnMes---");
		//Test diasEnMes
		testDiasEnMes(2023,11);
		testDiasEnMes(2023,2);
		testDiasEnMes(2024,2);
	}
	public static void testNombreMes(Fecha fecha) {
		String nombreMes= fecha.nombreMes();
		System.out.println(nombreMes);
	}
	public static void testDiaSemana(Fecha fecha) {
       String diaSemana = fecha.diaSemana();
       System.out.println(diaSemana);
	}
	public static void testSumarDias(Fecha fecha,int n) {
       Fecha fecha1 = fecha.sumarDias(n);
       System.out.format("Si a la fecha : " + fecha + " le sumamos %d dias," ,n); System.out.println(" la fecha resultante es: "+fecha1);
	}
	public static void testRestarDias(Fecha fecha,int n) {
       Fecha fecha2 = fecha.restarDias(n);
       System.out.format("Si a la fecha : " + fecha + " le restamos %d dias, " ,n); System.out.println("la fecha resultante es: "+fecha2);
	}
	public static void testDiferenciaEnDias(Fecha fecha, Fecha fecha1) {
       int diferencia= fecha.diferenciaEnDias(fecha1);
       System.out.println("La diferencia entre: " +fecha+ " y " +fecha1+ " son " +diferencia+ " dias");
	}
	public static void testEsAñoBisiesto(int año){
       boolean esbisiesto= Fecha.esAñoBisiesto(año);
       if(esbisiesto) {
	      System.out.format("%d es año bisiesto\n",año);
       }else {
	      System.out.format("%d no es año bisiesto\n",año);
	      }
	}
     public static void testDiasEnMes(int año,int mes) {
     System.out.println("El mes indicado tiene "+ Fecha.diasEnMes(año, mes)+ " dias");
    }
}


	

