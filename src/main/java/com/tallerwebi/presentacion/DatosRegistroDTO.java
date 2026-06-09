package com.tallerwebi.presentacion;

public class DatosRegistroDTO {

  private String mail;
  private String password;
  private String repitePassword;
  private String nombre;
  private String apellido;
  private String telefono;

  public DatosRegistroDTO(String mail, String password, String repitePassword) {
    this.mail = mail;
    this.password = password;
    this.repitePassword = repitePassword;
  }

  public DatosRegistroDTO() {
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRepitePassword() {
    return repitePassword;
  }

  public void setRepitePassword(String repitePassword) {
    this.repitePassword = repitePassword;
  }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
