/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Properties;

public class MavenWrapperDownloader {

    private static final String WRAPPER_VERSION = "3.2.0";
    private static final String DEFAULT_DOWNLOAD_URL =
            "https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/" +
                    WRAPPER_VERSION + "/maven-wrapper-" + WRAPPER_VERSION + ".jar";

    public static void main(String[] args) {
        System.out.println("- Downloading Maven Wrapper JAR");

        File baseDirectory = new File(args[0]);
        File propertiesFile = new File(baseDirectory, ".mvn/wrapper/maven-wrapper.properties");
        File wrapperJar = new File(baseDirectory, ".mvn/wrapper/maven-wrapper.jar");

        String downloadUrl = DEFAULT_DOWNLOAD_URL;
        if (propertiesFile.exists()) {
            Properties props = new Properties();
            try (InputStream in = new URL(propertiesFile.toURI().toString()).openStream()) {
                props.load(in);
                String wrapperUrl = props.getProperty("wrapperUrl");
                if (wrapperUrl != null && !wrapperUrl.isBlank()) {
                    downloadUrl = wrapperUrl;
                }
            } catch (Exception ignored) {
            }
        }

        try {
            downloadFileFromURL(downloadUrl, wrapperJar);
            System.out.println("- Maven Wrapper JAR downloaded to " + wrapperJar.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("- Error downloading Maven Wrapper JAR from " + downloadUrl);
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void downloadFileFromURL(String urlString, File destination) throws IOException {
        if (!destination.getParentFile().exists() && !destination.getParentFile().mkdirs()) {
            throw new IOException("Could not create directory: " + destination.getParentFile());
        }
        URL url = new URL(urlString);
        try (InputStream in = url.openStream();
             ReadableByteChannel rbc = Channels.newChannel(in);
             FileOutputStream fos = new FileOutputStream(destination)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }
}

