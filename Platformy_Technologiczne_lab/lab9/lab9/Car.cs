using System.Xml.Serialization;

namespace lab9;

[Serializable]
[XmlType("car")]
public class Car
{
    public string Model;
    public int Year;
    [XmlElement("motor")]
    public Engine Engine;
    
    public Car(string model, Engine engine, int year)
    {
        Model = model;
        Engine = engine;
        Year = year;
    }
    
    public Car() 
    {}

    public override string ToString()
    {
        return $"Model: {Model}, Year: {Year}, Engine: {Engine}";
    }
}