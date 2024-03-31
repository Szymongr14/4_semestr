using System.Net.Mail;

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
            var dirInfo = new DirectoryInfo(path);
            var oldestDate = dirInfo.GetOldestElementDate();
            Console.WriteLine(oldestDate);

        }

        private static void RecursiveSearch(string currentDir)
        {
            foreach (var directory in Directory.GetDirectories(currentDir))
            {
                for (var i = 0; i < depth * 4; i++)
                {
                    Console.Write(" ");
                }
                var dirInfo = new FileInfo(directory!);
                var dirRahs = dirInfo.GetRAHS();
                var dir = new DirectoryInfo(directory);
                Console.Write(dir.Name + " ");
                Console.WriteLine(dirRahs);
                depth++;
                var files = Directory.GetFiles(directory);
                    // .Select(Path.GetFileName).ToArray();
                foreach (var file in files)
                {
                    for (var i = 0; i < depth * 4; i++)
                    {
                        Console.Write(" ");
                    }
                    var fileInfo = new FileInfo(file!);
                    var fileInfoName = fileInfo.Name;
                    var rahs = fileInfo.GetRAHS();

                    Console.Write(fileInfoName + " ");
                    Console.WriteLine(rahs);
                }
                RecursiveSearch(directory);
                depth--;
            }
        }
    }
}
