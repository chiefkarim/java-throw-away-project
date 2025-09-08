package com.example.java_throw_away;

public class User{
private String email;
private String password;

public User(String email,String password){

  this.email  = email;
  this.password = password;
}

public String getEmail(){
  return email;
}

public String getPassword(){
  return password;
}

String hashPassword(String password){
  return password +1;
}

}
