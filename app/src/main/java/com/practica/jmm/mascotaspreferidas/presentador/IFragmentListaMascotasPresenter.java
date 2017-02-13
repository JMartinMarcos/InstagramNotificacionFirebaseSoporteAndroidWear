package com.practica.jmm.mascotaspreferidas.presentador;

/**
 * Created by sath on 3/01/17.
 */

public interface IFragmentListaMascotasPresenter {

    public void obtenerMascotasDB();

    public void obtenerMediosRecientes();

    public void mostrarMascotasRV();

    public void obtenerIdUser();

    public void obtenerMediosRecienteesByUserId(String user);

}
