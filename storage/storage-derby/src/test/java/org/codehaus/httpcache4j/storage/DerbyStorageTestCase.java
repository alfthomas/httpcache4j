/*
 * Copyright (c) 2009. The Codehaus. All Rights Reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.codehaus.httpcache4j.storage;

import org.junit.*;
import org.codehaus.httpcache4j.util.TestUtil;
import org.codehaus.httpcache4j.util.DeletingFileFilter;
import org.codehaus.httpcache4j.cache.Key;
import org.codehaus.httpcache4j.cache.Vary;
import org.codehaus.httpcache4j.HTTPResponse;
import org.codehaus.httpcache4j.MIMEType;
import org.codehaus.httpcache4j.Status;
import org.codehaus.httpcache4j.Headers;
import org.codehaus.httpcache4j.payload.InputStreamPayload;
import org.apache.commons.io.input.NullInputStream;

import java.io.File;
import java.net.URI;

/**
 * @author <a href="mailto:erlend@escenic.com">Erlend Hamnaberg</a>
 * @version $Revision: $
 */
public class DerbyStorageTestCase {
    private DerbyCacheStorage storage;
    private static final MIMEType MIME_TYPE = MIMEType.valueOf("text/uri-list");
    private static File storageDirectory;

    @BeforeClass
    public static void beforeClass() {
        storageDirectory = TestUtil.getTestFile("target/storage");
    }
    
    @Before
    public void setUp() {
        storage = new DerbyCacheStorage(storageDirectory);
    }
    
    @Test
    public void testPutSingleResponseWithEmptyVaryAndPayload() {
        Key key = Key.create(URI.create("foo"), new Vary());
        storage.put(key, new HTTPResponse(null, Status.OK, new Headers()));
    }

    @Test
    public void testPutSingleResponseWithEmptyVary() {
        Key key = Key.create(URI.create("foo"), new Vary());
        storage.put(key, new HTTPResponse(new InputStreamPayload(new NullInputStream(1), MIME_TYPE), Status.OK, new Headers()));
    }


    @After
    public void tearDown() {
        storage.clear();        
    }


    @AfterClass
    public static void afterClass() {
        storageDirectory.listFiles(new DeletingFileFilter());
    }
}
