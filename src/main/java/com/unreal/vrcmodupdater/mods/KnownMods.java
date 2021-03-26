package com.unreal.vrcmodupdater.mods;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

public class KnownMods {

    String getDBPath() {
        return "src/main/resources/mods.sqlite";
    }

    public boolean isDBValid() {
        File db = new File(getDBPath());
        if (!db.exists() || db.isDirectory()) return false;
        return true;
    }

    public Optional<String> getRepoUrlFromName(String modName) {
        /*
        if (!isDBValid()) {
            System.out.println(getDBPath() + " is not a valid DB path");
            return Optional.empty();
        }

         */


        String safeModName = modName.replaceAll("[^a-zA-Z0-9\\-]", "");
        if (!safeModName.equals(modName)) System.out.println("this mod contains special characters: " + modName);

        try {

            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + getDBPath());
            connection.setAutoCommit(false);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM mods WHERE mod_name IS '" + safeModName + "'");

            while (resultSet.next()) {
                return Optional.of(resultSet.getString("download_url"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("No repo for " + safeModName + " found");
        return Optional.empty();
    }

    public ArrayList<ModCluster> groupModsByRepo(ArrayList<String> modNames) {
        ArrayList<ModCluster> result = new ArrayList<>();

        for (String modName : modNames) {
            Optional<String> repoUrl = getRepoUrlFromName(modName);
            if (repoUrl.isPresent()) {
                result = ModCluster.addUrlToClusters(result, repoUrl.get(), modName);
            }
        }

        return result;
    }

}
