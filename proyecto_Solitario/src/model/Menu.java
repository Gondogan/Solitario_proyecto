package model;

import java.util.Scanner;

public class Menu {

	private Scanner leer;

	
	public Menu(Tablero tablero, int cartasARobar) {
		
		this.leer = new Scanner(System.in);
	}
	
	//======================== METODOS ========================================
	
	//Devuelve la opción elegida
	public int mostrarMenuInicio() {
		int opcion;
		
		do {
            System.out.println("\n===== SOLITARIO =====");
            System.out.println("	1. Iniciar sesión");
            System.out.println("	2. Registrarse");
            System.out.println("	0. Salir");
            System.out.print("Elige una opción: ");

            opcion = leerEntero();

            if (opcion < 0 || opcion > 2) {
                System.out.println("Opción no válida.");
            }

        } while (opcion < 0 || opcion > 2);

        return opcion;
	}
	
	//Aparece cuando el jugador ya ha iniciado sesión
	
	 public int mostrarMenuPrincipal() {
	        int opcion;

	        do {
	            System.out.println("\n===== MENÚ PRINCIPAL =====");
	            System.out.println("	1. Nueva partida");
	            System.out.println("	2. Ver ranking");
	            System.out.println("	3. Ver historial");
	            System.out.println("	0. Cerrar sesión");
	            System.out.print("Elige una opción: ");

	            opcion = leerEntero();

	            if (opcion < 0 || opcion > 3) {
	                System.out.println("Opción no válida.");
	            }

	        } while (opcion < 0 || opcion > 3);

	        return opcion;
	    }
	 
	 
	 //Devuelve un objeto Dificultad
	 public Dificultad elegirDificultad() {
	        int opcion;

	        do {
	            System.out.println("\n===== ELEGIR DIFICULTAD =====");
	            System.out.println("	1. Fácil - Robar 1 carta");
	            System.out.println("	2. Media - Robar 2 cartas");
	            System.out.println("	3. Difícil - Robar 3 cartas");
	            System.out.print("Elige una dificultad: ");

	            opcion = leerEntero();

	            if (opcion < 1 || opcion > 3) {
	                System.out.println("Dificultad no válida.");
	            }

	        } while (opcion < 1 || opcion > 3);

	        switch (opcion) {
	            case 1:
	                return Dificultad.FACIL;
	            case 2:
	                return Dificultad.MEDIA;
	            case 3:
	                return Dificultad.DIFICIL;
	            default:
	                return Dificultad.FACIL;
	        }
	    }
	 
	 public String pedirNombre() {
		 
		 System.out.println("Nombre: ");
		 return leer.nextLine();
	 }
	
	    public String pedirPassword() {
	    	
	        System.out.print("Contraseña: ");
	        return leer.nextLine();
	    }

	    private int leerEntero() {
	    	//Es un método de Scanner que sirve para comprobar si lo siguiente que ha escrito el usuario se puede leer como un número entero
	        while (!leer.hasNextInt()) {
	            System.out.println("Introduce un número válido.");
	            leer.next();
	            System.out.print("Elige una opción: ");
	        }

	        int numero = leer.nextInt();
	        leer.nextLine();

	        return numero;
	    }
}
