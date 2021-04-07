package com.unreal.vrcmodupdater.mods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class KnownMods {

    HashMap<String, String> modMap = new HashMap<>();

    public KnownMods() {
        modMap.put("AdvancedSafety", "knah/VRCMods/");
        modMap.put("FavCat-merged", "knah/VRCMods/");
        modMap.put("JoinNotifier", "knah/VRCMods/");
        modMap.put("IKTweaks", "knah/VRCMods/");
        modMap.put("LagFreeScreenshots", "knah/VRCMods/");
        modMap.put("TrueShaderAntiCrash", "knah/VRCMods/");
        modMap.put("UIExpansionKit", "knah/VRCMods/");
        modMap.put("ViewPointTweaker", "knah/VRCMods/");
        modMap.put("LagFreeScreenshots-merged", "knah/VRCMods/");
        modMap.put("CameraMinus", "knah/VRCMods/");
        modMap.put("EmojiPageButtons", "knah/VRCMods/");
        modMap.put("Finitizer", "knah/VRCMods/");
        modMap.put("SparkleBeGone", "knah/VRCMods/");
        modMap.put("ParticleAndBoneLimiterSettings", "knah/VRCMods/");
        modMap.put("FriendsPlusHome", "knah/VRCMods/");
        modMap.put("LocalPlayerPrefs", "knah/VRCMods/");
        modMap.put("NoSteamAtAll", "knah/VRCMods/");
        modMap.put("MirrorResolutionUnlimiter", "knah/VRCMods/");
        modMap.put("RuntimeGraphicsSettings", "knah/VRCMods/");
        modMap.put("HWIDPatch", "knah/VRCMods/");
        modMap.put("CoreLimiter", "knah/VRCMods/");

        modMap.put("UserInfoExtentions", "loukylor/VRC-Mods/");
        modMap.put("CloningBeGone", "loukylor/VRC-Mods/");

        modMap.put("DownloadFix", "gompocp/DownloadFix/");
    }

    public HashMap<String, String> getModMap() {
        return this.modMap;
    }

    public Optional<String> getRepoUrlFromName(String modName) {
        String safeModName = modName.replaceAll("[^a-zA-Z0-9\\-]", "");
        String result = getModMap().get(safeModName);

        if (result == null) {
            System.out.println("No repo for " + safeModName + " found");
            return Optional.empty();
        }

        return Optional.of(result);
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
