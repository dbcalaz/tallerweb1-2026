package com.tallerwebi.presentacion;

public class DatosLogin {

  private String email;
  private String password;
  private String passwordConfirm;

  public DatosLogin(String email, String passwordInvalida) {
    this.email = email;
    this.password = passwordInvalida;
  }

  public DatosLogin(String email, String password, String passwordConfirm) {
    this.email = email;
    this.password = password;
    this.passwordConfirm = passwordConfirm;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPasswordConfirm() {
    return passwordConfirm;
  }
}
