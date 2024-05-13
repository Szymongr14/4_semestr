using System.Xml.Serialization;

namespace lab9;

public static class Program
{
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
    }

    private static void SecondTask(IEnumerable<Car> cars)
    {
        var serializer = new XmlSerializer(typeof(List<Car>));
        using (var textWriter = new StringWriter())
        {
            serializer.Serialize(textWriter, cars);
            Console.WriteLine(textWriter.ToString());
        }

        // Deserializacja z XML
        var deserializer = new XmlSerializer(typeof(List<Car>));
        using (var reader = new StringReader())
        {
            var deserializedCars = (List<Car>)deserializer.Deserialize(reader)!;
            foreach (var car in deserializedCars)
            {
                Console.WriteLine($"Model: {car.Model}, Engine: {car.Engine.Model}, Year: {car.Year}");
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

