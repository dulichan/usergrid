/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.usergrid.persistence.graph.impl;


import java.util.UUID;

import org.apache.usergrid.persistence.collection.mvcc.entity.ValidationUtils;
import org.apache.usergrid.persistence.graph.Edge;
import org.apache.usergrid.persistence.graph.SearchByEdgeType;
import org.apache.usergrid.persistence.model.entity.Id;


/**
 *
 *
 */
public class SimpleSearchByEdgeType implements SearchByEdgeType{

    private final Id node;
    private final String type;
    private final UUID maxVersion;
    private final Edge last;


    /**
     * Create the search modules
     * @param node The node to search from
     * @param type The edge type
     * @param maxVersion The maximum version to return
     * @param last The value to start seeking from.  Must be >= this value
     */
    public SimpleSearchByEdgeType( final Id node, final String type, final UUID maxVersion, final Edge last ) {
        ValidationUtils.verifyIdentity(node);
        ValidationUtils.verifyString( type, "type" );
        ValidationUtils.verifyTimeUuid( maxVersion, "maxVersion" );


        this.node = node;
        this.type = type;
        this.maxVersion = maxVersion;
        this.last = last;
    }


    @Override
    public Id getNode() {
        return node;
    }


    @Override
    public String getType() {
        return type;
    }


    @Override
    public UUID getMaxVersion() {
        return maxVersion;
    }


    @Override
    public Edge last() {
        return last;
    }
}