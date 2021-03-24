package com.unreal.vrcmodupdater.mods;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;

@Builder
@RequiredArgsConstructor
public class ModCluster {

    @Getter
    private final String repoUrl;

    @Getter
    private final ArrayList<String> modNames;

    public ModCluster(String repoUrl, String... modNames) {
        this.repoUrl = repoUrl;
        this.modNames = new ArrayList<>(Arrays.asList(modNames));
    }

    public void addMod(String modName) {
        modNames.add(modName);
    }

    public static ArrayList<ModCluster> addUrlToClusters(ArrayList<ModCluster> modClusters, String repoUrl, String modName) {
        boolean added = false;

        for (ModCluster modCluster : modClusters) {
            if (modCluster.repoUrl.equals(repoUrl)) {
                modCluster.addMod(modName);
                added = true;
                break;
            }
        }

        if (!added)
            modClusters.add(new ModClusterBuilder().repoUrl(repoUrl).modNames(new ArrayList<>(Arrays.asList(modName))).build());

        return modClusters;
    }


}
