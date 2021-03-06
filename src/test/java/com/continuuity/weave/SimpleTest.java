/*
 * Copyright 2012-2013 Continuuity,Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.continuuity.weave;

import com.google.common.base.Throwables;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.net.InetAddress;

/**
 *
 */
public class SimpleTest {

  @Test
  public void test() {
    try {
      Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        .parse(getClass().getClassLoader().getResourceAsStream("logback-template.xml"));

      NodeList appenders = document.getElementsByTagName("appender");
      for (int i = 0; i < appenders.getLength(); i++) {
        Node node = appenders.item(i);
        if ("KAFKA".equals(node.getAttributes().getNamedItem("name").getNodeValue())) {
          Element hostname = document.createElement("hostname");
          hostname.appendChild(document.createTextNode(InetAddress.getLocalHost().getCanonicalHostName()));
          node.appendChild(hostname);
        }
      }

      StringWriter result = new StringWriter();
      try {
        TransformerFactory.newInstance().newTransformer().transform(new DOMSource(document), new StreamResult(result));
      } finally {
        result.close();
      }
      System.out.println(result.toString());
    } catch (Exception e) {
      throw Throwables.propagate(e);
    }

  }
}
