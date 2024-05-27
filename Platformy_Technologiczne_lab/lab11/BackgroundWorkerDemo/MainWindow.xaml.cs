using System.ComponentModel;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace BackgroundWorkerDemo;

/// <summary>
/// Interaction logic for MainWindow.xaml
/// </summary>
public partial class MainWindow : Window
{
    private readonly BackgroundWorker _worker = new BackgroundWorker();

    public MainWindow()
    {
        InitializeComponent();

        _worker.WorkerReportsProgress = true;
        _worker.DoWork += Worker_DoWork;
        _worker.ProgressChanged += Worker_ProgressChanged;
        _worker.RunWorkerCompleted += Worker_RunWorkerCompleted;
    }

    private void CalculateFibonacci_Click(object sender, RoutedEventArgs e)
    {
        int number = int.Parse(InputTextBox.Text);
        _worker.RunWorkerAsync(number);
    }

    private static void Worker_DoWork(object? sender, DoWorkEventArgs e)
    {
        int number = (int)(e.Argument ?? throw new InvalidOperationException());
        e.Result = CalculateFibonacci(number, sender as BackgroundWorker ?? throw new InvalidOperationException());
    }

    private static long CalculateFibonacci(int number, BackgroundWorker worker)
    {
        long a = 0;
        long b = 1;

        for (int i = 0; i < number; i++)
        {
            long temp = a;
            a = b;
            b = temp + b;
            Thread.Sleep(5);
            worker.ReportProgress((i + 1) * 100 / number);
        }

        return a;
    }

    private void Worker_ProgressChanged(object? sender, ProgressChangedEventArgs e)
    {
        ProgressBar.Value = e.ProgressPercentage;
    }

    private void Worker_RunWorkerCompleted(object? sender, RunWorkerCompletedEventArgs e)
    {
        ResultTextBlock.Text = "Result: " + e.Result;
    }
}