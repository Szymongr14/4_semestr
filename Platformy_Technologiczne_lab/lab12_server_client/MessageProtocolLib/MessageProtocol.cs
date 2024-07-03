using System.Text.Json;

namespace MessageProtocolLib;

[Serializable]
public class MessageProtocol(float x, float y, float z)
{
    public float X { get; set; } = x;
    public float Y { get; set; } = y;
    public float Z { get; set; } = z;

    public string Serialize()
    {
        return JsonSerializer.Serialize(this);
    }

    public static MessageProtocol? Deserialize(string json)
    {
        return JsonSerializer.Deserialize<MessageProtocol>(json);
    }

    public override string ToString()
    {
        return $"X: {X}, Y: {Y}, Z: {Z}";
    }
}