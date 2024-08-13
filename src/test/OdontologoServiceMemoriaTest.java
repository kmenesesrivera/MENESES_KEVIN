package test;

import dao.impl.OdontologoDaoMemoria;
import model.Odontologo;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.OdontologoService;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OdontologoServiceMemoriaTest {
    static Logger logger = Logger.getLogger(OdontologoServiceTest.class);
    OdontologoService odontologoService = new OdontologoService(new OdontologoDaoMemoria());
    @Test
    @DisplayName("Testear que se liste los odontologos de la base de datos")
    void caso1(){
        //dado
        Odontologo odontologo = new Odontologo(1,"Romero","Luciana");
        Odontologo odontologo2 = new Odontologo(2,"Rawer","Lucio");
        Odontologo odontologo3 = new Odontologo(3,"Rodolfo","Lucas");
        // cuando
        odontologoService.guardarOdontologo(odontologo);
        odontologoService.guardarOdontologo(odontologo2);
        odontologoService.guardarOdontologo(odontologo3);
        // entonces
        assertNotNull(odontologoService.listarOdontologos());
    }


}