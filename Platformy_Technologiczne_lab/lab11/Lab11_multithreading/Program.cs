namespace Lab11_multithreading;

internal static class Program
{
    public static void Main()
    {
        NewtonTask(5,4);
        NewtonAsync(5, 3);
        
    }

    private static async Task NewtonAsync(int n, int k)
    {
        Task<long> taskDenominator = Task.Run(() => NewtonCountDenominator(k));
        long numerator = NewtonCountNumerator(n, k);
        long denominator = await taskDenominator;
        Console.WriteLine($"Task with Async-Await: For N={n}, K={k} result is {numerator/denominator}");
    }
    

    private static long NewtonCountNumerator(int n, int k)
    {
        long numerator = 1;
        for (var i = n-k+1; i<=n; i++)
        {
            numerator *= i;
        }
        return numerator;
    }

    private static long NewtonCountDenominator(int k)
    {
        long denominator = 1;
        for (var i = 1; i <= k; i++)
        {
            denominator *= i;
        }
        return denominator;
    }

    private static void NewtonTask(int n, int k)
    {
        Task<long> taskNumerator = Task.Run(() => NewtonCountNumerator(n, k));
        Task<long> taskDenominator = Task.Run(() => NewtonCountDenominator(k));
        Task.WaitAll(taskDenominator, taskDenominator);
        long result = taskNumerator.Result / taskDenominator.Result;
        Console.WriteLine($"Task: For N={n}, K={k} result is {result}");
    }
}