package service;

import dao.IDao;
import model.Odontologo;

import java.util.ArrayList;
import java.util.List;

public class OdontologoService {
    private IDao<Odontologo> OdontologoIDao;

    public OdontologoService(IDao<Odontologo> OdontologoIDao) {
        this.OdontologoIDao = OdontologoIDao;
    }

    public Odontologo guardarOdontologo(Odontologo Odontologo) {
        return OdontologoIDao.guardar(Odontologo);
    }

    public List<Odontologo> listarOdontologos() {
        return OdontologoIDao.buscarTodos();
    }
}
