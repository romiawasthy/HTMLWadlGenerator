package com.rest.cxf.wadl;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.cxf.jaxrs.impl.HttpHeadersImpl;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.jaxrs.model.wadl.WadlGenerator;
import org.apache.cxf.message.Message;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HTMLWadlGenerator extends WadlGenerator {
  private final static Logger logger = Logger.getLogger(HTMLWadlGenerator.class.getName());

  @Override
  public Response handleRequest(Message m, ClassResourceInfo resource) {

    Response response = null;
    TransformerFactory factory = TransformerFactory.newInstance();
    Templates template;
    StringWriter writer = new StringWriter();
    HttpHeaders headers = new HttpHeadersImpl(m);

    List<MediaType> accepts = headers.getAcceptableMediaTypes();
    if (accepts.contains(MediaType.TEXT_HTML_TYPE)) {

      m.getExchange().put(Message.ACCEPT_CONTENT_TYPE, MediaType.APPLICATION_XML_TYPE);

      response = super.handleRequest(m, resource);

      String xmlString = response.getEntity().toString();

      try {

        template = factory.newTemplates(new StreamSource(getClass().getResourceAsStream("/wadl.xsl")));
        Transformer xformer = (Transformer) template.newTransformer();
        xformer.transform(new StreamSource(new StringReader(xmlString)), new StreamResult(writer));
        
        
      } catch (TransformerConfigurationException e) {
        logger.log(Level.SEVERE, e.toString());
      } catch (TransformerException e) {
        logger.log(Level.SEVERE, e.toString());
      }

      response = Response.ok().type(MediaType.APPLICATION_XML_TYPE).entity(writer.toString()).build();

    } else {
      response = super.handleRequest(m, resource);
    }

    return response;
  }

}
