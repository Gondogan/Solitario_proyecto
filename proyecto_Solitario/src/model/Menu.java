package model;
 
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;
import dao.DaoJugador;
import dao.DaoPartida;
 
public class Menu {
 
    // Compartido con MenuPartida para evitar conflictos al leer la entrada
    private Scanner leer;
 
    // null = nadie logueado | Jugador = hay sesión activa
    private Jugador jugadorActivo;
 
    public Menu() { this.leer = new Scanner(System.in); }
 
    public void iniciar() throws SQLException, NoSuchAlgorithmException {
        int opcion;
        do {
            opcion = mostrarMenuInicio();
            switch (opcion) {
                case 1: iniciarSesion(); break;
                case 2: registrarse();   break;
                case 0: System.out.println("¡Hasta luego!"); break;
                default: System.out.println("Opción no válida."); break;
            }
        } while (opcion != 0);
    }
 
    private int mostrarMenuInicio() {
        int opcion;
        do {
            String lin = linea(30);
            System.out.println("\n" + lin);
            System.out.printf("| %-29s|\n", "         SOLITARIO         ");
            System.out.println(lin);
            System.out.printf("| %-29s|\n", " [ 1 ]  Iniciar sesion");
            System.out.printf("| %-29s|\n", " [ 2 ]  Registrarse");
            System.out.println(lin);
            System.out.printf("| %-29s|\n", " [ 0 ]  Salir");
            System.out.println(lin);
            System.out.print(" Elige una opcion: ");
            opcion = leerEntero();
            if (opcion < 0 || opcion > 2) {
                System.out.println(" Opcion no valida.");
            }
        } while (opcion < 0 || opcion > 2);
        return opcion;
    }
 
    // Si no existe el usuario pregunta si quiere registrarse
    // Usamos if-else para controlar el flujo sin return en mitad del método
    private void iniciarSesion() throws SQLException, NoSuchAlgorithmException {
        System.out.println("\n--- INICIAR SESIÓN ---");
        String nombre   = pedirNombre();
        String password = pedirPassword();
        DaoJugador dao  = DaoJugador.getInstance();
 
        if (!dao.existeUsuario(nombre)) {
            System.out.println("El usuario '" + nombre + "' no existe.");
            System.out.print("¿Quieres registrarte? (s/n): ");
            if (leer.nextLine().trim().toLowerCase().equals("s")) {
                registrarNuevoJugador(nombre, password);
            }
        } else {
            Jugador jugador = dao.login(nombre, password);
            if (jugador != null) {
                jugadorActivo = jugador;
                menuPrincipal();
            }
        }
    }
 
    private void registrarse() throws SQLException, NoSuchAlgorithmException {
        System.out.println("\n--- REGISTRARSE ---");
        registrarNuevoJugador(pedirNombre(), pedirPassword());
    }
 
    // Lógica compartida de registro. Usamos if-else en lugar de return
    private void registrarNuevoJugador(String nombre, String password)
            throws SQLException, NoSuchAlgorithmException {
        DaoJugador dao = DaoJugador.getInstance();
        if (dao.existeUsuario(nombre)) {
            System.out.println("Ese nombre ya está en uso. Prueba con otro.");
        } else {
            dao.registrarJugador(new Jugador(nombre, password));
        }
    }
 
    private void menuPrincipal() throws SQLException, NoSuchAlgorithmException {
        int opcion;
        do {
            opcion = mostrarOpcionesMenuPrincipal();
            switch (opcion) {
                case 1: nuevaPartida(); break;
                case 2: DaoPartida.getInstance().mostrarRanking(); break;
                case 3: DaoPartida.getInstance().mostrarHistorial(jugadorActivo.getId()); break;
                case 0: System.out.println("Cerrando sesión..."); jugadorActivo = null; break;
                default: System.out.println("Opción no válida."); break;
            }
        } while (opcion != 0);
    }
 
    private String linea(int ancho) {
        String resultado = "+";
        int i = 0;
        while (i < ancho) {
            resultado += "-";
            i++;
        }
        resultado += "+";
        return resultado;
    }
    
    private int mostrarOpcionesMenuPrincipal() {
        int opcion;
        do {
            String lin = linea(30);
            System.out.println("\n" + lin);
            System.out.printf("| %-29s|\n", "      MENU PRINCIPAL");
            System.out.println(lin);
            System.out.printf("| %-29s|\n", " [ 1 ]  Nueva partida");
            System.out.printf("| %-29s|\n", " [ 2 ]  Ver ranking");
            System.out.printf("| %-29s|\n", " [ 3 ]  Ver historial");
            System.out.println(lin);
            System.out.printf("| %-29s|\n", " [ 0 ]  Cerrar sesion");
            System.out.println(lin);
            System.out.print(" Elige una opcion: ");
            opcion = leerEntero();
            if (opcion < 0 || opcion > 3) {
                System.out.println(" Opcion no valida.");
            }
        } while (opcion < 0 || opcion > 3);
        return opcion;
    }
 
    // Partida carga el mazo de la BBDD y crea el Tablero internamente
    private void nuevaPartida() throws SQLException {
        Dificultad dificultad = elegirDificultad();
        Partida partida       = new Partida(jugadorActivo, dificultad);
        partida.iniciarPartida();
        new MenuPartida(partida, leer).iniciarMenu();
    }
 
    private Dificultad elegirDificultad() {
        int opcion;
        do {
            System.out.println("\n===== ELEGIR DIFICULTAD =====");
            System.out.println("\t1. Fácil (1 carta) | 2. Media (2 cartas) | 3. Difícil (3 cartas)");
            System.out.print("Elige: ");
            opcion = leerEntero();
            if (opcion < 1 || opcion > 3) { System.out.println("No válido."); }
        } while (opcion < 1 || opcion > 3);
 
        switch (opcion) {
            case 1:  return Dificultad.FACIL;
            case 2:  return Dificultad.MEDIA;
            default: return Dificultad.DIFICIL;
        }
    }
 
    private String pedirNombre()   { System.out.print("Nombre: "); return leer.nextLine().trim(); }
    private String pedirPassword() { System.out.print("Contraseña: "); return leer.nextLine().trim(); }
 
    private int leerEntero() {
        while (!leer.hasNextInt()) {
            System.out.println("Introduce un número válido.");
            leer.next();
            System.out.print("Elige una opción: ");
        }
        int n = leer.nextInt();
        leer.nextLine();
        return n;
    }
}
