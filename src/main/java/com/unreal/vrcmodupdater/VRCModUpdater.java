package com.unreal.vrcmodupdater;

import com.unreal.vrcmodupdater.exceptions.WrongFolderException;
import com.unreal.vrcmodupdater.fileandfolder.ModFileManager;
import com.unreal.vrcmodupdater.fileandfolder.WhereAmI;
import com.unreal.vrcmodupdater.github.NewRelease;
import com.unreal.vrcmodupdater.mods.KnownMods;
import com.unreal.vrcmodupdater.mods.ModCluster;
import org.kohsuke.github.GHAsset;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class VRCModUpdater {

    public static void main(String[] args) {
        new VRCModUpdater(args);
    }

    public VRCModUpdater(String[] args) {
        handleArgs(args);
    }


    private void handleArgs(String[] args) {
        if (args.length == 0) {
            noArgs();
        }
    }

    private void noArgs() {

        try {
            ModFileManager modFileManager = new ModFileManager();
            NewRelease newRelease = new NewRelease();

            File folder = new WhereAmI().getValidExecutionFolder();

            Optional<ArrayList<String>> allModNames = modFileManager.getAllModNames(folder);
            ArrayList<ModCluster> modClusters = new KnownMods().groupModsByRepo(allModNames.get());

            for (ModCluster modCluster : modClusters) {
                ArrayList<GHAsset> ghAssets = newRelease.getNewestReleasesByNames(modCluster.getModNames(), modCluster.getRepoUrl());

                for (GHAsset ghAsset : ghAssets) {
                    modFileManager.downloadFileFromAsset(ghAsset, folder.getAbsolutePath());
                }
            }


        } catch (IOException | WrongFolderException e) {
            System.out.println(e.getMessage());
        }
    }

}
