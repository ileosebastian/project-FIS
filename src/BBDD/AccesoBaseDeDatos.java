package BBDD;
import java.sql.*;
import javax.swing.JOptionPane;

public class AccesoBaseDeDatos {
    private final String host;
    private final String nombre_bd;
    private final String user;
    private final String password;
    private Connection conexion;

    public AccesoBaseDeDatos(String host, String nombre_bd, String user, String password) {
        this.host = host;
        this.nombre_bd = nombre_bd;
        this.user = user;
        this.password = password;
    }
    
    //Obtenr los datos del acceso
    public String getHost() {
        return host;
    }

    public String getNombre_bd() {
        return nombre_bd;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public Connection getConexion() {
        return conexion;
    }
    
    public void conectar () throws Exception //CONECTAR CON LA BASE DE DATOS
    {
        try {
            //agrgar el driver del sistema
            Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
            DriverManager.registerDriver(driver);//instala en driver
            //"jdbc:mysql://" + servidor + ":" + puerto + "/" + bd;
            String servidor = "jdbc:mysql://"+getHost()+":3306"+"/"+getNombre_bd();
            //permite conectar YA con la BD
            conexion = DriverManager.getConnection(servidor,getUser(),getPassword());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "ERROR AL CONECTAR", JOptionPane.WARNING_MESSAGE);
            JOptionPane.showMessageDialog(null, e.toString(), "Posiblemente sea el puerto de conexion con la base de datos", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void cerrarConexion () throws Exception //CERRAR LA CONEXION CON LA BASE DE DATOS
    {
        conexion.close();
    }
    
    
    /*
    *Solo retorna tablas O valores de una tabla en concreto
    *Puede ser una visualizacion de los datos
    OJO: Consultar puede llevar a retornar SOLO UN VALOR en concreto
    */
    public ResultSet consultar (String sql) throws Exception
    {
        //variable que guardara las tablas que se quiere consultar
        ResultSet tablas;
        Statement sentencia = conexion.createStatement();
        //sql almacena la sentencia que se quiere hacer
        tablas = sentencia.executeQuery(sql); 
        
        return tablas;//Devuelve las tablas que se quiere consultar
    }
    
    /*
    *NO devuelve nada, porque solo modifica los datos de la Base de Datoa
    ESO CONLLEVA A PODER:
        INSERTAR valores concretos o registros Completos
        MODIFICAR registros o columnas concretas
        ELIMINAR registros enteros
    */
    public void actualizar (String sql) throws Exception
    {
        try {
            Statement sentencia = conexion.createStatement();
            sentencia.executeUpdate(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "ERROR AL ACUALIZAR", JOptionPane.WARNING_MESSAGE);
        }
    }
}
