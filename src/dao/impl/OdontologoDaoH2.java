package dao.impl;

import dao.IDao;
import db.H2Connection;
import model.Odontologo;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoH2 implements IDao<Odontologo> {
    private static final Logger logger = Logger.getLogger(OdontologoDaoH2.class);
    private static final String INSERT = "INSERT INTO ODONTOLOGO(APELLIDO, NOMBRE) VALUES (?, ?)";
    private static final String SELECT_ID = "SELECT * FROM ODONTOLOGO WHERE MATRICULA = ?";
    private static final String SELECT_ALL = "SELECT * FROM ODONTOLOGO";

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        Odontologo odontologoARetornar = null;

        try (Connection connection = H2Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, odontologo.getApellido());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    Integer id = resultSet.getInt(1);
                    odontologoARetornar = new Odontologo(id, odontologo.getApellido(), odontologo.getNombre());
                }
            }

            logger.info("Odontologo persistido " + odontologoARetornar);

        } catch (SQLException e) {
            logger.error("Error al guardar el odontólogo: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return odontologoARetornar;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        List<Odontologo> listaOdontologos = new ArrayList<>();

        try (Connection connection = H2Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Integer idDB = resultSet.getInt(1);
                String apellido = resultSet.getString(2);
                String nombre = resultSet.getString(3);
                listaOdontologos.add(new Odontologo(idDB, apellido, nombre));
            }

        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error al buscar todos los odontólogos: " + e.getMessage(), e);
        }

        logger.info("Listar Odontologos");
        for (Odontologo odontologo : listaOdontologos) {
            logger.info(odontologo);
        }

        return listaOdontologos;
    }
}
