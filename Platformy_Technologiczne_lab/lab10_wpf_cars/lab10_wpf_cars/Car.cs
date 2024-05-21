using System.Xml.Serialization;

namespace lab10_wpf_cars;

[Serializable]
[XmlType("car")]
public class Car
{
    public string Model { get; set; }
    public int Year { get; set; }
    [XmlElement("motor")]
    public Engine Engine { get; set; }
    
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