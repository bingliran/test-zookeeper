/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.zookeeper.graph;

import java.util.Iterator;

public interface LogSource extends Iterable<LogEntry> {
    public LogIterator iterator(long starttime, long endtime, FilterOp filter) throws IllegalArgumentException, FilterException;

    public LogIterator iterator(long starttime, long endtime) throws IllegalArgumentException;

    public LogIterator iterator() throws IllegalArgumentException;

    public boolean overlapsRange(long starttime, long endtime);

    public long size();

    public long getStartTime();

    public long getEndTime();
}
