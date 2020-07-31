package com.yrx.squirrel.nut.concurrent.java.file.scanner;

import java.io.IOException;
import java.util.List;

/**
 * Created by r.x on 2020/7/31.
 */
public interface PackageScanner {
    List<String> getFullyQualifiedClassNameList() throws IOException;
}
