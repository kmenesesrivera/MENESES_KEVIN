package dao.impl;

import dao.IDao;
import model.Odontologo;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoMemoria implements IDao<Odontologo> {
    public static final Logger logger = Logger.getLogger(OdontologoDaoMemoria.class);
    private List<Odontologo> odontologos = new ArrayList<>();

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        odontologo.setMatricula(odontologos.size() + 1);
        odontologos.add(odontologo);
        logger.info("Odontologo guardado " + odontologo);
        return odontologo;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        List<Odontologo> odontologosARetornar = null;
        odontologosARetornar = this.odontologos;

        logger.info("Listar Odontologos");
        for(Odontologo odontologo: odontologos){
            logger.info(odontologo);
        }
        return odontologosARetornar;
    }


}
