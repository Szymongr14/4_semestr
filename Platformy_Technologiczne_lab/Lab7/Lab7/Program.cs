using System.ComponentModel.DataAnnotations;
using System.Net.Mail;
using Microsoft.VisualBasic;
using static System.String;
using System.Runtime.Serialization.Formatters.Binary;

namespace Lab7
{
    class Program
    {
        private static int depth = 0;
        public static void Main(string[] args)
        {
            var path = args[0];
            var dirs = Directory.GetFiles(path);
            
            RecursiveSearch(path);
            depth = 0;
            
            Console.WriteLine(new DirectoryInfo(path).GetOldestElementDate());

            var mappedFiles = CreateCollection(path);
            SerializeCollection(mappedFiles, "mappedFiles.bin");
            var deserializedMappedFiles = DeserializeCollection<SortedDictionary<string, long>>("mappedFiles.bin");

            foreach (var key in deserializedMappedFiles)
            {
                Console.WriteLine($"{key.Key} -> {key.Value}");
            }
        }

        private static void RecursiveSearch(string currentDir)
        {
            foreach (var directory in Directory.GetDirectories(currentDir))
            {
                for (var i = 0; i < depth * 4; i++)
                {
                    Console.Write(" ");
                }
                
                var files = Directory.GetFiles(directory);
                    // .Select(Path.GetFileName).ToArray();
                var directories = Directory.GetDirectories(directory);
                
                var dirRahs = new FileInfo(directory).GetRahs();
                var dir = new DirectoryInfo(directory);
                var numberOfElements = files.Length + directories.Length;
                
                Console.Write(dir.Name + " ");
                Console.Write("(" + numberOfElements+ ") ");
                Console.WriteLine(dirRahs);
                
                depth++;
                foreach (var file in files)
                {
                    for (var i = 0; i < depth * 4; i++)
                    {
                        Console.Write(" ");
                    }
                    var fileInfo = new FileInfo(file!);
                    var fileInfoName = fileInfo.Name;
                    var fileRahs = fileInfo.GetRahs();

                    Console.Write(fileInfoName + " ");
                    Console.Write(fileInfo.Length + " bytes ");
                    Console.WriteLine(fileRahs);
                }
                RecursiveSearch(directory);
                depth--;
            }
        }
        
        [Obsolete("Obsolete")]
        private static void SerializeCollection<T>(T collection, string filePath)
        {
            using (FileStream fs = new FileStream(filePath, FileMode.Create))
            {
                BinaryFormatter formatter = new BinaryFormatter();
                if (collection != null) formatter.Serialize(fs, collection);
            }
        }
        
        [Obsolete("Obsolete")]
        static T DeserializeCollection<T>(string filePath)
        {
            using (FileStream fs = new FileStream(filePath, FileMode.Open))
            {
                BinaryFormatter formatter = new BinaryFormatter();
                return (T)formatter.Deserialize(fs);
            }
        }

        private static SortedDictionary<string, long> CreateCollection(string dir)
        {
            var dirs = Directory.GetDirectories(dir);
            var files = Directory.GetFiles(dir);

            var mappedElements = new SortedDictionary<string, long>(new StringLengthComparator());

            foreach (var file in files)
            {
                var fileInfo = new FileInfo(file);
                mappedElements.Add(fileInfo.Name, fileInfo.Length);
            }

            foreach (var dirElement in dirs)
            {
                var childFiles = Directory.GetFiles(dirElement);
                var childDirectories = Directory.GetDirectories(dirElement);
                
                var directoryInfo = new DirectoryInfo(dirElement);
                mappedElements.Add(directoryInfo.Name, childDirectories.Length + childFiles.Length);
            }
            return mappedElements;
        }
    }
    
    [Serializable]
    public class StringLengthComparator : IComparer<string>
    {
        public int Compare(string? x, string? y)
        {
            if (x == null || y == null) return 0;
            var lengthComparison = x.Length.CompareTo(y.Length);
            return lengthComparison != 0 ? lengthComparison : string.Compare(x, y, StringComparison.Ordinal);
        }
    }
}
