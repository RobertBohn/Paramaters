package com.company;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import sun.misc.IOUtils;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class Main {

    @Option(name = "-u", aliases = "--user", required = true, usage = "user name.")
    private String user;

    @Option(name = "-s", aliases = "--password", usage = "password.")
    private String password;

    public static void main(String[] args) throws JAXBException, UnsupportedEncodingException {
        final Main main = new Main();
        final CmdLineParser cmdParser = new CmdLineParser(main);

        try {
            cmdParser.parseArgument(args);
            main.display();

            Customer customer = main.xmlString2class();
            main.class2xmlString(customer);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            cmdParser.printUsage(System.err);
            System.exit(1);
        }
    }

    public void display() {
        System.out.println("user: " + user);
        System.out.println("password: " + password);
    }

    public Customer xmlString2class() throws JAXBException, UnsupportedEncodingException {
        String c = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><gtwy:tupacCatskillsTemplate xmlns:gtwy=\"http://schema.amazon.com/zgw/v1\"><firstName>robert</firstName><lastName>smith</lastName></gtwy:tupacCatskillsTemplate>";

        Unmarshaller unmarshaller = JAXBContext.newInstance(Customer.class).createUnmarshaller();
        StreamSource streamSource = new StreamSource(new ByteArrayInputStream(c.getBytes("UTF-8")));
        JAXBElement<Customer> jaxElement = unmarshaller.unmarshal(streamSource, Customer.class);

        Customer customer = jaxElement.getValue();
        return customer;
    }

    public void class2xmlString(Customer customer) throws JAXBException, UnsupportedEncodingException {
        StringWriter stringWriter = new StringWriter();

        JAXBElement<Customer> jaxElement = new JAXBElement<Customer>(new QName("http://schema.amazon.com/zgw/v1", "tupacCatskillsTemplate", "gtwy"), Customer.class, customer);
        Marshaller marshaller = JAXBContext.newInstance(Customer.class).createMarshaller();
        marshaller.marshal(jaxElement, stringWriter);

        System.out.println(stringWriter.toString());
    }
}
