package com.unreal.vrcmodupdater.fileandfolder;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

public class ModFileManager {

    public Optional<ArrayList<String>> getAllModNames(File folder) {
        ArrayList<String> result = new ArrayList<>();
        if (folder.listFiles() != null)
            for (File file : folder.listFiles()) {
                if (file.getName().endsWith(".dll"))
                    result.add(file.getName());
            }

        return (result.size() > 0 ? Optional.of(result) : Optional.empty());
    }
}
