/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package estudiodecaso2;

import javax.swing.JOptionPane;

/**
 *
 * @author bryan
 */
public class EstudioDeCaso2 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Configuración inicial del hotel
        // Se define que el hotel tiene 5 pisos y que cada piso tiene 5 habitaciones
        int pisos = 5;
        int habPorPiso = 5;
        
        // Matriz creada para almacenar todas las habitaciones: Estado de habitaciones [piso][habitación][0=número, 1=tipo, 2=precio, 3=estado]
        String[][][] hotel = new String[pisos][habPorPiso][4];
        
        // Precargar habitaciones, esto llama al método que inicializa todas las habitaciones con valores predeterminados.
        precargarHabitaciones(hotel);
        
        // Menú principal
        while(true) {
            String opcion = JOptionPane.showInputDialog(
                "Sistema De Gestion: Hotel El Bryan\n\n" +
                "1. Ver estado de las habitaciones\n" +
                "2. Modificar habitación\n" +
                "3. Ver resumen del hotel\n" +
                "4. Salir del menu\n\n" +
                "Seleccione una opción (1-4):");
            
            if(opcion == null || opcion.equals("4")) {
                JOptionPane.showMessageDialog(null, "El sistema se ha cerrado con exito");
                break;
            }
            
            switch(opcion) {
                case "1": 
                    mostrarHabitaciones(hotel); 
                    break;
                case "2": 
                    modificarHabitacion(hotel); 
                    break;
                case "3": 
                    mostrarResumen(hotel); 
                    break;
                default: 
                    JOptionPane.showMessageDialog(null, "Opción inválida");
            }
        }
    }
    
    public static void precargarHabitaciones(String[][][] hotel) {
        for(int p = 0; p < hotel.length; p++) {
            for(int h = 0; h < hotel[p].length; h++) {
                // Numeración por piso (101..., 201..., 301..., 401..., 501...)
                hotel[p][h][0] = String.valueOf((p+1)*100 + (h+1));
                
                // Se asignan los tipos de habitacion y el precio según el piso 
                if(p < 2) { // Pisos 1 al 2: Son habitaciones simples con un costo de($50)
                    hotel[p][h][1] = "Simple";
                    hotel[p][h][2] = "50";
                } 
                else if(p < 4) { // Pisos 3 al 4: Son habitaciones dobles, con un costo de ($80)
                    hotel[p][h][1] = "Doble";
                    hotel[p][h][2] = "80";
                } 
                else { // Piso 5: Son habitaciones Suites, con un costo de($150)
                    hotel[p][h][1] = "Suite";
                    hotel[p][h][2] = "150";
                }
                
                // Todas las habitaciones comienzan estando libres
                hotel[p][h][3] = "Libre";
            }
        }
    }
    
    public static void mostrarHabitaciones(String[][][] hotel) {
        String reporte = "Sistema De Gestion: Hotel El Bryan\n\n";
        
        // Se muestran los pisos en un orden de arriba hacia abajo (piso 5 de primero a piso 1 de ultimo)
        for(int p = hotel.length-1; p >= 0; p--) {
            reporte += "PISO " + (p+1) + "\n";
            
            // Titulo encabezado de las opciones
            reporte += "Habitación  Tipo    Precio  Estado\n";
            
            // Datos de cada habitación
            for(int h = 0; h < hotel[p].length; h++) {
                reporte += String.format("%-11s%-8s%-8s%-6s\n", 
                    hotel[p][h][0], 
                    hotel[p][h][1], 
                    "$"+hotel[p][h][2], 
                    hotel[p][h][3]);
            }
            reporte += "\n";
        }
        
        JOptionPane.showMessageDialog(null, reporte);
    }
    
    public static void modificarHabitacion(String[][][] hotel) {
        String numHab = JOptionPane.showInputDialog("Ingrese número de habitación (ej: 101, 201, 301..):");
        
        for(int p = 0; p < hotel.length; p++) {
            for(int h = 0; h < hotel[p].length; h++) {
                if(hotel[p][h][0].equals(numHab)) {
                    String menu = "Modificar habitación " + numHab + "\n\n" +
                                 "1. Tipo: " + hotel[p][h][1] + "\n" +
                                 "2. Precio: $" + hotel[p][h][2] + "\n" +
                                 "3. Estado: " + hotel[p][h][3] + "\n" +
                                 "4. Cancelar\n\n" +
                                 "Seleccione qué desea modificar (1-4):";
                    
                    String opcion = JOptionPane.showInputDialog(menu);
                    
                    switch(opcion) {
                        case "1":
                            hotel[p][h][1] = JOptionPane.showInputDialog("Nuevo tipo (Simple/Doble/Suite):");
                            break;
                        case "2":
                            hotel[p][h][2] = JOptionPane.showInputDialog("Nuevo precio:");
                            break;
                        case "3":
                            hotel[p][h][3] = JOptionPane.showInputDialog("Nuevo estado (Libre/Ocupada/Sucia):");
                            break;
                        case "4":
                            return;
                        default:
                            JOptionPane.showMessageDialog(null, "Opción no válida");
                    }
                    
                    JOptionPane.showMessageDialog(null, "Cambios guardados");
                    return;
                }
            }
        }
        
        JOptionPane.showMessageDialog(null, "Habitación no encontrada");
    }
    
    public static void mostrarResumen(String[][][] hotel) {
        int total = hotel.length * hotel[0].length;
        int libres = 0, ocupadas = 0, sucias = 0;
        int ganancias = 0;
        
        for(int p = 0; p < hotel.length; p++) {
            for(int h = 0; h < hotel[p].length; h++) {
                switch(hotel[p][h][3]) {
                    case "Libre": libres++; break;
                    case "Ocupada": 
                        ocupadas++; 
                        ganancias += Integer.parseInt(hotel[p][h][2]);
                        break;
                    case "Sucia": sucias++; break;
                }
            }
        }
        
        String resumen = "RESUMEN DEL HOTEL\n\n" +
                        "Total habitaciones: " + total + "\n" +
                        "Libres: " + libres + " (" + (libres*100/total) + "%)\n" +
                        "Ocupadas: " + ocupadas + " (" + (ocupadas*100/total) + "%)\n" +
                        "Sucias: " + sucias + " (" + (sucias*100/total) + "%)\n\n" +
                        "Ganancias diarias: $" + ganancias;
        
        JOptionPane.showMessageDialog(null, resumen);
    }
}