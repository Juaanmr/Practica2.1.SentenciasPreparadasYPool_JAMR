/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.conexionbd;

import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

/**
 *
 * @author Juan
 */
public class ConexionBDEx {

    //Datos de conn a la base de datos
    /*static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/jcvd";
    static final String USER = "juan";
    static final String PASS = "1234";*/
    
    static PoolDataSource pds;

    public static void main(String[] args) {
        
        try {
            //Crea la instancia con el pool
            pds = PoolDataSourceFactory.getPoolDataSource();
            
            //introduce las condiciones de la conn
            pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
            pds.setURL("jdbc:mysql://loclahost:3306/jcvd");
            pds.setUser("juan");
            pds.setPassword("1234");
            
            pds.setInitialPoolSize(5);
            
            Scanner sc = new Scanner(System.in);
            int opcion;
            //Abre la conexión
            try (Connection conn = pds.getConnection(); 
                    Statement stmt = conn.createStatement();) {

                System.out.println("\tElige una opción:");
                System.out.println("\n\tConexionBD");
                System.out.println("1 - Buscar nombre por teclado");
                System.out.println("2 - Lanza consulta SELECT * FROM videojuegos");
                System.out.println("3 - Añadir un nuevo registro por Parametro");
                System.out.println("4 - Añadir un nuevo registro por Teclado");
                System.out.println("5 - Eliminar un juego por teclado");
                
                System.out.println("\n\tConexionBDEx");
                System.out.println("6 - Buscar un videojuego");
                System.out.println("7 - Añadir un nuevo registro por Parametro");
                System.out.println("8 - Añadir un nuevo registro por Teclado");
                
                opcion = sc.nextInt();

                switch(opcion){
                    case 1: 
                        System.out.println("Introduce el nombre del videojuego");
                        String buscarNombre = sc.next();
                        System.out.println(buscarNombre(buscarNombre));
                        break;


                    case 2:
                        lanzaConsulta("SELECT * FROM videojuegos");
                        break;
                    case 3:
                        nuevoRegistroParametro("C:S", "Shooter", "2023-11-24", "Steam", 00.00);
                        break;
                    case 4:
                        nuevoRegistroTeclado();
                        break;
                    case 5:
                        System.out.println("Introduzca el nombre del Videojuego: ");
                        String eliminarNombre = sc.nextLine();
                        eliminarRegistro(eliminarNombre);
                        break;

                        /*ConexionBDEx*/
                        
                    case 6:
                        lanzarConsultaV2();
                        break;
                    case 7:
                        nuevoRegistroParametroV2();
                        break;
                    case 8:
                        nuevoRegistroTecladoV2();
                        break;
                }

                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        /*try {
            System.out.println(buscarNombre("Juan"));
            System.out.println(lanzaConsulta("SELECT * FROM videojuegos"));
            lanzarConsultaV2();
            nuevoRegistroParametro("C:S", "Shooter", "2023-11-24", "Steam", 00.00);
            nuevoRegistroParametroV2();
            nuevoRegistroTeclado();
            nuevoRegistroTecladoV2();
            System.out.println(eliminarRegistro("Valorant"));
            System.out.println(eliminarRegistroV2("Batman"));

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public static boolean buscarNombre(String nombre) {
        String Query = "SELECT *FROM videojuegos WHERE Nombre = '" + nombre + "'";

        try (Connection conn = pds.getConnection(); 
                Statement stmt = conn.createStatement(); 
                ResultSet rs = stmt.executeQuery(Query);) {

            boolean existe = false;

            while (rs.next()) {
                String nJuego = rs.getString("Nombre");
                if (nJuego.equals(nombre)) {
                    existe = true;
                }
            }

            conn.close();
            return existe;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String lanzaConsulta(String consulta) {

        try (Connection conn = pds.getConnection(); 
                Statement stmt = conn.createStatement(); 
                ResultSet rs = stmt.executeQuery(consulta)) {

            while (rs.next()) {
                System.out.print("EL ID es : " + rs.getInt("id"));
                System.out.print(", EL Nombre es : " + rs.getString("Nombre"));
                System.out.print(", EL Genere es : " + rs.getString("Genero"));
                System.out.print(", la Fecha de Lanzamiento es : " + rs.getDate("FechaLanzamiento"));
                System.out.print(", La Compañia es : " + rs.getString("Compañia"));
                System.out.println(", EL Precio es : " + rs.getFloat("Precio"));
            }

            ResultSetMetaData meta = rs.getMetaData();
            int colum = meta.getColumnCount();

            StringBuilder resultado = new StringBuilder();

            while (rs.next()) {
                for (int i = 1; i <= colum; i++) {
                    String valor = rs.getString(i);
                    resultado.append(meta.getColumnName(i)).append(": ").append(valor).append("\t");
                }
                resultado.append("\n");
            }

            conn.close();

            return resultado.toString();

        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public static void nuevoRegistroParametro(String Nombre, String Genero, String FechaLanzamiento, String Compañia, double Precio) {
        String Query = "INSERT INTO `videojuegos` (`Nombre`, `Genero`, `FechaLanzamiento`, `Compañia`, `Precio`) "
                + "VALUES ('" + Nombre + "', '" + Genero + "', '" + FechaLanzamiento + "', '" + Compañia + "', " + Precio + ")";

        try (Connection conn = pds.getConnection();  
                Statement stmt = conn.createStatement();) {

            stmt.executeUpdate(Query);
            System.out.println("videojuego insertado");

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void nuevoRegistroTeclado() throws ParseException {
        Scanner sc = new Scanner(System.in);
        String Query;
        String Nombre, Genero, FechaLanzamiento, Compañia;
        float Precio;

        try (Connection conn = pds.getConnection(); 
                Statement stmt = conn.createStatement();) {

            System.out.println("Añade un nuevo videojuego: ");
            System.out.print("\t - Nombre: ");
            Nombre = sc.nextLine();
            System.out.print("\t - Genero: ");
            Genero = sc.nextLine();
            System.out.print("\t - Fecha de lanzamiento (yyyy-MM-dd): ");
            FechaLanzamiento = sc.nextLine();
            System.out.print("\t - Compañia: ");
            Compañia = sc.nextLine();
            System.out.print("\t - Precio (00,00): ");
            Precio = sc.nextFloat();

            Query = "INSERT INTO `videojuegos` (`id`, `Nombre`, `Genero`, `FechaLanzamiento`, `Compañia`, `Precio`) "
                    + "VALUES (NULL,'" + Nombre + "', '" + Genero + "', '" + FechaLanzamiento + "', '" + Compañia + "', " + Precio + ")";

            stmt.executeUpdate(Query);
            System.out.println("\n\tvideojuego insertado");

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean eliminarRegistro(String nombre) {
        String delQuery = "DELETE FROM videojuegos WHERE Nombre = '" + nombre + "'";

        if (buscarNombre(nombre)) {
            try (Connection conn = pds.getConnection(); 
                    Statement stmt = conn.createStatement();) {

                stmt.executeUpdate(delQuery);

                conn.close();
                return true;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("El videojuego introducido no existe en la base");
            return false;
        }
    }

    
    /*ConexionBDEx*/
    
    
    public static void lanzarConsultaV2() throws SQLException {
        String Query = "SELECT * FROM `videojuegos` WHERE nombre = ? ";
        
        Connection conn = pds.getConnection();
        try {
            PreparedStatement sentencia = conn.prepareStatement (Query);
            sentencia.setString(1, "Mario Bors");
            ResultSet rs = sentencia.executeQuery();
            while (rs.next()) {
                System.out.print("EL ID es : " + rs.getInt("id"));
                System.out.print(", EL Nombre es : " + rs.getString("Nombre"));
                System.out.print(", EL Genere es : " + rs.getString("Genero"));
                System.out.print(", la Fecha de Lanzamiento es : " + rs.getDate("FechaLanzamiento"));
                System.out.print(", La Compañia es : " + rs.getString("Compañia"));
                System.out.println(", EL Precio es : " + rs.getFloat("Precio"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public static void nuevoRegistroParametroV2() throws SQLException {
        Connection conn = pds.getConnection(); 
        try {
            PreparedStatement sentencia = conn.prepareStatement("INSERT INTO videojuegos values (NULL, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, "prueba");
            sentencia.setString(2, "shooter");
            sentencia.setString(3, "2007-04-11");
            sentencia.setString(4, "Steam");
            sentencia.setFloat(5, 15);
            
            sentencia.executeUpdate();
            ResultSet rs = sentencia.getGeneratedKeys();
            while (rs.next()) {
                int claveGuardada = rs.getInt(1);
                System.out.println("Clave guardada = " + claveGuardada);
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public static void nuevoRegistroTecladoV2() throws SQLException {
        Scanner sc = new Scanner(System.in);

        String Nombre, Genero, FechaLanzamiento, Compañia;
        float Precio;
        
        Connection conn = pds.getConnection(); 

        try {
            PreparedStatement sentencia = conn.prepareStatement("INSERT INTO videojuegos values (NULL, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            System.out.println("Añade un nuevo videojuego: ");
            System.out.print("\t - Nombre: ");
            Nombre = sc.nextLine();
            System.out.print("\t - Genero: ");
            Genero = sc.nextLine();
            System.out.print("\t - Fecha de lanzamiento (yyyy-MM-dd): ");
            FechaLanzamiento = sc.nextLine();
            System.out.print("\t - Compañia: ");
            Compañia = sc.nextLine();
            System.out.print("\t - Precio (00,00): ");
            Precio = sc.nextFloat();

            sentencia.setString(1, Nombre);
            sentencia.setString(2, Genero);
            sentencia.setString(3, FechaLanzamiento);
            sentencia.setString(4, Compañia);
            sentencia.setFloat(5, Precio);
            sentencia.executeUpdate();
            
            System.out.println("\n\tvideojuego insertado");


        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public static void eliminarRegistroV2(String nombre) throws SQLException {
        String delQuery = "DELETE * FROM `videojuegos` WHERE nombre = ? ";
        
        Connection conn = pds.getConnection();
        try {
            PreparedStatement sentencia = conn.prepareStatement (delQuery);
            sentencia.setString(1, "Mario Bors");
            ResultSet rs = sentencia.executeQuery();
            while (rs.next()) {
                System.out.print("EL ID es : " + rs.getInt("id"));
                System.out.print(", EL Nombre es : " + rs.getString("Nombre"));
                System.out.print(", EL Genere es : " + rs.getString("Genero"));
                System.out.print(", la Fecha de Lanzamiento es : " + rs.getDate("FechaLanzamiento"));
                System.out.print(", La Compañia es : " + rs.getString("Compañia"));
                System.out.println(", EL Precio es : " + rs.getFloat("Precio"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}