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

package org.apache.iotdb.db.queryengine.plan.relational.sql.ast;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Objects;

public class KillQuery extends Statement {
  private final String queryId;

  public KillQuery(String queryId, @Nullable NodeLocation location) {
    super(location);
    this.queryId = queryId;
  }

  public boolean isKillAll() {
    return queryId == null;
  }

  public String getQueryId() {
    return queryId;
  }

  @Override
  public List<Node> getChildren() {
    return ImmutableList.of();
  }

  @Override
  public <R, C> R accept(AstVisitor<R, C> visitor, C context) {
    return visitor.visitKillQuery(this, context);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KillQuery killQuery = (KillQuery) o;
    return Objects.equals(queryId, killQuery.queryId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(queryId);
  }

  @Override
  public boolean shallowEquals(Node other) {
    return sameClass(this, other);
  }

  @Override
  public String toString() {
    return "KILL QUERY " + queryId;
  }
}
