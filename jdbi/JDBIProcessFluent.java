package com.navteq.cf.foundation.jdbi;

import com.navtech.util.xml.XMLTools;
import com.navteq.cf.foundation.process.TaskDefinitionElement;
import com.navteq.cf.foundation.workflow.InvalidConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.HashMap;
import java.util.Map;

public class JDBIProcessFluent extends JDBIProcess
{
  private Map<String, IO> ios = new HashMap<>();

  @Override
  protected void connect()
  {
    super.connect();
    ios.forEach(
        (n, i) -> i.connect(transports.get(i.getTransport()).handle)
    );
  }

  public Input getInput(String name) throws InvalidConfigurationException
  {
    IO io = ios.get(name);
    if (io instanceof Input)
    {
      return (Input) io;
    }
    else
    {
      throw new InvalidConfigurationException(String.format("Input %s doesn't found", name));
    }
  }

  public Output getOutput(String name) throws InvalidConfigurationException
  {
    IO io = ios.get(name);
    if (io instanceof Output)
    {
      return (Output) io;
    }
    else
    {
      throw new InvalidConfigurationException(String.format("Output %s doesn't found", name));
    }
  }

  @Override
  public void initialize(TaskDefinitionElement taskDefinitionElement) throws InvalidConfigurationException
  {
    Element processConfigElem = taskDefinitionElement.getTaskElement();
    Element transportRefElem = XMLTools.getFirstElement(processConfigElem, "TransportRef");
    if (transportRefElem == null)
    {
      throw new InvalidConfigurationException("Transport " + getName() + " not found.");
    }
    String defTransport = transportRefElem.getTextContent();
    XPathFactory xPathfactory = XPathFactory.newInstance();
    XPath xpath = xPathfactory.newXPath();
    try
    {
      XPathExpression expr = xpath.compile("Data/Input|Data/Output");
      NodeList nl = (NodeList) expr.evaluate(processConfigElem, XPathConstants.NODESET);
      for (int i = 0; i < nl.getLength(); i++)
      {
        Node node = nl.item(i);
        String ioName = node.getAttributes().getNamedItem("Name").getTextContent();
        if (ioName == null)
        {
          throw new InvalidConfigurationException("I/O name attribute is not defined");
        }
        expr = xpath.compile("TransportRef/@Name");
        Node tNode = (Node) expr.evaluate(nl.item(i), XPathConstants.NODE);
        String transport;
        if (tNode == null)
        {
          transport = defTransport;
        } else
        {
          transport = tNode.getTextContent();
        }

        Transport t = new Transport();
        t.database = getConfiguration().getDatabase(transport);
        transports.put(transport, t);
        expr = xpath.compile("SQL");
        Node sNode = (Node) expr.evaluate(nl.item(i), XPathConstants.NODE);
        String sql;
        if (tNode == null)
        {
          throw new InvalidConfigurationException("SQL is not provided");
        }
        else
        {
          sql = sNode.getTextContent();
        }
        IO io = null;
        if ("Input".equals(node.getNodeName()))
        {
          io = new JdbiInput();
        }
        else if ("Output".equals(node.getNodeName()))
        {
          io = new JdbiOutput();
        }
        if (io != null)
        {
          io.initialize(transport, sql);
          ios.put(ioName, io);
        }
      }
    }
    catch (XPathExpressionException e)
    {
      throw new InvalidConfigurationException(e);
    }
  }
}
