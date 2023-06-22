package com.example.examenprimerparcial.config;

import java.io.File;

public class Settings {
    public static final String NameDatabase = "EXAMENPP";

    //Table
    public static final String tableContacts = "Contactos";

    //Table fields
    public static final String id = "id";
    public static final String nombres = "nombres";

    public static final String pais = "pais";
    public static final String telefono= "telefono";
    public static final String nota = "nota";
    public static final String foto = "foto";

    //DDL Create and Drop

    public static final String CreateTableContacts = "CREATE TABLE Contactos" +
            "( id INTEGER PRIMARY KEY AUTOINCREMENT, nombres TEXT, pais TEXT, telefono INTEGER, nota TEXT, foto TEXT)";

    public static final String DropTableContacts = "DROP TABLE IF EXISTS Contactos";

    public static final String SelectTableContacts = "SELECT * FROM " + Settings.tableContacts;


}
