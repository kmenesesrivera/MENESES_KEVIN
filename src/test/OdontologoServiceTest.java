package test;

import dao.impl.OdontologoDaoH2;
import model.Odontologo;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.OdontologoService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

class OdontologoServiceTest {
    static Logger logger = Logger.getLogger(OdontologoServiceTest.class);
    OdontologoService odontologoService = new OdontologoService(new OdontologoDaoH2());

    @BeforeAll
    static void cargarTablas(){
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:./clinica;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "sa");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Testear que se liste los odontologos de la base de datos")
    void caso1() {
        //dado
        Odontologo odontologo = new Odontologo(2, "Romero", "Luciana");
        Odontologo odontologo1 = new Odontologo(3, "Aliaga", "Pavel");
        Odontologo odontologo2 = new Odontologo(4, "Pando", "Pierre");
        // cuando
         odontologoService.guardarOdontologo(odontologo);
         odontologoService.guardarOdontologo(odontologo1);
         odontologoService.guardarOdontologo(odontologo2);

        // entonces
        Assertions.assertNotNull(odontologoService.listarOdontologos());
    }

}