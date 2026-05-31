package model;

import java.util.Scanner;

public class MenuPartida {

	    private Scanner leer;
	    private Tablero tablero;
	    private int cartasARobar;

	    public MenuPartida(Tablero tablero, int cartasARobar) {
	        this.leer = new Scanner(System.in);
	        this.tablero = tablero;
	        this.cartasARobar = cartasARobar;
	    }

	    public void iniciarMenu() {
	        int opcion;

	        do {
	            mostrarOpciones();
	            opcion = pedirEntero("Elige una opción: ");

	            switch (opcion) {
	                case 1:
	                    tablero.mostrarTablero();
	                    break;

	                case 2:
	                    pedirCartasDelMazo();
	                    break;

	                case 3:
	                    moverDescarteAColumna();
	                    break;

	                case 4:
	                    moverColumnaAColumna();
	                    break;

	                case 0:
	                    System.out.println("Saliendo de la partida...");
	                    break;

	                default:
	                    System.out.println("Opción no válida.");
	                    break;
	            }

	        } while (opcion != 0);
	    }

	    private void mostrarOpciones() {
	        System.out.println("\n===== MENÚ DE JUEGO =====");
	        System.out.println("1. Ver tablero");
	        System.out.println("2. Pedir cartas del mazo");
	        System.out.println("3. Mover carta del descarte a una columna");
	        System.out.println("4. Mover carta de una columna a otra");
	        System.out.println("0. Salir de la partida");
	    }

	    private int pedirEntero(String mensaje) {
	        System.out.print(mensaje);

	        while (!leer.hasNextInt()) {
	            System.out.println("Introduce un número válido.");
	            leer.next();
	            System.out.print(mensaje);
	        }

	        int numero = leer.nextInt();
	        leer.nextLine();

	        return numero;
	    }

	    private void pedirCartasDelMazo() {
	        tablero.pedirCartasDelMazo(cartasARobar);
	        System.out.println("Has pedido cartas del mazo.");
	    }

	    private void moverDescarteAColumna() {
	        Carta carta = tablero.getDescarte().verUltimaCarta();

	        if (carta == null) {
	            System.out.println("No hay cartas en el descarte.");
	            return;
	        }

	        System.out.println("Carta del descarte: " + carta);

	        int columnaDestino = pedirEntero("¿A qué columna quieres moverla? (1-7): ");
	        columnaDestino--;

	        boolean movimientoValido = validarMovimientoAColumna(carta, columnaDestino);

	        if (!movimientoValido) {
	            System.out.println("Movimiento no válido.");
	            return;
	        }

	        Carta cartaMovida = tablero.getDescarte().quitarUltimaCarta();
	        tablero.agregarCartaAColumna(columnaDestino, cartaMovida);

	        System.out.println("Carta movida correctamente.");
	    }

	    private void moverColumnaAColumna() {
	        int columnaOrigen = pedirEntero("Columna origen (1-7): ");
	        columnaOrigen--;

	        int columnaDestino = pedirEntero("Columna destino (1-7): ");
	        columnaDestino--;

	        Carta carta = tablero.obtenerUltimaCarta(columnaOrigen);

	        if (carta == null) {
	            System.out.println("La columna origen está vacía.");
	            return;
	        }

	        boolean movimientoValido = validarMovimientoAColumna(carta, columnaDestino);

	        if (!movimientoValido) {
	            System.out.println("Movimiento no válido.");
	            return;
	        }

	        Carta cartaMovida = tablero.quitarUltimaCarta(columnaOrigen);
	        tablero.agregarCartaAColumna(columnaDestino, cartaMovida);

	        System.out.println("Carta movida correctamente.");
	    }

	    private boolean validarMovimientoAColumna(Carta carta, int columnaDestino) {
	        if (columnaDestino < 0 || columnaDestino >= 7) {
	            return false;
	        }

	        Carta cartaDestino = tablero.obtenerUltimaCarta(columnaDestino);

	        if (cartaDestino == null) {
	            return carta.getValor() == 13;
	        }

	        boolean colorDiferente = carta.getColor() != cartaDestino.getColor();
	        boolean valorCorrecto = carta.getValor() == cartaDestino.getValor() - 1;

	        return colorDiferente && valorCorrecto;
	    }
}

