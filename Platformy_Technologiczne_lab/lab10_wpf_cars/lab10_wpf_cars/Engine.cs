using System.Xml.Serialization;

namespace lab10_wpf_cars;

[Serializable]
public class Engine
{
    public double Displacement { get; set; }
    public int HorsePower { get; set; }
    [XmlAttribute("model")]
    public string Type { get; set; }
    
    public Engine(double displacement, int horsePower, string type)
    {
        Displacement = displacement;
        HorsePower = horsePower;
        Type = type;
    }
    
    public Engine() 
    {}

    public override string ToString()
    {
        return $"Displacement: {Displacement}, HorsePower: {HorsePower}, Type: {Type}";
    }


}