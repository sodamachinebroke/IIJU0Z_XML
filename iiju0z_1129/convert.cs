
using System;
using System.Xml;
using System.Xml.XPath;
using System.Xml.Xsl;

class Program
{
    static void Main()
    {
        string myXmlFile = "hallgatoiiju0z.xml";
        string myStyleSheet = "hallgatoiiju0z.xsl";
        string outputXmlFile = "hallgatoiiju0z.out.xml";

        try
        {
            // Load XML document
            XPathDocument myXPathDoc = new XPathDocument(myXmlFile);

            // Load XSLT stylesheet
            XslCompiledTransform myXslTrans = new XslCompiledTransform();
            myXslTrans.Load(myStyleSheet);

            // Create XmlTextWriter for writing the result to a file
            using (XmlTextWriter myWriter = new XmlTextWriter(outputXmlFile, null))
            {
                // Perform XSLT transformation
                myXslTrans.Transform(myXPathDoc, null, myWriter);
            }

            Console.WriteLine($"Transformation successful. Output saved to {outputXmlFile}");
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Error: {ex.Message}");
        }
    }
}
