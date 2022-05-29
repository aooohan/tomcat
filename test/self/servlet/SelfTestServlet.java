/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package self.servlet;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : lihan
 * @date : 2022/5/27 21:56
 */
public class SelfTestServlet extends TomcatBaseTest {

    @Test
    public void testDemo() throws LifecycleException, IOException {
        Tomcat tomcat = getTomcatInstance();
        StandardContext ctx = (StandardContext) tomcat.addContext("", null);
        DemoServlet demoServlet = new DemoServlet();
        Tomcat.addServlet(ctx, "demoServlet", demoServlet);
        ctx.addServletMappingDecoded("/","demoServlet");
        tomcat.start();

        Map<String, List<String>> resHeaders= new HashMap<>();
        ByteChunk out = new ByteChunk();
        int rc = getUrl("http://localhost:" + getPort() + "/", out , resHeaders);
        Assert.assertEquals(HttpServletResponse.SC_OK, rc);
        Assert.assertEquals("hello world",out.toString());
    }

    public static class DemoServlet extends HttpServlet {
        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.getWriter().write("hello world");
        }
    }
}
