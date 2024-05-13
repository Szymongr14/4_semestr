using System.Xml.Serialization;

namespace lab9;

[XmlRoot("engine")]
public class Engine(double displacement, int horsePower, string type, string model)
{
    public double Displacement { get; set; } = displacement;
    public int HorsePower { get; set; } = horsePower;
    public string Type { get; set; } = type;

    [XmlAttribute("model")]
    public string Model { get; set; } = model;
}