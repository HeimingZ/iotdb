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
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.iotdb.db.wal.io;

import org.apache.iotdb.db.wal.checkpoint.Checkpoint;
import org.apache.iotdb.db.wal.checkpoint.CheckpointType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** CheckpointReader is used to read all checkpoints from .checkpoint file. */
public class CheckpointReader {
  private static final Logger logger = LoggerFactory.getLogger(CheckpointReader.class);

  private final File logFile;
  private long maxMemTableId;
  private List<Checkpoint> checkpoints;

  public CheckpointReader(File logFile) {
    this.logFile = logFile;
    init();
  }

  private void init() {
    Set<Long> memtableIds = new HashSet<>();
    checkpoints = new ArrayList<>();
    try (DataInputStream logStream =
        new DataInputStream(new BufferedInputStream(new FileInputStream(logFile)))) {
      maxMemTableId = logStream.readLong();
      while (logStream.available() > 0) {
        Checkpoint checkpoint = Checkpoint.deserialize(logStream);
        checkpoints.add(checkpoint);
        if (!checkpoint.getMemTableInfos().isEmpty()) {
          long id = checkpoint.getMemTableInfos().get(0).getMemTableId();
          System.out.println(checkpoint.getType() + "memtable-" + id);
          if (memtableIds.contains(id)) {
            if (checkpoint.getType() == CheckpointType.FLUSH_MEMORY_TABLE) {
              memtableIds.remove(id);
            }
          } else {
            if (checkpoint.getType() == CheckpointType.CREATE_MEMORY_TABLE) {
              memtableIds.add(id);
            }
          }
          if (id == 5048) {
            int i = 0;
          }
        }
      }
    } catch (IOException e) {
      logger.warn(
          "Meet error when reading checkpoint file {}, skip broken checkpoints", logFile, e);
    }
  }

  public long getMaxMemTableId() {
    return maxMemTableId;
  }

  public List<Checkpoint> getCheckpoints() {
    return checkpoints;
  }

  public static void main(String[] args) {
    CheckpointReader checkpointReader =
        new CheckpointReader(new File("/Users/heimingz/Downloads/_0.checkpoint"));
    System.out.println(checkpointReader.getMaxMemTableId());
    System.out.println(checkpointReader.getCheckpoints());
  }
}
