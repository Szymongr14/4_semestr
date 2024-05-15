using System.Xml.Linq;
using System.Xml.Serialization;
using System.Xml.XPath;

namespace lab9;

public static class Program
{
    private const string XmlFilePath = @"Cars.xml";
    private const string LinqToXmlFilePath = @"CarsFromLinq.xml";
    private const string PathToTemplate = @"template.html";
    private const string PathToHtmlResultFile = @"CarsTable.html";
    public static void Main()
    {
        var myCars = new List<Car>(){
            new Car("E250", new Engine(1.8, 204, "CGI"), 2009),
            new Car("E350", new Engine(3.5, 292, "CGI"), 2009),
            new Car("A6", new Engine(2.5, 187, "FSI"), 2012),
            new Car("A6", new Engine(2.8, 220, "FSI"), 2012),
            new Car("A6", new Engine(3.0, 295, "TFSI"), 2012),
            new Car("A6", new Engine(2.0, 175, "TDI"), 2011),
            new Car("A6", new Engine(3.0, 309, "TDI"), 2011),
            new Car("S6", new Engine(4.0, 414, "TFSI"), 2012),
            new Car("S8", new Engine(4.0, 513, "TFSI"), 2012)
        };

        FirstTask(myCars);
        SecondTask(myCars);
        ThirdTask();
        CreateXmlFromLinq(myCars);
        CreateXhtmlTableFromLinq(myCars);
        ModifyXml();
    }

    private static void ModifyXml()
    {
        XDocument doc = XDocument.Load(XmlFilePath);

        foreach (XElement hpElement in doc.Descendants("HorsePower"))
        {
            hpElement.Name = "hp";
        }

        foreach (XElement carElement in doc.Descendants("car"))
        {
            XElement? yearElement = carElement.Element("Year");
            XElement? modelElement = carElement.Element("Model");

            if (yearElement != null && modelElement != null)
            {
                modelElement.Add(new XAttribute("year", yearElement.Value));
                yearElement.Remove();
            }
        }
        
        doc.Save(XmlFilePath);
    }
    
    private static void CreateXhtmlTableFromLinq(List<Car> myCars)
    {
        XDocument xhtmlDoc = XDocument.Load(PathToTemplate);
        XElement table = new XElement(XName.Get("table", "http://www.w3.org/1999/xhtml"));
        foreach (var car in myCars)
        {
            XElement row = new XElement(XName.Get("tr", "http://www.w3.org/1999/xhtml"),
                new XElement(XName.Get("td", "http://www.w3.org/1999/xhtml"), car.Model),
                new XElement(XName.Get("td", "http://www.w3.org/1999/xhtml"), car.Year),
                new XElement(XName.Get("td", "http://www.w3.org/1999/xhtml"), car.Engine.Type),
                new XElement(XName.Get("td", "http://www.w3.org/1999/xhtml"), car.Engine.Displacement),
                new XElement(XName.Get("td", "http://www.w3.org/1999/xhtml"), car.Engine.HorsePower)
            );
            table.Add(row);
        }

        // Find the body element and add the table to it
        XElement? body = xhtmlDoc.Root!.Element(XName.Get("body", "http://www.w3.org/1999/xhtml"));
        body?.Add(table);

        // Save the modified XHTML document
        xhtmlDoc.Save(PathToHtmlResultFile);
    }
    
    private static void CreateXmlFromLinq(IEnumerable<Car> myCars)
    {
        IEnumerable<XElement> nodes = myCars.Select(car => new XElement("car",
            new XElement("Model", car.Model),
            new XElement("Year", car.Year),
            new XElement("motor", new XAttribute("model", car.Engine.Type),
                new XElement("Displacement", car.Engine.Displacement),
                new XElement("HorsePower", car.Engine.HorsePower)
            )
        ));
        XElement rootNode = new XElement("cars", nodes);
        rootNode.Save(LinqToXmlFilePath);
    }

    private static void ThirdTask()
    {
        var xmlFile = XDocument.Load(XmlFilePath);
        
        const string xPathQuery1 = "//motor[@model!='TDI']";
        IEnumerable<XElement> hpValues = xmlFile.XPathSelectElements(xPathQuery1);
        double avgHp = hpValues.Average(x => double.Parse(x.Value));
        // Console.WriteLine($"AvgHP for not TDI engines = {avgHp}");
        
        const string xPathQuery2 = "//car/Model";
        IEnumerable<XElement> modelNodes = xmlFile.XPathSelectElements(xPathQuery2);
        IEnumerable<string> distinctModels = modelNodes.Select(x => x.Value).Distinct();
        // Console.Write("Unique models: ");
        // foreach (var model in distinctModels)
        // {
        //     Console.Write(model);
        //     Console.Write(", ");
        // }
    }

    private static void SecondTask(IEnumerable<Car> cars)
    {
        // Serialization
        var xmlRootAttribute = new XmlRootAttribute("Cars");
        var serializer = new XmlSerializer(typeof(List<Car>), xmlRootAttribute);
        
        using (var streamWriter = new StreamWriter(XmlFilePath))
        {
            serializer.Serialize(streamWriter, cars);
        }
        
        // Console.WriteLine(textWriter.ToString());
        
        // Deserialization
        using (var streamReader = new StreamReader(XmlFilePath))
        {
            List<Car> deserializedCars = (List<Car>)serializer.Deserialize(streamReader)!;
            
            foreach (var car in deserializedCars)
            {
                // Console.WriteLine(car);
            }
        }
    }

    private static void FirstTask(IEnumerable<Car> cars)
    {
        var audiA6Cars = cars.Where(car => car.Model == "A6")
            .Select(car => new
            {
                engineType = car.Engine.Type == "TDI" ? "Diesel" : "Petrol",
                hppl = car.Engine.HorsePower / car.Engine.Displacement
            });

        var groupedByEngineType = audiA6Cars.GroupBy(car => car.engineType)
            .Select(group => new
            {
                engineType = group.Key, averageHppl = group.Average(car => car.hppl) 
                
            });

        foreach (var group in groupedByEngineType)
        {
            Console.WriteLine($"{group.engineType}: {group.averageHppl}");
        }
    }
    
}

