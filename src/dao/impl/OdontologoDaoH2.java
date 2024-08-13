package dao.impl;

import dao.IDao;
import db.H2Connection;
import model.Odontologo;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoH2 implements IDao<Odontologo> {
    public static final Logger logger = Logger.getLogger(OdontologoDaoH2.class);
    public static final String INSERT = "INSERT INTO ODONTOLOGO VALUES (DEFAULT,?,?)";
    public static final String SELECT_ID = "SELECT * FROM ODONTOLOGO WHERE MATRICULA = ?";
    public static final String SELECT_ALL = "SELECT * FROM ODONTOLOGO";

    @Override
    public Odontologo guardar(Odontologo Odontologo) {
        Connection connection = null;
        Odontologo OdontologoARetornar = null;
        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, Odontologo.getApellido());
            preparedStatement.setString(2, Odontologo.getNombre());
            preparedStatement.executeUpdate();
            connection.commit();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                OdontologoARetornar = new Odontologo(id, Odontologo.getApellido(), Odontologo.getNombre());
            }
            logger.info("Odontologo persistido " + OdontologoARetornar);

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    logger.error(ex.getMessage());
                    e.printStackTrace();
                }
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return OdontologoARetornar;
    }

    @Override
    public  List<Odontologo>  buscarTodos() {
        Connection connection = null;
        List<Odontologo> listaOdontologos;
        listaOdontologos= new ArrayList<>();
        try {
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer idDB = resultSet.getInt(1);
                String apellido = resultSet.getString(2);
                String nombre = resultSet.getString(3);
                listaOdontologos.add(new Odontologo(idDB, apellido, nombre));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }

        logger.info("Listar Odontologos");
        for(Odontologo odontologo: listaOdontologos){
            logger.info(odontologo);
        }
        return listaOdontologos;
    }

}
