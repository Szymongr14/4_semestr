using System.Xml.Serialization;

namespace lab9;

[XmlRoot ("cars")]
public class Car(string model, Engine engine, int year)
{
    public string Model = model;
    public int Year = year;
    public Engine Engine = engine;
}