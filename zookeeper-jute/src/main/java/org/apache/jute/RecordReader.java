/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jute;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Front-end interface to deserializers. Also acts as a factory
 * for deserializers.
 */
public class RecordReader {

    private static HashMap<String, Method> archiveFactory;

    private InputArchive archive;

    static {
        archiveFactory = new HashMap<>();

        try {
            archiveFactory.put(
                    "binary",
                    BinaryInputArchive.class.getDeclaredMethod("getArchive", InputStream.class));
        } catch (SecurityException | NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }

    private static InputArchive createArchive(InputStream in, String format) {
        Method factory = archiveFactory.get(format);

        if (factory != null) {
            Object[] params = {in};
            try {
                return (InputArchive) factory.invoke(null, params);
            } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Creates a new instance of RecordReader.
     *
     * @param in     Stream from which to deserialize a record
     * @param format Deserialization format ("binary", "xml", or "csv")
     */
    public RecordReader(InputStream in, String format) {
        archive = createArchive(in, format);
    }

    /**
     * Deserialize a record.
     *
     * @param r Record to be deserialized
     */
    public void read(Record r) throws IOException {
        r.deserialize(archive, "");
    }

}
