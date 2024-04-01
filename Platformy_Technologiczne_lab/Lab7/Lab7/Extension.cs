namespace Lab7
{
    public static class MyExtensions
    {
        private static DateTime oldestDate=DateTime.MaxValue;

        private static void GetOldestElementDateBody(this DirectoryInfo directoryInfo)
        {
            foreach (var d in directoryInfo.GetDirectories())
            {
                foreach (var f in directoryInfo.GetFiles())
                {
                    if (f.CreationTime < oldestDate)
                    {
                        oldestDate = f.CreationTime;
                    }
                }
                GetOldestElementDateBody(d);
            }
        }

        public static DateTime GetOldestElementDate(this DirectoryInfo directoryInfo)
        {
            GetOldestElementDateBody(directoryInfo);
            return oldestDate;
        }

        public static string GetRahs(this FileInfo fileInfo)
        {
            var rahsString = "";
            if (fileInfo.IsReadOnly)
            {
                rahsString += 'r';
            }
            else rahsString += '_';
            if (fileInfo.Attributes.HasFlag(FileAttributes.Archive))
            {
                rahsString += 'a';
            }
            else rahsString += '_';
            if (fileInfo.Attributes.HasFlag(FileAttributes.Hidden))
            {
                rahsString += 'h';
            }
            else rahsString += '_';
            if (fileInfo.Attributes.HasFlag(FileAttributes.System))
            {
                rahsString += 's';
            }
            else rahsString += '_';


            return rahsString;
        }
    }
}