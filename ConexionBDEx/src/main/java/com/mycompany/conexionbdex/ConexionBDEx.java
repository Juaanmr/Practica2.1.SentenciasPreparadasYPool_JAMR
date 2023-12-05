/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.conexionbdex;

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
    //Codigo que usamos antes para conectarnos a la base de datos
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
            pds.setConnectionFactoryClassName("com.mysql.cj.jdbc.MysqlDataSource");
            pds.setURL("jdbc:mysql://localhost:3306/jcvd");
            pds.setUser("juan");
            pds.setPassword("1234");
            
            pds.setInitialPoolSize(5);
            
            Scanner sc = new Scanner(System.in);
            int opcion;
            //Abre la conexión
            try (Connection conn = pds.getConnection(); 
                Statement stmt = conn.createStatement();)
            {
                //muestro un menso para el usuario para que la interfaz se mejor 
                System.out.println("\tElige la opcion que quiera:");
                System.out.println("\n\tConexionBD");
                System.out.println("1. Buscar nombre por teclado");
                System.out.println("2. Lanza consulta SELECT * FROM videojuegos");
                System.out.println("3. Añadir un nuevo registro por Parametro");
                System.out.println("4. Añadir un nuevo registro por Teclado");
                System.out.println("5. Eliminar un juego por teclado");
                
                System.out.println("\n\n\tConexionBDEx");
                System.out.println("6. Buscar un videojuego");
                System.out.println("7. Añadir un nuevo registro por Parametro");
                System.out.println("8. Añadir un nuevo registro por Teclado");
                
                //lee la opacion que ha insertado el usaurio y se guarda en esta variable
                opcion = sc.nextInt();

                //dependiendo de la opacion que haya escogido el usuario escoge un metodo u otro
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

                //cierro la conexion con la base de datos
                conn.close();
            } catch (SQLException e) {
                //excepcion que salta si hay algun error durante la ejecucion de las operaciones
                e.printStackTrace();
            }
            
        } catch (Exception e) {
            //excepcion que salta si hay algun error durante la ejecucion del pool
            e.printStackTrace();
        }
        
        /*Esto lo utilizaba antes para llamar a los metodos antes de tener el menu*/
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

    /**
     * buscar algun videojuego en la base de datos
     * @param nombre le paso el nombre del videojuego que lo pasa por teclado
     * @return me muestra ture si existe o false si no existe
     */
    public static boolean buscarNombre(String nombre) {
        //creo la consulta que quiero hacer en la base de datos
        String Query = "SELECT * FROM videojuegos WHERE Nombre = '" + nombre + "'";
        
        //hago la conexion con la base de datos y ejecuto la consulta que he decalrado anterirormente 
        try (Connection conn = pds.getConnection(); 
                Statement stmt = conn.createStatement(); 
                ResultSet rs = stmt.executeQuery(Query);) {

            //creo una variable para ver si existe el nombre que ha insertido el usuario
            boolean existe = false;

            while (rs.next()) {
                //pillo el nombre del videojuego a traves de la columna de la base de datos  
                String nJuego = rs.getString("Nombre");
                //verifico si el nombre coincide y si es asi me sale true
                if (nJuego.equals(nombre)) {
                    existe = true;
                }
            }

            //cierro la conexion con la base de datos
            conn.close();
            //devuelvo true si el nombre existe
            return existe;
        } catch (SQLException e) {
            //excepcion que salta si hay algun error derante la ejecucion de la consulta
            e.printStackTrace();
            return false;
        }
    }

    /**
     * lanza la consulta que le paso por parametro
     * @param consulta variable donde se guarda la consulta
     * @return devuelve el resultado de la consulta
     */
    public static String lanzaConsulta(String consulta) {
        //hago la conexion con la base de datos y ejecuto la consulta
        try (Connection conn = pds.getConnection(); 
                Statement stmt = conn.createStatement(); 
                ResultSet rs = stmt.executeQuery(consulta)) {

            while (rs.next()) {
                //imprimo a traves de consola la informacion detallada de cada fila
                System.out.print("EL ID es : " + rs.getInt("id"));
                System.out.print(", EL Nombre es : " + rs.getString("Nombre"));
                System.out.print(", EL Genere es : " + rs.getString("Genero"));
                System.out.print(", la Fecha de Lanzamiento es : " + rs.getDate("FechaLanzamiento"));
                System.out.print(", La Compañia es : " + rs.getString("Compañia"));
                System.out.println(", EL Precio es : " + rs.getFloat("Precio"));
            }
            //obtiene la informacion sobre las columnas de la base 
            ResultSetMetaData meta = rs.getMetaData();
            int colum = meta.getColumnCount();

            StringBuilder resultado = new StringBuilder();

            while (rs.next()) {
                for (int i = 1; i <= colum; i++) {
                    //Obtengo el valor de cada columna y lo agregao a la cadena
                    String valor = rs.getString(i);
                    resultado.append(meta.getColumnName(i)).append(": ").append(valor).append("\t");
                }
                resultado.append("\n");
            }
            //cierro la conexion
            conn.close();
            //devuelvo la cadena con los resultados de la cosnulta
            return resultado.toString();

        } catch (SQLException e) {
            //excepcion que salta si hay algun error durante la ejecucion de la consulta y devuelve error
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * añade un videojuego que le meto por paramtero 
     * @param Nombre variable donde guardo el nombre del juego
     * @param Genero variable donde guardo el tipo de genero del videojuego
     * @param FechaLanzamiento variable donde gurado la fecha del lanzamiento del videojuego
     * @param Compañia variable donde gurado la compañia del videojuego
     * @param Precio variable donde guardo el precio del juego
     */
    public static void nuevoRegistroParametro(String Nombre, String Genero, String FechaLanzamiento, String Compañia, double Precio) {
        //creo la consulta que quiero para añadir un videojuego a la base de datos y uso la varibales que he creado antes
        String Query = "INSERT INTO `videojuegos` (`Nombre`, `Genero`, `FechaLanzamiento`, `Compañia`, `Precio`) "
                + "VALUES ('" + Nombre + "', '" + Genero + "', '" + FechaLanzamiento + "', '" + Compañia + "', " + Precio + ")";

        //hago la conexion a la base de datos y ejecuto la consulta
        try (Connection conn = pds.getConnection();  
                Statement stmt = conn.createStatement();) {

            //ejecuto la consulta y ejecuto un mensaje si se añade el videojuego
            stmt.executeUpdate(Query);
            System.out.println("videojuego insertado");
            
            //cierro la conexion a la base de datos
            conn.close();

        } catch (SQLException e) {
            //excepcion que salta si hay algun error durante la ejecucion de la consulta y devuelve error
            e.printStackTrace();
        }
    }

    /**
     * añadir un videojuego por teclado
     * @throws ParseException 
     */
    public static void nuevoRegistroTeclado() throws ParseException {
        //creo el objeto scanner para leer la entrada por teclado
        Scanner sc = new Scanner(System.in);
        String Query;
        String Nombre, Genero, FechaLanzamiento, Compañia;
        float Precio;

        //hago la conexion a la base de datos
        try (Connection conn = pds.getConnection(); 
                Statement stmt = conn.createStatement();) {

            //solicito al usuario que ingrese todos los parametros
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

            //construyo la consulta con los datos que ha ingresado el usuario
            Query = "INSERT INTO `videojuegos` (`id`, `Nombre`, `Genero`, `FechaLanzamiento`, `Compañia`, `Precio`) "
                    + "VALUES (NULL,'" + Nombre + "', '" + Genero + "', '" + FechaLanzamiento + "', '" + Compañia + "', " + Precio + ")";

            //ejecuto la consulta y enseño un mensaje 
            stmt.executeUpdate(Query);
            System.out.println("\n\tvideojuego insertado");

            //cierro la conexion
            conn.close();

        } catch (SQLException e) {
            //excepcion que salta si hay algun error durante la ejecucion de la consulta
            e.printStackTrace();
        }
    }

    /**
     * elimina un videojuego
     * @param nombre variable donde se introduce el nombre del juego que se quiere eliminar
     * @return true si el juego ha sido eliminado o false si no ha sido eliminado
     */
    public static boolean eliminarRegistro(String nombre) {
        //consulta para eliminar el juego qu emete el usuario por teclaod
        String delQuery = "DELETE FROM videojuegos WHERE Nombre = '" + nombre + "'";

        //verifico si existe el nombre del videojuego en l abase de datos llamando al metodo buscarNombre
        if (buscarNombre(nombre)) {
            //establezco la conexion con la base de datos
            try (Connection conn = pds.getConnection(); 
                    Statement stmt = conn.createStatement();) {

                //ejecuto la consulta para eliminar un juego
                stmt.executeUpdate(delQuery);

                //cierro la conexion con la base de datos
                conn.close();
                //el programa me devuelve true si se ha eliminado correctamente
                return true;

            } catch (Exception e) {
                //excepcion que salta si hay algun error durante la ejecucion de la consulta
                e.printStackTrace();
                //el programa me devuelve false si hay algun problema a la hora de eliminar el videojuego
                return false;
            }
        } else {
            //si le videojuego no existe en la base de datos imprime este mensaje 
            System.out.println("El videojuego introducido no existe en la base");
            //el programa me devuelve este mensaje para indicarme que el videojuego no existe y por lo tanto no lo puede eliminar
            return false;
        }
    }

    
    /*ConexionBDEx*/
    
    /**
     * metodo que lanza una sentencia ya preparada para obtener infromacion sobre un videojuego en especifico
     * @throws SQLException 
     */
    public static void lanzarConsultaV2() throws SQLException {
        //consulta para saber la informacion de un videojuego  de la base de datos a traves de su nombre
        String Query = "SELECT * FROM `videojuegos` WHERE nombre = ? ";
        
        //me conecto a la base de datos 
        Connection conn = pds.getConnection();
        
        //meto el videojugeo que quiero buscar la infromacion y ejecuto la consulta 
        try {
            PreparedStatement sentencia = conn.prepareStatement (Query);
            sentencia.setString(1, "Mario Bors");
            ResultSet rs = sentencia.executeQuery();
            
            while (rs.next()) {
                //impirmo por consola el resultado 
                System.out.print("EL ID es : " + rs.getInt("id"));
                System.out.print(", EL Nombre es : " + rs.getString("Nombre"));
                System.out.print(", EL Genere es : " + rs.getString("Genero"));
                System.out.print(", la Fecha de Lanzamiento es : " + rs.getDate("FechaLanzamiento"));
                System.out.print(", La Compañia es : " + rs.getString("Compañia"));
                System.out.println(", EL Precio es : " + rs.getFloat("Precio"));
            }
        } catch (SQLException e) {
            //excepcion que salta si hay algun error durante la ejecucion de la consulta
            e.printStackTrace();
        } finally {
            //cierro la conexion en la base de la base de datos
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    /**
     * metodo que inserta un videojuego en la base de datos por parametro
     * @throws SQLException 
     */
    public static void nuevoRegistroParametroV2() throws SQLException {
        //me conecto con la base de datos
        Connection conn = pds.getConnection(); 
        try {
            //creo la consulta para añadir el videojuego a la base de datos el valor NULL es el id en la base de datos
            PreparedStatement sentencia = conn.prepareStatement("INSERT INTO videojuegos values (NULL, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            
            //establezco los valores y los numeros indican las posiciones de cada ?
            sentencia.setString(1, "mama");
            sentencia.setString(2, "shooter");
            sentencia.setString(3, "2007-04-11");
            sentencia.setString(4, "Steam");
            sentencia.setFloat(5, 15);
            
            //ejecuto la consuta
            sentencia.executeUpdate();
            
            //obtengo las claves generadas en este caso el ID que se ha autogenerado durante la insercion del videojuego
            ResultSet rs = sentencia.getGeneratedKeys();
            while (rs.next()) {
                int claveGuardada = rs.getInt(1);
                System.out.println("Clave guardada = " + claveGuardada);
            }
            
            //cierro la conexion
            conn.close();
        } catch (SQLException e) {
            //excepcion que salta si hay algun error durante la ejecucion de la consulta
            e.printStackTrace();
        }finally{
            //cierro la conexion en la base de datos
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    /**
     * metodo que permite al usuario ingresar un nuevo registro por medio del teclado y añadirlo a la base de datos
     * @throws SQLException 
     */
    public static void nuevoRegistroTecladoV2() throws SQLException {
        //se crea el scanner para leer la entrada
        Scanner sc = new Scanner(System.in);

        //decalro estas variables para que se guarden los datos del videojuego
        String Nombre, Genero, FechaLanzamiento, Compañia;
        float Precio;
        
        //me conecto a la base de datos
        Connection conn = pds.getConnection(); 

        try {
            //creo la consulta para añadir el videojuego a la base de datos el valor NULL es el id en la base de datos
            PreparedStatement sentencia = conn.prepareStatement("INSERT INTO videojuegos values (NULL, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            //le pido al usario que inserte la informacion para el nuevo videojuego
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

            //establezco los valores de los parametros 
            sentencia.setString(1, Nombre);
            sentencia.setString(2, Genero);
            sentencia.setString(3, FechaLanzamiento);
            sentencia.setString(4, Compañia);
            sentencia.setFloat(5, Precio);
            
            //ejecuto la consulta
            sentencia.executeUpdate();
            
            //imprimo este mesnaje por la consola si el videojuego se ha insertado 
            System.out.println("\n\tvideojuego insertado");


        } catch (SQLException e) {
            //excepcion que salta si hay algun error durante la ejecucion de la consulta
            e.printStackTrace();
        } finally{
            //cierro la conexion en la base de datos
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    /*public static void eliminarRegistroV2(String nombre) throws SQLException {
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
    }*/
}