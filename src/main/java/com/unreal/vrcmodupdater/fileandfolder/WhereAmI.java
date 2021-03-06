package com.unreal.vrcmodupdater.fileandfolder;


import com.unreal.vrcmodupdater.exceptions.WrongFolderException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WhereAmI {

    String getPath() {
        return System.getProperty("user.dir");
    }

    public String getValidExecutionPath() throws WrongFolderException {
        String path = getPath();
        Pattern pattern = Pattern.compile("(.*)(\\\\VRChat\\\\Mods)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) return path;
        throw new WrongFolderException(path + " is not a valid path");
    }
}
