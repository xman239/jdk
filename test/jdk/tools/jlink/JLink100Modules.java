/*
 * Copyright (c) 2022, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringJoiner;

/*
 * @test
 * @summary Make sure that 100 modules can be linked using jlink.
 * @bug 8240567
 * @modules jdk.jlink
 * @library /test/lib
 * @run main JLink100Modules
 */
public class JLink100Modules {

    public static void main(String[] args) throws Exception {
        Path src = Paths.get("bug8240567");

        StringJoiner mainModuleInfoContent = new StringJoiner("; requires ", "module mainModules { requires ", "}");

        // create 100 modules
        for (int i = 0; i<100; i++) {
            String name = "module" + i;
            Path moduleDir = Files.createDirectories(src.resolve(name));
            String moduleInfoContent = "module " + name + " {}";
            Files.writeString(moduleDir.resolve("module-info.java"), moduleInfoContent);
            mainModuleInfoContent.add(name);
        }

        // create module reading the generated modules
        Path mainModulePath = src.resolve("bug8240567");
        Path mainModuleInfo = mainModulePath.resolve("module-info.java");
        Files.writeString(mainModuleInfo, mainModuleInfoContent.toString());


    }
}

