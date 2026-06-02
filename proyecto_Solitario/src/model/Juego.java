package model;

public class Juego {

    // Tablero principal de la partida
    private Tablero tablero;

    // Menú del juego, desde aquí llamamos a los métodos de Menu
    private Menu menu;

    // Jugador que inicia sesión o se registra
    private Jugador jugador;

    // Dificultad elegida por el jugador
    private Dificultad dificultad;

    // Booleano para controlar cuándo termina el programa
    private boolean salir;

    public Juego() {
        this.tablero = new Tablero();

        // Como tu constructor de Menu pide un Tablero y un int, le pasamos el tablero y 0 como valor inicial
        this.menu = new Menu(tablero, 0);

        this.salir = false;
    }

    // Método principal del juego
    // Desde el Main solo llamaremos a este método
    public void iniciarMenu() {

        while (!salir) {

            int opcionInicio = menu.mostrarMenuInicio();

            switch (opcionInicio) {

                case 1:
                    iniciarSesion();
                    menuPrincipal();
                    break;

                case 2:
                    registrarJugador();
                    menuPrincipal();
                    break;

                case 3:
                    System.out.println("Saliendo del juego...");
                    salir = true;
                    break;

                default:
                    System.out.println("Opción incorrecta.");
                    break;
            }
        }
    }

    // Pide nombre y contraseña usando los métodos que ya tiene Menu
    private void iniciarSesion() {

        String nombre = menu.pedirNombre();
        String password = menu.pedirPassword();

        jugador = new Jugador(nombre, password);

        System.out.println("Bienvenido, " + jugador.getNombreUsuario());
    }

    // De momento hace lo mismo que iniciar sesión
    // Más adelante aquí podéis conectar con la base de datos para insertar el jugador
    private void registrarJugador() {

        String nombre = menu.pedirNombre();
        String password = menu.pedirPassword();

        jugador = new Jugador(nombre, password);

        System.out.println("Jugador registrado: " + jugador.getNombreUsuario());
    }

    // Menú principal después de iniciar sesión o registrarse
    private void menuPrincipal() {

        boolean volverInicio = false;

        while (!volverInicio && !salir) {

            int opcionPrincipal = menu.mostrarMenuPrincipal();

            switch (opcionPrincipal) {

                case 1:
                    elegirDificultad();
                    break;

                case 2:
                    iniciarPartida();
                    break;

                case 3:
                    System.out.println("Volviendo al menú de inicio...");
                    volverInicio = true;
                    break;

                case 4:
                    System.out.println("Saliendo del juego...");
                    salir = true;
                    break;

                default:
                    System.out.println("Opción incorrecta.");
                    break;
            }
        }
    }

    // Llama al método elegirDificultad de Menu
    private void elegirDificultad() {
        dificultad = menu.elegirDificultad();

        System.out.println("Dificultad elegida: " + dificultad);
    }

    // Aquí se empezaría la partida
    private void iniciarPartida() {

        if (dificultad == null) {
            System.out.println("Primero debes elegir una dificultad.");
            return;
        }

        System.out.println("Iniciando partida...");
        System.out.println("Jugador: " + jugador.getNombreUsuario());
        System.out.println("Dificultad: " + dificultad);

        // Aquí más adelante llamaremos a Partida
        // Partida partida = new Partida(jugador, dificultad);
        // partida.iniciarPartida();
    }
}