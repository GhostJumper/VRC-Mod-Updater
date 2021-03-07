package com.unreal.vrcmodupdater.mods;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Optional;

public class KnownMods {

    String getDBPath() {
        return "src\\main\\resources\\mods.sqlite";
    }

    public boolean isDBValid() {
        File db = new File(getDBPath());
        if (!db.exists() || db.isDirectory()) return false;
        return true;
    }

    public Optional<String> getDownloadUrlFromName(String modName) throws ClassNotFoundException, SQLException, FileNotFoundException {

        if (!isDBValid()) throw new FileNotFoundException(getDBPath() + " is not a valid DB path");

        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + getDBPath());
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM mods WHERE mod_name like '" + modName + "'");

        while (resultSet.next()) {
            return Optional.of(resultSet.getString("download_url"));
        }
        return Optional.empty();
    }
}
