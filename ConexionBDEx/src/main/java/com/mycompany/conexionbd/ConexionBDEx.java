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

    //Datos de conexion a la base de datos
    /*static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/jcvd";
    static final String USER = "juan";
    static final String PASS = "1234";*/

    public static void main(String[] args) {
        
        try {
            //Crea la instancia con el pool
            PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();
            
            //introduce las condiciones de la conexion
            pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
            pds.setURL("jdbc:mysql://127.0.0.1:3306/jcvd");
            pds.setUser("juan");
            pds.setPassword("1234");
            pds.setInitialPoolSize(5);
            
            //obtener la conexion con la base de datos
            Connection conn = pds.getConnection();
            
            System.out.println("\nConnection obtained from UniversalConnectionPool\n");
            
            //se ejecuta las operaciones con las bases de datos
            Statement stmt = conn.createStatement();
            stmt.execute("SELECT * FROM foo");
            
            //se cierra la conexion
            conn.close();
            conn=null;
            
            System.out.println("\nConnection retutned from UniversalConnectionPool\n");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        try {
            //System.out.println(buscarNombre("Valorant"));
            //buscarNombreV2();
            //System.out.println(lanzaConsulta("SELECT * FROM videojuegos"));
            //lanzarConsultaV2();
            //nuevoRegistroParametro("C:S", "Shooter", "2023-11-24", "Steam", 00.00);
            //nuevoRegistroParametroV2();
            //nuevoRegistroTeclado();
            //nuevoRegistroTecladoV2();
            //System.out.println(eliminarRegistro("Valorant"));
            //System.out.println(eliminarRegistroV2("Batman"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean buscarNombre(String nombre) {
        String consulta = "SELECT *FROM videojuegos WHERE Nombre = '" + nombre + "'";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); 
                Statement stmt = conn.createStatement(); 
                ResultSet rs = stmt.executeQuery(consulta);) {

            boolean existe = false;

            while (rs.next()) {
                String nJuego = rs.getString("Nombre");
                if (nJuego.equals(nombre)) {
                    existe = true;
                }
            }

            conn.close();
            return existe;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void buscarNombreV2() {
        String nombreABuscar = "asd";
        String consulta = "SELECT * FROM videojuegos WHERE Nombre = ?";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement sentencia = conn.prepareStatement(consulta);
            sentencia.setString(1, nombreABuscar);
            ResultSet rs = sentencia.executeQuery();

            boolean existe = rs.next();

            conn.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String lanzaConsulta(String consulta) {

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); 
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

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public static void lanzarConsultaV2() throws SQLException {

        String consulta = "SELECT * FROM videojuegos where Nombre = ?";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            PreparedStatement sentencia = conn.prepareStatement(consulta);
            sentencia.setString(1, "Juan");
            ResultSet rs = sentencia.executeQuery();

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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void nuevoRegistroParametro(String Nombre, String Genero, String FechaLanzamiento, String Compañia, double Precio) {
        String Query = "INSERT INTO `videojuegos` (`Nombre`, `Genero`, `FechaLanzamiento`, `Compañia`, `Precio`) "
                + "VALUES ('" + Nombre + "', '" + Genero + "', '" + FechaLanzamiento + "', '" + Compañia + "', " + Precio + ")";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); 
                Statement stmt = conn.createStatement();) {

            stmt.executeUpdate(Query);
            System.out.println("videojuego insertado");

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void nuevoRegistroParametroV2() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void nuevoRegistroTeclado() throws ParseException {
        Scanner sc = new Scanner(System.in);
        String Query;
        String Nombre, Genero, FechaLanzamiento, Compañia;
        float Precio;

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); 
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
    
    public static void nuevoRegistroTecladoV2() {
        Scanner sc = new Scanner(System.in);

        String Nombre, Genero, FechaLanzamiento, Compañia;
        float Precio;

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
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

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean eliminarRegistro(String nombre) {
        String delQuery = "DELETE FROM videojuegos WHERE Nombre = '" + nombre + "'";

        if (buscarNombre(nombre)) {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); 
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

    public static void eliminarRegistroV2(String nombre) {
        String delQuery = "DELETE FROM videojuegos WHERE Nombre = ?";

        if (buscarNombre(nombre)) {
            try {
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
                PreparedStatement sentencia = conn.prepareStatement(delQuery);
                sentencia.setString(1, "asd");
                ResultSet rs = sentencia.executeQuery();

                stmt.executeUpdate(delQuery);

                conn.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("El videojuego introducido no existe en la base");
        }
    }

    
}
