
package cn.com.hwxt.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.com.hwxt.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DataReciveJsonResponse_QNAME = new QName("http://service.hwxt.com.cn/", "dataReciveJsonResponse");
    private final static QName _DataReciveXmlResponse_QNAME = new QName("http://service.hwxt.com.cn/", "dataReciveXmlResponse");
    private final static QName _FileReciveXml_QNAME = new QName("http://service.hwxt.com.cn/", "fileReciveXml");
    private final static QName _DataReciveXml_QNAME = new QName("http://service.hwxt.com.cn/", "dataReciveXml");
    private final static QName _FileReciveXmlResponse_QNAME = new QName("http://service.hwxt.com.cn/", "fileReciveXmlResponse");
    private final static QName _FileReciveJsonResponse_QNAME = new QName("http://service.hwxt.com.cn/", "fileReciveJsonResponse");
    private final static QName _DataReciveJson_QNAME = new QName("http://service.hwxt.com.cn/", "dataReciveJson");
    private final static QName _FileReciveJson_QNAME = new QName("http://service.hwxt.com.cn/", "fileReciveJson");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.com.hwxt.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DataReciveXml }
     * 
     */
    public DataReciveXml createDataReciveXml() {
        return new DataReciveXml();
    }

    /**
     * Create an instance of {@link DataReciveJsonResponse }
     * 
     */
    public DataReciveJsonResponse createDataReciveJsonResponse() {
        return new DataReciveJsonResponse();
    }

    /**
     * Create an instance of {@link DataReciveXmlResponse }
     * 
     */
    public DataReciveXmlResponse createDataReciveXmlResponse() {
        return new DataReciveXmlResponse();
    }

    /**
     * Create an instance of {@link FileReciveXml }
     * 
     */
    public FileReciveXml createFileReciveXml() {
        return new FileReciveXml();
    }

    /**
     * Create an instance of {@link FileReciveJsonResponse }
     * 
     */
    public FileReciveJsonResponse createFileReciveJsonResponse() {
        return new FileReciveJsonResponse();
    }

    /**
     * Create an instance of {@link DataReciveJson }
     * 
     */
    public DataReciveJson createDataReciveJson() {
        return new DataReciveJson();
    }

    /**
     * Create an instance of {@link FileReciveJson }
     * 
     */
    public FileReciveJson createFileReciveJson() {
        return new FileReciveJson();
    }

    /**
     * Create an instance of {@link FileReciveXmlResponse }
     * 
     */
    public FileReciveXmlResponse createFileReciveXmlResponse() {
        return new FileReciveXmlResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataReciveJsonResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.hwxt.com.cn/", name = "dataReciveJsonResponse")
    public JAXBElement<DataReciveJsonResponse> createDataReciveJsonResponse(DataReciveJsonResponse value) {
        return new JAXBElement<DataReciveJsonResponse>(_DataReciveJsonResponse_QNAME, DataReciveJsonResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataReciveXmlResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.hwxt.com.cn/", name = "dataReciveXmlResponse")
    public JAXBElement<DataReciveXmlResponse> createDataReciveXmlResponse(DataReciveXmlResponse value) {
        return new JAXBElement<DataReciveXmlResponse>(_DataReciveXmlResponse_QNAME, DataReciveXmlResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileReciveXml }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.hwxt.com.cn/", name = "fileReciveXml")
    public JAXBElement<FileReciveXml> createFileReciveXml(FileReciveXml value) {
        return new JAXBElement<FileReciveXml>(_FileReciveXml_QNAME, FileReciveXml.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataReciveXml }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.hwxt.com.cn/", name = "dataReciveXml")
    public JAXBElement<DataReciveXml> createDataReciveXml(DataReciveXml value) {
        return new JAXBElement<DataReciveXml>(_DataReciveXml_QNAME, DataReciveXml.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileReciveXmlResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.hwxt.com.cn/", name = "fileReciveXmlResponse")
    public JAXBElement<FileReciveXmlResponse> createFileReciveXmlResponse(FileReciveXmlResponse value) {
        return new JAXBElement<FileReciveXmlResponse>(_FileReciveXmlResponse_QNAME, FileReciveXmlResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileReciveJsonResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.hwxt.com.cn/", name = "fileReciveJsonResponse")
    public JAXBElement<FileReciveJsonResponse> createFileReciveJsonResponse(FileReciveJsonResponse value) {
        return new JAXBElement<FileReciveJsonResponse>(_FileReciveJsonResponse_QNAME, FileReciveJsonResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataReciveJson }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.hwxt.com.cn/", name = "dataReciveJson")
    public JAXBElement<DataReciveJson> createDataReciveJson(DataReciveJson value) {
        return new JAXBElement<DataReciveJson>(_DataReciveJson_QNAME, DataReciveJson.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileReciveJson }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.hwxt.com.cn/", name = "fileReciveJson")
    public JAXBElement<FileReciveJson> createFileReciveJson(FileReciveJson value) {
        return new JAXBElement<FileReciveJson>(_FileReciveJson_QNAME, FileReciveJson.class, null, value);
    }

}
