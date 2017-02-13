package com.practica.jmm.mascotaspreferidas.presentador;

/**
 * Created by sath on 4/01/17.
 */

public interface IFragmentPerfilMascotaPresenter {

    public void obtenerMascotasDB();

    public void mostrarMascotasRV();

    public void obtenerMediosRecientes();

    public void obtenerIdUsuario();

    public void obtenerMediosRecienteesByUserId(String user);
}
