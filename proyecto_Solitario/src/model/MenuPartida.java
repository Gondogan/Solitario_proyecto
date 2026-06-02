package model;
 
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import dao.DaoPartida;
 
public class MenuPartida {
 
    private Scanner leer;
    private Partida partida;
    private VistaTablero vistaTablero;
 
    public MenuPartida(Partida partida, Scanner leer) {
        this.partida      = partida;
        this.leer         = leer;
        this.vistaTablero = new VistaTablero(partida.getTablero());
    }
 
    public void iniciarMenu() throws SQLException {
        int opcion;
        do {
            vistaTablero.mostrar(); // muestra el tablero antes de cada turno
            mostrarOpciones();
            opcion = pedirEntero("Elige una opción: ");
 
            switch (opcion) {
                case 1: pedirCartasDelMazo();    break;
                case 2: moverDescarteAColumna(); break;
                case 3: moverColumnaAColumna();  break;
                case 4: moverCartaAFundacion();  break;
                case 0: salirDeLaPartida();      break;
                default: System.out.println("Opción no válida."); break;
            }
 
            if (opcion != 0 && hayVictoria()) { victoria(); opcion = 0; }
 
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
    
    
    private void mostrarOpciones() {
        String lin = linea(38);
        System.out.println("\n" + lin);
        System.out.printf("| %-37s|\n", "           MENU DE JUEGO");
        System.out.println(lin);
        System.out.printf("| %-37s|\n",
            " Movimientos: " + partida.getMovimientos()
            + "   Tiempo: " + partida.getTiempoFormateado());
        System.out.println(lin);
        System.out.printf("| %-37s|\n", " [ 1 ]  Pedir cartas del mazo");
        System.out.printf("| %-37s|\n", " [ 2 ]  Mover descarte a columna");
        System.out.printf("| %-37s|\n", " [ 3 ]  Mover columna a columna");
        System.out.printf("| %-37s|\n", " [ 4 ]  Mover carta a fundacion");
        System.out.println(lin);
        System.out.printf("| %-37s|\n", " [ 0 ]  Salir de la partida");
        System.out.println(lin);
    }
 
    private void pedirCartasDelMazo() {
        partida.getTablero().pedirCartasDelMazo(partida.getDificultad().getCartasARobar());
        partida.setMovimientos(partida.getMovimientos() + 1);
        System.out.println("Cartas robadas del mazo.");
    }
 
    // if-else en lugar de return para controlar el flujo sin cortarlo
    private void moverDescarteAColumna() {
        Carta cartaDescarte = partida.getTablero().getDescarte().verUltimaCarta();
        if (cartaDescarte == null) {
            System.out.println("No hay cartas en el descarte.");
        } else {
            System.out.println("Carta del descarte: " + cartaDescarte);
            int col = pedirEntero("¿A qué columna? (1-7): ") - 1;
            if (!validarMovimientoAColumna(cartaDescarte, col)) {
                System.out.println("Movimiento no válido.");
            } else {
                partida.getTablero().agregarCartaAColumna(col,
                    partida.getTablero().getDescarte().quitarUltimaCarta());
                partida.setMovimientos(partida.getMovimientos() + 1);
                System.out.println("Carta movida.");
            }
        }
    }
 
    // if-else encadenado para cada condición sin cortar el método
    private void moverColumnaAColumna() {
        int origen  = pedirEntero("Columna origen (1-7): ")  - 1;
        int destino = pedirEntero("Columna destino (1-7): ") - 1;
        Carta carta = partida.getTablero().obtenerUltimaCarta(origen);
 
        if (carta == null) {
            System.out.println("La columna origen está vacía.");
        } else if (!carta.isBocaArriba()) {
            System.out.println("Esa carta está boca abajo.");
        } else if (!validarMovimientoAColumna(carta, destino)) {
            System.out.println("Movimiento no válido.");
        } else {
            partida.getTablero().agregarCartaAColumna(destino,
                partida.getTablero().quitarUltimaCarta(origen));
            partida.setMovimientos(partida.getMovimientos() + 1);
 
            // Si queda una carta oculta al descubierto, la volteamos
            Carta nuevaUltima = partida.getTablero().obtenerUltimaCarta(origen);
            if (nuevaUltima != null && !nuevaUltima.isBocaArriba()) {
                nuevaUltima.setBocaArriba(true);
            }
            System.out.println("Carta movida.");
        }
    }
 
    // Bandera origenValido para controlar el flujo sin return ni break en el bucle
    private void moverCartaAFundacion() {
        System.out.println("\t1. Desde el descarte | 2. Desde una columna");
        int origen       = pedirEntero("Elige: ");
        Carta carta      = null;
        int columna      = -1;
        boolean origenValido = true;
 
        if (origen == 1) {
            carta = partida.getTablero().getDescarte().verUltimaCarta();
            if (carta == null) { System.out.println("No hay cartas en el descarte."); origenValido = false; }
        } else if (origen == 2) {
            columna = pedirEntero("¿De qué columna? (1-7): ") - 1;
            carta   = partida.getTablero().obtenerUltimaCarta(columna);
            if (carta == null) { System.out.println("Columna vacía."); origenValido = false; }
        } else {
            System.out.println("Opción no válida."); origenValido = false;
        }
 
        if (origenValido) {
            ArrayList<Fundacion> fundaciones = partida.getTablero().getFundaciones();
            boolean colocada = false;
            int i            = 0;
 
            // While con bandera para no usar break (rúbrica)
            while (i < fundaciones.size() && !colocada) {
                if (fundaciones.get(i).agregarCarta(carta)) {
                    if (origen == 1) {
                        partida.getTablero().getDescarte().quitarUltimaCarta();
                    } else {
                        partida.getTablero().quitarUltimaCarta(columna);
                        Carta nueva = partida.getTablero().obtenerUltimaCarta(columna);
                        if (nueva != null && !nueva.isBocaArriba()) { nueva.setBocaArriba(true); }
                    }
                    partida.setMovimientos(partida.getMovimientos() + 1);
                    colocada = true;
                    System.out.println("Carta enviada a la fundación.");
                }
                i++;
            }
            if (!colocada) { System.out.println("Esa carta no puede ir a ninguna fundación todavía."); }
        }
    }
 
    private void salirDeLaPartida() throws SQLException {

        // Paramos el tiempo y marcamos como terminada
        partida.terminarPartida();

        // ganada ya es false por defecto, no hace falta cambiarla
        // La guardamos aunque no haya ganado para que aparezca en el historial
        DaoPartida.getInstance().guardarPartida(partida);

        System.out.println("Has salido de la partida.");
        System.out.println("Movimientos: " + partida.getMovimientos()
                + " | Tiempo: " + partida.getTiempoFormateado());
        System.out.println("La partida se ha guardado en tu historial.");
    }
 
    private void victoria() throws SQLException {
        partida.terminarPartida();
        partida.setGanada(true);
        System.out.println("\n¡¡ENHORABUENA, " + partida.getJugador().getNombreUsuario() + "!!");
        System.out.println("Movimientos: " + partida.getMovimientos()
                + " | Tiempo: " + partida.getTiempoFormateado());
        DaoPartida.getInstance().guardarPartida(partida); // solo si gana
    }
 
    // While con bandera en lugar de break (rúbrica)
    private boolean hayVictoria() {
        ArrayList<Fundacion> f = partida.getTablero().getFundaciones();
        boolean todas          = true;
        int i                  = 0;
        while (i < f.size() && todas) {
            if (!f.get(i).estaCompleta()) { todas = false; }
            i++;
        }
        return todas;
    }
 
    // Columna vacía → solo Rey | Con cartas → color contrario + valor - 1
    private boolean validarMovimientoAColumna(Carta carta, int destino) {
        if (!partida.getTablero().existeColumna(destino)) { return false; }
        Carta cartaDest = partida.getTablero().obtenerUltimaCarta(destino);
        if (cartaDest == null)  { return carta.getValor() == 13; }
        boolean colorDistinto = carta.getColor() != cartaDest.getColor();
        boolean valorOk       = carta.getValor() == cartaDest.getValor() - 1;
        return colorDistinto && valorOk;
    }
 
    private int pedirEntero(String msg) {
        System.out.print(msg);
        while (!leer.hasNextInt()) {
            System.out.println("Introduce un número válido."); leer.next(); System.out.print(msg);
        }
        int n = leer.nextInt();
        leer.nextLine();
        return n;
    }
}
